package com.happy.basicLearn.Collection.Map;

import java.util.Map;
import java.util.TreeMap;

public class MapDemo02 {
    public static void main(String[] args) {
        String s = "aababcabcdabcde";
        TreeMap<Character, Integer> tm = new TreeMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (tm.containsKey(c)) {
                tm.put(c, tm.get(c) + 1);
            } else {
                tm.put(c, 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : tm.entrySet()) {
            System.out.println(entry.getKey() + "(" + entry.getValue()+")");
        }


//        TreeMap<Student01, String> tm1 = new TreeMap<>();
//        tm1.put(new Student01("zhangsan", 18), "home");
//        tm1.put(new Student01("wangwu", 18), "tall");
//        tm1.put(new Student01("lisi", 20), "school");
//        System.out.println(tm1);
    }
}
