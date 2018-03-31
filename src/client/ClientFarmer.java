package client;

import com.jfoenix.controls.JFXColorPicker;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
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
import kart.Kart;
import org.controlsfx.control.PopOver;
import users.Farmer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static client.Client.primaryStage;

public class ClientFarmer extends Client  {




    Thread t;
    StartServer startServer;
    ObjectOutputStream meOut;
    ObjectInputStream in;
    File file;
    static Farmer user = new Farmer();

    ControllerFarmerHome controllerFarmerHome;


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

            configureDisplay();
            setTable();
            setupChatBox();
            getHistory();
            setUpImage();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setUpImage() {
       try {
           byte[] b = user.getImage();

         //  File file = new File(String.valueOf(b));


           Image test = Image.impl_fromPlatformImage(ImageIO.read(new ByteArrayInputStream(b)));
           controllerFarmerHome.image.setImage(test);
           BufferedImage img = ImageIO.read(new ByteArrayInputStream(b));

       //    ByteArrayInputStream bis = new ByteArrayInputStream(b);
       //    BufferedImage bImage2 = ImageIO.read(bis);
       //    ImageIO.write(bImage2, "jpg", new File("output.jpg") );




       }catch(NullPointerException e){
           logger.error("no image in db");
       } catch (IOException e) {
           e.printStackTrace();
       }

    }


    private void configureDisplay() {

        controllerFarmerHome.fName.setText(user.getFullName());
        controllerFarmerHome.address.setText(user.getAddress());
        controllerFarmerHome.alias.setText(user.getAlias());
        controllerFarmerHome.email.setText(user.getEmail());
        controllerFarmerHome.chat.setSelected(user.isAvailable());
        controllerFarmerHome.balance.setText("BALANCE : "+String.valueOf(user.getBalance()));
    }




    public void setupChatBox() {

        logger.info("setting up chatbox");
        startServer = new StartServer();
        t = new Thread(startServer);
        t.start();
        controllerFarmerHome.gridChat.setVgap(1);

    }



   //todo set up chat box





    public void getHistory() {

        try {
            os.writeObject("getHistory");
            os.writeObject(user);
            ArrayList<Kart> list = (ArrayList) is.readObject();
            int i=0;

            for (Kart k: list
                 ) {


                BorderPane bp = new BorderPane();

                String dateString = k.getDate().toString();
                String cropNameString = k.getCropName();
                String amountString = String.valueOf(k.getAmount());
                String cropQuantityString = String.valueOf(k.getCropQuantity());

                Label label = new Label("you bought : "+cropNameString);
                label.setPrefWidth(400);
                AnchorPane space = new AnchorPane();
                space.setPrefSize(550,10);
                space.getStyleClass().add("filler");
                VBox vBox = new VBox();
                VBox hBox = new VBox();
                vBox.getChildren().addAll(label, new Label("the quantity is : "+cropQuantityString), new Label("your total is : "+amountString),space);
                bp.setCenter(vBox);
                bp.setLeft(new Label(dateString));
                bp.setRight(hBox);
                bp.getStyleClass().add("bp");
                bp.setPadding(new Insets(10));
                vBox.setPadding(new Insets(0,10,0,25));
                bp.setPrefSize(600,300);
                bp.requestLayout();


//                GridPane gp = new GridPane();
//                Label cropName, customerName, quantity, amount;
//                cropName = new Label(k.getCropName());
//                customerName = new Label(k.getCustomerEmail());
//                quantity = new Label(String.valueOf(k.getCropQuantity()));
//                amount = new Label(String.valueOf(k.getAmount()));
//                gp.setMinWidth(300);
//                gp.add(cropName, 1, 1);
//                gp.add(customerName, 1, 2);
//                gp.add(quantity, 1, 3);
//                gp.add(amount, 1, 4);
                controllerFarmerHome.gridHistory.add(bp,1,controllerFarmerHome.gridHistory.getChildren().size());
                i++;


            }


        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


    }






    public void upload()  {
        FileChooser fc = new FileChooser();
        File fil = fc.showOpenDialog(primaryStage);
        if (fil == null)
            return;

        Image ill = new Image(fil.toURI().toString());
      //todo  cf.pImage.setImage(ill);
      //  user.setImage(ill.toString().getBytes());
        controllerFarmerHome.image.setImage(ill);

        try {
//            os.writeObject("uploadUserImage");
//            os.writeObject(user);
//            os.writeObject(fil.getAbsoluteFile());

            os.writeObject("updateFarmerImage");
            os.writeObject(user);
            os.writeObject(fil);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //crops operations

    public void addCrop() {


        if (checkCropValues()) {
            Crop c = new Crop(controllerFarmerHome.cName.getText(), Double.valueOf(controllerFarmerHome.cWeight.getText()), Double.valueOf(controllerFarmerHome.cCost.getText()), Double.valueOf(controllerFarmerHome.cQuantity.getText()), controllerFarmerHome.cropAvailable.isSelected(), file);
            c.setOwner(user.getEmail());
            Image img = controllerFarmerHome.cImage.getImage();
            c.setImage(img.toString().getBytes());
            try {
                os.writeObject("addCrop");
                os.writeObject(c);
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }



        }else{
            checkErr("all values must be entered", controllerFarmerHome.cWeight,0);
        }

        setTable();
        updateTable();
        clearCropBar();
    }



    private void clearCropBar() {
        controllerFarmerHome.cName.clear();
        controllerFarmerHome.cWeight.clear();
        controllerFarmerHome.cCost.clear();
        controllerFarmerHome.cImage.setImage(null);
        controllerFarmerHome.cQuantity.clear();
        controllerFarmerHome.cropAvailable.setSelected(false);

      //  controllerFarmerHome.cImage.setImage(ill);

    }

    private boolean checkCropValues() {

        if (controllerFarmerHome.cName.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerHome.cWeight.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerHome.cCost.getText().compareTo("") == 0)
            return false;
        if (controllerFarmerHome.cQuantity.getText().compareTo("") == 0)
            return false;

        return true;

    }

    private void updateTable() {
        ArrayList list = getArrayList();
        ObservableList<Object> cropList = FXCollections.observableArrayList(list);

        controllerFarmerHome.cropTable.setItems(null);
     //   controllerFarmerHome.cropTable.getColumns().removeAll();
        controllerFarmerHome.cropTable.setItems(cropList);


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
        controllerFarmerHome.ctName.setCellValueFactory(new PropertyValueFactory("Name"));
        controllerFarmerHome.ctWeight.setCellValueFactory(new PropertyValueFactory("Weight"));
        controllerFarmerHome.ctCost.setCellValueFactory(new PropertyValueFactory("Cost"));
        controllerFarmerHome.ctQuantity.setCellValueFactory(new PropertyValueFactory("Quantity"));
        controllerFarmerHome.ctAvailable.setCellValueFactory(new PropertyValueFactory("Available"));


        TREC expans = getExpanse();

        controllerFarmerHome.cropTable.setItems(null);
        controllerFarmerHome.cropTable.getColumns().removeAll();
        controllerFarmerHome.cropTable.getColumns().add(0, expans);
        controllerFarmerHome.cropTable.setItems(cropList);



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
            JFXToggleButton editAvailable = new JFXToggleButton();



            editWeight.setPromptText("edit weight");
            editCost.setPromptText("edit cost");
            editQuantity.setPromptText("edit quatity");


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

                c.setAvailable(editAvailable.isSelected());

                //  Crop c = (Crop) param.getTableRow().getItem();
                try {
                    os.writeObject("updateCrop");
                    os.writeObject(c);

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
        String msg = controllerFarmerHome.chatSendBoxFarmer.getText();
        if(msg.compareTo("")==0)
            return;
        Label lab = new Label(msg);

        controllerFarmerHome.chatSendBoxFarmer.clear();
        lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
        lab.getStyleClass().add("out");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controllerFarmerHome.gridChat.addRow(controllerFarmerHome.gridChat.getChildren().size()+1,lab);
                Animation animation = new Timeline(
                        new KeyFrame(Duration.seconds(.3),
                                new KeyValue(controllerFarmerHome.scrollPane.vvalueProperty(), 1)));
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
            os.writeObject("updatefarmer");
            os.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cropImage() throws IOException {

        FileChooser f = new FileChooser();
        File file = f.showOpenDialog(primaryStage);
        if(file==null)
            return;

        Image image = new Image(file.toURI().toString());
        controllerFarmerHome.cImage.setImage(image);



    }

    public void chooseColor() {

        JFXColorPicker colorPick = new JFXColorPicker();
        Stage stage = new Stage();
        AnchorPane parent = new AnchorPane();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        parent.getChildren().add(colorPick);
        stage.show();
        colorPick.show();
        Color color = colorPick.getValue();



        colorPick.setOnAction((EventHandler<ActionEvent>) t -> {
            String strings;
            stage.close();
            strings = colorPick.getValue().toString();
            strings = strings.substring(2);
            strings = strings.replace("ff","");

            strings = ".troot{ -fx-somcolor : #"+ strings +"; }";
          //  System.out.println(strings);

            File file = new File("Client/src/helpers/background.css");
            try {
                file.createNewFile();
//                FileWriter fileWriter = new FileWriter(file);
//                PrintWriter printWriter = new PrintWriter(fileWriter);
//                printWriter.print(strings);

//                for (int i =0; i<root.getStylesheets().size();i++){
//
//                    if(root.getStylesheets().get(i).endsWith("d.css")){
//                        root.getStylesheets().remove(i);
//
//                    }
//                }
//
//                for (int i = 0; i < root.getStyleClass().size() ; i++) {
//                    if(root.getStyleClass().get(i).endsWith("troot")){
//                  //      root.getStyleClass().remove(i);
//                        //   root.requestLayout();
//                    }
//                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(strings);
                writer.close();
             //   root.getStylesheets().add(getClass().getResource("/helpers/background.css").toExternalForm());
             //   root.getStylesheets().add(getClass().getResource("/helpers/style.css").toExternalForm());
             //   root.getStylesheets().add(getClass().getResource("/windows/FarmerHome.css").toExternalForm());

                root.getStylesheets().add(getClass().getResource("/helpers/background.css").toExternalForm());
             //   root.getStyleClass().add("troot");
              //  root.getStyleClass().remove(2);

                //root.requestLayout();


                root.getStyleClass().clear();
                root.applyCss();
                root.getStyleClass().addAll("troot","mo","root" );
                root.applyCss();



            } catch (IOException e) {
                e.printStackTrace();

            }


        });


    }

    public void checkT() {
        if(t.isAlive())
            startServer.server.interrupt();

    }


    private class Server implements Runnable {

        Socket socket;
        String msg = "";
        TextArea t;


        public Server(Socket socket) {
            this.socket = sock;
        }

        public Server() {

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
                            controllerFarmerHome.gridChat.addRow(controllerFarmerHome.gridChat.getChildren().size()+1,huh);
                            Animation animation = new Timeline(
                                    new KeyFrame(Duration.seconds(.3),
                                            new KeyValue(controllerFarmerHome.scrollPane.vvalueProperty(), 1)));
                            animation.play();
                        }
                    });


                    //controllerFarmerHome.chatfarmer.appendText("n"+msg);


                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }


           // setupChatBox();

        }

        public void interrupt() {
            msg="0000";
            Platform.exit();
        }
    }

    private class StartServer implements Runnable {


        public ClientFarmer.Server server = new Server();

        public StartServer() {

        }

        @Override
        public void run() {


            try {


                ServerSocket serverSocket = new ServerSocket(user.getChatPort());
                Socket socket = serverSocket.accept();
                logger.info("client server accepted");
//                chatBoxFarm.appendText("client connected");
              //  controllerFarmerHome.chatfarmer.appendText("client connected \n\n");
                Label lab = new Label("Customer Connected");
                lab.getStylesheets().add(getClass().getResource("/views/msg sheets.css").toExternalForm());
                lab.getStyleClass().add("mid");
                lab.setTranslateX((433/2.75));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controllerFarmerHome.gridChat.addRow(1,lab);
                        controllerFarmerHome.gridChat.setVgap(2);
                        controllerFarmerHome.scrollPane.setFitToHeight(true);
//                        controllerFarmerHome.gridChat.add



                    }
                });
                meOut = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                server = new Server(socket);
                Thread t = new Thread(server);
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        public void interrupt() {
            Thread.currentThread().interrupt();
            return;
        }
    }
}




