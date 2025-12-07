package com.happy.basicLearn.Collection.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapDemo01 {
    public static void main(String[] args) {
        HashMap<String, Integer> hs = new HashMap<>();
        Random r = new Random();
        String[] names = {"张三", "李四", "王五", "赵六"};
        ArrayList<String> list = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < 80; i++) {
            int index = r.nextInt(names.length);
            list.add(names[index]);
        }
        for (String s : list) {
            if (hs.containsKey(s)) {
                hs.put(s, hs.get(s) + 1);
            } else {
                hs.put(s, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : hs.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        for (Map.Entry<String, Integer> entry : hs.entrySet()) {
            System.out.println(entry.getKey() + "--" + entry.getValue());
        }
        System.out.println("出现最多次数为：" + max);
        for (Map.Entry<String, Integer> entry : hs.entrySet()) {
            if (entry.getValue() == max) {
                System.out.println(entry.getKey());
            }
        }

//        map.forEach(new BiConsumer<String, String>() {
//            @Override
//            public void accept(String s, String s2) {
//                System.out.println(s+"--"+s2);
//            }
//        });

//        Iterator<Map.Entry<String, String>> it = entries.iterator();
//        while (it.hasNext()) {
//            Map.Entry<String,String> e=it.next();
//            System.out.println(e.getKey()+"--"+e.getValue());
//        }
//        Set<String> set = map.keySet();
        //增强for循环
//        for (String s : set) {
//            System.out.println(map.get(s));
//        }
        //迭代器遍历
//        Iterator<String> it = set.iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            System.out.println(map.get(key));
//        }
        //foreach循环
//        set.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(map.get(s));
//            }
//        });
    }
}
