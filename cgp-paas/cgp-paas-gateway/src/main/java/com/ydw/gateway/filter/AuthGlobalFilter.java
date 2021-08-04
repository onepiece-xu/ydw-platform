package com.ydw.gateway.filter;
//自定义全局过滤器需要实现GlobalFilter和Ordered接口

import com.alibaba.fastjson.JSON;
import com.ydw.gateway.config.CgpPropsConfig;
import com.ydw.gateway.service.IYdwAuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CgpPropsConfig mypropsConfig;
	
	@Autowired
	private IYdwAuthenticationService ydwAuthenticationServiceImpl;
	
	// 完成判断逻辑
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String currentUrl = request.getURI().getPath();
		logger.info("当前请求{}url:{}",request.getId(),currentUrl);
		if (!isOpenApi(currentUrl)) {
			String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			Map<String, Object> map = doAuthCheck(token);
			if(map == null){
				logger.info("当前请求url:{}验证token:{}失败！",currentUrl,token);
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}else{
                ServerHttpRequest.Builder mutate = request.mutate();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    mutate.header(entry.getKey(), (String)entry.getValue());
				}
			}
		}
		logger.info("当前请求{}url:{}继续走下层业务！",request.getId(),currentUrl);
		// 调用chain.filter继续向下游执行
		return chain.filter(exchange);
	}

	// 顺序,数值越小,优先级越高
	@Override
	public int getOrder() {
		return 0;
	}
	
	private boolean isOpenApi(String currentUrl){
		PathMatcher p = new AntPathMatcher();
		Set<String> urls = mypropsConfig.getOpenUrls();
		boolean flag = false;
		for(String url : urls){
			if(flag = p.match(url, currentUrl)){
				break;
			}
		}
		return flag;
	}
	
	private Map<String, Object> doAuthCheck(String token){
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(token)){
			String authentic = ydwAuthenticationServiceImpl.authentic(token);
			map = JSON.parseObject(authentic).getJSONObject("data");
		}
		return map;
	}
}