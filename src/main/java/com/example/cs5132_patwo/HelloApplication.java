package com.example.cs5132_patwo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/hello-view.fxml"));
        stage.setTitle("ChemisTREE: The Chemical Database You Never Knew You Needed");
        scene = new Scene(root, 960, 540);
        stage.setScene(scene);
        stage.show();
    }

    // CHANGE TABS
    public static void openExampleCompoundsTab() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/Displacement.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openMyCompoundsTab() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/Displacement.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void returnToMenu() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/hello-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}