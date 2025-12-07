package com.happy.basicLearn.Reflect;

import com.happy.basicLearn.IO.Student;

import java.io.*;
import java.lang.reflect.Field;

public class ReflectTest01 {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        Student01 s = new Student01("zhangsan", 15, 167.5, "睡觉");
        Teacher t = new Teacher("波妞", 10000);
        saveObject(s);
    }

    //将对象里的成员变量名和值保存在文件中
    public static void saveObject(Object obj) throws IllegalAccessException, IOException {
        //获取字节码对象
        Class clazz = obj.getClass();
        //获取所以成员变量
        Field[] fields = clazz.getDeclaredFields();
        //创建IO流
        BufferedWriter bw = new BufferedWriter(new FileWriter("a.txt"));
        for (Field field : fields) {
            //无论是否私有化,都进行暴力反射
            field.setAccessible(true);
            //名
            String name = field.getName();
            //值
            Object o = field.get(obj);

            bw.write(name + "-" + o);
            bw.newLine();
        }
        bw.close();
    }
}
