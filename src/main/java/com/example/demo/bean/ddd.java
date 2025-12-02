package com.example.demo.bean;

import sun.security.provider.ConfigFile;

/**
 * @Author: lixk
 * @Date: 2022/11/4  13:51
 */
public class ddd {
    public static void main(String[] args) {
        String accSuccess = String.format(
               " byAccSuccess:%s:%s:%s",
                "submitTime",
               "entity.getAccount()",
               "entity.getFeeType()"
        );
        System.out.println(accSuccess);
    }
}
