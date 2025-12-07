package com.happy.basicLearn.Thread.test01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumThread implements Runnable {
    int i = 1;

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (i > 100) {
                    break;
                } else {
                    if (i % 2 == 0) {
                        i++;
                    } else {
                        System.out.println(Thread.currentThread().getName() + " " + i);
                        i++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

//    @Override
//    public void run() {
//        while (true) {
//            synchronized (NumThread.class) {
//                if (i > 100) {
//                    break;
//                } else {
//                    if (i % 2 == 0) {
//                        i++;
//                    } else {
//                        System.out.println(Thread.currentThread().getName() + " " + i);
//                        i++;
//                    }
//                }
//            }
//        }
//    }

}
