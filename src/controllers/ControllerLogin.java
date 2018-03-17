package controllers;

import client.Client;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {
    Client cli;

    @FXML
    public TextField signInEmail, signUpEmail,
            signInPassword, signUpPassword,
            signUpPassword2, signUpName;
    @FXML
    public
    JFXRadioButton radioFarmer;
    @FXML
    public
    JFXRadioButton radioCustomer;
    @FXML
    public
    TextField address;

    @FXML
    public AnchorPane block,signUpPage,signInPage;
    boolean signinshowing = false, finished = true;




    public void signIn(ActionEvent actionEvent) {
        try {
            cli.signIn();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void switchToSignUp(ActionEvent actionEvent) {

        radioCustomer.setSelected(false);
        radioFarmer.setSelected(false);
        address.setOpacity(0.0);



        ScaleTransition rt = new ScaleTransition(Duration.millis(350), block);
      rt.setByY(-1);
        rt.play();
        signInPage.setDisable(true);
        signUpPage.setDisable(true);


        double target = signinshowing ? 0 : 1;
        double target1 = signinshowing ? 1 : 0;
        KeyValue keyValue = new KeyValue(signInPage.opacityProperty(), target);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(350), keyValue));
        timeline.play();


        if(signinshowing){
            signUpPage.setDisable(false);
         //   signUpPage.setOpacity(1.0);
            signinshowing = false;

        }else{
            signInPage.setDisable(false);
          //  signInPage.setOpacity(1.0);
            signinshowing=true;
        }


        rt.setOnFinished(e->{

            if(!finished)
                return;
            rt.setByY(1);
            rt.play();
            KeyValue keyValue1 = new KeyValue(signUpPage.opacityProperty(), target1);
            Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(350), keyValue1));
            timeline1.play();

            finished=false;

        });

        finished = true;




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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList e = new ArrayList();
        e.add("will@g");
        e.add("myl@g");
        e.add("ill");
        e.add("pill");
        TextFields.bindAutoCompletion(signInEmail,e);
    }
}
