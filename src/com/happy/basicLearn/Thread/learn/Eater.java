package com.happy.basicLearn.Thread.learn;

import java.util.concurrent.ArrayBlockingQueue;

public class Eater implements Runnable {
    ArrayBlockingQueue<String> queue;

    public Eater(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String take = queue.take();
                System.out.println(Thread.currentThread().getName() + "正在吃" + take);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//    @Override
//    public void run() {
//        while (true) {
//            synchronized (Desk.lock) {
//                if (Desk.count == 0) {
//                    break;
//                } else {
//                    if (Desk.status == 0) {
//                        try {
//                            Desk.lock.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Desk.count--;
//                        if (Desk.count > 0) {
//                            System.out.println(Thread.currentThread().getName() + "正在吃,还能吃" + Desk.count);
//                        } else System.out.println(Thread.currentThread().getName() + "正在吃,吃完就吃不下了");
//                        Desk.status = 0;
//                        Desk.lock.notifyAll();
//                    }
//                }
//            }
//        }
//    }

}
