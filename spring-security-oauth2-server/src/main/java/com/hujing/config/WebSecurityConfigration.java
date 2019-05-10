package com.hujing.config;

import com.hujing.authentication.code.image.filter.ValidateCodeAuthenticationFilter;
import com.hujing.authentication.code.sms.bean.SmsAuthenticationSecurityConfig;
import com.hujing.authentication.handler.DefaultAuthenticationFailureHandler;
import com.hujing.authentication.handler.DefaultAuthenticationSuccessHandler;
import com.hujing.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author hj
 * @create 2019-04-26 22:05
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfigration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;
    @Autowired
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ValidateCodeAuthenticationFilter validateCodeAuthenticationFilter;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SmsAuthenticationSecurityConfig smsAuthenticationSecurityConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(validateCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) //将自己的校验filter加入到filter chain
                .formLogin()
                .loginPage(securityProperties.getAuth().getLoginPage()) //自定义登录
                .loginProcessingUrl(securityProperties.getAuth().getLoginProcessingUrl()) //定义登录的表单提交url,默认为/login
                .successHandler(defaultAuthenticationSuccessHandler)//登录成功处理器
                .failureHandler(defaultAuthenticationFailureHandler) //失败处理器

                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getAuth().getTokenValiditySeconds())
                .userDetailsService(userDetailsServiceImpl)


                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getAuth().getLoginPage(),
                        securityProperties.getAuth().getLoginProcessingUrl(),
                        securityProperties.getAuth().getMobileLoginUrl(),
                        securityProperties.getAuth().getCodeUrl()).permitAll()


                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .apply(smsAuthenticationSecurityConfig);
    }



    /**
     * 配置remember me 的持久化
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setCreateTableOnStartup(true); //首次启动创建表
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/check_token");
    }
}
