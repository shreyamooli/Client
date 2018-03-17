package controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCrop implements Initializable{

    @FXML
    public TextField cName, cWeight, cCost, cQuantity;
    @FXML
    public JFXToggleButton cAvail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void cropImage(ActionEvent actionEvent) {
    }

    public void addCrop(ActionEvent actionEvent) {
    }
}
