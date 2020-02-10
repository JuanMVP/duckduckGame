package com.example.duckduckhunt.models;

public class User {

    private String name;
    private int ducks;

    public User() {
    }

    public User(String name, int ducks) {
        this.name = name;
        this.ducks = ducks;
    }

    public String getName() {
        return name;
    }

    public int getDucks() {
        return ducks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDucks(int ducks) {
        this.ducks = ducks;
    }
}
