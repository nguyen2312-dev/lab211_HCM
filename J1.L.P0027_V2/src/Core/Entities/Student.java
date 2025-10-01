package Core.Entities;

import Core.Interfaces.IMountainDAO;
import DataObjects.MountainDAO;
import Utilities.DataValidation;

public class Student {
    IMountainDAO mountainDAO; 

    private String studentID;
    private String name;
    private String phoneNumber;
    private String email;
    private String mountainCode;
    private double tuitionFee;

    public Student(String studentID, String name, String phoneNumber,
            String email, String mountainCode) throws Exception {
        this.mountainDAO = new MountainDAO();
        setStudentID(studentID);
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setMountainCode(mountainCode);
        setTuitionFee(getPhoneNumber());
    }

    public String getStudentID() {
        return studentID.toUpperCase();
    }

    public void setStudentID(String studentID) throws Exception{
        if (!DataValidation.checkStringWithFormat(studentID.toUpperCase(), "^[HSDQC][E]\\d{6}$")) {
            throw new Exception("Id is invalid. The correct format:Exxx, with x is digits!");
        }
        this.studentID = studentID.toUpperCase(); 
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (!DataValidation.checkStringWithFormat(name.toUpperCase(), "^[A-Za-z ]{2,20}$")) {
            throw new Exception("Name is invalid. Name is a "
                    + "non-empty string between 2 and 20 characters long!");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if (!DataValidation.checkStringWithFormat(phoneNumber, "^0\\d{9}$")) {
            throw new Exception("Phone number is invalid!");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if (!DataValidation.checkStringWithFormat(email, 
                "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new Exception("Email is invalid!");
        }
        this.email = email;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) throws Exception {
        if (mountainDAO.getMountainByCode(mountainCode) == null) {
            throw new Exception("Mountain code is invalid! Only choose code from the table!");
        }
        this.mountainCode = mountainCode;
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setTuitionFee(String phoneNumber) throws Exception {
        //check VNPT and Viettel
        if (DataValidation.checkStringWithFormat(phoneNumber,"^(081|082|083|084|085|088|091|094)\\d{7}$") 
                ||DataValidation.checkStringWithFormat(phoneNumber,"^(086|096|097|098|039|038|037|036|035|034|033|032)\\d{7}$")) {
            this.tuitionFee = 6000000 * 0.65;
        } else {
            this.tuitionFee = 6000000;
        }
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s", getStudentID(),
                getName(), getPhoneNumber(), getEmail(), getMountainCode(), getTuitionFee());
    }
}

