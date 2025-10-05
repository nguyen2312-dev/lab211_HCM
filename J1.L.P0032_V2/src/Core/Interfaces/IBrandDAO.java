package Core.Interfaces;

import Core.Entities.Brand;
import java.util.List;
import java.util.function.Predicate;

public interface IBrandDAO {
    List<Brand> getBrands() throws Exception ;
    Brand getBrandById(String id) throws Exception ;
    void addBrand(Brand customer) throws Exception ;
    void updateBrand(Brand customer) throws Exception ;
    void saveBrandListToFile() throws Exception ;
    List<Brand> search(Predicate<Brand> predicate) throws Exception ;
}