package com.lchen.ccdeploy.service.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CommonFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}