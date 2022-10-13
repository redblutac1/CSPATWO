package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ExploreController implements Initializable {
    @FXML
    public Button homeButton;
    @FXML
    public Label currentCompoundLabel;
    @FXML
    public ListView<ReagentNode<Reagent>> reactantListView;
    @FXML
    public Button goToCompoundButton;
    @FXML
    public Button backButton;

    ReagentNode<Reagent> product;
    ReagentNode<Reagent>[] reactants;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = MyCompoundsController.set_compound;
        reactants = (ReagentNode<Reagent>[]) product.neighbours;
        currentCompoundLabel.setText(product.getItem().getName());
        reactantListView.setItems(FXCollections.observableArrayList(reactants));
    }

    public void goHome(ActionEvent actionEvent) {
        HelloApplication.returnToMenu();
    }

    public void goToCompound(ActionEvent actionEvent) {
        ReagentNode<Reagent> selection = reactantListView.getSelectionModel().getSelectedItem();
        if (selection == null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("No selection");
            dialog.setContentText("Please select a compound to continue.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            return;
        }
        String name = selection.getItem().getName();
        Node<Reagent> newProduct = searchTree(name);
        if (newProduct == null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Exploration fail");
            dialog.setContentText("There is no further breakdown for this compound.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            return;
        }
        MyCompoundsController.set_compound = (ReagentNode<Reagent>) newProduct;
        HelloApplication.openExploreTab();
    }

    public void ascend(ActionEvent actionEvent) {
        HelloApplication.openMyCompoundsTab();
    }

    private Node<Reagent> searchTree(String name) {
        ChemisTREE<Reagent> sct = MyCompoundsController.superChemisTREE;
        Node<Reagent>[] nodes = sct.getRoot().neighbours;
        for (Node<Reagent> n : nodes) {
            if (n != null && n.getItem().toString().equals(name)) {
                return n;
            }
        }
        return null;
    }
}