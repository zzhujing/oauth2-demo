package com.hujing.authentication.code.common;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hj
 *
 * 2019-05-01 22:30
 *  校验码基类
 */
@Data
@Builder
@NoArgsConstructor
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 8210774563804307861L;
    private String code;
    private LocalDateTime expireTime;


    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 传入过期的秒数构造过期时间
     *
     * @param code 校验码
     * @param expire 过期时间，单位s
     */
    public ValidateCode(String code, int expire) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    /**
     * 判断是否过期
     *
     * @return 是否过期
     */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}