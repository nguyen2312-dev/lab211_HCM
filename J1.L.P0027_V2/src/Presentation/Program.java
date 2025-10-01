package Presentation;

import Core.Interfaces.IStudentDAO;
import DataObjects.StudentDAO;

public class Program {
    public static void main(String[] args) {
        String registrationDataFile = "registrations.txt";
        try {
            IStudentDAO studentService = new StudentDAO(registrationDataFile);
            Menu.manageStudent(studentService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
