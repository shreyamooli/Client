package controllers;

import client.Client;
import client.ClientFarmer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFarmerHome implements Initializable {

    @FXML
    public Label fName, lName, address, email, alias, balance;

    Client client;

    




    public void addClient(ClientFarmer client) {
        this.client = client;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showCrops(ActionEvent actionEvent) {

    }

    public void showKart(ActionEvent actionEvent) {
    }

    public void showChat(ActionEvent actionEvent) {
    }

    public void closeApp(ActionEvent actionEvent) {
        client.appClose();
    }
}
