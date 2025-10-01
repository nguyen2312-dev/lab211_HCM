package Presentation;

import java.util.*;

import BussinessObject.StudentManagement;
import Core.Interfaces.IStudentDAO;
import Utilities.Inputter;


public class Menu {
    public static void print(String str) {
        var menuList = Arrays.asList(str.split("\\|"));
        menuList.forEach(menuItem -> {
            if (menuItem.equalsIgnoreCase("Select")) {
                System.out.println(menuItem);
            } else {
                System.out.println(menuItem);
            }
        });
    }

    public static int getUserChoice() {
        int num = 0;
        try {
            num = Inputter.getIntegerNumber();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return num;
    }

    public static void manageStudent(IStudentDAO service) throws Exception {
        StudentManagement stuMenu = new StudentManagement(service);
        stuMenu.processMenuForStudent();
    }
}
