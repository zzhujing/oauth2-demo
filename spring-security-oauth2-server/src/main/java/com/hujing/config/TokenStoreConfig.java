package com.hujing.config;

import com.hujing.authorize.JwtTokenEnhancer;
import com.hujing.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author hj
 * 2019-05-10 9:15
 * token存储配置
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 使用Redis存储token
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    @ConditionalOnProperty(prefix = "security.properties.token", name = "tokenStoreType", havingValue = "redis", matchIfMissing = false)
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "security.properties.token", name = "tokenStoreType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenStoreConfig {

        @Autowired
        private SecurityProperties securityProperties;

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(securityProperties.getToken().getSignKey());
            return converter;
        }
        /**
         * jwtTokenStore
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        /**
         * jwtToken增强器
         * @return
         */
        @Bean
        public TokenEnhancer jwtTokenEnhancer() {
            return new JwtTokenEnhancer();
        }
    }
}
