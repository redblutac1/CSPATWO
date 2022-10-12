package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCompoundsController implements Initializable {
        @FXML
        public TextField compoundTextField;
        @FXML
        public Button addCompoundButton;
        @FXML
        public Button exploreButton;
        @FXML
        public ListView<ReagentNode<Reagent>> compoundsListView;
        @FXML
        public Button backButton;

        static ArrayList<ReagentNode<Reagent>> myCompounds = new ArrayList<>();
        static ChemisTREE<Reagent> superChemisTREE;
        static ReagentNode<Reagent> set_compound;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                superChemisTREE = new ChemisTREE<>(new Reagent("ROOT"));
                //TODO: write to file on startup
        }

        public void returnToHome(ActionEvent actionEvent){
                HelloApplication.returnToMenu();
        }

        public void addCompound(ActionEvent actionEvent){
                String compoundString = compoundTextField.getText();
                Pattern pattern = Pattern.compile("[#$]");
                Matcher matcher = pattern.matcher(compoundString);
                if (matcher.find()){ //prevent issues with serialising
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setTitle("Invalid name");
                        dialog.setContentText("Please do not include # or $ in your compound name.");
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        return;
                }
                String[] compoundArray = compoundString.split("\\|");

                ReagentNode<Reagent>[] reactants = new ReagentNode[compoundArray.length-1];
                for (int i = 1; i<compoundArray.length;i++){
                        reactants[i] = new ReagentNode<>(new Reagent(compoundArray[i]));
                }
                ReagentNode<Reagent> compound = new ReagentNode<>(new Reagent(compoundArray[0]),reactants);
                myCompounds.add(compound);
                superChemisTREE.insert(compound);
        }

        public void explore(ActionEvent actionEvent){
                ReagentNode<Reagent> compound = compoundsListView.getSelectionModel().getSelectedItem();
                if (compound==null){
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setTitle("No selection");
                        dialog.setContentText("Please select a compound to explore.");
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        return;
                }
                set_compound = compound;
                HelloApplication.openExploreTab();
        }

        public static void save(){
                //TODO: implement save
        }

}