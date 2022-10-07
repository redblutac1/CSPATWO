package com.example.cs5132_patwo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class ExampleCompoundsController implements Initializable {
    @FXML
    public ComboBox comboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.getItems().addAll(HelloApplication.superChemisTREE.getStringChildren());
        Collections.sort(comboBox.getItems());
    }

    public void returnToMenu(ActionEvent actionEvent) {
        HelloApplication.returnToMenu();
    }
}