package com.example.cacvasbe.enums;

public enum RoleType {
    GENERAL_USER("GENERAL_USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String abbreviate;


    RoleType(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public String getAbbreviate() {
        return abbreviate;
    }
}
