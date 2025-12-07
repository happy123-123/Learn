package com.happy.basicLearn.IO;

import java.io.*;
import java.util.ArrayList;

public class IOTest07 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student s1=new Student("zhangsan",13,"南京");
        Student s2=new Student("lisi",14,"广州");
        Student s3=new Student("wangwu",15,"重庆");

        ArrayList<Student> ListSrc=new ArrayList<>();
        ListSrc.add(s1);
        ListSrc.add(s2);
        ListSrc.add(s3);

        ObjectOutputStream ous=new ObjectOutputStream(new FileOutputStream("a.txt"));
        ous.writeObject(ListSrc);
        ous.close();

        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("a.txt"));
        ArrayList<Student> ListDest = (ArrayList<Student>) ois.readObject();
        System.out.println(ListDest);
        ois.close();
    }
}
