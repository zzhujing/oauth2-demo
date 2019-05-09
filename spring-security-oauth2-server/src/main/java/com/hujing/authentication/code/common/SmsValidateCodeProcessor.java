package com.hujing.authentication.code.common;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * 2019-05-09 17:44
 */
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Override
    public void send(ServletWebRequest request, ValidateCode code) {

    }
}
