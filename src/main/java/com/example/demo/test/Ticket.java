package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Ticket {
    public static void main(String[] args) throws InterruptedException {
         Window window = new Window();
        List<Integer> list = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                int sell = window.sell(random());
                list.add(sell);
                try {
                    Thread.sleep(100);//模拟网络延迟
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
            thread.start();
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.join();
        }
        log.info("售出总数:{}",list.stream().mapToInt(i->i).sum());
        log.info("余票数:{} ",window.getCount());
    }
    private static int random(){
        return (int)(Math.random()*5 + 1); //生成0-99的随机数
    }
}

@Slf4j
class Window{
     private int count = 10 ;
    public  synchronized int  sell(int num){
        if(count >= num){
            count -= num;
            log.info("卖出{}张票，剩余{}张票",num,count);
            return num;
        }else {
            log.info("买票{}张,剩余票数不足",num);
            return 0;
        }
    }

    public Integer getCount(){
        return count;
    }
}