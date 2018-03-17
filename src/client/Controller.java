package client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label pName, pAlias, pAddress, pEmail, pBalance, errorLabel, cusName, cAlias, cEmail;
    @FXML
    TitledPane cBalance;
    @FXML
    ImageView pImage, cropImage;
    @FXML
    ToggleButton first, second, third;
    @FXML
    JFXButton signUpBtn;
    @FXML
    JFXToggleButton cAvail;
    @FXML
    TextField signInEmail, signUpEmail,
            signInPassword, signUpPassword,
            signUpPassword2, signUpName, chatSendBox;

    @FXML
    AnchorPane slider, farmSlider, custSlider;
    @FXML
    JFXRadioButton radioFarmer, radioCustomer, radioAvail;
    @FXML
    TextField address, cName, cWeight, cCost, cQuantity;
    @FXML
    ImageView homeicon = new ImageView();
    @FXML
    TableColumn ctName, ctWeight, ctCost, ctQuantity, ctAvailable;
    @FXML
    TableView cropTable;
    double xOffset, yOffset;
    boolean farmBool = true, cropBool = true, historyBool = true, chatBool = true;
    protected boolean selected;
    protected FXMLLoader floader;
    @FXML
    protected AnchorPane signUpPage, signInPage, anchorPane, errorPane, bottomPane,bottomPaneCus, mainFarm;
    protected boolean firstRun = true;
    @FXML
    protected GridPane chatHolderPane;
    protected boolean firstInfo = true;
    @FXML
    protected ToggleButton homeBtn, cropBtn, historyBtn, chatBtn, homeBtnCus, cropBtnCus, historyBtnCus, chatBtnCus;
    @FXML
     TextArea chatBoxFarm;
    @FXML
     TextField chatSendBoxFarmer;
    @FXML
     TextArea chatfarmer;


    @FXML
    protected TextArea chatBoxCustomer;
    @FXML
    protected TableView farmers;
    @FXML
    protected TextField chatSendBoxCustomer;

    Client cli;
    ClientFarmer cf;
    ClientCustomer cc;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    //** section for login

    public void signIn(ActionEvent actionEvent)  {
        try {
            cli.signIn();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void switchToSignUp(ActionEvent actionEvent) {
        cli.switchToSignUp();
    }

    public void signUp(ActionEvent actionEvent) {
        try {
            cli.signUp();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void radioChanged(ActionEvent actionEvent) {
        cli.radioChanged(actionEvent);
    }

    public void appClose(ActionEvent actionEvent) {
        cli.appClose();
    }

    public void setIconified(ActionEvent actionEvent) {
        cli.setIconified();
    }

    public void addClient(Client client) {
        cli = client;
    }

    public void loadWhoeverFarm(ActionEvent actionEvent) {
        cf.loadWhoever(actionEvent);
    }
    public void loadWhoeverCus(ActionEvent actionEvent) {
cc.loadWhoever(actionEvent);
    }

    public void addCrop(ActionEvent actionEvent) {
        cf.displayAddCrop();
    }

    public void cropImage(ActionEvent actionEvent) {
        try {
            cf.cropImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addCustomer(ClientCustomer customer) {
        cc = customer;
    }

    public void sendMessage(ActionEvent actionEvent) {
        cc.sendMessage();
    }

    public void initiateChat(ActionEvent actionEvent) {
        cc.initiateChat();
    }

    public void setupChatBox(MouseEvent mouseEvent) {
      //  cf.setupChatBox();
    }

    public void setTable(MouseEvent mouseEvent) {
        cf.setTable();
    }

    public void displayAddCrop(ActionEvent actionEvent) {
        cf.displayAddCrop();
    }

    public void shift(MouseEvent mouseEvent) {
       cf.shift();
    }

    public void upload(ActionEvent actionEvent) {
        try {
            cf.upload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void addFarmer(ClientFarmer cf) {
        this.cf = cf;
    }

    //** farmers options
    
    
    
}
