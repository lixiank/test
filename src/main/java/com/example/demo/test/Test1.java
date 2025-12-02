package com.example.demo.test;

import jdk.nashorn.internal.ir.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test1 {
    private static int currentThread = 1; // 1:A, 2:B, 3:C

    public static void main(String[] args) {
        Object lock = new Object();

        Thread a = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (currentThread != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("A");
                    currentThread = 2;
                    lock.notifyAll();
                }
            }
        });

        Thread b = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (currentThread != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("B");
                    currentThread = 3;
                    lock.notifyAll();
                }
            }
        });

        Thread c = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (currentThread != 3) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("C");
                    currentThread = 1;
                    lock.notifyAll();
                }
            }
        });

        a.start();
        b.start();
        c.start();
    }
}
