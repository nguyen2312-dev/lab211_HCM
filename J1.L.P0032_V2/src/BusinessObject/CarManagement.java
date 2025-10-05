package BusinessObject;

import Core.Entities.Car;
import Core.Interfaces.IBrandDAO;
import Core.Interfaces.ICarDAO;
import DataObjects.BrandDAO;
import Presentation.Menu;
import Utilities.DataInput;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarManagement {

    ICarDAO carDAO;
    IBrandDAO brandDAO;
    BrandManagement brandManagement;

    public CarManagement(ICarDAO carDAO) throws Exception {
        this.carDAO = carDAO;
        this.brandDAO = new BrandDAO();
        brandManagement = new BrandManagement(brandDAO);
    }

    public void processMenuForCar(int choice) {
        try {
            switch (choice) {
                case 6:
                    printCarMenu(sortCarList(carDAO.getCars()));
                    break;
                case 7:
                    printCarMatchBrandName();
                    break;
                case 8:
                    addNewCar();
                    break;
                case 9:
                    deleteCar();
                    break;
                case 10:
                    updateCar();
                    break;
                case 11:
                    printCarMatchColor();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Car inputCar() throws Exception {
        String carID = DataInput.getString("Enter the car id:");
        if (carDAO.getCarById(carID) != null) {
            System.out.println(">>The car already exists.");
            return null;
        }
        brandManagement.printBrandMenu(brandDAO.getBrands());
        String brandID = DataInput.getString("Enter the brand id(match with the id in the table):");
        if (brandDAO.getBrandById(brandID) == null) {
            System.out.println(">>The brand is not exists.");
            return null;
        }
        String color = DataInput.getString("Enter the car color:");
        String frameID = DataInput.getString("Enter the frame id:");
        String engineID = DataInput.getString("Enter the engine id:");
        return new Car(carID, brandDAO.getBrandById(brandID), color, frameID, engineID);
    }

    //------------------------------------------------
    public void setNewCarInfo(Car car) throws Exception {
        String color = DataInput.getString("Enter new car color:");
        if (!color.isEmpty()) {
            car.setColor(color);
        }
        String frameID = DataInput.getString("Enter new frame id:");
        if (!frameID.isEmpty()) {
            car.setFrameID(frameID);
        }
        String engineID = DataInput.getString("Enter new engine id:");
        if (!engineID.isEmpty()) {
            car.setEngineID(engineID);
        }
    }

    public Car getCar() throws Exception {
        String id = DataInput.getString("Enter car id:");
        Car car = carDAO.getCarById(id);
        return car;
    }

    public void addNewCar() {
        try {
            Car car = inputCar();
            if (car == null) {
                return;
            }
            carDAO.addCar(car);
            Menu.isSave = false;
            System.out.println(">>The car has added successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    public void updateCar() {
        try {
            Car car = getCar();
            if (car == null) {
                System.out.println(">>This car has not registered yet.");
                return;
            }
            System.out.println(">>Enter new information to update or press 'ENTER' to skip.");
            setNewCarInfo(car);
            carDAO.updateCar(car);
            Menu.isSave = false;
            System.out.println(">>The car has updated successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    public void deleteCar() {
        try {
            Car car = getCar();
            if (car == null) {
                System.out.println(">>This car has not registered yet.");
                return;
            }
            showCarInfo(car);
            boolean isDelete = DataInput.getYesNo("Are you sure you want to delete this car? (Y/N): ");
            if (isDelete) {
                carDAO.removeCar(car);
                Menu.isSave = false;
                System.out.println(">>The car has been successfully deleted.");
            } else {
                System.out.println(">>The deletion process is cancelled.");
            }
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    public void showCarInfo(Car car) throws Exception {
        System.out.println("Car Details:");
        System.out.println("--------------------------------------------------");
        System.out.println("Car ID      : " + car.getCarID());
        System.out.println("Brand ID    : " + car.getBrand().getBrandID());
        System.out.println("Brand Name  : " + car.getBrand().getBrandName());
        System.out.println("Sound Brand : " + car.getBrand().getSoundBrand());
        System.out.println("Price       : " + car.getBrand().getPrice());
        System.out.println("Color       : " + car.getColor());
        System.out.println("Frame ID    : " + car.getFrameID());
        System.out.println("Engine ID   : " + car.getEngineID());
        System.out.println("--------------------------------------------------");
    }

    public List<Car> searchByPartialName(String value) throws Exception {
        Predicate<Car> predicate = p -> p.getBrand().getBrandName().toLowerCase()
                .contains(value.toLowerCase());
        List<Car> cars = carDAO.search(predicate);
        return cars;
    }

    public void printCarMatchBrandName() throws Exception {
        String searchBrandName = DataInput.getString("Enter by partial brand name:");
        List<Car> matchList = searchByPartialName(searchBrandName);
        printCarMenu(matchList);
    }

    public List<Car> filterCarByColor(String value) throws Exception {
        Predicate<Car> filterColor = p -> p.getColor().equalsIgnoreCase(value);
        List<Car> cars = carDAO.search(filterColor);
        return cars;
    }

    public void printCarMatchColor() throws Exception {
        String searchColor = DataInput.getString("Enter color you want to list car:");
        List<Car> matchList = filterCarByColor(searchColor);
        printCarMenu(matchList);
    }

    public List<Car> sortCarList(List<Car> carSortedList) {
        List<Car> sorted = carSortedList.stream()
                .sorted(Comparator
                        .comparing((Car c) -> c.getBrand().getBrandName(), String.CASE_INSENSITIVE_ORDER)
                        .thenComparing((Car c) -> c.getBrand().getPrice(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return sorted;
    }

    public void printCarMenu(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("No car available.");
            return;
        }
        System.out.println("\n|==================================================== CARS LIST "
                + "====================================================|");
        System.out.println(String.format("| %-6s | %-8s | %-30s | %-16s | %-8s | %-7s | %-8s | %-9s |",
                 "CAR ID", "BRAND ID", "BRAND NAME", "SOUND BRAND", "PRICE",
                 "COLOR", "FRAME ID", "ENGINE ID"));
        System.out.println("|====================================================="
                + "==============================================================|");
        for (Car car : cars) {
            System.out.println(String.format("| %-6s | %-8s | %-30s | %-16s | %-8s | %-7s | %-8s | %-9s |",
                    car.getCarID(), car.getBrand().getBrandID(), car.getBrand().getBrandName(),
                    car.getBrand().getSoundBrand(), car.getBrand().getPrice() + "B", car.getColor(),
                    car.getFrameID(), car.getEngineID()));
            System.out.println("|-----------------------------------------------------"
                    + "--------------------------------------------------------------|");
        }
        System.out.println("\nTOTAL: " + cars.size() + " brand(s).");
    }
    public void exportToFile() throws Exception {
        carDAO.saveCarListToFile();
        System.out.println("Car data has been successfully saved to `cars.txt`.");
    }

}
