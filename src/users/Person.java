package users;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;

public class Person implements Serializable {

    boolean available;
   transient SimpleStringProperty emailss;
    transient SimpleStringProperty fullnamess;
    transient SimpleStringProperty addressss;
    transient SimpleStringProperty aliasss;
    transient SimpleStringProperty availss;
    private String email;
    private String fullName;
    private String password;
    private Image image;
    private double balance = 0.0;
    private String address;
    private String alias;
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }



//    private int chatPort = 4000;

    public Person() {

    }

    public Person(String email, String fullName, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.image = image;
        this.emailss = new SimpleStringProperty(email) ;
        this.fullnamess = new SimpleStringProperty(fullName);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        this.availss = new SimpleStringProperty("yes");
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
        this.aliasss = new SimpleStringProperty(alias);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.addressss = new SimpleStringProperty(address);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        this.fullnamess = new SimpleStringProperty(fullName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String searchVal(String val){return val; }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", image=" + image +
                '}';
    }
}
