package com.happy.basicLearn.IO;

import java.io.*;

public class IOTest03 {
    public static void main(String[] args) throws IOException {
        //四种方式拷贝文件,并计算各自用时
        long start = System.currentTimeMillis();
//        method01();
//        method02();
//        method03();
//        method04();
        long end = System.currentTimeMillis();
        System.out.println(start - end);
    }

    public static void method01() throws IOException {
        FileInputStream fis = new FileInputStream("a.txt");
        FileOutputStream fos = new FileOutputStream("b.txt");
        int i;
        while ((i = fis.read()) != -1) {
            fos.write(i);
        }
        fos.close();
        fis.close();
    }

    public static void method02() throws IOException {
        FileInputStream fis = new FileInputStream("a.txt");
        FileOutputStream fos = new FileOutputStream("b.txt");
        byte[] b = new byte[2];
        int i;
        while ((i = fis.read(b)) != -1) {
            fos.write(b, 0, i);
        }
        fos.close();
        fis.close();
    }

    public static void method03() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("b.txt"));
        int i;
        while ((i = bis.read()) != -1) {
            bos.write(i);
        }
        bos.close();
        bis.close();
    }

    public static void method04() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("b.txt"));
        byte[] b = new byte[8192];
        int i;
        while ((i = bis.read(b)) != -1) {
            bos.write(b, 0, i);
        }
        bos.close();
        bis.close();
    }
}
