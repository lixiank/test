package com.example.demo.bean;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @Author: lixk
 * @Date: 2022/10/12  17:53
 */
public class Test {
    public static void main(String[] args) {
        int[] ints = plusOne(new int[]{8, 9, 9});
        System.out.println(Arrays.toString(ints));
    }
    public static int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] = digits[i] % 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }
}
