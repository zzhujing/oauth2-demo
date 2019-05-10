package com.hujing.authorize;

import com.google.common.collect.Maps;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

/**
 * @author hj
 * 2019-05-10 11:44
    扩展jwtToken
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        HashMap<String, Object> map = Maps.newHashMap();
        //设置自己附加的参数
        map.put("company", "bdth");
        map.put("message", "extend mes");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        return accessToken;
    }
}
