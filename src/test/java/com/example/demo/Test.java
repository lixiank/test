package com.example.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Test implements  Runnable {
    private int num = 10 ;
    // 分布式锁
    private InterProcessMutex lock ;

    public Test(){
        CuratorFramework client1 = CuratorFrameworkFactory.builder().connectString("106.14.72.68:2181").
                retryPolicy(new ExponentialBackoffRetry (1000, 3)).build();
        //开启连接
        client1.start();
        lock = new InterProcessMutex(client1,"/lock") ;
    }
    @Override
    public void run() {
        while (true) {
            //获取锁
            try {
                lock.acquire(3, TimeUnit.SECONDS);
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    num--;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    //释放锁
                    lock.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
