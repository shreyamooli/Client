package client;

import com.jfoenix.controls.JFXButton;
import controllers.ControllerCustomer;
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
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jfxtras.scene.control.window.CloseIcon;
import jfxtras.scene.control.window.MinimizeIcon;
import jfxtras.scene.control.window.Window;
import kart.Kart;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCustomer extends Client {

    Thread t;
    listenchat l;
    static ObjectOutputStream myOut;
    static ObjectInputStream  myIn;
    private ControllerCustomer controllerCustomer;

    TextField editWeight = new TextField();
    TextField editCost = new TextField();
    TextField editQuantity = new TextField();
    TextField editAvailable = new TextField();


    public ClientCustomer() throws IOException {

    }



    public void initiateChat(){



       l= this.new listenchat();


        t = new Thread(l);
        t.start();



    }

    public void sendMessage(){
        String msg = controllerCustomer.chatField.getText();
        controllerCustomer.chatField.clear();
        Label lab = new Label(msg);
        lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
        lab.getStyleClass().add("out");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controllerCustomer.gridChat.addRow(controllerCustomer.gridChat.getChildren().size()+1,lab);
                Animation animation = new Timeline(
                        new KeyFrame(Duration.seconds(.3),
                                new KeyValue(controllerCustomer.scrollPane.vvalueProperty(), 1)));
                animation.play();
            }
        });


       // l.sendMessage(msg);
        try {
            myOut.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








    private void setCustomer() {

        controllerCustomer.email.setText("Email : "+user.getEmail());
        controllerCustomer.fName.setText("Name : "+user.getFullName());
        controllerCustomer.alias.setText("customer");
        controllerCustomer.cBalance.setText("Balance : $"+String.valueOf(user.getBalance()));

       // getFarmersForChat();
        getCrops();
        updateKart();

    }


    private void getCrops(){
        try {
            os.writeObject("getCropsForCustomer");
            ArrayList e  = (ArrayList) is.readObject();
            ObservableList<Object> crops = FXCollections.observableArrayList(e);

            controllerCustomer.ctName.setCellValueFactory(new PropertyValueFactory("name"));
            controllerCustomer.ctWeight.setCellValueFactory(new PropertyValueFactory("weight"));
            controllerCustomer.ctCost.setCellValueFactory(new PropertyValueFactory("cost"));
            controllerCustomer.ctQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
            controllerCustomer.ctAvailable.setCellValueFactory(new PropertyValueFactory("available"));
            controllerCustomer.ctOwner.setCellValueFactory(new PropertyValueFactory("owner"));
            controllerCustomer.cropTable.setItems(crops);


            TREC expans = getExpanse();

            controllerCustomer.cropTable.getColumns().add(expans);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


   /* private TREC getExpanse() {
        TREC<Object> expans = new TREC<>(param -> {

            //   Crop c = (Crop) param.getTableRow().getItem();
            HBox hbox = new HBox();
            HBox hboxbtn = new HBox();
            GridPane editor = new GridPane();
            editor.setBackground(new Background(new BackgroundFill(Color.gray(0.95), CornerRadii.EMPTY, Insets.EMPTY)));


            Crop c = (Crop) param.getValue();
            double weight;
            final double[] cost = new double[1];
            double quantity;


            editQuantity.setPromptText("quatity");
           editQuantity.setPrefSize(100, 25);



//
//
            hbox.getChildren().addAll( editQuantity);
//
            editor.setVgap(17);
//
            Button save = new Button("add to kart");
            save.setOnAction(event -> {




                if(editQuantity.getText().compareToIgnoreCase("")!=0){
                    cost[0] = Double.parseDouble(editQuantity.getText());
                }

                writeToKart(c,cost[0]);

                //todo save something
                param.toggleExpanded();
            });
            Button cancel = new Button("Cancel");
            cancel.setOnAction(event -> {
                // save();
                param.toggleExpanded();
            });

            hboxbtn.getChildren().addAll(save, cancel);
            save.getStyleClass().add("saveandcancel");
            cancel.getStyleClass().add("saveandcancel");



            hbox.setSpacing(5);
            hboxbtn.setSpacing(15);
            hbox.setPrefHeight(45);
            hboxbtn.setPrefHeight(45);

            editor.addRow(1, hbox);
            editor.addRow(1, hboxbtn);

            editWeight.setPadding(new Insets(5));
            editCost.setPadding(new Insets(5));
            editQuantity.setPadding(new Insets(5));
            editAvailable.setPadding(new Insets(5));
            editor.setPadding(new Insets(5, 5, 5, 5));

            return editor;
        });

        return expans;
    }*/




    private TREC getExpanse() {
        TREC<Object> expans = new TREC<>(param -> {

            //   Crop c = (Crop) param.getTableRow().getItem();


            Crop c = (Crop) param.getValue();
            GridPane gridPane = new GridPane();

            JFXButton buy = new JFXButton("Buy");
            JFXButton cancel = new JFXButton("Cancel");
            Label label = new Label("Quantity:");
            label.setPrefWidth(89);

            ChoiceBox<Integer> choices = new ChoiceBox<>();
            choices.setValue(1);
          //  choices.setPrefSize(80,40);

            for (int i = 0; i < c.getQuantity() ; i++) {
                choices.getItems().add(i+1);
            }

            gridPane.addRow(1,label,choices,buy,cancel);
            gridPane.setHgap(15);
            gridPane.setVgap(10);
            gridPane.getStylesheets().add(getClass().getResource("/windows/FarmerHome.css").toExternalForm());
            buy.getStyleClass().add("btnSend");
            cancel.getStyleClass().add("btnSend");




            Window window = new Window("New Item");
            window.setPrefSize(340,100);
            window.getContentPane().getChildren().add(gridPane);
      //      window.getRightIcons().add(new CloseIcon(window));

            controllerCustomer.crops.getChildren().add(window);
            cancel.setOnAction(e->{
                window.close();
            });
            buy.setOnAction(e->{
                writeToKart(c,choices.getValue());
                window.close();
            });

            window.setVisible(true);


            GridPane giridPane = new GridPane();
            param.toggleExpanded();

            return giridPane;

        });

        return expans;
    }

    private void writeToKart(Crop c, double v) {
        //todo write info to database then to kart

        Kart item = new Kart();
        item.setCustomerEmail(user.getEmail());
        item.setCropQuantity(v);
        item.setAmount(v*c.getCost());
        item.setCropCost(c.getCost());
        item.setCropName(c.getName());
        item.setCropOwner(c.getOwner());

        try {
            os.writeObject("saveToKart");
            os.writeObject(item);


            updateKart();



        } catch (IOException e) {



        }
    }

    private void updateKart() {

        if(controllerCustomer.grisHistory.getChildren().size() > 0)
        controllerCustomer.grisHistory.getChildren().remove(0,controllerCustomer.grisHistory.getChildren().size());

        try {
            os.writeObject("getKart");
            os.writeObject(user.getEmail());
            ArrayList<Kart> list = (ArrayList) is.readObject();

            for (Kart k: list
                 ) {
                addToKart(k);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addToKart(Kart k) {

        BorderPane bp = new BorderPane();

        String dateString = k.getDate().toString();
        String cropNameString = k.getCropName();
        String amountString = String.valueOf(k.getAmount());
        String cropQuantityString = String.valueOf(k.getCropQuantity());
        JFXButton checkout = new JFXButton("Checkout");
        JFXButton remove = new JFXButton("remove from kart");

        checkout.setOnAction(e->{
            try {
                os.writeObject("commit");
                os.writeObject(k);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        remove.setOnAction(e->{
            try {
                os.writeObject("remove");
                os.writeObject(k);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        Label label = new Label("you bought : "+cropNameString);
        label.setPrefWidth(400);
        AnchorPane space = new AnchorPane();
        space.setPrefSize(550,10);
        space.getStyleClass().add("filler");
        VBox vBox = new VBox();
        VBox hBox = new VBox();
        vBox.getChildren().addAll(label, new Label("the quantity is : "+cropQuantityString), new Label("your total is : "+amountString),space);
        hBox.getChildren().addAll(checkout, remove);
        bp.setCenter(vBox);
        bp.setLeft(new Label(dateString));
        bp.setRight(hBox);
        bp.getStyleClass().add("bp");
        bp.setPadding(new Insets(10));
        vBox.setPadding(new Insets(0,10,0,25));
        bp.setPrefSize(600,300);
        bp.requestLayout();

    //    controllerCustomer.grisHistory.addRow(controllerCustomer.grisHistory.getChildren().size(), bp);
        controllerCustomer.grisHistory.add(bp,1,controllerCustomer.grisHistory.getChildren().size());






    }

    public void search(){

        try{

            os.writeObject("search by farmer");


        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    private void getFarmersForChat(){
        try {

            os.writeObject("get farmer list");

            ArrayList e  = (ArrayList) is.readObject();
            ObservableList<Object> client = FXCollections.observableArrayList(e);

            controllerCustomer.chatColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
            controllerCustomer.chatTable.setItems(client);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void addController(ControllerCustomer controllerCustomer) {
        this.controllerCustomer = controllerCustomer;
    }

    public void addCredit(int a) {
        try {

            user.setBalance((float) (user.getBalance()+a));
            os.writeObject("update");
            os.writeObject(user);
            controllerCustomer.cBalance.setText("Balance : $"+String.valueOf(user.getBalance()));
            controllerCustomer.creditField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startUp() {
        setCustomer();
    }

    private class listenchat implements Runnable{

        private String msg="";

        Socket s;


        public listenchat() {


        }



        @Override
        public void run() {

            try {
                s = new Socket(InetAddress.getLocalHost(),4000);
                myIn = new ObjectInputStream(s.getInputStream());
                myOut = new ObjectOutputStream(s.getOutputStream());
                //controllerCustomer.chatBoxCustomer.appendText("connected \n");

                Label lab = new Label("Connected");
                lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                lab.getStyleClass().add("mid");
                lab.setTranslateX((433/2.75));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controllerCustomer.gridChat.addRow(1,lab);
                        controllerCustomer.gridChat.setVgap(2);
                        controllerCustomer.scrollPane.setFitToHeight(true);


                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }


            while(msg.compareTo("0000")!=0){


               try {
                   msg = (String) myIn.readObject();
//                    controllerCustomer.chatBoxCustomer.appendText("\n"+msg);

                   Label lab = new Label(msg);
                   Pane huh = new Pane();
                   huh.setMinSize(410,19);
                   huh.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                   huh.getChildren().add(lab);
                   huh.setPrefSize(410,19);
                   huh.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                   huh.getStyleClass().add("paneHolder");
                   lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                   lab.getStyleClass().add("in");

                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           controllerCustomer.gridChat.addRow(controllerCustomer.gridChat.getChildren().size()+1,huh);
                           Animation animation = new Timeline(
                                   new KeyFrame(Duration.seconds(.3),
                                           new KeyValue(controllerCustomer.scrollPane.vvalueProperty(), 1)));
                           animation.play();
                       }
                   });

                } catch (IOException e) {
                    e.printStackTrace();
                //    initiateChat();
                    return;
                } catch (ClassNotFoundException e) {
                   e.printStackTrace();
               }
            }
        }
    }
}
