package com.example.demo;

import com.alibaba.excel.util.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int x = 4;
        // 特殊值判断
        if (x == 0) {
            System.out.println(0);
        }
        if (x == 1) {
            System.out.println(1);
        }

        int left = 1;
        int right = x;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (mid *mid > x ) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }
        System.out.println(left);
    }

}
