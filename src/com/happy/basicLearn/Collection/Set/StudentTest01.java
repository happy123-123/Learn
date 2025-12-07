package com.happy.basicLearn.Collection.Set;

public class StudentTest01 implements Comparable<StudentTest01>{
    private String name;
    private int age;
    private int ChineseScore;
    private int MathScore;
    private int EnglishScore;

    public StudentTest01() {
    }

    public StudentTest01(String name, int age, int ChineseScore, int MathScore, int EnglishScore) {
        this.name = name;
        this.age = age;
        this.ChineseScore = ChineseScore;
        this.MathScore = MathScore;
        this.EnglishScore = EnglishScore;
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
     * @return ChineseScore
     */
    public int getChineseScore() {
        return ChineseScore;
    }

    /**
     * 设置
     * @param ChineseScore
     */
    public void setChineseScore(int ChineseScore) {
        this.ChineseScore = ChineseScore;
    }

    /**
     * 获取
     * @return MathScore
     */
    public int getMathScore() {
        return MathScore;
    }

    /**
     * 设置
     * @param MathScore
     */
    public void setMathScore(int MathScore) {
        this.MathScore = MathScore;
    }

    /**
     * 获取
     * @return EnglishScore
     */
    public int getEnglishScore() {
        return EnglishScore;
    }

    /**
     * 设置
     * @param EnglishScore
     */
    public void setEnglishScore(int EnglishScore) {
        this.EnglishScore = EnglishScore;
    }

    public String toString() {
        return "StudentTest01{name = " + name + ", age = " + age + ", ChineseScore = " + ChineseScore + ", MathScore = " + MathScore + ", EnglishScore = " + EnglishScore + "}";
    }

    @Override
    public int compareTo(StudentTest01 o) {
        //按照总分排序
        int sum = this.getChineseScore() + this.getMathScore() + this.getEnglishScore();
        int i = sum - (o.getChineseScore() + o.getMathScore() + o.getEnglishScore());
        if (i == 0) {
            i = this.getChineseScore() - o.getChineseScore();
            if (i == 0) {
                i = this.getMathScore() - o.getMathScore();
                if (i == 0) {
                    i = this.getEnglishScore() - o.getEnglishScore();
                    if (i == 0) {
                        i = this.getAge() - o.getAge();
                        if (i == 0) {
                            i = this.getName().compareTo(o.getName());
                        }
                    }
                }
            }
        }
        return i;
    }
}
