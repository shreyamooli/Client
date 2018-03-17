package crops;

import javafx.scene.image.Image;

import java.io.File;

public class Crop extends Crops {

    public Crop() {
    }

    public Crop(String name, Image image, double weight, double cost, double quantity, boolean available, File imagefile) {
        super(name, image, weight, cost, quantity, available, imagefile);
    }

    public Crop(String name, double weight, double cost, double quantity, boolean available, File imagefile) {
        super(name, weight, cost, quantity, available, imagefile);
    }


}
