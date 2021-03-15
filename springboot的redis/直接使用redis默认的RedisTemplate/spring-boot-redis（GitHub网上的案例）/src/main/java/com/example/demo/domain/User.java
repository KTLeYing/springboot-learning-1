package com.example.demo.domain;

import java.io.Serializable;


public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return  "User [id=" + Id + ", name=" + Name + ", age=" + Age + "]";

    }

    public User(String id, String name, int age) {
        super();
        Id = id;
        Name = name;
        Age = age;
    }

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getAge() {
        return Age;
    }
    public void setAge(int age) {
        Age = age;
    }
    private String Id;
    private String Name;
    private int Age;



}