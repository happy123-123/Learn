package com.happy.basicLearn.Collection.List;

import java.util.ArrayList;
import java.util.List;

public class list {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        //迭代器遍历
        //可在遍历时删除元素(迭代器的方法)
//        Iterator<String> it=list.iterator();
//        while (it.hasNext()){
//            String s = it.next();
//            System.out.println(s);
//        }

        //普通for遍历
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

        //增强for遍历
//        for (String s:list){
//            System.out.println(s);
//        }

        //lambda表达式遍历
        //遍历集合list的每个元素,并将元素传给accept方法
        //即形参s表示集合list的每一个元素
//        list.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });

        //列表迭代器遍历
        //可在遍历时增加(迭代器的方法)
//        ListIterator<String> lit=list.listIterator();
//        while (lit.hasNext()){
//            String s = lit.next();
//            System.out.println(s);
//        }
    }
}
