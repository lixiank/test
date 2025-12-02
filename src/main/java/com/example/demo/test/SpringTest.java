package com.example.demo.test;

import  com.example.demo.bean.User;
import com.example.demo.config.AppConfig;
import com.example.demo.service.UserService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        User user = (User ) context.getBean( "user");

        UserService userService1 = new UserService();
         System.out.println( userService1);
        System.out.println(user);

    }
}
