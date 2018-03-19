package client;

import com.jfoenix.controls.JFXToggleButton;
import controllers.*;
import crops.Crop;
import helpers.TREC;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import users.Farmer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static client.Client.primaryStage;

public class ClientFarmer extends Client  {




    Thread t;
    ObjectOutputStream meOut;
    ObjectInputStream in;
    File file;
    PopOver pp;
    Farmer user = new Farmer();

    ControllerFarmerHome controllerFarmerHome;
    ControllerFarmerCrop controllerFarmerCrop;
    ControllerFarmerHistory controllerFarmerHistory;
    ControllerFarmerChat controllerFarmerChat;

    TextField name,weight,cost,quantity;
    JFXToggleButton available;
    ImageView imageView;


    private AnchorPane root1, root2, root3;
    private Stage crop= new Stage(), history= new Stage(), chat= new Stage();



    private boolean chatBoxBool = true;
    public boolean farmBool = true;
    public boolean firstRun = true;
    private boolean cropBool = true;

    public ClientFarmer(ControllerFarmerHome cf, Farmer f) {

        this.user = f;
        controllerFarmerHome = cf;
        controllerFarmerHome.addClient(this);
        try {

         //   loadAll();
            configureDisplay();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setUser(Farmer f) {
        this.user = f;
    }

    private void configureDisplay() {

        controllerFarmerHome.fName.setText(user.getFullName());
        controllerFarmerHome.address.setText(user.getAddress());
        controllerFarmerHome.alias.setText(user.getAlias());
        controllerFarmerHome.email.setText(user.getEmail());
        controllerFarmerHome.chat.setSelected(user.isAvailable());
        controllerFarmerHome.balance.setText("BALANCE : "+String.valueOf(user.getBalance()));
    }

    private void loadAll() throws IOException {
        floader = new FXMLLoader(getClass().getResource("/windows/FarmerCrop.fxml"));
        root1=floader.load();
        controllerFarmerCrop = floader.<ControllerFarmerCrop>getController();
        controllerFarmerCrop.addClient(this);

        floader = new FXMLLoader(getClass().getResource("/windows/FarmerHistory.fxml"));
        root2=floader.load();
        controllerFarmerHistory = floader.<ControllerFarmerHistory>getController();
        controllerFarmerHistory.addClient(this);

        floader = new FXMLLoader(getClass().getResource("/windows/FarmerChat.fxml"));
        root3=floader.load();
        controllerFarmerChat = floader.<ControllerFarmerChat>getController();
        controllerFarmerChat.addClient(this);


    }


    public void showCrops() {

       if(crop!=null && !crop.isShowing()) {
           crop.show();
           return;
       }
       crop.setScene(new Scene(root1));
       crop.show();

    }

    public void showHistory() {
        if(history!=null && !history.isShowing()) {
            history.show();
            return;
        }
        history.setScene(new Scene(root2));
        history.show();
    }

    public void showChat() {
        if(chat!=null && !chat.isShowing()) {
            chat.show();
            return;
        }
        chat.setScene(new Scene(root3));
        chat.show();
    }

   /* public void shift() {


        if (!controllerFarmerChat.farmBool)
            return;

        try {
            cf.pName.setText("Name : " + user.getFullName());
            cf.pAlias.setText("Alias : " + user.getAlias());
            cf.pAddress.setText("Address : " + user.getAddress());
            cf.pEmail.setText("Email : " + user.getEmail());
            double num = user.getBalance();
            //todo format num into dollars
            cf.pBalance.setText("$"+num+"");
            os.writeObject("getImage");
            os.writeObject(user);


            try {
                File file = (File) is.readObject();
                if(file!=null)
                cf.pImage.setImage(new Image(file.toURI().toString()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cf.farmBool = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
*/
//    protected void loadFarm(AnchorPane p) {
//        try {
//
//            cf.welcome.setOpacity(0.0);
//            cf.welcome.setDisable(true);
//            cf.homeFarm.setOpacity(0.0);
//            cf.cropFarm.setOpacity(0.0);
//            cf.historyFarm.setOpacity(0.0);
//            cf.chatFarm.setOpacity(0.0);
//
//            cf.homeFarm.setDisable(true);
//            cf.cropFarm.setDisable(true);
//            cf.historyFarm.setDisable(true);
//            cf.chatFarm.setDisable(true);
//
//            p.setDisable(false);
//            p.toFront();
//
//            KeyValue keyValue = new KeyValue(p.opacityProperty(), 1);
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(350), keyValue));
//            timeline.play();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


   /* public void setupChatBox() {
        if (!chatBoxBool)
            return;
        logger.info("setting up chatbox");
        t = new Thread(new StartServer());
        t.start();
        cf.gridChat.setVgap(1);

        chatBoxBool = false;
    }
*/



   //todo set up chat box





    public void getHistory() {

        try {
            os.writeObject("getHistory");
            ResultSet rs = (ResultSet) is.readObject();

            while (rs.next()) {
                GridPane gp = new GridPane();
                Label cropName, customerName, quantity, amount;
                cropName = new Label(rs.getString(1));
                customerName = new Label(rs.getString(2));
                quantity = new Label(String.valueOf(rs.getDouble(3)));
                amount = new Label(String.valueOf(rs.getDouble(4)));
                gp.setMinWidth(300);
                gp.add(cropName, 1, 1);
                gp.add(customerName, 1, 2);
                gp.add(quantity, 1, 3);
                gp.add(amount, 1, 4);

            }


        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }






    public void upload() throws IOException {
        FileChooser fc = new FileChooser();
        File fil = fc.showOpenDialog(primaryStage);
        if (fil == null)
            return;

        Image ill = new Image(fil.toURI().toString());
      //todo  cf.pImage.setImage(ill);
        user.setImage(ill.toString().getBytes());

        os.writeObject("uploadUserImage");
        os.writeObject(user);
        os.writeObject(fil.getAbsoluteFile());

    }


    //crops operations

    public void addCrop() {


        if (checkCropValues()) {
            Crop c = new Crop(controllerFarmerCrop.cName.getText(), Double.valueOf(controllerFarmerCrop.cWeight.getText()), Double.valueOf(controllerFarmerCrop.cCost.getText()), Double.valueOf(controllerFarmerCrop.cQuantity.getText()), controllerFarmerCrop.cropAvailable.isSelected(), file);
            c.setOwner(user.getEmail());
            try {
                os.writeObject("addCrop");
                os.writeObject(c);
            } catch (IOException e) {
                logger.error(e.getMessage());
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
        controllerFarmerCrop.cName.clear();
        controllerFarmerCrop.cWeight.clear();
        controllerFarmerCrop.cCost.clear();
        controllerFarmerCrop.cQuantity.clear();
        controllerFarmerCrop.cropAvailable.setSelected(false);
        controllerFarmerCrop.cImage.setImage(null);

    }


    public void cropImage() throws IOException {

        FileChooser fc = new FileChooser();
        File fil = fc.showOpenDialog(primaryStage);
        if(fil==null)
            return;
        file = fil;
        Image ill = new Image(fil.toURI().toString());
        controllerFarmerCrop.cImage.setImage(ill);

    }

    private boolean checkCropValues() {

        if (controllerFarmerCrop.cName.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerCrop.cWeight.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerCrop.cCost.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerCrop.cQuantity.getText().compareTo("") == 0)
            return false;

        return true;

    }

    private void updateTable() {
        ArrayList list = getArrayList();
        ObservableList<Object> cropList = FXCollections.observableArrayList(list);

        controllerFarmerCrop.cropTable.setItems(null);
        controllerFarmerCrop.cropTable.getColumns().removeAll();
        controllerFarmerCrop.cropTable.setItems(cropList);


    }

    private ArrayList getArrayList() {
        try {
            os.writeObject("getObservableList");
            os.writeObject(user);


            return (ArrayList) is.readObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList();
    }


    public void setTable() {

        if (!cropBool)
            return;

        ArrayList list = getArrayList();
        ObservableList<Object> cropList = FXCollections.observableArrayList(list);
        controllerFarmerCrop.ctName.setCellValueFactory(new PropertyValueFactory("Name"));
        controllerFarmerCrop.ctWeight.setCellValueFactory(new PropertyValueFactory("Weight"));
        controllerFarmerCrop.ctCost.setCellValueFactory(new PropertyValueFactory("Cost"));
        controllerFarmerCrop.ctQuantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
        controllerFarmerCrop.ctAvailable.setCellValueFactory(new PropertyValueFactory("Available"));


        TREC expans = getExpanse();

        controllerFarmerCrop.cropTable.setItems(null);
        controllerFarmerCrop.cropTable.getColumns().removeAll();
        controllerFarmerCrop.cropTable.getColumns().add(0, expans);
        controllerFarmerCrop.cropTable.setItems(cropList);



        cropBool = false;
    }

    private TREC getExpanse() {
        TREC<Object> expans = new TREC<> (param -> {

            //   Crop c = (Crop) param.getTableRow().getItem();
            HBox hbox = new HBox();
            HBox hboxbtn = new HBox();
            GridPane editor = new GridPane();
            editor.setBackground(new Background(new BackgroundFill(Color.gray(0.95), CornerRadii.EMPTY, Insets.EMPTY)));


            Crop c = (Crop) param.getValue();

            //trying values here
            TextField editWeight = new TextField();
            TextField editCost = new TextField();
            TextField editQuantity = new TextField();
            TextField editAvailable = new TextField();



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
                    os.writeObject("updateCrop");
                    os.writeObject(c);
                    os.writeObject(user);
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

    //chat options


    public void sendMessage() {
        String msg = controllerFarmerChat.chatSendBoxFarmer.getText();
        if(msg.compareTo("")==0)
            return;
        Label lab = new Label(msg);

        controllerFarmerChat.chatSendBoxFarmer.clear();
        lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
        lab.getStyleClass().add("out");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controllerFarmerChat.gridChat.addRow(controllerFarmerChat.gridChat.getChildren().size()+1,lab);
                Animation animation = new Timeline(
                        new KeyFrame(Duration.seconds(.3),
                                new KeyValue(controllerFarmerChat.scrollPane.vvalueProperty(), 1)));
                animation.play();
            }
        });

        try {
            meOut.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateAvailability(boolean selected) {
        try {
            this.user.setAvailable(selected);
            super.os.writeObject("updateFarmer");
            super.os.writeObject(this.user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



/*
    private class Server implements Runnable {

        Socket socket;
        String msg = "";
        TextArea t;


        public Server(Socket socket) {
            this.socket = sock;
        }

        @Override
        public void run() {


            while (msg.compareTo("0000") != 0) {

                try {
                    msg = (String) in.readObject();
                    Label lab = new Label(msg);

                    lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                    lab.getStyleClass().add("in");
                    Pane huh = new Pane();
                    huh.setMinSize(410,19);
                    huh.setPrefSize(410,19);
                    huh.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                    huh.getStyleClass().add("paneHolder");

                    huh.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                    huh.getChildren().add(lab);
                    lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                    lab.getStyleClass().add("in");

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controllerFarmerChat.gridChat.addRow(controllerFarmerChat.gridChat.getChildren().size()+1,huh);
                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(.3),
                                            new KeyValue(controllerFarmerChat.scrollPane.vvalueProperty(), 1)));
                            animation.play();
                        }
                    });


                    //controllerFarmerChat.chatfarmer.appendText("n"+msg);


                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    private class StartServer implements Runnable {

        TextArea ta = new TextArea();

        public StartServer() {

        }

        @Override
        public void run() {


            try {
                ta = controllerFarmerChat.chatfarmer;


                ServerSocket serverSocket = new ServerSocket(4000);
                Socket socket = serverSocket.accept();
                logger.info("client server accepted");
//                chatBoxFarm.appendText("client connected");
              //  controllerFarmerChat.chatfarmer.appendText("client connected \n\n");
                Label lab = new Label("Customer Connected");
                lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                lab.getStyleClass().add("mid");
                lab.setTranslateX((433/2.75));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controllerFarmerChat.gridChat.addRow(1,lab);
                        controllerFarmerChat.gridChat.setVgap(2);
                        controllerFarmerChat.scrollPane.setFitToHeight(true);
//                        controllerFarmerChat.gridChat.add



                    }
                });
                meOut = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                Thread t = new Thread(new Server(socket));
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }*/
}




