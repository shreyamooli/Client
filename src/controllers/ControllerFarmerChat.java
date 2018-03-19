package controllers;

import client.ClientFarmer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerFarmerChat {

    ClientFarmer clientFarmer;

    @FXML
    public GridPane gridChat;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public JFXToggleButton chatAvail;
    @FXML
    public
    TextArea chatfarmer;
    @FXML
    public
    TextField chatSendBoxFarmer;

    public void addClient(ClientFarmer clientFarmer) {
        this.clientFarmer = clientFarmer;
    }

    public void updateChat(boolean selected) {
        try {
            clientFarmer.os.writeObject("update selected");
            clientFarmer.os.writeObject(clientFarmer.getUser().getEmail());
            clientFarmer.os.writeObject(selected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
