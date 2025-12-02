package com.example.demo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.util.*;


@Slf4j
public class Test2 {

    @Test
     public void test() {
        int[] nums ={5,7,7,8,8,10};
        System.out.println(Arrays.toString(searchRange(nums, 6)));
    }
    public int[] searchRange(int[] nums, int target) {
        int first = -1;
        int last = -1;
        int left = 0;
        int right = nums.length-1;

        while(left<=right){
            int min = (left + right )/ 2 ;
            if (nums[min] == target){
                first = min;
                last = min ;
                while(first>=0 && nums[first] == target){
                    first--;
                }
                first++;
                while(last<=nums.length-1 && nums[last] == target){
                    last++;
                }
                last--;
                return new int[]{first,last};

            } else if (nums[min] > target){
                right = min - 1;
            }else{
                left = min + 1;

            }
        }
        return new int[]{-1,-1};
    }
}
