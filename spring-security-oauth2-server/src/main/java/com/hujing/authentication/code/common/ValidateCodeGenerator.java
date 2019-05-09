package com.hujing.authentication.code.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj 2019-05-02 11:02
 * 生成验证码的接口
 */
public interface ValidateCodeGenerator {
    ValidateCode generator(HttpServletRequest request);
}
