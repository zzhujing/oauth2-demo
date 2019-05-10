package com.hujing.authentication.code.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hj
 * 2019-05-09 9:41
 * 控制验证码执行器
 */
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor getValidateCodeProcessor(String type) {
        String beanName = type + "ValidateCodeProcessor";
        return validateCodeProcessorMap.get(beanName);
    }
}
