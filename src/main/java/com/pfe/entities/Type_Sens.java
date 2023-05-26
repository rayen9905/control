package com.pfe.entities;

public enum Type_Sens {
    VALUE1(1),
    VALUE2(2);
    private final int value;

    Type_Sens(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
