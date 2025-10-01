package Core.Entities;

import Utilities.DataValidation;


public class Mountain {

    private String mountainCode;
    private String mountainName;
    private String province;
    private String description;

    public Mountain() {}
    
    public Mountain(String mountainCode, String mountainName, String province, String description) {
        this.mountainCode = mountainCode;
        this.mountainName = mountainName;
        this.province = province;
        this.description = description;
    }

    public String getMountainCode() {
        return (mountainCode.length() == 1) ? "MT0" + mountainCode : "MT" + mountainCode;
    }

    public String getMountainName() {
        return mountainName;
    }

    public String getProvince() {
        return province;
    }

    public String getDescription() {
        return description;
    } 

    @Override
    public String toString() {
        return String.format("| %-5s | %-20s | %-10s | %-38s |"
                , getMountainCode(), getMountainName(), getProvince(), getDescription());
    }
}

