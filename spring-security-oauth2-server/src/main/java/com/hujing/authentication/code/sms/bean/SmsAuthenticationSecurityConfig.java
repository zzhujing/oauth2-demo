package com.hujing.authentication.code.sms.bean;

import com.hujing.authentication.code.sms.filter.SmsAuthenticationFilter;
import com.hujing.authentication.handler.DefaultAuthenticationFailureHandler;
import com.hujing.authentication.handler.DefaultAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * 2019-05-09 13:39
 */
@Component
public class SmsAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SmsAuthenticationProvider smsAuthenticationProvider;
    @Autowired
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsAuthenticationFilter filter = new SmsAuthenticationFilter();
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(defaultAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);
        builder.authenticationProvider(smsAuthenticationProvider)
                //将短信拦截器加入到filter chain 中
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
