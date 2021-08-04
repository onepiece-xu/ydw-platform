package com.ydw.gateway.config;

import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExceptionHandler {

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