package kart;

import client.ClientFarmer;
import crops.Crop;
import users.Customer;
import users.Farmer;

import java.util.Date;

public class Kart {

    public Farmer farmer = new Farmer();
    public Customer customer = new Customer();
    public Crop crop = new Crop();
    public Date date;

    public String farmerEmail;
    public String customerEmail;
    public double cropPrice;
    public double cropQuantity;



    public Kart(){

        farmerEmail = farmer.getEmail();
        customerEmail=customer.getEmail();
        cropPrice = crop.getCost();
        cropQuantity = crop.getQuantity();
        date = new Date();
    }
    public String getFarmerEmail(){
        return farmerEmail;
    }
    public String getCustomerEmail(){
        return customerEmail;
    }
    public double getCropPrice(){
        return cropPrice;
    }

    public double getCropQuantity() {
        return cropQuantity;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setFarmerEmail(String farmerEmail) {
        this.farmerEmail = farmerEmail;
    }

    public void setCropPrice(double cropPrice) {
        this.cropPrice = cropPrice;
    }

    public void setCropQuantity(double cropQuantity) {
        this.cropQuantity = cropQuantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
