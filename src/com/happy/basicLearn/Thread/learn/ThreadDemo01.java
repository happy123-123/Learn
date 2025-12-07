package com.happy.basicLearn.Thread.learn;

public class ThreadDemo01 {
    public static void main(String[] args) {
        MyThread01 tt1 = new MyThread01();
        Thread t1 = new Thread(tt1);
        Thread t2 = new Thread(tt1);
        Thread t3 = new Thread(tt1);
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}
