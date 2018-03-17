package interfaces;

import crops.Crop;

import java.util.List;

public interface FarmerOptions {


    public void addCrop();
    public List viewAllCrops();
    public void updateCrop(Crop c);
    public Crop getCropByName(String name);
    public void viewHistory();

}
