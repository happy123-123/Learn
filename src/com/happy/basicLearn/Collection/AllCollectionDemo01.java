package com.happy.basicLearn.Collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;

public class AllCollectionDemo01 {
    public static void main(String[] args) {
        HashMap<String,ArrayList<String>> hm=new HashMap<>();
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("南京市");
        list1.add("扬州市");
        list1.add("苏州市");
        list1.add("无锡市");
        list1.add("常州市");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("武汉市");
        list2.add("孝感市");
        list2.add("宜昌市");
        list2.add("十堰市");
        list2.add("鄂州市");

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("石家庄市");
        list3.add("唐山市");
        list3.add("邢台市");
        list3.add("保定市");
        list3.add("张家口市");

        hm.put("江苏省",list1);
        hm.put("湖北省",list2);
        hm.put("河北省",list3);

        hm.entrySet().forEach(new Consumer<Map.Entry<String, ArrayList<String>>>() {
            @Override
            public void accept(Map.Entry<String, ArrayList<String>> entry) {
                ArrayList<String> value = entry.getValue();
                StringJoiner sj = new StringJoiner(",");
                for (String s : value) {
                    sj.add(s);
                }
                System.out.println(entry.getKey()+"="+sj);
            }
        });

//        ArrayList<String> list = new ArrayList<>();
//        Collections.addAll(list, "张三", "犯贱", "赵六", "小龙女", "洞可", "赵六");
//        Collections.shuffle(list);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        ArrayList<Integer> list = new ArrayList<>();
//        Collections.addAll(list, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        Collections.shuffle(list);
//        if (list.get(0) < 8) {
//            System.out.println(list.get(0)+" 抽的是男生");
//        } else {
//            System.out.println(list.get(0)+" 抽的是女生");
//        }
    }
}
