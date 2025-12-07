package com.happy.basicLearn.Collection.Set;

import java.util.TreeSet;

public class set {
    public static void main(String[] args) {
        Student s1 = new Student("wangwu", 12);
        Student s2 = new Student("lisi", 13);
        Student s3 = new Student("zhangsan", 14);
        Student s4 = new Student("zhangsan", 14);

//        HashSet<Student> s = new HashSet<>();
//        System.out.println(s.add(s1));
//        System.out.println(s.add(s2));
//        System.out.println(s.add(s3));
//        System.out.println(s.add(s4));

//        TreeSet<String> ts = new TreeSet<>(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                int i = o1.length() - o2.length();
//                int x = i == 0 ? o1.compareTo(o2) : i;
//                return x;
//            }
//        });
        TreeSet<Student> ts = new TreeSet<>();
        ts.add(s1);
        ts.add(s2);
        ts.add(s3);
        System.out.println(ts);
    }
}
