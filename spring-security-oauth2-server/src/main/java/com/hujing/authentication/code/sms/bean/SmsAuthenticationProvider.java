package com.hujing.authentication.code.sms.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author hj
 * 2019-05-09 13:14
    短信provider,处理token获取userDetails
 */
@Data
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    /**
     * 使用针对短信验证的userDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //使用authentication对象获取userDetails对象，并且返回

        SmsAuthenticationToken sms = (SmsAuthenticationToken) authentication;

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(((String) sms.getPrincipal()));

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsAuthenticationToken token = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());

        token.setDetails(sms.getDetails());

        return token;
    }

    /**
     * 该provider支持的token类型
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
