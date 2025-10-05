package Core.Entities;

import DataObjects.CarDAO;
import Utilities.DataValidation;

public class Car {
    
    private String carID;
    private Brand brand;
    private String color;
    private String frameID;
    private String engineID;
    
    public Car() {}
    
    public Car(String carID, Brand brand, String color, String frameID, String engineID) throws Exception {
        setCarID(carID);
        setBrand(brand);
        setColor(color);
        setFrameID(frameID);
        setEngineID(engineID);
    }
    
    // Getters and Setters
    public String getCarID() { 
        return carID.toUpperCase(); 
    }
    public void setCarID(String carID) throws Exception{ 
        this.carID = carID; 
    }
    
    public Brand getBrand() { 
        return brand; 
    }
    public void setBrand(Brand brand) throws Exception{
        this.brand = brand;
    }
    
    public String getColor() { 
        return color.toLowerCase(); 
    }
    public void setColor(String color) throws Exception { 
        if (color.isEmpty()) {
            throw new Exception("Color can not be empty!");
        }
        this.color = color;
    }
    
    public String getFrameID() {
        return frameID.toUpperCase(); 
    }
    public void setFrameID(String frameID) throws Exception { 
        if (!DataValidation.checkStringWithFormat(frameID.toUpperCase(), "F\\d{5}")
                || CarDAO.frameSet.contains(frameID.toUpperCase())) {
            throw new Exception("Frame ID cannot be empty, must follow "
                    + "the format “F00000” and must be unique!");
        }
        this.frameID = frameID;
    }
    
    public String getEngineID() { 
        return engineID.toUpperCase(); 
    }
    public void setEngineID(String engineID) throws Exception { 
        if (!DataValidation.checkStringWithFormat(engineID.toUpperCase(), "E\\d{5}")
                || CarDAO.engineSet.contains(engineID.toUpperCase())) {
            throw new Exception("Engine ID cannot be empty, must follow "
                    + "the format “E00000” and must be unique!");
        }
        this.engineID = engineID; 
    }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", 
                getCarID(), getBrand().getBrandID(), getColor(), getFrameID(), getEngineID());
    }
}