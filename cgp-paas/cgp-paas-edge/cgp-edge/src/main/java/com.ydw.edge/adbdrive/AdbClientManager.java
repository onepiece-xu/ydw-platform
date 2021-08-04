package com.ydw.edge.adbdrive;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdbClientManager {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * ip:port:adbClient
	 */
	private static ConcurrentHashMap<String, IAdbClient> adbClientMap = new ConcurrentHashMap<>();

	public static IAdbClient getAdbClient(String ip, int port, String model) {
		String key = ip + ":" + port;
		IAdbClient client = adbClientMap.get(key);
		if (client == null) {
			switch (model) {
			case AdbConstant.ARM_RK3399:
				client = new RK3399AdbClient(ip, port);
				break;
			case AdbConstant.ARM_MKT:
				client = new MKTAdbClient(ip, port);
				break;
			default:
				client = new RK3399AdbClient(ip, port);
			}
			adbClientMap.putIfAbsent(key, client);
			client = adbClientMap.get(key);
		}
		AdbInvocationHandler handler = new AdbInvocationHandler(client);
		return handler.proxy();
	}
	
	private static class AdbInvocationHandler implements InvocationHandler{
		
		Logger logger = LoggerFactory.getLogger(getClass());
		
		//维护一个目标对象
	    private IAdbClient target;
	    
	    public AdbInvocationHandler(IAdbClient target) {
			this.target = target;
		}

		public IAdbClient proxy(){
			Object newProxyInstance = Proxy.newProxyInstance(target.getClass().getClassLoader(), 
					target.getClass().getInterfaces(), this);
			return (IAdbClient)newProxyInstance;
		}
	    
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object returnValue = null;
			if (Object.class.equals(method.getDeclaringClass())){
				returnValue = method.invoke(target, args);
			}else{
				boolean locked = false;
				try {
					if(locked = target.lock()){
						//连接
						if(target.connect()){
							//执行目标对象方法
					        returnValue = method.invoke(target, args);
					        //断开连接
					        target.disconnect();
						}else{
							logger.error("ARM板【{}:{}】执行方法【{}】时,adb无法连接！", target.getIp(), target.getPort() , method.getName());
						}
					}
				} finally {
					if(locked){
						target.unLock();
					}
				}
			}
	        return returnValue;
		}
	}
	
}
