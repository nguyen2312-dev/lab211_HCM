package Core.Interfaces;

import Core.Entities.Brand;

import java.util.*;
import java.util.function.Predicate;


public interface IBrandDAO {
    List<Brand> getBrand() throws Exception;

    Brand findBrandById(String found) throws Exception;
    
    void updateBrandById(Brand brand) throws Exception;

    void addBrand(Brand brand) throws Exception;

    void writeToFile() throws Exception;

    List<Brand> search(Predicate<Brand> predicate) throws Exception;
}
