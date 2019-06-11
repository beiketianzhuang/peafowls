package com.lchen.ccdeploy.model.constants;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
public enum ContextType {

    //aa
    SPRINGBOOT("springBootContext");
    private String beanName;
    ContextType(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }
}
