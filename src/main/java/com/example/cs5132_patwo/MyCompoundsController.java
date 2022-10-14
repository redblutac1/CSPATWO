package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cs5132_patwo.HelloApplication.mySuperChemisTREE;
import static com.example.cs5132_patwo.HelloApplication.mySuperChemisTREEPath;

public class MyCompoundsController implements Initializable {
    static ArrayList<ReagentNode<Reagent>> myCompounds = new ArrayList<>();
    static ReagentNode<Reagent> set_compound;
    @FXML
    public TextField compoundTextField;
    @FXML
    public Button addCompoundButton;
    @FXML
    public Button exploreButton;
    @FXML
    public ListView<Node<Reagent>> compoundsListView;
    @FXML
    public Button backButton;

    public static void save() {
        if (mySuperChemisTREE == null || mySuperChemisTREE.getRoot().getNumNeighbours() == 0) return;
        try {
            PrintWriter output = new PrintWriter(mySuperChemisTREEPath);
            output.println(mySuperChemisTREE.getRoot().serialize());
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File(mySuperChemisTREEPath);

        if (HelloApplication.mySuperChemisTREE == null) {
            try {
                file.createNewFile();

                BufferedReader br = new BufferedReader(new FileReader(mySuperChemisTREEPath));
                String line = br.readLine();
                if (line == null) {
                    HelloApplication.mySuperChemisTREE = new ChemisTREE<>(new Reagent("ROOT"));
                } else {
                    mySuperChemisTREE = new ChemisTREE<>(ReagentNode.deserialize(line));
                    compoundsListView.getItems().addAll(HelloApplication.mySuperChemisTREE.getRoot().getNonNullNeighbours());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            compoundsListView.getItems().addAll(HelloApplication.mySuperChemisTREE.getRoot().getNonNullNeighbours());
        }
    }

    public void returnToHome(ActionEvent actionEvent) {
        HelloApplication.returnToMenu();
    }

    public void addCompound(ActionEvent actionEvent) {
        String compoundString = compoundTextField.getText();
        Pattern pattern = Pattern.compile("[#$]");
        Matcher matcher = pattern.matcher(compoundString);
        if (matcher.find()) { //prevent issues with serialising, which uses # and $ as delimiters
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Invalid name");
            dialog.setContentText("Please do not include # or $ in your compound name.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }
        String[] compoundArray = compoundString.split("\\|");

        if (compoundArray.length <= 1) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Invalid input");
            dialog.setContentText("Please ensure that you have at least one product and one reactant.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        if (Arrays.asList(compoundArray).contains("")) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Empty input");
            dialog.setContentText("Please ensure that you do not have any empty inputs.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        ReagentNode<Reagent>[] reactants = new ReagentNode[compoundArray.length - 1];
        for (int i = 1; i < compoundArray.length; i++) {
            reactants[i - 1] = new ReagentNode<>(new Reagent(compoundArray[i]));
        }
        ReagentNode<Reagent> compound = new ReagentNode<>(new Reagent(compoundArray[0]), reactants);
        myCompounds.add(compound);
        mySuperChemisTREE.insert(compound);
        compoundsListView.getItems().add(compound);
    }

    public void explore(ActionEvent actionEvent) {
        ReagentNode<Reagent> compound = (ReagentNode<Reagent>) compoundsListView.getSelectionModel().getSelectedItem();
        if (compound == null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("No selection");
            dialog.setContentText("Please select a compound to explore.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }
        set_compound = compound;
        HelloApplication.openExploreTab();
    }

}