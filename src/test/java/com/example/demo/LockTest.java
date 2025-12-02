package com.example.demo;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LockTest {
    public static void main(String[] args) {
        Test test = new Test();

        //创建客户端
        Thread t1 = new Thread(test,"携程");
        Thread t2 = new Thread(test,"飞猪");

        t1.start();
        t2.start();
    }
}
