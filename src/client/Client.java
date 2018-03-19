package client;

import controllers.ControllerCustomer;
import controllers.ControllerFarmer;
import controllers.ControllerFarmerHome;
import controllers.ControllerLogin;
import helpers.EffectUtilities;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.PopOver;
import users.Customer;
import users.Farmer;
import users.Person;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Application {

    static AnchorPane cust, cropCust, kart, chatCus;
    public static Socket sock;
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    public ObjectInputStream reader; // for reading data from server
    public static Person user = new Person();
    public static Stage primaryStage;
    public AnchorPane root;
    public static String erre = "";
    public  Logger logger = LogManager.getLogger(Client.class);
    FXMLLoader floader = new FXMLLoader();
    Controller con = floader.getController();
   // private Person user;
    private int offset = 0;
    private double average=350;
    private ClientFarmer cf;
    private ClientCustomer cc;
    private ControllerLogin controllerLogin;
    private ControllerFarmer controllerFarmer;
    private ControllerFarmerHome controllerFarmerHome;
    private ControllerCustomer controllerCustomer;


    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {



        try {
            sock = new Socket(InetAddress.getLocalHost(), 1347);
            setStreams();
            logger.info("stream started");

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }


        this.primaryStage = primaryStage;

        floader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        floader = new FXMLLoader(getClass().getResource("/frames/login.fxml"));
        floader = new FXMLLoader(getClass().getResource("/windows/login.fxml"));
        root = floader.load();

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        controllerLogin = floader.<ControllerLogin>getController();
        controllerLogin.addClient(this);

        //    floader.<Controller>getController().pName.setText("help");

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("/helpers/style.css").toExternalForm());
        root.getStyleClass().add("mo");
        primaryStage.setScene(scene);
        primaryStage.show();
        registerStage(primaryStage);

    }


    public void registerStage(Stage stage) {
        EffectUtilities.makeDraggable(stage, root);
    }


    public void appClose() {
        Platform.exit();
    }


    public void setIconified() {
        primaryStage.setIconified(true);
    }

    public Controller getController(){
        return con;
    }



    public void loadPane() {
        try {
            con.anchorPane = FXMLLoader.load(getClass().getResource("/views/add.fxml"));
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/add.fxml"));
            root.getChildren().add(newLoadedPane);

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public void setStreams() {
        try {
            os = new ObjectOutputStream(sock.getOutputStream());
            is = new ObjectInputStream(sock.getInputStream());
            reader = is;

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void generic(FXMLLoader fxml) {
        try {

            root = fxml.load();
            con = fxml.<Controller>getController();
            con.addClient(this);


        //    floader.<Controller>getController().pName.setText("help");

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource("/views/paneFarm.css").toExternalForm());
            root.getStyleClass().add("mo");
            primaryStage.setScene(scene);
            primaryStage.show();
            registerStage(primaryStage);
        } catch (IOException e) {
            logger.error(e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();

        }


    }



    public void loadScene(String s, Person p) {
        user = p;
        floader = new FXMLLoader(getClass().getResource(s));
        generic(floader);

    }




    //update UI





    public void signIn() throws IOException, ClassNotFoundException {
        if (checkErr(controllerLogin.signInEmail) && controllerLogin.signInEmail.getText().contains("@")) {
            if (checkErr(controllerLogin.signInPassword)) {
                //continue


                os.writeObject("signin");
                os.writeObject(controllerLogin.signInEmail.getText());
                os.writeObject(controllerLogin.signInPassword.getText());
                int a = (int) is.readObject();
                if (a == 1) {
                    //switch to scene
                    os.writeObject("getUser");
                    os.writeObject(controllerLogin.signInEmail.getText());
                    os.writeObject(a);
                    Farmer f  = (Farmer) is.readObject();
                    System.out.println(f.toString());
//                    floader = new FXMLLoader(getClass().getResource("/views/paneFarm.fxml"));
//                    root = floader.load();
//                    controllerFarmer = floader.<ControllerFarmer>getController();
//                    controllerFarmer.addClient(this);
//                    cf = new ClientFarmer(this);
//                    controllerFarmer.addImmediateClient(cf);
//                    cf.setUser(f);


                    floader = new FXMLLoader(getClass().getResource("/windows/FarmerHome.fxml"));
                    root = floader.load();
                    controllerFarmerHome = floader.<ControllerFarmerHome>getController();
                    cf = new ClientFarmer(controllerFarmerHome, f);


                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    scene.getStylesheets().add(getClass().getResource("/helpers/style.css").toExternalForm());
                    root.getStyleClass().add("mo");
                    root.layout();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    registerStage(primaryStage);

                    return;
                }
                if (a == 2) {
                    //switch to scene
                    os.writeObject("getUser");
                    os.writeObject(controllerLogin.signInEmail.getText());
                    os.writeObject(a);
                    Person p = (Person) is.readObject();


                    user = p;


                    floader = new FXMLLoader(getClass().getResource("/views/paneCustomer.fxml"));

                    root = floader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    scene.getStylesheets().add(getClass().getResource("/views/paneFarm.css").toExternalForm());
                    root.getStyleClass().add("mo");
                    root.layout();

                    primaryStage.setScene(scene);
                    primaryStage.show();
                    registerStage(primaryStage);
                   // generic(floader);

                    controllerCustomer = floader.<ControllerCustomer>getController();
                    controllerCustomer.addClient(this);
                    cc = new ClientCustomer(this);
                    controllerCustomer.addImmediateClient(cc);
                    //loadScene("/views/paneCustomer.fxml", p);
                    return;
                } else {
                    checkErr(controllerLogin.signInEmail);
                    erre = "Incorrect email or password";
                    return;
                }

            }

        }
        checkErr("Incorrect email or password", con.signInEmail, -10);
        erre = "Incorrect email or password";


    }

    public Person getUser() {
        return user;
    }

    public boolean checkErr(String c, TextField b, int a) {

        showError(b);
        erre = "";


        return false;


    }


    public boolean checkErr(TextField node) {

        String word = node.getText();

        if (word == null || word.compareTo("") == 0) {
            showError(node);
            return false;
        }
        return true;
    }



    public void signUp() throws IOException, ClassNotFoundException {

        if (checkErr(controllerLogin.signUpName))
            if (checkErr(controllerLogin.signUpEmail))
                if (checkErr(controllerLogin.signUpPassword))
                    if (checkErr(controllerLogin.signUpPassword2)) {
                        if (controllerLogin.radioFarmer.isSelected()) {

                            System.out.println("radio selecetd");
                            if (checkErr(controllerLogin.address)) {
                                //do something
                                if (controllerLogin.signUpPassword.getText().compareTo(controllerLogin.signUpPassword2.getText()) == 0 && controllerLogin.signUpEmail.getText().contains("@")) {
                                    registerFarmer();
                                    return;
                                } else {
                                    if (controllerLogin.signUpPassword.getText().compareTo(controllerLogin.signUpPassword2.getText()) != 0)
                                        checkErr("passwors do not match", con.signUpPassword, offset);
                                    if (!controllerLogin.signUpEmail.getText().contains("@"))
                                        checkErr("not a valid email", controllerLogin.signUpEmail, offset);

                                }
                            }
                        }

                        //if customer, do same thing
                        if (controllerLogin.signUpPassword.getText().compareTo(controllerLogin.signUpPassword2.getText()) == 0 && controllerLogin.signUpEmail.getText().contains("@")) {
                            registerCustomer();
                            return;
                        } else {
                            if (controllerLogin.signUpPassword.getText().compareTo(controllerLogin.signUpPassword2.getText()) != 0)
                                checkErr("passwors do not match", controllerLogin.signUpPassword, offset);
                            if (!controllerLogin.signUpEmail.getText().contains("@"))
                                checkErr("not a valid email", controllerLogin.signUpEmail, offset);

                        }
                    }
    }

    public void registerCustomer() throws IOException, ClassNotFoundException {
        Person c = new Customer(controllerLogin.signUpEmail.getText(), controllerLogin.signUpName.getText(), controllerLogin.signUpPassword.getText());
        c.setAlias("customer");
        os.writeObject("signup");
        os.writeObject(c);
        int h = (int) is.readObject();
        if (h == 1) {
            //success
            switchToSignUp();
            controllerLogin.signInEmail.setText(c.getEmail());
            controllerLogin.signInPassword.setText(c.getPassword());
        } else {
            //failure
        }
    }

    public void registerFarmer() throws IOException, ClassNotFoundException {
        Person f = new Farmer(controllerLogin.signUpEmail.getText(), controllerLogin.signUpName.getText(), controllerLogin.signUpPassword.getText(), controllerLogin.address.getText());
        f.setAlias("farmer");
        os.writeObject("signup");
        os.writeObject(f);
        int h = (int) is.readObject();
        if (h == 1) {
            //success
            switchToSignUp();
            controllerLogin.signInEmail.setText(f.getEmail());
            controllerLogin.signInPassword.setText(f.getPassword());
        } else {
            //failure
        }
    }



    public void showError(TextField node) {

        Parent basicRoot = null;
        try {
            basicRoot = FXMLLoader.load(getClass().getResource("/views/error.fxml"));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        // Scene scene = new Scene(basicRoot);
        PopOver pp = new PopOver(basicRoot);
        pp.setAutoHide(true);
        //pp.setArrowSize(0);
        double height = node.getParent().getScene().getWindow().getHeight();
        double width = node.getParent().getScene().getWindow().getWidth();
        double startx = node.getParent().getScene().getWindow().getX();
        double starty = node.getParent().getScene().getWindow().getY();

        double centerx = (height + (2 * startx)) / 2;
        double centery = (width + (2 * starty)) / 2;

        if (erre.compareTo("") == 0)
            pp.setTitle("please enter " + node.getPromptText());
        else
            pp.setTitle(erre);

        pp.setHeaderAlwaysVisible(true);
        pp.show(node.getParent().getScene().getWindow(), node.getLayoutX() * 3 + centerx + startx, node.getLayoutY() + starty);
        // todo id

        //    NotificationPane np = new NotificationPane(basicRoot);
        //    root.getChildren().add(np);
        //    np.show("help");

    }

    public void showSuccessMsg() {
        Parent basicRoot = null;
        try {
            basicRoot = root = FXMLLoader.load(getClass().getResource("/views/success.fxml"));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        ;
        // Scene scene = new Scene(basicRoot);
        PopOver pp = new PopOver(basicRoot);
        pp.setAutoHide(true);
        pp.setArrowSize(0);
        pp.show(primaryStage);
    }



    public void switchToSignUp() {





        controllerLogin.radioCustomer.setSelected(false);
        controllerLogin.radioFarmer.setSelected(false);
        controllerLogin.address.setOpacity(0.0);
        //   boolean slid = slider.getTranslateX() > -379 ? false : true;
        boolean signinshowing = (controllerLogin.signInPage.getOpacity() > 0.5);
        double target = signinshowing ? 0 : 1;
        double target1 = signinshowing ? 1 : 0;
        KeyValue keyValue = new KeyValue(controllerLogin.signInPage.opacityProperty(), target);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(550), keyValue));
        timeline.play();
        KeyValue keyValue1 = new KeyValue(controllerLogin.signUpPage.opacityProperty(), target1);
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(550), keyValue1));
        timeline1.play();
        controllerLogin.signInPage.setDisable(signinshowing);
        controllerLogin.signUpPage.setDisable(!signinshowing);


    }


    public void radioChanged(ActionEvent e) {

        if (e.getSource() == controllerLogin.radioFarmer) {

            controllerLogin.address.setDisable(false);
            KeyValue keyValue = new KeyValue(controllerLogin.address.opacityProperty(), 1.0);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(average), keyValue));
            timeline.play();


        } else {

            controllerLogin.address.setDisable(true);
            KeyValue keyValue = new KeyValue(controllerLogin.address.opacityProperty(), 0.0);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(average), keyValue));

            timeline.play();

        }
    }



}
