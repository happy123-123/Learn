package com.happy.basicLearn.Thread.test02;

import java.text.DecimalFormat;
import java.util.Random;

public class RedPacketThread implements Runnable {
    double money = 100;
    int count = 3;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void run() {
        synchronized (RedPacketThread.class) {
            if (count == 0) {
                System.out.println(Thread.currentThread().getName() + "没抢到");
            } else {
                if (count == 1) {
                    System.out.println(Thread.currentThread().getName() + "抢到" + df.format(money) + "元");
                    count--;
                } else {
                    Random r = new Random();
                    double d = r.nextDouble(money - (count - 1) * 0.01);
                    if (d == 0) {
                        d = 0.01;
                    }
                    System.out.println(Thread.currentThread().getName() + "抢到" + df.format(d) + "元");
                    money -= d;
                    count--;
                }
            }
        }
    }
}
