package com.hujing.authentication.code.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author hj
 * 2019-05-10 14:21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LoginType {
    SMS(1),
    IMAGE(2),;
    private int type;

    public static LoginType findByType(int type) {
        for (LoginType value : values()) {
            if (type == value.getType()) {
                return value;
            }
        }
        return null;
    }
}
