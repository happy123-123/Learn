package com.happy.basicLearn.IO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;

//对出师表进行排序
public class IOTest04 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("b.txt"));
        String s;
        ArrayList<String> list = new ArrayList<>();
        while ((s = br.readLine()) != null) {
            list.add(s);
        }
        br.close();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i1 = Integer.parseInt(o1.split("\\.")[0]);
                int i2 = Integer.parseInt(o2.split("\\.")[0]);
                return i1 - i2;
            }
        });
        for (String string : list) {
            bw.write(string);
            bw.newLine();
        }
        bw.close();
    }
}
