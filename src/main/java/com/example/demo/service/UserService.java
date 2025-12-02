package com.example.demo.service;

import com.example.demo.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lidaye
 */
@Component
public class UserService {
    @Autowired
    private OrderService orderService;

    public void test(){
        System.out.println("test");
    }
}
