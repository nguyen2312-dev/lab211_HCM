package DataObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import Core.Entities.Car;
import Core.Interfaces.IBrandDAO;
import Core.Interfaces.ICarDAO;

public class CarDAO implements ICarDAO {
    private final List<Car> carList = new ArrayList<>();
    private final FileManager fileManager;
    IBrandDAO brandDAO;

    public CarDAO(IBrandDAO brandDAO) throws Exception {
        this.fileManager = new FileManager("cars.txt");
        this.brandDAO = brandDAO;
        loadFromFile();
    }

    public void loadFromFile() throws Exception {
        String id, brandId, color, frameId, engineId;
        try {
            carList.clear();
            List<String> carData = fileManager.readDataFromFile();
            for (String c : carData) {
                List<String> car = Arrays.asList(c.split(","));
                id = car.get(0).trim();
                brandId = car.get(1).trim();
                color = car.get(2).trim();
                frameId = car.get(3).trim();
                engineId = car.get(4).trim();

                Car carDataIn = new Car(id, brandDAO.findBrandById(brandId), color, frameId, engineId);
                carList.add(carDataIn);
                if (carList.isEmpty()) {
                    throw new Exception("Car list is empty");
                }
            }
        } catch (Exception e) {
            System.out.println("\nCannot read data from file - error from car");
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCar() throws Exception {
        return carList;
    }

    @Override
    public Car findCarById(String found) throws Exception {
        if (carList.isEmpty()) {
            getCar();
        }

        Car car = carList.stream()
                .filter(c -> c.getCarId().equalsIgnoreCase(found))
                .findAny().orElse(null);
        return car;
    }

    @Override
    public void addCar(Car car) throws Exception {
        carList.add(car);
    }

    @Override
    public void updateCarById(Car car) throws Exception {
        Car carData = findCarById(car.getCarId());
        if (carData != null) {
            car.setColor(car.getColor());
            car.setFrameId(car.getFrameId());
            car.setEngineId(car.getEngineId());
        }

    }

    @Override
    public void deleteCarById(Car car) throws Exception {
        Car carData = findCarById(car.getCarId());
        if (carData != null) {
            carList.remove(carData);
        }
    }

    @Override
    public void writeToFile() throws Exception {
        List<String> stringObjects = carList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }

    @Override
    public List<Car> search(Predicate<Car> predicate) throws Exception {
        return carList.stream()
                .filter(car -> predicate.test(car))
                .collect(Collectors.toList());
    }
}
