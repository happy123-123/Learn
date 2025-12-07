package com.happy.basicLearn.IO;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class IOTest08 {
    public static void main(String[] args) throws IOException {
        File src=new File("D:\\aaa.zip");
        File dest=new File("D:\\aaa");
        unzip(src,dest);

    }
    public static void unzip(File src,File dest) throws IOException {
        ZipInputStream zip=new ZipInputStream(new FileInputStream(src));
        ZipEntry entry;
        while ((entry=zip.getNextEntry())!=null){
            //如果是文件夹
            if (entry.isDirectory()){
                File f=new File(dest,entry.toString());
                f.mkdirs();
            }else {
                FileOutputStream fos=new FileOutputStream(new File(dest,entry.toString()));
                int b;
                while ((b= zip.read())!=-1){
                    fos.write(b);
                }
                fos.close();
                zip.closeEntry();
            }
        }
        zip.close();
    }
}
