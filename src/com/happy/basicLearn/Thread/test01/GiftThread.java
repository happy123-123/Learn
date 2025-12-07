package com.happy.basicLearn.Thread.test01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GiftThread implements Runnable{
    int gift=100;
    Lock l=new ReentrantLock();
    @Override
    public void run() {
        while (true){
            l.lock();
            try {
                if (gift < 10) {
                    break;
                }else {
                    gift--;
                    System.out.println(Thread.currentThread().getName()+"还剩"+gift+"个礼物");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                l.unlock();
            }
        }
    }
}
