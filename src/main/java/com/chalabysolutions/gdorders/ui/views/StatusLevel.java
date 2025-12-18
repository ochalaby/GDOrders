package com.chalabysolutions.gdorders.ui.views;

import lombok.Getter;

@Getter
public enum StatusLevel {
    INFO("Info", "black"),
    ERROR("Error", "red");

    private final String label;
    private final String color;

    StatusLevel(String label, String color) {
        this.label = label;
        this.color = color;
    }
}
