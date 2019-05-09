package com.hujing.authentication.code.sms.bean;

import com.hujing.authentication.code.common.ValidateCode;
import com.hujing.authentication.code.common.ValidateCodeGenerator;
import com.hujing.properties.SecurityProperties;
import com.hujing.properties.SmsCodeProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * 2019-05-09 14:54
 */
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(HttpServletRequest request) {
        SmsCodeProperties sms = securityProperties.getCode().getSms();
        String result = RandomStringUtils.randomNumeric(sms.getLength());
        return new ValidateCode(result,sms.getExpireIn());
    }
}
