package com.happy.basicLearn.Collection.Set;

import java.util.TreeSet;

public class TreesetTest01 {
    public static void main(String[] args) {
        TreeSet<StudentTest01> ts=new TreeSet<>();
        StudentTest01 s1=new StudentTest01("lisi", 12, 100, 100, 100);
        StudentTest01 s2=new StudentTest01("wangwu", 14, 69, 19, 91);
        StudentTest01 s3=new StudentTest01("zhangsan", 13, 89, 89, 89);
        ts.add(s1);
        ts.add(s2);
        ts.add(s3);
//        System.out.println(ts);
        for (StudentTest01 s:ts){
            System.out.println(s);
        }
    }
}
