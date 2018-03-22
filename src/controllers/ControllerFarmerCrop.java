package controllers;

import client.ClientFarmer;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

public class ControllerFarmerCrop {
    public
    TableColumn ctAvailable;
    @FXML
    public
    TableColumn ctCost;
    @FXML
    public
    TableColumn ctName;
    @FXML
    public
    TableColumn ctQuantity;
    @FXML
    public
    TableColumn ctWeight;
    @FXML
    public
    TableView cropTable;
    @FXML
    public
    TextField cName;
    @FXML
    public
    TextField cCost;
    @FXML
    public
    TextField cQuantity;
    @FXML
    public
    TextField cWeight;
    @FXML
    public ImageView cImage;
    @FXML
    public
    ToggleButton homeBtn, cropAvailable;
    ClientFarmer clientFarmer;

    public void addClient(ClientFarmer clientFarmer) {
        this.clientFarmer = clientFarmer;
    }


}
