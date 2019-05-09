package com.hujing.authentication.code.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author hj
 * @create 2019-05-02 16:52
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext act;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.act = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        return act.getBean(clz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String className) {
        return (T) act.getBean(className);
    }

}
