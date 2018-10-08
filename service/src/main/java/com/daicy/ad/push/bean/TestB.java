package com.daicy.ad.push.bean;

import java.io.Serializable;

public class TestB implements Serializable {
    private Integer id;

    private String name;

    private Integer age;

    private String message;

    public TestB(Integer id, String name, Integer age, String message) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.message = message;
    }

    public TestB() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}