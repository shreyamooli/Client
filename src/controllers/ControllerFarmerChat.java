package controllers;

import client.ClientFarmer;

import java.io.IOException;

public class ControllerFarmerChat {

    ClientFarmer clientFarmer;

    public void addClient(ClientFarmer clientFarmer) {
        this.clientFarmer = clientFarmer;
    }

    public void updateChat(boolean selected) {
        try {
            cli.os.writeObject("update selected");
            cli.os.writeObject(user.getEmail());
            cli.os.writeObject(selected);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
