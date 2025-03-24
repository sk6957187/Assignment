package com.project.sql.sringboot.controller;

public class Student {
    private String name;
    private int age;
    private String email;
    private byte[] photo;
    private String photoes;// Store photo as a byte array

//    {name=Sumit Kumar, age=21, dob=2025-03-12, image=blob:http://localhost:3000/c16b2c1f-a16f-40f2-9412-2a5a4bf3b526, video=blob:http://localhost:3000/59232684-e7c1-4c78-9330-868e28273cd8, audio=blob:http://localhost:3000/24352944-aff7-47e5-8cc4-9c1594da43a3, textFile=textFile.txt, address=dgfbhnm}

    public Student() {}

    // Convenience Constructor
    public Student(String name, int age, String email, byte[] photo) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.photo = photo;
    }
//    sid, sname, sroll,  email, photo
    public Student(int sid, String name, int age, String email, String photo) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.photoes = photo;
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
