package com.hujing.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hj
 * @create 2019-05-08 22:24
    可以针对不同的请求方式来对需要认证的url进行不同的处理
 */
@Controller
@Slf4j
public class TransferController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/auth/transfer")
    public void transfer(HttpServletRequest request, HttpServletResponse response) {
        log.info("【进入登录中转controller】");
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            try {
                log.info("【pre request】: {} ",savedRequest.getRedirectUrl());
                response.sendRedirect("/default-login.html");
            } catch (IOException e) {
                log.error("【重定向到登录页面失败】");
            }
        }
    }

}
