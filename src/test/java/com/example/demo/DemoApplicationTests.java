package com.example.demo;

import com.alibaba.excel.util.DateUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.DateUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String filePath = "/Users/lidaye/IdeaProjects/test1/src/main/resources/a.txt";
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // 装饰者模式使得 BufferedReader 组合了一个 Reader 对象
        // 在调用 BufferedReader 的 close() 方法时会去调用 Reader 的 close() 方法
        // 因此只要一个 close() 调用即可
        bufferedReader.close();
    }

    @Test
    void test()  {
//        BigDecimal multiply = new BigDecimal(308058L).multiply(new BigDecimal("0.038")).setScale(2, BigDecimal.ROUND_HALF_UP);
//        System.out.println(multiply);
//        //返回 JVM 堆大小
//        long l = Runtime.getRuntime().maxMemory()/ 1024 /1024 * 64 /1024;
//        System.out.println(l);
//        //返回 JVM 堆的最大内存
//        long l2 = Runtime.getRuntime().totalMemory()/ 1024 /1024;
//        System.out.println(l2);
        int foo = foo();
        System.out.println(foo);
    }
    public int foo() {
        int x;
        try {
            x = 1 ;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
            return x;
        }
    }


    @Test
    public void test2() {
        List<String> list1 = new ArrayList();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("f");
        list1.add("d");
        new Thread(new Runnable(){

            @Override
            public void run() {
                for (String s : list1){
                    if (s.equals("c") ) {
                        list1.remove(s);
                    }
                }
            }
        });
        for (String s : list1){
            if (s.equals("c") ) {
                list1.remove(s);
            }
        }
    }

    @Test
    void test11()  {
        Thread thread = new Thread();
        thread.setDaemon(true);
        System.out.printf( thread.getName());
        thread.start();

    }
}
