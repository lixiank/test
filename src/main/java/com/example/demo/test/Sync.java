package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sync {
    public static void main(String[] args) {
        Thread t  = new Thread(){
            @Override
            public void run() {
                log.info("线程启动了");
            }
        };
        t.start();
        log.info("main");
    }
}
