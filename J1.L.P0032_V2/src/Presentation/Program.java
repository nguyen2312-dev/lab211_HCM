package Presentation;

import Core.Interfaces.IBrandDAO;
import Core.Interfaces.ICarDAO;
import DataObjects.BrandDAO;
import DataObjects.CarDAO;

public class Program {
    public static void main(String[] args) {
        try {
            IBrandDAO brandService = new BrandDAO();
            ICarDAO carService = new CarDAO();
            Menu.manageMenu(brandService, carService);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
