package controllers;

import client.ClientFarmer;
import crops.Crop;
import helpers.TREC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerFarmerCrop {
    ClientFarmer clientFarmer;

    public void addClient(ClientFarmer clientFarmer) {
        this.clientFarmer = clientFarmer;
    }

    public void addCrop() {


        if (checkCropValues()) {
            Crop c = new Crop(cf.cName.getText(), Double.valueOf(cf.cWeight.getText()), Double.valueOf(cf.cCost.getText()), Double.valueOf(cf.cQuantity.getText()), cf.cropAvailable.isSelected(), file);
            c.setOwner(user.getEmail());
            try {
                cli.os.writeObject("addCrop");
                cli.os.writeObject(c);
            } catch (IOException e) {
                cli.logger.error(e.getMessage());
                e.printStackTrace();
            }



        }


    }

    public void displayAddCrop() {

        addCrop();
        updateTable();
        clearCropBar();


    }

    private void clearCropBar() {
        cf.cName.clear();
        cf.cWeight.clear();
        cf.cCost.clear();
        cf.cQuantity.clear();
        cf.cropAvailable.setSelected(false);
        cf.cImage.setImage(null);

    }


    public void cropImage() throws IOException {

        FileChooser fc = new FileChooser();
        File fil = fc.showOpenDialog(primaryStage);
        if(fil==null)
            return;
        file = fil;
        Image ill = new Image(fil.toURI().toString());
        cf.cImage.setImage(ill);

    }

    private boolean checkCropValues() {

        if (cf.cName.getText().compareTo("") == 0)
            return false;
        if (cf.cWeight.getText().compareTo("") == 0)
            return false;
        if (cf.cCost.getText().compareTo("") == 0)
            return false;
        if (cf.cQuantity.getText().compareTo("") == 0)
            return false;

        return true;

    }

    private void updateTable() {
        ArrayList list = getArrayList();
        ObservableList<Object> cropList = FXCollections.observableArrayList(list);

        cf.cropTable.setItems(null);
        cf.cropTable.getColumns().removeAll();
        cf.cropTable.setItems(cropList);


    }

    private ArrayList getArrayList() {
        try {
            cli.os.writeObject("getObservableList");
            cli.os.writeObject(user);


            return (ArrayList) cli.is.readObject();
        } catch (IOException e) {
            cli.logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            cli.logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList();
    }


    public void setTable() {

        if (!cf.cropBool)
            return;

        ArrayList list = getArrayList();
        ObservableList<Object> cropList = FXCollections.observableArrayList(list);
        cf.ctName.setCellValueFactory(new PropertyValueFactory("Name"));
        cf.ctWeight.setCellValueFactory(new PropertyValueFactory("Weight"));
        cf.ctCost.setCellValueFactory(new PropertyValueFactory("Cost"));
        cf.ctQuantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
        cf.ctAvailable.setCellValueFactory(new PropertyValueFactory("Available"));


        TREC expans = getExpanse();

        cf.cropTable.setItems(null);
        cf.cropTable.getColumns().removeAll();
        cf.cropTable.getColumns().add(0, expans);
        cf.cropTable.setItems(cropList);



        cf.cropBool = false;
    }

    private TREC getExpanse() {
        TREC<Object> expans = new TREC<> (param -> {

            //   Crop c = (Crop) param.getTableRow().getItem();
            HBox hbox = new HBox();
            HBox hboxbtn = new HBox();
            GridPane editor = new GridPane();
            editor.setBackground(new Background(new BackgroundFill(Color.gray(0.95), CornerRadii.EMPTY, Insets.EMPTY)));


            Crop c = (Crop) param.getValue();


            editWeight.setPromptText("edit weight");
            editCost.setPromptText("edit cost");
            editQuantity.setPromptText("edit quatity");
            editAvailable.setPromptText("edit availablity");


            editWeight.setPrefSize(100, 25);
            editCost.setPrefSize(100, 25);
            editQuantity.setPrefSize(100, 25);
            editAvailable.setPrefSize(100, 25);

//
//
            hbox.getChildren().addAll(editWeight, editCost, editQuantity, editAvailable);
//
            editor.setVgap(17);
//
            Button save = new Button("Save");
            save.setOnAction(event -> {
                // save();

                if(editCost.getText().compareToIgnoreCase("")!=0){
                    c.setCost(Double.parseDouble(editCost.getText()));
                }
                if(editQuantity.getText().compareToIgnoreCase("")!=0){
                    c.setQuantity(Double.parseDouble(editQuantity.getText()));
                }
                if(editWeight.getText().compareToIgnoreCase("")!=0){
                    c.setWeight(Double.parseDouble(editWeight.getText()));
                }


                //  Crop c = (Crop) param.getTableRow().getItem();
                try {
                    cli.os.writeObject("updateCrop");
                    cli.os.writeObject(c);
                    cli.os.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //todo save something
                param.toggleExpanded();
            });
            Button cancel = new Button("Cancel");
            cancel.setOnAction(event -> {
                // save();
                param.toggleExpanded();
            });

            hboxbtn.getChildren().addAll(save, cancel);
            save.getStyleClass().add("saveAndCancel");
            cancel.getStyleClass().add("saveAndCancel");

            hbox.setSpacing(5);
            hboxbtn.setSpacing(15);
            hbox.setPrefHeight(45);
            hboxbtn.setPrefHeight(45);

            editor.addRow(1, hbox);
            editor.addRow(2, hboxbtn);

            editWeight.setPadding(new Insets(5));
            editCost.setPadding(new Insets(5));
            editQuantity.setPadding(new Insets(5));
            editAvailable.setPadding(new Insets(5));
            editor.setPadding(new Insets(5, 5, 5, 5));

            return editor;
        });

        return expans;
    }

}
