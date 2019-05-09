package com.hujing.properties;

import lombok.Data;

/**
 * @author hj
 * @create 2019-05-02 10:21
 */
@Data
public class ValidateCodeProperties {
    private String sessionKeySuffix = "_code_session_key";
    private ImageCodeProperties image = new ImageCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
}
