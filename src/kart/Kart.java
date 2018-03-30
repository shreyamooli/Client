package kart;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Kart implements Serializable {



    private String date;
    private String customerEmail;
    private String cropName, cropOwner;
    private double cropCost, cropQuantity, amount;
    private boolean committed = false;

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    public Kart(String customerEmail, String cropName, String cropOwner, double cropCost, double cropQuantity, double amount) {
        this.customerEmail = customerEmail;
        this.cropName = cropName;
        this.cropOwner = cropOwner;
        this.cropCost = cropCost;
        this.cropQuantity = cropQuantity;
        this.amount = amount;


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now =  LocalDateTime.now();

        date = dtf.format(now);
    }

    public Kart() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now =  LocalDateTime.now();

        date = dtf.format(now);
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropOwner() {
        return cropOwner;
    }

    public void setCropOwner(String cropOwner) {
        this.cropOwner = cropOwner;
    }

    public double getCropCost() {
        return cropCost;
    }

    public void setCropCost(double cropCost) {
        this.cropCost = cropCost;
    }

    public double getCropQuantity() {
        return cropQuantity;
    }

    public void setCropQuantity(double cropQuantity) {
        this.cropQuantity = cropQuantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
