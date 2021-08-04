package com.ydw.gateway.filter;
//自定义全局过滤器需要实现GlobalFilter和Ordered接口

import com.alibaba.fastjson.JSON;
import com.ydw.gateway.config.CgpPropsConfig;
import com.ydw.gateway.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CgpPropsConfig mypropsConfig;

	@Autowired
	private AuthenticationService authenticationService;
	
	// 完成判断逻辑
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String currentUrl = exchange.getRequest().getURI().getPath();
		logger.info("当前请求url:{}",currentUrl);
		if (!isOpenApi(currentUrl)) {
			String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
			if(StringUtils.isBlank(token) || !doAuthCheck(token)){
				logger.info("当前请求url:{}验证token:{}失败！",currentUrl,token);
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
		}
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
	
	private boolean doAuthCheck(String token){
		String authentic = authenticationService.authentic(token);
		logger.info(authentic);
		return HttpStatus.OK.value() == JSON.parseObject(authentic).getIntValue("code");
	}
}