package com.hujing.springsecurityoauth2resource.authorize;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author hj
 * @create 2019-05-08 20:27
 */
@Component
@Slf4j
public class AuthorizationProcessor {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean checkPermission(HttpServletRequest request, Authentication authentication) {

        log.info("【进入鉴权方法，其中Authentication对象为  : {}】", authentication);
        boolean hasPermission = false;
        //获取所有的权限url
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (CollectionUtils.isNotEmpty(authorities)) {
            for (GrantedAuthority authority : authorities) {
                if (antPathMatcher.match(authority.getAuthority(), request.getRequestURI())) {
                    hasPermission = true;
                }
            }
        }
        return hasPermission;
    }

    /**
     * spEl权限表达式解析。
     * @param applicationContext
     * @return
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler = new OAuth2WebSecurityExpressionHandler();
        oAuth2WebSecurityExpressionHandler.setApplicationContext(applicationContext);
        return oAuth2WebSecurityExpressionHandler;
    }
}
