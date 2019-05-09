package com.hujing.authentication.code.common;

import com.hujing.authentication.code.exception.ValidateCodeException;
import com.hujing.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author hj
 * 2019-05-09 15:53
 * 生成验证码
 */

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGeneratorMap;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeSaveStrategy validateCodeSaveStrategy;

    /**
     * 完成验证码的流程
     *
     * @param request
     * @param type
     */
    @Override
    public void createCode(ServletWebRequest request, String type) {
        //1.生成验证码
        T code = generatorCode(request.getRequest(), type);
        //2.保存验证码
        save(request.getRequest(), code, type);
        //3.发送验证码
        send(request,code);
    }

    /**
     * 发送验证码
     *
     * @param request
     * @param code
     */
    public abstract void send(ServletWebRequest request,T code);


    /**
     * 保存验证码
     *
     * @param request
     * @param code
     */
    private void save(HttpServletRequest request, T code, String type) {
        ValidateCodeSaveStrategy saveStrategy = SpringContextHolder.getBean(ValidateCodeSaveStrategy.class);
        saveStrategy.save(request, code, type);
    }

    /**
     * 根据不同类型的请求生成不同的验证码
     * * @param request
     *
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    private T generatorCode(HttpServletRequest request, String type) {
        ValidateCodeGenerator validateCodeGenerator = getValidateCodeGeneratorByType(type);
        return (T) validateCodeGenerator.generator(request);
    }

    /**
     * 根据类型获取对应的验证码生成器
     *
     * @param type
     * @return
     */
    public ValidateCodeGenerator getValidateCodeGeneratorByType(String type) {
        String beanName = type + "ValidateCodeGenerator";
        return validateCodeGeneratorMap.get(beanName);
    }

    @Override
    public void validate(HttpServletRequest request, ValidateCodeType codeType) throws ServletRequestBindingException {
        String type = codeType.toString().toLowerCase();
        String sessionKey = type + securityProperties.getCode().getSessionKeySuffix();
        ValidateCode code = validateCodeSaveStrategy.get(request, type);
        String requestParam = ServletRequestUtils.getStringParameter(request, codeType.getFormName());
        if (StringUtils.isEmpty(requestParam)) {
            throw new ValidateCodeException("请求参数中的l验证码不能为空");
        }
        if (code == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (code.isExpire()) {
            validateCodeSaveStrategy.remove(request, type);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(code.getCode(), requestParam)) {
            throw new ValidateCodeException("验证码不正确");
        }
        validateCodeSaveStrategy.remove(request, type);
    }
}
