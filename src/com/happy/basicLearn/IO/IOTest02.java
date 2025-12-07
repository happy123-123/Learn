package com.happy.basicLearn.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//拷贝文件夹(含子文件夹,文件)
public class IOTest02 {
    public static void main(String[] args) throws IOException {
        File src = new File("a.txt");
        File dest = new File("b.txt");
        copy(src, dest);
    }

    private static void copy(File src, File dest) throws IOException {
        dest.mkdirs();
        for (File file : src.listFiles()) {
            if (file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(new File(dest, file.getName()));
                byte[] b = new byte[1024];
                int len;
                while ((len = fis.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                fos.close();
                fis.close();
            } else {
                copy(file, new File(dest, file.getName()));
            }
        }
    }
}
