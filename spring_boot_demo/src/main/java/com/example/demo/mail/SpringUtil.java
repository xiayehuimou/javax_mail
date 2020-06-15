package com.example.demo.mail;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import  org.springframework.beans.factory.support.DefaultListableBeanFactory;

@Component
@SuppressWarnings({"static-access"})
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static DefaultListableBeanFactory getBeanFactory(){
        return (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();  

   }
    
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}