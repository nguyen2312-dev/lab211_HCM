package Core.Entities;

import Utilities.Validation;

public class Brand {
    private String brandId;
    private String brandName;
    private String soundBrand;
    private double price;

    public Brand() {
    }

    public Brand(String brandId, String brandName, String soundBrand, double price) throws Exception {
        setBrandId(brandId);
        setBrandName(brandName);
        setSoundBrand(soundBrand);
        setPrice(price);
    }

    public String getBrandId() {
        return this.brandId;
    }

    public void setBrandId(String brandId) throws Exception {
        if (!Validation.checkStringWithFormat(brandId, "^B[A-Z0-9]+(-[A-Z0-9]+)?$")) {
            throw new Exception("Invalid brand ID format. Please try again");
        }
        this.brandId = brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) throws Exception{
        if(brandName.isEmpty()){
            throw new Exception("Brand name cannot be empty");
        }
        this.brandName = brandName;
    }

    public String getSoundBrand() {
        return this.soundBrand;
    }

    public void setSoundBrand(String soundBrand) throws Exception{
        if(soundBrand.isEmpty()){
            throw new Exception("Sound manufacturers cannot be empty.");
        }
        this.soundBrand = soundBrand;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) throws Exception{
        if(price <= 0){
            throw new Exception("Price must be a positive real number. For example, 1.234 means that 1.234 billion(s)$");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s" + "B",
                getBrandId(), getBrandName(), getSoundBrand(), getPrice());
    }
}
