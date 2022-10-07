package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class ExampleCompoundsController implements Initializable {
    @FXML
    public ComboBox comboBox;
    @FXML
    public Button showButton;
    @FXML
    public Label reagent1;
    @FXML
    public Label reagent2;
    @FXML
    public Label reagent3;
    @FXML
    public Label reagent4;
    @FXML
    public Label reagent5;
    @FXML
    public Label reagent6;
    @FXML
    public Label reagent7;
    @FXML
    public Label reagent8;
    @FXML
    public Label reagent9;
    @FXML
    public Label reagent10;
    @FXML
    public ImageView productImage;
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;
    @FXML
    public ImageView image4;
    @FXML
    public ImageView image5;
    @FXML
    public ImageView image6;
    @FXML
    public ImageView image7;
    @FXML
    public ImageView image8;
    @FXML
    public ImageView image9;
    @FXML
    public ImageView image10;

    public Label[] labels;
    public ImageView[] imageViews;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.getItems().addAll(HelloApplication.superChemisTREE.getStringChildren());
        Collections.sort(comboBox.getItems());

        labels = new Label[]{reagent1, reagent2, reagent3, reagent4, reagent5, reagent6, reagent7, reagent8, reagent9, reagent10};
        imageViews = new ImageView[]{image1, image2, image3, image4, image5, image6, image7, image8, image9, image10};
    }

    public void returnToMenu(ActionEvent actionEvent) {
        HelloApplication.returnToMenu();
    }

    synchronized public void showSynthesis(ActionEvent actionEvent) {
        if (comboBox.getSelectionModel().getSelectedItem() != null) {
            String product = comboBox.getSelectionModel().getSelectedItem().toString();
            ReagentNode<Reagent> reagentNode = HelloApplication.superChemisTREE.findNode(product);

            for (int i = 0; i < reagentNode.getNumNeighbours(); i++) {
                try {
                    String name = reagentNode.neighbours[i].toString();

                    // For the image of the product, set it once only
                    if (i == 0) {
                        moleculeToFile(reagentNode.getItem().getMolecule(), product);
                        productImage.setImage(new Image(new File("src/main/resources/molecules/" + product + ".png").toURI().toString()));
                        productImage.setVisible(true);
                    }

                    moleculeToFile(reagentNode.neighbours[i].getItem().getMolecule(), name);
                    labels[i].setText(name);
                    imageViews[i].setImage(new Image(new File("src/main/resources/molecules/" + name + ".png").toURI().toString()));
                    labels[i].setVisible(true);
                } catch (CDKException | IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public void moleculeToFile(IAtomContainer container, String name) throws CDKException, IOException, InterruptedException {
        DepictionGenerator dg = new DepictionGenerator().withSize(256, 256).withAtomColors();
        dg.depict(container).writeTo("src/main/resources/molecules/" + name + ".png");
    }
}
