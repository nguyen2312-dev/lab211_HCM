package DataObjects;

import Core.Entities.Brand;
import Core.Entities.Car;
import Core.Interfaces.ICarDAO;
import Core.Interfaces.IBrandDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CarDAO implements ICarDAO {
    private final List<Car> carList = new ArrayList<>();
    private final FileManager fileManager;
    IBrandDAO brandDAO;
    public static Set<String> frameSet = new HashSet<>();
    public static Set<String> engineSet = new HashSet<>();

    public CarDAO() throws Exception {
        this.brandDAO = new BrandDAO();
        this.fileManager = new FileManager("cars.txt");
        loadDataFromFile();
    }
    //----------------------------------------

    public void loadDataFromFile() throws Exception {
        String carID, brandID, color, frameID, engineID;

        try {
            carList.clear();
            List<String> brandData = fileManager.readDataFromFile();
            for (String e : brandData) {
                List<String> parts = Arrays.asList(e.split(","));
                carID = parts.get(0).trim();
                brandID = parts.get(1).trim();
                color = parts.get(2).trim();
                frameID = parts.get(3).trim();
                engineID = parts.get(4).trim();
                Brand brand = brandDAO.getBrandById(brandID);
                if (brandDAO.getBrands().contains(brand) && getCarById(carID) == null 
                        && !color.isEmpty() && !frameSet.contains(frameID) 
                        && !engineSet.contains(engineID)) {
                    Car newCar = new Car(carID, brand, color, frameID, engineID);
                    carList.add(newCar);   
                    frameSet.add(frameID.toUpperCase());
                    engineSet.add(engineID.toUpperCase());
                }
            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file. Please check file again.");
        }
    }
    //----------------------------------------

    @Override
    public List<Car> getCars() throws Exception {
        return carList;
    }
    //----------------------------------------

    @Override
    public void addCar(Car car) throws Exception {
        carList.add(car);
    }
    //----------------------------------------

    @Override
    public void updateCar(Car car) throws Exception {
        Car cars = getCarById(car.getCarID());
        if(cars != null) {
            cars.setBrand(car.getBrand());
            cars.setColor(car.getColor());
            cars.setFrameID(car.getFrameID());
            cars.setEngineID(car.getEngineID());
        }
    }
    //----------------------------------------

    @Override
    public void removeCar(Car car) throws Exception {
        Car cars = getCarById(car.getCarID());
        if(cars != null) {
            carList.remove(cars);
        }
    }
    //----------------------------------------

    @Override
    public Car getCarById(String id) throws Exception {
        Car car = carList.stream()
                .filter(e -> e.getCarID().equalsIgnoreCase(id))
                .findAny().orElse(null);
        return car;
    }
    //----------------------------------------

    @Override
    public List<Car> search(Predicate<Car> predicate) throws Exception {
        return carList.stream()
                .filter(brand -> predicate.test(brand))
                .collect(Collectors.toList());
    }
    //----------------------------------------

    @Override
    public void saveCarListToFile() throws Exception {
        List<String> stringObjects = carList.stream()
                .map(String::valueOf).collect(Collectors.toList());
        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }
    //----------------------------------------
    //More the methods here
}
