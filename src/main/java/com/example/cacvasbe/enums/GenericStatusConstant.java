package com.example.cacvasbe.enums;

public enum GenericStatusConstant {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String abbreviate;

    private GenericStatusConstant(String abbreviate) {
        this.abbreviate = abbreviate;
    }

    public String getAbbreviate() {
        return abbreviate;
    }
}
