package com.hujing.properties;

import lombok.Data;

/**
 * @author hj   2019-05-09 8:54
 */
@Data
public class AuthenticationProperties {

    //跳转到登录页url
    private String loginPage = "/default-login.html";

    //登录表单提交url
    private String loginProcessingUrl = "/auth/form";

    //验证码地址
    private String codeUrl = "/code/*";

    //手机验证码登录url
    private String mobileLoginUrl = "/auth/mobile";

    //remember me过期时间 默认为7天
    private int tokenValiditySeconds = 7 * 24 * 60 * 60;

    //表单登录隐藏域的实行名称
    private String loginTypeParamName = "type";
}
