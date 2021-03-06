package com.hujing.config;

import com.hujing.authentication.code.common.strategy.SessionValidateCodeSaveStrategy;
import com.hujing.authentication.code.common.strategy.ValidateCodeSaveStrategy;
import com.hujing.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

import javax.sql.DataSource;

/**
 * @author hj   2019-05-09 8:58
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCommonBeanConfig {

    @Autowired
    private DataSource dataSource;
    /**
     * 加密解密器
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 路径匹配器
     * @return AntPathMatcher
     */
    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }


    /**
     * ioc 容器中没有验证码储存策略的时候该bean生效
     * @return  ValidateCodeSaveStrategy
     */
    @Bean
    @ConditionalOnMissingBean(ValidateCodeSaveStrategy.class)
    public ValidateCodeSaveStrategy validateCodeSaveStrategy() {
        return new SessionValidateCodeSaveStrategy();
    }
}
