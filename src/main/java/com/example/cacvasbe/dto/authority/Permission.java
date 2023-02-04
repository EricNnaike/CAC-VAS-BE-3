package com.example.cacvasbe.dto.authority;

public class Permission {
    private String name;

    public Permission() {
    }

    public Permission(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
