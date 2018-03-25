package controllers;

import client.ClientFarmer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
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

import java.io.IOException;
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
    @FXML public ScrollPane scrollPane;
    


    ClientFarmer client;

    




    public void addClient(ClientFarmer client) {
        this.client = client;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    private void showCrop(ActionEvent actionEvent) {
        show(cropPane);
    }

    @FXML
    private void showHistory(ActionEvent actionEvent) {
        show(historyPane);
    }

    @FXML
    private void showChat(ActionEvent actionEvent) {
show(chatPane);    }
    @FXML
    private void showHome(ActionEvent actionEvent) {
        show(homePane);
    }

    private void show(AnchorPane pane) {

        chatPane.setOpacity(0);
        historyPane.setOpacity(0);
        cropPane.setOpacity(0);
        homePane.setOpacity(0);

        chatPane.setDisable(true);
        historyPane.setDisable(true);
        cropPane.setDisable(true);
        homePane.setDisable(true);

        pane.toFront();
        pane.setDisable(false);

        KeyValue keyValue1 = new KeyValue(pane.opacityProperty(), 1);
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(Speed.FAST), keyValue1));
        timeline1.play();
    }

    @FXML
    private void closeApp(ActionEvent actionEvent) {
        client.appClose();
    }

    @FXML
    private void updateAvailability(){
        client.updateAvailability(chat.isSelected());
    }

    @FXML
    private void cropImage(ActionEvent actionEvent) {
        try {
            client.cropImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void addCrop(ActionEvent actionEvent) {
        client.addCrop();
    }
    @FXML
    private void sendMessage(ActionEvent actionEvent) {
    }

    @FXML
    private void chooseColor(){
        client.chooseColor();
    }

}
