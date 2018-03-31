package controllers;

import client.Client;
import client.ClientCustomer;
import interfaces.Speed;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {




    @FXML
    public AnchorPane chatPane, cropPane, crops,homePane,kartPane,middle;
    @FXML
    public GridPane gridChat, grisHistory;
    @FXML
            public Label alias, email, fName, shoppingKart;
    @FXML
            public ScrollPane scrollPane, scrollPane1;

    @FXML public TableColumn ctAvailable, ctCost, ctName, ctQuantity, ctWeight, ctOwner, chatColumn;
    @FXML public TableView cropTable, chatTable;
    @FXML public TextArea chatBox;
    @FXML public TextField chatField, creditField;
    @FXML public TitledPane cBalance;


    public ControllerCustomer() {
    }

    ClientCustomer clientCustomer;
    public boolean firstRun = true;

    public void addClient(ClientCustomer client) {
        clientCustomer = client;
        client.addController(this);
    }



    @FXML
    private void addCredit(){
        if(creditField.getText().equalsIgnoreCase(""))
            return;
        int a = Integer.parseInt(creditField.getText());
        clientCustomer.addCredit(a);
    }


    @FXML
    private void appClose(ActionEvent actionEvent) {
        clientCustomer.appClose();

    }

    @FXML
    private void setIconified(ActionEvent actionEvent) {

    }

    @FXML
    private void sendMessage(ActionEvent actionEvent) {
        clientCustomer.sendMessage();
    }

    @FXML
    private void initiateChat(ActionEvent actionEvent) {
        clientCustomer.initiateChat();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    private void showHome(ActionEvent actionEvent) {
        show(homePane);
    }

    @FXML
    private void showCrop(ActionEvent actionEvent) {
        show(cropPane);
    }

    @FXML
    private void showHistory(ActionEvent actionEvent) {
        show(kartPane);
    }

    @FXML
    private void showChat(ActionEvent actionEvent) {
        show(chatPane);
    }

    @FXML
    private void loadImage(ActionEvent actionEvent) {
        clientCustomer.loadImage();
    }

    @FXML
    private void chooseColor(ActionEvent actionEvent) {
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
    }

    private void show(AnchorPane pane) {

        chatPane.setOpacity(0);
        kartPane.setOpacity(0);
        cropPane.setOpacity(0);
        homePane.setOpacity(0);

        chatPane.setDisable(true);
        kartPane.setDisable(true);
        cropPane.setDisable(true);
        homePane.setDisable(true);

        pane.toFront();
        pane.setDisable(false);

        KeyValue keyValue1 = new KeyValue(pane.opacityProperty(), 1);
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(Speed.FAST), keyValue1));
        timeline1.play();
    }
}
