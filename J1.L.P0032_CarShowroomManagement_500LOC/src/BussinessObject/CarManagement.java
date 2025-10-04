package BussinessObject;

import Core.Entities.Car;
import Core.Interfaces.ICarDAO;
import DataObject.BrandDAO;
import Presentation.Menu;
import Core.Interfaces.IBrandDAO;
import Utilities.Inputter;

import java.util.*;
import java.util.function.Predicate;

public class CarManagement {
    ICarDAO carDAO;
    IBrandDAO brandDAO;
    BrandManagement br;

    public CarManagement(ICarDAO carDAO) throws Exception {
        this.carDAO = carDAO;
        this.br = new BrandManagement(brandDAO);
        this.brandDAO = new BrandDAO();
    }

    public Car inputCar() throws Exception {
        String id = Inputter.getString("Input car ID: ");
        br.displayBrandList(brandDAO.getBrand());
        String brand = Inputter.getString("Input brand ID: ");
        if (brandDAO.findBrandById(brand) == null) {
            System.out.println("Brand invalid");
        }
        String color = Inputter.getString("Input car color: ");
        String frame = Inputter.getString("Input car frame ID: ");
        String engine = Inputter.getString("Input car engine ID: ");
        return new Car(id, brandDAO.findBrandById(brand), color, frame, engine);
    }

    // function 8: Add a new car
    public void addNewCar() {
        try {
            Car car = inputCar();
            if (carDAO.findCarById(car.getCarId()) != null) {
                System.out.println("Car already exist");
                return;
            }

            carDAO.addCar(car);
            Menu.isSaved = false;
            System.out.println("Car added successfully");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    public void setNewCarInfo(Car car) throws Exception {
        String color = Inputter.getString("Input new color: ");
        if (!color.isEmpty()) {
            car.setColor(color);
        }

        String frame = Inputter.getString("Input new frame ID: ");
        if (!frame.isEmpty()) {
            car.setFrameId(frame);
        }

        String engine = Inputter.getString("Input new engine ID: ");
        if (!engine.isEmpty()) {
            car.setEngineId(engine);
        }
    }

    // function 10: Update a car by ID
    public void updateCar() {
        try {
            String id = Inputter.getString("Input car ID: ");
            Car car = carDAO.findCarById(id);
            if (car == null) {
                System.out.println("Car not found");
                return;
            }

            setNewCarInfo(car);
            carDAO.updateCarById(car);
            Menu.isSaved = false;
            System.out.println("Updated successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // function 9: Remove a car by ID
    public void deleteCar() {
        try {
            String id = Inputter.getString("Enter car ID: ");
            Car car = carDAO.findCarById(id);
            if (car == null) {
                System.out.println("Car not found");
                return;
            }

            carDAO.deleteCarById(car);
            Menu.isSaved = false;
            System.out.println("Car delete successfully");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public List<Car> searchByColor(String value) throws Exception {
        Predicate<Car> predicate = p -> p.getColor().toLowerCase()
                .contains(value.toLowerCase());

        List<Car> result = carDAO.search(predicate);
        return result;
    }

    // function 11: List all cars by a specific color
    public void printCarListColor() throws Exception {
        String color = Inputter.getString("Enter color to search: ");
        List<Car> match = searchByColor(color);
        displayCarList(match);
    }

    public List<Car> searchByBrandName(String value) throws Exception {
        Predicate<Car> predicate = p -> p.getBrand()
                .getBrandName().toLowerCase().contains(value.toLowerCase());

        List<Car> result = carDAO.search(predicate);
        return result;
    }

    // function 7: Search cars by partial brand name match
    public void printCarListBrandName() throws Exception {
        String brand = Inputter.getString("Enter brand to search: ");
        List<Car> match = searchByBrandName(brand);
        displayCarList(match);
    }

    public void displayCarList(List<Car> cars) throws Exception {
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
                    car.getCarId(), car.getBrand().getBrandId(), car.getBrand().getBrandName(),
                    car.getBrand().getSoundBrand(), car.getBrand().getPrice() + "B", car.getColor(),
                    car.getFrameId(), car.getEngineId()));
            System.out.println("|-----------------------------------------------------"
                    + "--------------------------------------------------------------|");
        }
    }


    // function 6: List all cars in ascending order of brand names
    public List<Car> sortCarInOrder() throws Exception {
        List<Car> cars = carDAO.getCar();
        if (cars == null)
            return new ArrayList<>();

        // Sort by brand name ascending (case-insensitive). If brand names equal,
        // sort by brand price descending.
        cars.sort((Car c1, Car c2) -> {
            String name1 = c1.getBrand().getBrandName().toLowerCase();
            String name2 = c2.getBrand().getBrandName().toLowerCase();
            int temp = name1.compareTo(name2);
            if (temp != 0)
                return temp;
            return Double.compare(c2.getBrand().getPrice(), c1.getBrand().getPrice());
        });

        return cars;
    }

    // function 12: Save car data to file
    public void saveCarToFile() throws Exception {
        System.out.println("Car data have been saved successfully");
        carDAO.writeToFile();
    }

}
