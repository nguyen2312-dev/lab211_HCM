package Core.Entities;

public class Mountain {
    private String mountainCode;
    private String mountain;
    private String province;
    private String description;

    public Mountain() {
    }

    public Mountain(String mountainCode, String mountain, String province, String description) {
        this.mountainCode = mountainCode;
        this.mountain = mountain;
        this.province = province;
        this.description = description;
    }

    public String getMountainCode() {
        return (mountainCode.length() == 1) ? "MT0" + mountainCode : "MT" + mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public String getMountain() {
        return this.mountain;
    }

    public void setMountain(String mountain) {

        this.mountain = mountain;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Mountain mountainCode(String mountainCode) {
        setMountainCode(mountainCode);
        return this;
    }

    public Mountain mountain(String mountain) {
        setMountain(mountain);
        return this;
    }

    public Mountain province(String province) {
        setProvince(province);
        return this;
    }

    public Mountain description(String description) {
        setDescription(description);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", 
        getMountainCode(), getMountain(), getProvince(), getDescription());
    }

}
