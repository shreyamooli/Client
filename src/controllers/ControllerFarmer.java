package controllers;

import client.Client;
import client.ClientFarmer;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFarmer implements Initializable {
    static Client cli;
    static ClientFarmer cfi;

    @FXML
    AnchorPane topPane;
    @FXML
    public
    AnchorPane bottomPane;
    @FXML
    public AnchorPane chatFarm;
    @FXML
    public AnchorPane homeFarm;
    @FXML
    public AnchorPane cropFarm;
    @FXML
    public AnchorPane historyFarm;
    @FXML
    public
    ImageView pImage;
    @FXML
    public ImageView cImage;
    @FXML
    public
    Label pAddress;
    @FXML
    public
    Label pAlias, pBalance;
    @FXML
    public
    Label pEmail;
    @FXML
    public
    Label pName;
    @FXML
    public
    Label welcome;
    @FXML
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
    TextArea chatfarmer;
    @FXML
    public
    TextField chatSendBoxFarmer;
    @FXML
    public
    ToggleButton chatBtn;
    @FXML
    public
    ToggleButton cropBtn;
    @FXML
    public
    ToggleButton historyBtn;
    @FXML
    public
    ToggleButton homeBtn, cropAvailable;
    @FXML
    ToggleGroup bar1;


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
    public GridPane gridChat;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public JFXToggleButton chatAvail;


    public boolean farmBool = true;
    public boolean firstRun = true;
    public boolean cropBool = true;



    public void appClose(ActionEvent actionEvent) {
        cli.appClose();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeBtn.setTooltip(new Tooltip("show home tab"));
        cropBtn.setTooltip(new Tooltip("show crops"));
        chatBtn.setTooltip(new Tooltip("show chat tab"));
        historyBtn.setTooltip(new Tooltip("show history tab"));

    }

    public void addClient(Client client) {
        cli = client;
    }

    public void addImmediateClient(ClientFarmer cf) {
        this.cfi = cf;
    }

    public void setIconified(ActionEvent actionEvent) {
        cli.setIconified();
    }

    public void upload(ActionEvent actionEvent) {
        try {
            cfi.upload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void displayAddCrop(ActionEvent actionEvent) {
        cfi.displayAddCrop();
    }

    public void setupChatBox(MouseEvent mouseEvent) {
        //cfi.setupChatBox();
    }


    public void addCrop(ActionEvent actionEvent) {
        cfi.addCrop();
    }

    public void cropImage(ActionEvent actionEvent) {
        try {
            cfi.cropImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void updateChat(){
//        cfi.updateChat(chatAvail.isSelected());
//    }
}
