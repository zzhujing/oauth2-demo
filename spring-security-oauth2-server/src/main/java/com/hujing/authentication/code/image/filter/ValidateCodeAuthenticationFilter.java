package com.hujing.authentication.code.image.filter;

import com.hujing.authentication.code.common.ValidateCodeType;
import com.hujing.authentication.code.common.utils.ValidateCodeGeneratorHolder;
import com.hujing.authentication.code.exception.ValidateCodeException;
import com.hujing.authentication.handler.DefaultAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author hj   2019-05-09 9:06
 * 扩展处理检验码的过滤器
 */
@Component
@Slf4j
public class ValidateCodeAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;
    @Autowired
    private ValidateCodeGeneratorHolder validateCodeGeneratorHolder;
    //储存验证码对应的验证码关系 k-> url ,v -> codeType
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws ServletException {
        urlMap.put("/auth/form", ValidateCodeType.IMAGE);
        urlMap.put("/auth/mobile", ValidateCodeType.SMS);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ValidateCodeType codeType = getCodeTypeByRequest(request);
        if (codeType != null) {
            try {
                log.info("【校验开始】 验证码类型为: {} ", codeType);
                validateCodeGeneratorHolder.getValidateCodeProcessor(codeType.toString().toLowerCase()).validate(request,codeType);
            } catch (ValidateCodeException e) {
                log.warn("【校验失败】", e.getMessage());
                defaultAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                //执行完错误之后退出不放行。
                return;
            }
        }
        //2.如果不是则直接放心交给spring security filter chain 去处理
        filterChain.doFilter(request, response);
    }

    /**
     * 根据请求获取验证码类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getCodeTypeByRequest(HttpServletRequest request) {
        Set<String> urls = urlMap.keySet();
        for (String url : urls) {
            if (url.equals(request.getRequestURI())) {
                return urlMap.get(url);
            }
        }
        return null;
    }
}
