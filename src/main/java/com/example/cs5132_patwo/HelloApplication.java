package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openscience.cdk.exception.CDKException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    public static ChemisTREE<Reagent> examplesSuperChemisTREE;
    public static String mySuperChemisTREEPath;
    public static ChemisTREE<Reagent> mySuperChemisTREE;
    private static Scene scene;

    public static void main(String[] args) {
        launch();
    }

    // CHANGE TABS
    public static void openExampleCompoundsTab() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/example-compounds.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openMyCompoundsTab() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/my-compounds.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void returnToMenu() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openExploreTab() {
        try {
            scene.setRoot(FXMLLoader.load(HelloApplication.class.getResource("/explore-compound.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage.setTitle("ChemisTREE: The Chemical Database You Never Knew You Needed");
        scene = new Scene(root, 960, 540);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(HelloApplication.class.getResource("/light.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        mySuperChemisTREEPath = "src/main/resources/mySuperChemisTREE.txt";

        initialiseExampleChemisTREEs(new String[]{"text.txt", "ord-total.txt"});

        //save entered compounds in My Compounds tab
        stage.setOnCloseRequest(event -> {
            MyCompoundsController.save();
            Platform.exit();
            System.exit(0);
        });
    }

    public void initialiseExampleChemisTREEs(String[] textFilePaths) {
        ArrayList<ArrayList<Reagent>> allReagents = new ArrayList<>();

        try {
            FileReader fr = null;
            BufferedReader br = null;

            for (String path : textFilePaths) {
                fr = new FileReader(path);
                br = new BufferedReader(fr);

                String line;
                while ((line = br.readLine()) != null) {
                    ArrayList<Reagent> reagents = new ArrayList<>();

                    // for each product
                    String productString = line.split(Pattern.quote("\\"))[1];
                    if (productString.split(":").length <= 1) continue;
                    reagents.add(new Reagent(productString.split(":")[0], productString.split(":")[1]));

                    // for each reactant
                    for (String s : line.split(Pattern.quote("\\"))[0].split("~")) {
                        if (s.split(":").length <= 1) continue;
                        reagents.add(new Reagent(s.split(":")[0], s.split(":")[1]));
                    }

                    allReagents.add(reagents);
                }
            }

            fr.close();
            br.close();
        } catch (IOException | CDKException e) {
            e.printStackTrace();
        }

        examplesSuperChemisTREE = new ChemisTREE<Reagent>(new Reagent("ROOT"));

        for (ArrayList<Reagent> reagent : allReagents) {
            ChemisTREE<Reagent> chemisTREE = new ChemisTREE<Reagent>(reagent);
            examplesSuperChemisTREE.insert(chemisTREE.getRoot());
        }
    }
}