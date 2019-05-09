package com.hujing.properties;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author hj
 * @create 2019-05-02 13:35
 */
@Data
public class SmsCodeProperties {
    private int expireIn = 60; //默认60S
    private int length = 6; //默认6位
    private Set<String> interceptPathList = new HashSet<>();
}
