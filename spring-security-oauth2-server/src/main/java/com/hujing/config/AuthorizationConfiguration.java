package com.hujing.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * @author hj
 * @create 2019-04-26 21:09
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {


    private final DataSource dataSource;

    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final UserDetailsService userDetailsServiceImpl;

    private final TokenEnhancer jwtTokenEnhancer;

    private final AuthenticationManager authenticationManager;

    public AuthorizationConfiguration(DataSource dataSource, TokenStore tokenStore,
                                      UserDetailsService userDetailsServiceImpl,
                                      JwtAccessTokenConverter jwtAccessTokenConverter,
                                      TokenEnhancer jwtTokenEnhancer,
                                      AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.dataSource = dataSource;
        this.tokenStore = tokenStore;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
    }

    /**
     * 持久化client信息
     * @return
     */
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        //可以使用JdbcClientDetailsService进行ClientDetails的管理。和设置超时时间等。
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * token端点配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl);

        //配置jwtToken增强器
        if (jwtTokenEnhancer != null && jwtAccessTokenConverter != null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            ArrayList<TokenEnhancer> tokenEnhancers = Lists.newArrayList();
            tokenEnhancers.add(jwtTokenEnhancer);
            tokenEnhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
            endpoints.tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);

        }
    }
}
