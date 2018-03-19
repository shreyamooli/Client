package controllers;

import client.ClientFarmer;
import crops.Crop;
import helpers.TREC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerFarmerCrop {
    ClientFarmer clientFarmer;
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

    public void addClient(ClientFarmer clientFarmer) {
        this.clientFarmer = clientFarmer;
    }


}
