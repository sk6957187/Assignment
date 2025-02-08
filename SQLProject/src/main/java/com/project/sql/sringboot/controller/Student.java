package com.project.sql.sringboot.controller;

public class Student {
    private String name;
    private int age;
    private String email;
    private byte[] photo;
    private String photoes;// Store photo as a byte array

    // Default Constructor
    public Student() {}

    // Convenience Constructor
    public Student(String name, int age, String email, byte[] photo) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.photo = photo;
    }

    public Student(int sid, String name, int age, String email, String photo) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.photoes = photoes;
    }
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
}
