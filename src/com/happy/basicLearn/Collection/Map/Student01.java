package com.happy.basicLearn.Collection.Map;

public class Student01 implements Comparable<Student01> {
    private String name;
    private int age;


    public Student01() {
    }

    public Student01(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student01{name = " + name + ", age = " + age + "}";
    }

    @Override
    public int compareTo(Student01 o) {
        int i = this.getAge() - o.getAge();
        if (i == 0) {
            i = this.getName().compareTo(o.getName());
        }
        return i;
    }
}
