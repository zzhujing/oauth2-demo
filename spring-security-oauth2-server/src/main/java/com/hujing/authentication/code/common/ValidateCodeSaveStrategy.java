package com.hujing.authentication.code.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hj
 * 2019-05-09 16:19
    验证码储存策略
 */
public interface ValidateCodeSaveStrategy {

    /**
     * 保存验证码
     * @param request 请求
     * @param code 验证码对象
     * @param type 验证码类型
     */
    void save(HttpServletRequest request, ValidateCode code,String type);


    /**
     * 获取验证码
     * @param request 请求
     * @param type 验证码类型
     * @return 验证码对象
     */
    ValidateCode get(HttpServletRequest request,String type);


    /**
     *  删除验证码
     * @param request 请求
     * @param type 验证码类型
     */
    void remove(HttpServletRequest request, String type);

}
