package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.cs5132_patwo.HelloApplication.mySuperChemisTREE;

/**
 For explanation of how this works, see MyCompoundsController.java file
 **/

public class ExploreController implements Initializable {
    @FXML
    public Label currentCompoundLabel;
    @FXML
    public ListView<Node<Reagent>> reactantListView;
    @FXML
    public Button goToCompoundButton;
    @FXML
    public Button backButton;

    ReagentNode<Reagent> product;
    Node<Reagent>[] reactants;

    //set up new scene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = MyCompoundsController.set_compound;
        reactants = product.getNonNullNeighbours();
        currentCompoundLabel.setText(product.getItem().getName());
        reactantListView.setItems(FXCollections.observableArrayList(reactants));
    }

    //deeper search
    public void goToCompound(ActionEvent actionEvent) {
        Node<Reagent> selection = reactantListView.getSelectionModel().getSelectedItem();
        if (selection == null) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("No selection");
            dialog.setContentText("Please select a compound to continue.");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            return;
        }
        String name = selection.getItem().getName();
        Node<Reagent> newProduct = searchTree(name);
        if (newProduct == null) { //compound has no further breakdowns
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

    //algorithm for searching all children of the root (O(n))
    private Node<Reagent> searchTree(String name) {
        ChemisTREE<Reagent> sct = mySuperChemisTREE;
        Node<Reagent>[] nodes = sct.getRoot().neighbours;
        for (Node<Reagent> n : nodes) {
            if (n != null && n.getItem().toString().equals(name)) {
                return n;
            }
        }
        return null;
    }
}