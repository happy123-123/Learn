package com.happy.basicLearn.Exception;

import java.io.IOException;
import java.util.Scanner;

public class ExceptionTest01 {
    public static void main(String[] args) throws IOException {
        GirlFriend gf = new GirlFriend();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("请输入年龄:");
                int age = Integer.parseInt(sc.nextLine());
                gf.setAge(age);

                System.out.println("请输入姓名:");
                String name = sc.nextLine();
                gf.setName(name);

                break;
            } catch (NumberFormatException e) {
                System.out.println("年龄输入格式有误");
            } catch (RuntimeException r) {
                System.out.println("年龄或姓名输入有误");
            }
        }
        System.out.println(gf);

    }
}
