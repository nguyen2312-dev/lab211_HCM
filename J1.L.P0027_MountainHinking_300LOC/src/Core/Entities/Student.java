package Core.Entities;

import Core.Interfaces.IMountainDAO;
import DataObject.MountainDAO;
import Utilities.Validation;

public class Student {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String mountainCode;
    private double tuitionFee;

    IMountainDAO mountainDAO;

    public Student() {
    }

    public Student(String id, String name, String phone, String email, String mountainCode) throws Exception {
        this.mountainDAO = new MountainDAO();
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        setMountainCode(mountainCode);
        setTuitionFee(phone);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) throws Exception {
        if (!Validation.checkStringWithFormat(id, "^[HhSsCcDdQq][Ee]\\d{6}$")) {
            throw new Exception("Incorrect student ID format. Please try again");
        }
        this.id = id.toUpperCase();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws Exception {
        if (!Validation.checkStringWithFormat(name, "^[A-Za-z ]{2,20}$")) {
            throw new Exception("Incorrect student name format. Please try again");
        }
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) throws Exception {
        if (!Validation.checkStringWithFormat(phone, "^0\\d{9}$")) {
            throw new Exception("Incorrect phone number format. Please try again");
        }
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) throws Exception {
        if (!Validation.checkStringWithFormat(email, "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")) {
            throw new Exception("Incorrect email format. Please try again");
        }
        this.email = email;
    }

    public String getMountainCode() {
        return this.mountainCode;
    }

    public void setMountainCode(String mountainCode) throws Exception {
        // if (!Validation.checkStringWithFormat(mountainCode, "^MT(0[0-9]|1[0-3])$") ||
        if (mountainDAO.getMountainByCode(mountainCode) == null) {
            throw new Exception("Incorrect mountain code. Mountain code only valid from 01 to 13 like the table :D ");
        }
        this.mountainCode = mountainCode;
    }

    public double getTuitionFee() {
        return this.tuitionFee;
    }

    public void setTuitionFee(String phone) {
        if (!Validation.checkStringWithFormat(phone,
                "^(086|096|097|098|039|038|037|036|035|034|033|032)\\d{7}$") &&
                !Validation.checkStringWithFormat(phone, "^(081|082|083|084|085|088|091|094)\\d{7}$")) {
            this.tuitionFee = 6000000.0;
        } else {
            this.tuitionFee = 6000000.0 * 0.65;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %.2f",
                getId(), getName(), getPhone(), getEmail(), getMountainCode(), getTuitionFee());
    }
}
