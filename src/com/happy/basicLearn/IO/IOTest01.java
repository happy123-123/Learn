package com.happy.basicLearn.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IOTest01 {
    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\Happy\\JavaProjects\\arraylist");
        HashMap<String, Integer> hm = new HashMap<>();
        System.out.println(count(f, hm));

        FileOutputStream fos = new FileOutputStream("a.txt");
        //实际写出整数对应ASCII
        fos.write(97);

        fos.close();

    }

    public static HashMap<String, Integer> count(File file, HashMap<String, Integer> hashMap) {
        //读取file的信息(文件,文件夹)
        for (File listFile : file.listFiles()) {
            //遍历
            if (listFile.isFile()) {
                //获取名称(含后缀名)
                String name = listFile.getName();
                //将名称以"."分隔
                String[] s = name.split("\\.");
                //是否以类似(a.txt)形式存在
                if (s.length >= 2) {
                    if (hashMap.containsKey(s[s.length - 1])) {
                        hashMap.put(s[s.length - 1], hashMap.get(s[s.length - 1]) + 1);
                    } else {
                        hashMap.put(s[s.length - 1], 1);
                    }
                }
            } else {
                for (Map.Entry<String, Integer> entry : count(listFile, hashMap).entrySet()) {
                    hashMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return hashMap;
    }

}
