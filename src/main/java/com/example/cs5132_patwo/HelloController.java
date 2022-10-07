package com.example.cs5132_patwo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void openExampleCompoundsTab(ActionEvent actionEvent) {
        HelloApplication.openExampleCompoundsTab();
    }

    public void openMyCompoundsTab(ActionEvent actionEvent) {
        HelloApplication.openMyCompoundsTab();
    }

    public void returnToMenu(ActionEvent actionEvent) {
        HelloApplication.returnToMenu();
    }
}