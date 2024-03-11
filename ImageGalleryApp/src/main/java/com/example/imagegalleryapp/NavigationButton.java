package com.example.imagegalleryapp;

import javafx.scene.control.Button;

public class NavigationButton extends Button {
    public NavigationButton(String text) {
        super(text);
        getStyleClass().add("navigation-button");
    }
}
