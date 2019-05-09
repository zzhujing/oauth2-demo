package com.hujing.authentication.code.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * 2019-05-09 9:36
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ValidateCodeType {
    SMS("smsCode"),
    IMAGE("imageCode"),
    ;
    private String formName;
}
