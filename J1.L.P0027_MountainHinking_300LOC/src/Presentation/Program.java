package Presentation;

import DataObject.*;
import Core.Interfaces.IStudentDAO;

public class Program {

    public static void main(String[] args) {
        String registerations = "registrations.txt";
        try {
            IStudentDAO studentService = new StudentDAO(registerations);
            Menu.manageStudent(studentService);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
