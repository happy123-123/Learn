package com.happy.basicLearn.IO;

import java.io.*;

//加密解密文件夹
public class IOTest06 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("a.txt");
        FileOutputStream fos=new FileOutputStream("b.txt");
        int i;
        while ((i=fis.read())!=-1){
            fos.write(i^2);
        }
        fos.close();
        fis.close();
    }
}
