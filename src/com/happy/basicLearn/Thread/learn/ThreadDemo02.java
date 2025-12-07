package com.happy.basicLearn.Thread.learn;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadDemo02 {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> desk=new ArrayBlockingQueue<>(1);
        Cooker cooker=new Cooker(desk);
        Eater eater=new Eater(desk);
        Thread t1=new Thread(cooker);
        Thread t2=new Thread(eater);
        t1.setName("厨师");
        t2.setName("吃货");
        t1.start();
        t2.start();
    }
}
