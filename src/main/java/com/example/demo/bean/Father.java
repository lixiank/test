package com.example.demo.bean;

/**
 * @author lidaye
 */
public class Father {

    public   String name = "lxk";

}

class Son extends Father {
}

class Test1 {
    public static void main(String[] args) {
        Son son = new Son();
        System.out.println(son.name);
    }
}