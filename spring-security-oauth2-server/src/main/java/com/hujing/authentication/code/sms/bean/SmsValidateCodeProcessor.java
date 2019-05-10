package com.hujing.authentication.code.sms.bean;

import com.hujing.authentication.code.common.AbstractValidateCodeProcessor;
import com.hujing.authentication.code.common.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author hj
 * 2019-05-09 17:44
 */
@Slf4j
@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Override
    public void send(ServletWebRequest request, ValidateCode code) {
        log.info("【发送验证码成功：{}】", code.getCode());
    }
}
