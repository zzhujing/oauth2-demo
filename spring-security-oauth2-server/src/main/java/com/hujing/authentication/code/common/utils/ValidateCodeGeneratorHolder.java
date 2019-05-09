package com.hujing.authentication.code.common.utils;

import com.hujing.authentication.code.common.processor.ValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author hj
 * 2019-05-09 9:41
 */
@Component
public class ValidateCodeGeneratorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public ValidateCodeProcessor getValidateCodeProcessor(String type) {
        String beanName = type + "ValidateCodeProcessor";
        return validateCodeProcessorMap.get(beanName);
    }
}
