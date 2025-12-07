package com.happy.basicLearn.Thread.learn;

import java.util.concurrent.ArrayBlockingQueue;

public class Cooker implements Runnable {
    ArrayBlockingQueue<String> queue;

    public Cooker(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.put("面");
                System.out.println(Thread.currentThread().getName() + "做了一碗面");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//    @Override
//    public void run() {
//        synchronized (Desk.lock) {
//            while (true) {
//                if (Desk.count == 0) {
//                    break;
//                } else {
//                    if (Desk.status != 0) {
//                        try {
//                            Desk.lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        System.out.println(Thread.currentThread().getName() + "做了一碗面");
//                        Desk.status = 1;
//                        Desk.lock.notifyAll();
//                    }
//                }
//            }
//        }
//    }
}
