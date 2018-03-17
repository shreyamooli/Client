package controllers;

import client.Client;
import client.ClientCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCustomer implements Initializable {



    @FXML
    public AnchorPane bottomPaneCus, chatCustomer,homeCustomer,cropCustomer;
    @FXML
    ImageView cImage;
    @FXML
    public Label cAddress, cAlias, cEmail, cusName;
    @FXML
    public
    TextArea chatBoxCustomer;
    @FXML
    public
    TextField chatSendBoxCustomer, cropSearch, creditField;
    @FXML
    public TitledPane cBalance;
    @FXML
    public
    ToggleButton chatBtnCus;
    @FXML
    public
    ToggleButton cropBtnCus;
    @FXML
    public
    ToggleButton historyBtnCus;
    @FXML
    public
    ToggleButton homeBtnCus;
    @FXML
    ToggleGroup bar1;
    @FXML
    public GridPane gridChat;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public TableView farmerTable, cropTable;
    @FXML
    public TableColumn farmerColumn, cropName, cropWeight, cropQuantity, cropAvailable, cropOwner, cropCost;




    Client cli;
    ClientCustomer cc;
    public boolean firstRun = true;

    public void addClient(Client client) {
        cli = client;
    }

    public void addImmediateClient(ClientCustomer cc) {
        this.cc = cc;
        cc.addController(this);
    }


    public void addCredit(){
        if(creditField.getText().equalsIgnoreCase(""))
            return;
        int a = Integer.parseInt(creditField.getText());
        cc.addCredit(a);
    }

    public void loadWhoeverCus(ActionEvent actionEvent) {
        cc.loadWhoever(actionEvent);
    }

    public void appClose(ActionEvent actionEvent) {
        cli.appClose();
    }

    public void setIconified(ActionEvent actionEvent) {
        cli.setIconified();
    }

    public void sendMessage(ActionEvent actionEvent) {
        cc.sendMessage();
    }

    public void initiateChat(ActionEvent actionEvent) {
        cc.initiateChat();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeBtnCus.setTooltip(new Tooltip("show home tab"));
        cropBtnCus.setTooltip(new Tooltip("show crops"));
        chatBtnCus.setTooltip(new Tooltip("show chat tab"));
        historyBtnCus.setTooltip(new Tooltip("show kart tab"));

    }
}
