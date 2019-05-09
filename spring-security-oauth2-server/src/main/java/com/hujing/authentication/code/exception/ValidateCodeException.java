package com.hujing.authentication.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author hj
 * 2019-05-09 10:43
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }
}
