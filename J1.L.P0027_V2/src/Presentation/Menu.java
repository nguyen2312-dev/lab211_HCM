package Presentation;


import BusinessObject.StudentManagement;
import Core.Interfaces.IStudentDAO;
import Utilities.DataInput;
import java.util.Arrays;
import java.util.List;

public class Menu {
    //------------------------------------------------
    public static void print(String str) {
        List<String> menuList = Arrays.asList(str.split("\\|"));
        menuList.forEach(menuItem -> {
            if (menuItem.equalsIgnoreCase("Select:")) {
                System.out.print(menuItem);
            } else {
                System.out.println(menuItem);
            }
        });
    }

    //------------------------------------------------
    public static int getUserChoice() {
        int number = 0;
        try {
            number = DataInput.getIntegerNumber();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return number;
    }

    //------------------------------------------------
    public static void manageStudent(IStudentDAO service) throws Exception {
        StudentManagement stuMenu = new StudentManagement(service);
        stuMenu.processMenu();
    }
    
}
