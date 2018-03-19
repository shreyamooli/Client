package controllers;

import client.Client;
import client.ClientFarmer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFarmerHome implements Initializable {

    @FXML
    public Label fName, address, email, alias, balance;
    @FXML
    public JFXToggleButton chat;

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


}
