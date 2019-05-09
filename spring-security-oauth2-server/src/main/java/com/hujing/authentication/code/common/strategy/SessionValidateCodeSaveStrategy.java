package com.hujing.authentication.code.common.strategy;

import com.hujing.authentication.code.common.ValidateCode;
import com.hujing.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * 2019-05-09 16:34
    基于session实现的验证储存策略
 */
public class SessionValidateCodeSaveStrategy implements ValidateCodeSaveStrategy {

    @Autowired(required = false)
    private SecurityProperties securityProperties;

    @Override
    public void save(HttpServletRequest request, ValidateCode code, String type) {
        request.getSession().setAttribute(type+securityProperties.getCode().getSessionKeySuffix(),code);
    }

    @Override
    public ValidateCode get(HttpServletRequest request, String type) {
        return (ValidateCode) request.getSession().getAttribute(type+securityProperties.getCode().getSessionKeySuffix());
    }

    @Override
    public void remove(HttpServletRequest request, String type) {
        request.getSession().removeAttribute(type+securityProperties.getCode().getSessionKeySuffix());
    }
}
