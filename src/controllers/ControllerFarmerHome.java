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

    ClientFarmer client;

    




    public void addClient(ClientFarmer client) {
        this.client = client;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showCrops(ActionEvent actionEvent) {
        client.showCrops();
    }

    public void showHistory(ActionEvent actionEvent) {
        client.showHistory();
    }

    public void showChat(ActionEvent actionEvent) {
        client.showChat();
    }

    public void closeApp(ActionEvent actionEvent) {
        client.appClose();
    }
}
