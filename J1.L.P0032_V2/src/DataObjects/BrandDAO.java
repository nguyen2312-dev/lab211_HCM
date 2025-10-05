package DataObjects;

import Core.Entities.Brand;
import Core.Interfaces.IBrandDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class BrandDAO implements IBrandDAO {
    private final List<Brand> brandList = new ArrayList<>();
    private final FileManager fileManager;

    public BrandDAO() throws Exception {
        this.fileManager = new FileManager("brands.txt");
        loadDataFromFile();
    }
    //----------------------------------------

    public void loadDataFromFile() throws Exception {
        String brandID, brandName, soundBrand;
        double price;

        try {
            brandList.clear();
            List<String> brandData = fileManager.readDataFromFile();
            for (String e : brandData) {
                List<String> parts = Arrays.asList(e.split(","));
                brandID = parts.get(0).trim();
                brandName = parts.get(1).trim();
                List<String> soundPrice = Arrays.asList(parts.get(2).trim().split(":"));
                soundBrand = soundPrice.get(0).trim();
                price = Double.parseDouble(soundPrice.get(1).trim().split("B")[0]);
                if (getBrandById(brandID) == null && !brandName.isEmpty() && 
                        !soundBrand.isEmpty() && price > 0) {
                    Brand newBrand = new Brand(brandID, brandName, soundBrand, price);
                    brandList.add(newBrand);
                }
            }
            if (brandList.isEmpty()) {
                System.out.println("Brand List is empty!");
            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file. Please check file again.");
        }
    }
    //----------------------------------------

    @Override
    public List<Brand> getBrands() throws Exception {
        return brandList;
    }
    //----------------------------------------

    @Override
    public void addBrand(Brand brand) throws Exception {
        brandList.add(brand);
    }
    //----------------------------------------

    @Override
    public void updateBrand(Brand brand) throws Exception {
        Brand brands = getBrandById(brand.getBrandID());
        if(brands != null) {
            brands.setBrandName(brand.getBrandName());
            brands.setSoundBrand(brand.getSoundBrand());
            brands.setPrice(brand.getPrice());
        }
    }

    //----------------------------------------

    @Override
    public Brand getBrandById(String id) throws Exception {
        Brand brand = brandList.stream()
                .filter(e -> e.getBrandID().equalsIgnoreCase(id))
                .findAny().orElse(null);
        return brand;
    }
    //----------------------------------------

    @Override
    public List<Brand> search(Predicate<Brand> predicate) throws Exception {
        return brandList.stream()
                .filter(brand -> predicate.test(brand))
                .collect(Collectors.toList());
    }
    //----------------------------------------

    @Override
    public void saveBrandListToFile() throws Exception {
        List<String> stringObjects = brandList.stream()
                .map(String::valueOf).collect(Collectors.toList());
        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }
    //----------------------------------------
    //More the methods here
}
