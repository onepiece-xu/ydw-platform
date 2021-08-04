package com.ydw.gateway.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public abstract class AbstractExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    protected String formatMessage(Throwable ex) {
        String errorMessage = null;
        if (ex instanceof NotFoundException) {
            String reason = ((NotFoundException) ex).getReason();
            errorMessage = reason;
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            errorMessage = responseStatusException.getMessage();
        } else {
            errorMessage = ex.getMessage();
        }
        return errorMessage;
    }

    protected Map<String, Object> buildErrorMap(String errorMessage) {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", HttpStatus.NOT_FOUND.value());
        resMap.put("msg", errorMessage);
        resMap.put("data", null);
        return resMap;
    }

}