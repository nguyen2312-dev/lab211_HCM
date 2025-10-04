package Presentation;

import BussinessObject.*;
import Core.Interfaces.ICarDAO;
import Core.Interfaces.IBrandDAO;
import DataObject.CarDAO;
import DataObject.BrandDAO;
import Utilities.Inputter;

//this whole ass menu was created by GPT-5 Mini i will do my own menu later
public class Menu {
    BrandManagement brandService;
    CarManagement carService;
    public static boolean isSaved = true;

    public Menu() throws Exception {
        IBrandDAO brandDAO = new BrandDAO();
        ICarDAO carDAO = new CarDAO(brandDAO);
        this.brandService = new BrandManagement(brandDAO);
        this.carService = new CarManagement(carDAO);
    }

    // Main menu loop
    public void proccessMenu() {
        boolean stop = true;
        do {
            System.out.println("========================== Car Showroom Management ========================== ");
            System.out.println("1.  List all brands");
            System.out.println("2.  Add new brand");
            System.out.println("3.  Search brand by id");
            System.out.println("4.  Update brand by id");
            System.out.println("5.  Search brands by price <= value");
            System.out.println("6.  List all cars in order by brand name");
            System.out.println("7.  Search cars by brand name");
            System.out.println("8.  Add new car");
            System.out.println("9.  Remove car by id");
            System.out.println("10. Update car by id");
            System.out.println("11. Search cars by color");
            System.out.println("12. Save data to files");
            System.out.println("13. Exit");
            System.out.println("Select: ");
            try {
                int choice = Inputter.getIntegerNumber();
                switch (choice) {
                    case 1:
                        brandService.displayBrandList(brandService.getAllBrands());
                        break;
                    case 2:
                        brandService.addNewBrand();
                        break;
                    case 3:
                        brandService.showBrandById();;
                        break;
                    case 4:
                        brandService.updateBrand();
                        break;
                    case 5:
                        brandService.displayBrandListByPrice();
                        break;
                    case 6:
                        carService.displayCarList(carService.sortCarInOrder());
                        break;
                    case 7:
                        carService.printCarListBrandName();
                        break;

                    case 8:
                        carService.addNewCar();
                        break;
                    case 9:
                        carService.deleteCar();
                        break;
                    case 10:
                        carService.updateCar();
                        break;
                    case 11:
                        carService.printCarListColor();
                        break;
                    case 12:
                        brandService.saveDataToFile();
                        carService.saveCarToFile();
                        isSaved = true;
                        break;
                    case 13:
                        if (!isSaved) {
                            brandService.saveDataToFile();
                            carService.saveCarToFile();
                            isSaved = true;
                        }
                        System.out.println("Exiting...");
                        stop = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        } while (stop);
    }
}
