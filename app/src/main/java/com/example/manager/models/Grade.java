package com.example.manager.models;

public class Grade {
    private int id;
    private String name;
    private int managermentId;

    public Grade() {
    }

    public Grade(int id, String name, int managermentId) {
        this.id = id;
        this.name = name;
        this.managermentId = managermentId;
    }

    public Grade(String name, int managermentId) {
        this.name = name;
        this.managermentId = managermentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagermentId() {
        return managermentId;
    }

    public void setManagermentId(int teacherId) {
        this.managermentId = teacherId;
    }
}
