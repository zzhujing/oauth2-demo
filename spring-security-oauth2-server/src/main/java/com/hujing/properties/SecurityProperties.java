package com.hujing.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hj   2019-05-09 8:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties(prefix = "security.properties")
public class SecurityProperties {

    private AuthenticationProperties auth = new AuthenticationProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private TokenProperties token = new TokenProperties();

}
