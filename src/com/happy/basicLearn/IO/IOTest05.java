package com.happy.basicLearn.IO;

import java.io.*;

//控制软件运行次数
public class IOTest05 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));
        String s = br.readLine();
        int i = Integer.parseInt(s);
        System.out.println(s);
        br.close();
        i++;
        if (i <= 3) {
            System.out.println("第"+i+"次");
        }else
            System.out.println("只能第3次");
        BufferedWriter bw = new BufferedWriter(new FileWriter("a.txt"));
        bw.write(i+"");
        bw.close();
    }
}
