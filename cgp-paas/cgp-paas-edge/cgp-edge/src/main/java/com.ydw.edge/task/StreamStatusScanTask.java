//package com.ydw.edge.task;
//
//import com.ydw.edge.model.constants.Constant;
//import com.ydw.edge.service.IDeviceStatusService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.nio.ByteBuffer;
//import java.util.*;
//import java.util.concurrent.*;
//
//@Component
//public class StreamStatusScanTask implements StartTask{
//
//	Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Value("${stream.scanPeriod}")
//	private Long scanPeriod;
//
//    @Value("${stream.scanOfflineNumJudge}")
//    private Long scanOfflineNumJudge;
//
//    @Autowired
//    private IDeviceStatusService deviceStatusService;
//
//    private Map<String, String> checkStreamMap = new ConcurrentHashMap<>();
//
//    private Map<String, Integer> offlineStreamMap = new ConcurrentHashMap<>();
//
//    @Autowired
//    ThreadPool threadPool;
//
//	@Override
//	public void run() {
//	    new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                for (Map.Entry<String, String> entry : checkStreamMap.entrySet()) {
//                    threadPool.submit(() -> doCheck(entry.getKey(), entry.getValue()));
//                }
//            }
//        }, 10*1000, scanPeriod*1000);
//
//	}
//
//	public void add(String connectId, String ip, Integer port){
//        checkStreamMap.put(connectId, ip+":"+port);
//    }
//
//    public void remove(String connectId, String ip, Integer port){
//	    if (StringUtils.isBlank(connectId)){
//	        String ipPort = ip + ":" + port;
//            for (Map.Entry<String, String> entry : checkStreamMap.entrySet()) {
//                if (entry.getValue().equals(ipPort)){
//                    connectId = entry.getKey();
//                    break;
//                }
//            }
//        }
//        if (StringUtils.isNotBlank(connectId)){
//            remove(connectId);
//        }
//    }
//
//    private void remove(String connectId){
//        checkStreamMap.remove(connectId);
//        offlineStreamMap.remove(connectId);
//    }
//
//    public void doCheck(String connectId, String streamDetail){
//        if (null == checkStreamMap.remove(connectId)){
//            //已经被其他线程处理过
//            logger.info("发生异常时，此连接{}已经被其他线程处理过！",connectId);
//            return;
//        }
//        String[] split = streamDetail.split(":");
//        String ip = split[0];
//        int port = Integer.valueOf(split[1]);
//        Socket sock = new Socket();
//        try{
//            sock.connect(new InetSocketAddress(ip,port));
//            ByteBuffer byteBuffer = ByteBuffer.allocate(28);
//            byteBuffer.putShort((short)2);
//            byteBuffer.putShort((short)1);
//            byteBuffer.putInt(Constant.COMMAND_STREAM_STATUS);
//            byteBuffer.putInt(new Random().nextInt());
//            Long l = System.currentTimeMillis() / 1000;
//            byteBuffer.putInt(l.intValue());
//            byteBuffer.putInt(0);
//            byte[] array = byteBuffer.array();
//
//            OutputStream outputStream = sock.getOutputStream();
//            outputStream.write(array);
//            outputStream.flush();
//
//            InputStream inputStream = sock.getInputStream();
//            inputStream.read(array);
//
//            outputStream.close();
//            inputStream.close();
//
//            String result = new String(array, 20, 8, "gbk");
//            logger.info("检查{}:{}流服务返回结果：{}" ,ip, port, result);
//            split = result.split("\\|");
//            boolean flag = false;
//            for (String s : split){
//                if (flag){
//                    break;
//                }
//                if (s.indexOf(Constant.RETURN_PARAMETER) != -1){
//                    flag = s.split(":")[1].equals("0");
//                }
//            }
//            if (flag){
//                offlineStreamMap.remove(connectId);
//                //重新加入检查
//                checkStreamMap.put(connectId,streamDetail);
//            }else{
//                throw new RuntimeException();
//            }
//        }catch (Exception e){
//            Integer integer = offlineStreamMap.get(connectId);
//            if (integer == null){
//                integer = 0;
//            }
//            if (integer >= scanOfflineNumJudge){
//                logger.error("此连接{}，{}:{} 失联次数超过{}次", connectId, ip, port, integer);
//                deviceStatusService.abnormal("1", connectId);
//                offlineStreamMap.remove(connectId);
//            }else{
//                offlineStreamMap.put(connectId, ++integer);
//                logger.error("此连接{}，{}:{} 失联次数为{}次", connectId, ip, port, integer);
//                checkStreamMap.put(connectId,streamDetail);
//            }
//        }finally {
//            try {
//                sock.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}
