package interfaces;

import crops.Crop;

import java.util.List;

public interface CustomerOptions {

    public void addFunds();
    public void viewAllCrops();
    public List<Crop> searchAll(String key);
    public List<Crop> searchOne(String key);
    public void addToBasket(Crop c, float quantity);


}
