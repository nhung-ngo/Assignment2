package com.csc340.demo;

public class Cat {
    private String id;
    private String origin;
    private String name;

    public Cat(){}

    public Cat(String id, String origin, String name){
        this.id = id;
        this.origin = origin;
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
