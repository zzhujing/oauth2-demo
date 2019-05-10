package com.hujing.properties;

import lombok.Data;

/**
 * @author hj
 * 2019-05-10 9:22
 * 自定义token策略
 */
@Data
public class TokenProperties {

    private String signKey = "tokenKey";
    private String tokenStoreType;
    private boolean extend;
}
