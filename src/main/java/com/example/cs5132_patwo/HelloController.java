package com.example.cs5132_patwo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    public void openExampleCompoundsTab(ActionEvent actionEvent) {
        HelloApplication.openExampleCompoundsTab();
    }

    public void openMyCompoundsTab(ActionEvent actionEvent) {
        HelloApplication.openMyCompoundsTab();
    }
}