package com.happy.basicLearn.Thread.learn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread01 implements Runnable {
    int ticket;
//    static Object obj = new Object();
    Lock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (ticket < 100) {
                    ticket++;
                    System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
                } else {
                    break;
                }
            }
        }
    }
}
