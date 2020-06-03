package com.controller;

public class Concrete {

    private int limitDuration;

    private String title;

    private int frost;

    private int age;

    public Concrete() {
    }

    public Concrete(int limitDuration, String title, int frost) {
        this.limitDuration = limitDuration;
        this.title = title;
        this.frost = frost;
        this.age = 4;
    }

    @Override
    public String toString() {
        return "Concrete{" +
                "limitDuration=" + limitDuration +
                ", title='" + title + '\'' +
                ", frost=" + frost +
                ", age=" + age +
                '}';
    }

    public int calculateCharacterLimitDuratation() {
        return limitDuration*2;
    }

    public int calculateLimitDurationByAge() {
        return limitDuration/age;
    }

    public int getLimitDuration() {
        return limitDuration;
    }

    public void setLimitDuration(int limitDuration) {
        this.limitDuration = limitDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFrost() {
        return frost;
    }

    public void setFrost(int frost) {
        this.frost = frost;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
