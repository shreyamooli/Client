package controllers;

import client.Client;
import client.ClientFarmer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFarmerHome implements Initializable {

    @FXML
    public Label fName, address, email, alias, balance;
    @FXML
    public JFXToggleButton chat, cropAvailable;
    @FXML
    public AnchorPane chatPane, cropPane, crops, historyPane, homePane, middle;
    @FXML
            public GridPane gridChat, gridHistory;
    @FXML
            public ImageView cImage;
    @FXML public JFXTextField cCost, cName, cQuantity, cWeight;
    @FXML public TableColumn ctAvailable, ctCost, ctName, ctQuantity, ctWeight;
    @FXML public TableView cropTable ;
    @FXML public TextField chatSendBoxFarmer;
    


    ClientFarmer client;

    




    public void addClient(ClientFarmer client) {
        this.client = client;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void showCrops(ActionEvent actionEvent) {
        client.showCrops();
    }

    @FXML
    private void showHistory(ActionEvent actionEvent) {
        client.showHistory();
    }

    @FXML
    private void showChat(ActionEvent actionEvent) {
        client.showChat();
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
        client.appClose();
    }

    @FXML
    private void updateAvailability(){
        client.updateAvailability(chat.isSelected());
    }


    public void cropImage(ActionEvent actionEvent) {
    }

    public void displayAddCrop(ActionEvent actionEvent) {
    }

    public void sendMessage(ActionEvent actionEvent) {
    }

    public void showHome(ActionEvent actionEvent) {
    }
}
