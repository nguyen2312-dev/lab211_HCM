package Core.Interfaces;

import Core.Entities.Car;

import java.util.*;
import java.util.function.Predicate;

public interface ICarDAO {
    List<Car> getCar() throws Exception;

    Car findCarById(String found) throws Exception;

    void addCar(Car car) throws Exception;

    void updateCarById(Car car) throws Exception;

    void deleteCarById(Car car) throws Exception;

    void writeToFile() throws Exception;

    List<Car> search(Predicate<Car> predicate) throws Exception;

}
