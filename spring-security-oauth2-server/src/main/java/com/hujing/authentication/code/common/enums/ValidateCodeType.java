package com.hujing.authentication.code.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * 2019-05-09 9:36
    验证码枚举
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
