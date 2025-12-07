package com.happy.basicLearn.Reflect;

public class Student01 {
    private String name;
    private int age;
    private double height;
    private String hobby;


    public Student01() {
    }

    public Student01(String name, int age, double height, String hobby) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.hobby = hobby;
    }

    public void study(){
        System.out.println("我在学习");
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 获取
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * 设置
     * @param height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * 获取
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * 设置
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String toString() {
        return "Student{name = " + name + ", age = " + age + ", height = " + height + ", hobby = " + hobby + "}";
    }
}
