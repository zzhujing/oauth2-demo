package com.hujing.controller;

import com.hujing.authentication.code.common.utils.ValidateCodeGeneratorHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hj
 * 2019-05-09 9:30
    验证码controlle
 */
@Controller
@Slf4j
public class ValidateCodeController {

    @Autowired
    private ValidateCodeGeneratorHolder validateCodeGeneratorHolder;


    /**
     * 生成并返回validateCode
     * @param type
     * @param request
     * @param response
     */
    @GetMapping("/code/{type}")
    public void getCode(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        //针对不同的请求使用不同的验证码处理器来生成并返回validateCode
        validateCodeGeneratorHolder.getValidateCodeProcessor(type).createCode(new ServletWebRequest(request,response),type);
    }
}
