package com.happy.basicLearn.Reflect;

public class Teacher {
    private String name;
    private int salary;


    public Teacher() {
    }

    public Teacher(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public void teaching(){
        System.out.println("我在教书");
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
     * @return salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * 设置
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String toString() {
        return "Teacher{name = " + name + ", salary = " + salary + "}";
    }
}
