package com.happy.basicLearn.Thread.test01;

public class MovieThread implements Runnable {
    int ticket = 1000;

    @Override
    public void run() {
        while (true) {
            synchronized (MovieThread.class) {
                if (ticket == 0) {
                    break;
                } else {
                    System.out.println(Thread.currentThread().getName() + "正在卖第" + ticket + "张票");
                    ticket--;
                }
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
