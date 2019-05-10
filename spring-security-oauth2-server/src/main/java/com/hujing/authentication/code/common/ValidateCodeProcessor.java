package com.hujing.authentication.code.common;

import com.hujing.authentication.code.common.enums.ValidateCodeType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * 2019-05-09 10:04
 */
public interface ValidateCodeProcessor {
    void createCode(ServletWebRequest request, String type);
    void validate(HttpServletRequest request, ValidateCodeType codeType) throws ServletRequestBindingException;
}
