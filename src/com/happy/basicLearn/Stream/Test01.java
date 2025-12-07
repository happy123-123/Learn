package com.happy.basicLearn.Stream;

import com.happy.basicLearn.Stream.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test01 {
    public static void main(String[] args) {

//        ArrayList<Integer> list = new ArrayList<>();
//        Collections.addAll(list, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
//        list.stream()
//                .filter(i -> i % 2 == 0)
//                .forEach(i -> System.out.println(i));

//        ArrayList<String> list = new ArrayList<>();
//        Collections.addAll(list, "zhangsan,23", "lisi,24", "wangwu,25");
//        Map<String, Integer> map = list.stream()
//                .filter(s -> Integer.parseInt(s.split(",")[1]) >= 24)
//                .collect(Collectors.toMap(s -> s.split(",")[0], i -> Integer.parseInt(i.split(",")[1])));
//        System.out.println(map);

        ArrayList<String> manList = new ArrayList<>();
        ArrayList<String> womanList = new ArrayList<>();
        Collections.addAll(manList, "蔡坤坤,24", "叶齁咸,23", "刘不甜,22", "吴签,24", "谷嘉,30", "肖梁梁,27");
        Collections.addAll(womanList, "黄晶晶,18", "何润馨,19", "方斐斐,20", "李嘉颖,21", "黄敏贞,22", "余嘉雯,23");
        Stream<String> stream1 = manList.stream().filter(s -> s.split(",")[0].length() == 3).limit(2);
        Stream<String> stream2 = womanList.stream().filter(s -> s.split(",")[0].startsWith("黄")).skip(1);

        List<Actor> collect = Stream.concat(stream1, stream2).map(s -> {
                    String name = s.split(",")[0];
                    int age = Integer.parseInt(s.split(",")[1]);
                    return new Actor(name, age);
                })
                .collect(Collectors.toList());
        System.out.println(collect);

    }
}
