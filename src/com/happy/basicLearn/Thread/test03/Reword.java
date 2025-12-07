package com.happy.basicLearn.Thread.test03;

import java.util.ArrayList;
import java.util.Collections;

public class Reword implements Runnable {
    ArrayList<Integer> list;

    public Reword(ArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        ArrayList<Integer> reword = new ArrayList<>();
        int count = 0;
        int rewordSum = 0;
        while (true) {
            synchronized (Reword.class) {
                if (list.size() == 0) {
                    System.out.println(Thread.currentThread().getName() + "奖项为：" + reword + "次数为：" + count + "总数为：" + rewordSum);
                    break;
                } else {
                    Collections.shuffle(list);
                    Integer remove = list.remove(0);
                    reword.add(remove);
                    rewordSum += remove;
                    count++;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
