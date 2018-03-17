package crops;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.io.Serializable;

public abstract class Crops implements Serializable {

    transient SimpleStringProperty namess;
    transient SimpleStringProperty weightss;
    transient SimpleStringProperty costss;
    transient SimpleStringProperty quantityss;
    transient SimpleStringProperty availss;
    protected String name;
    protected Image image;
    protected double weight, cost, quantity;
    protected boolean available;
    protected File imagefile;
    protected String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Crops() {
        this.name = name;
        this.image = image;
        this.weight = weight;
        this.cost = cost;
        this.quantity = quantity;
        this.available = available;
        this.imagefile = imagefile;

        this.namess = new SimpleStringProperty(name);
        this.weightss = new SimpleStringProperty(String.valueOf(weight));
        this.costss = new SimpleStringProperty(String.valueOf(cost));
        this.quantityss = new SimpleStringProperty(String.valueOf(quantity));
        this.availss = new SimpleStringProperty(String.valueOf(available));
    }

    public Crops(String name, Image image, double weight, double cost, double quantity, boolean available, File imagefile) {
        this.name = name;
        this.image = image;
        this.weight = weight;
        this.cost = cost;
        this.quantity = quantity;
        this.available = available;
        this.imagefile = imagefile;

        this.namess = new SimpleStringProperty(name);
        this.weightss = new SimpleStringProperty(String.valueOf(weight));
        this.costss = new SimpleStringProperty(String.valueOf(cost));
        this.quantityss = new SimpleStringProperty(String.valueOf(quantity));
        this.availss = new SimpleStringProperty(String.valueOf(available));
    }

    public Crops(String name, double weight, double cost, double quantity, boolean available, File imagefile) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.quantity = quantity;
        this.available = available;
        this.imagefile = imagefile;
        this.image = image;

        this.namess = new SimpleStringProperty(name);
        this.weightss = new SimpleStringProperty(String.valueOf(weight));
        this.costss = new SimpleStringProperty(String.valueOf(cost));
        this.quantityss = new SimpleStringProperty(String.valueOf(quantity));
        this.availss = new SimpleStringProperty(String.valueOf(available));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        namess = new SimpleStringProperty(name);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        this.weightss = new SimpleStringProperty(String.valueOf(weight));
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
        this.costss = new SimpleStringProperty(String.valueOf(cost));

    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.quantityss = new SimpleStringProperty(String.valueOf(quantity));

    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        this.availss = new SimpleStringProperty(String.valueOf(available));

    }

    public File getImagefile() {
        return imagefile;
    }

    public void setImagefile(File imagefile) {
        this.imagefile = imagefile;
    }

    public void fileToImage(File file){
        Image img = new Image(file.toURI().toString());
    }

    public void thisFileToImage(){
        Image img = new Image(imagefile.toURI().toString());
    }
}
