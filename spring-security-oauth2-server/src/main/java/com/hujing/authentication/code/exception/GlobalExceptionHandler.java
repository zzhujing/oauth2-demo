package com.hujing.authentication.code.exception;

import com.hujing.controller.response.SimpleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hj
 * 2019-05-09 10:44
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidateCodeException.class)
    @ResponseBody
    public SimpleResult handler(ValidateCodeException e) {
        log.error("【接收到异常】: {}",e.getMessage());
        return SimpleResult.builder().content(e.getMessage()).build();
    }

    @ExceptionHandler(CustomerSecurityExcpetion.class)
    @ResponseBody
    public SimpleResult handler(CustomerSecurityExcpetion e) {
        log.error("【接收到异常】: {}",e.getMessage());
        return SimpleResult.builder().content(e.getMessage()).build();
    }
}
