
package Core.Entities;

import Utilities.Validation;

public class Car {
    private String carId;
    private String color;
    private String frameId;
    private String engineId;
    private Brand brand;

    public Car() {
    }

    public Car(String carId, Brand brand ,String color, String frameId, String engineId) throws Exception {
        setCarId(carId);
        setBrand(brand);
        setColor(color);
        setFrameId(frameId);
        setEngineId(engineId);
    }

    public String getCarId() {
        return this.carId;
    }


    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setCarId(String carId) throws Exception {
        if (!Validation.checkStringWithFormat(carId, "^C\\d+$")) {
            throw new Exception("Invalid Car ID format. Please try again");
        }
        this.carId = carId;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) throws Exception{
        if(color.isEmpty()){
            throw new Exception("Color cannot be empty.");
        }
        this.color = color;
    }

    public String getFrameId() {
        return frameId.toUpperCase();
    }

    public void setFrameId(String frameId) throws Exception {
        if (!Validation.checkStringWithFormat(frameId.toUpperCase(), "^F\\d+$")) {
            throw new Exception("Frame ID cannot be empty, must follow the format “F00000”, and must be unique.");
        }
        this.frameId = frameId;
    }

    public String getEngineId() {
        return engineId.toUpperCase();
    }

    public void setEngineId(String engineId) throws Exception {
        if (!Validation.checkStringWithFormat(engineId.toUpperCase(), "^E\\d+$")) {
            throw new Exception("Engine ID cannot be empty, must follow the format “E00000”, and must be unique.");
        }
        this.engineId = engineId;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s",
                getCarId(), getBrand().getBrandId() , getColor(), getFrameId(), getEngineId());
    }

}
