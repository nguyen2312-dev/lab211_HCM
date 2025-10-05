package Presentation;

import BusinessObject.BrandManagement;
import BusinessObject.CarManagement;
import Core.Interfaces.IBrandDAO;
import Core.Interfaces.ICarDAO;
import Utilities.DataInput;
import java.util.Arrays;
import java.util.List;

public class Menu {

    public static boolean isSave = true;

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
    public static void manageMenu(IBrandDAO serviceBrand, ICarDAO serviceCar) throws Exception {
        CarManagement carService = new CarManagement(serviceCar);
        BrandManagement brandService = new BrandManagement(serviceBrand);
        boolean stop = true;
        System.out.print("|========= Michael BMW CAR SHOWROOM MANAGEMENT SYSTEM =========|");
        try {
            do {
                System.out.println("\n\n|------------------------ MAIN  MENU --------------------------|");
                Menu.print("1.List all brands|2.Add a new brand|"
                        + "3.Search for a brand by ID|4.Update a brand by ID|"
                        + "5.List all brands with prices less than or equal to an input value|"
                        + "6.List all cars in ascending order of brand names|"
                        + "7.Search cars by partial brand name match|"
                        + "8.Add a new car|9.Remove a car by ID|"
                        + "10.Update a car by ID|11.List all cars by a specific color|"
                        + "12.Save data to files|13.Quit program|Select: ");
                int choice = Menu.getUserChoice();
                if (choice >= 1 && choice <= 5) {
                    brandService.processMenuForBrand(choice);
                } else if (choice >= 6 && choice <= 11) {
                    carService.processMenuForCar(choice);
                } else if (choice == 12) {
                    carService.exportToFile();
                    brandService.exportToFile();
                    isSave = true;
                } else if (choice == 13) {
                    if (!isSave) {
                        carService.exportToFile();
                        brandService.exportToFile();
                        isSave = true;
                    }
                    stop = false;
                }
            } while (stop);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
