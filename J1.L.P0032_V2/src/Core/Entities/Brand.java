package Core.Entities;

public class Brand {
    private String brandID;
    private String brandName;
    private String soundBrand;
    private double price;
    
    public Brand() {}
    
    public Brand(String brandID, String brandName, String soundBrand, double price) throws Exception {
        setBrandID(brandID);
        setBrandName(brandName);
        setSoundBrand(soundBrand);
        setPrice(price);
    }

    public String getBrandID() { 
        return brandID.toUpperCase(); 
    }
    public void setBrandID(String brandID) throws Exception{ 
        this.brandID = brandID; 
    }
    
    public String getBrandName() {
        return brandName; 
    }
    public void setBrandName(String brandName) throws Exception{
        if (brandName.isEmpty()) {
            throw new Exception("Brand name can not be empty!");
        }
        this.brandName = brandName; 
    }
    
    public String getSoundBrand() { 
        return soundBrand; 
    }
    public void setSoundBrand(String soundBrand) throws Exception {
        if (soundBrand.isEmpty()) {
            throw new Exception("Sound manufacturers can not be empty!");
        }
        this.soundBrand = soundBrand; 
    }
    
    public double getPrice() { 
        return price; 
    }
    public void setPrice(double price) throws Exception { 
        if (price <= 0) {
            throw new Exception("Price must be a positive real number!");
        }
        this.price = price; 
    }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s: %s", 
                    getBrandID(), getBrandName(),
                    getSoundBrand(), getPrice() + "B");
    }
}