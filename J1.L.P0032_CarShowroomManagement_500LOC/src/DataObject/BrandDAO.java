package DataObject;

import java.util.*;

import java.util.stream.Collectors;

import java.util.function.*;

import Core.Entities.Brand;
import Core.Interfaces.IBrandDAO;

public class BrandDAO implements IBrandDAO {
    private final List<Brand> brandList = new ArrayList<>();
    private final FileManager fileManager;

    public BrandDAO() throws Exception {
        this.fileManager = new FileManager("brands.txt");
        loadFromFile();
    }

    public void loadFromFile() throws Exception {
        String id, name, sound;
        double price;
        try {
            brandList.clear();
            List<String> brandData = fileManager.readDataFromFile();
            for (String b : brandData) {
                List<String> br = Arrays.asList(b.split("," , 3));
                id = br.get(0);
                name = br.get(1);
                // sound = br.get(2);
                // price = Double.parseDouble(br.get(3));
                String[] soundPrice = br.get(2).split(":");
                sound = soundPrice[0].trim();
                price = Double.parseDouble(soundPrice[1].replace("B", "").trim());
                
                Brand brand = new Brand(id, name, sound, price);
                brandList.add(brand);
                if (brandList.isEmpty()) {
                    throw new Exception("Empty brand list");
                }
            }
        } catch (Exception e) {
            System.err.println("\nCannot read data from file - error from brand");
            e.printStackTrace();
        }
    }

    @Override
    public List<Brand> getBrand() throws Exception {
        return brandList;
    }

    @Override
    public void addBrand(Brand brand) throws Exception {
        brandList.add(brand);
    }

    @Override
    public void updateBrandById(Brand brand) throws Exception {
        Brand br = findBrandById(brand.getBrandId());
        if (br != null) {
            br.setBrandName(brand.getBrandName());
            br.setSoundBrand(brand.getSoundBrand());
            br.setPrice(brand.getPrice());
        }
    }

    @Override
    public Brand findBrandById(String found) throws Exception {
        if (brandList.isEmpty()) {
            getBrand();
        }

        Brand brand = brandList.stream()
                .filter(b -> b.getBrandId().equalsIgnoreCase(found))
                .findAny().orElse(null);
        return brand;
    }

    @Override
    public void writeToFile() throws Exception {
        List<String> brandData = brandList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        String data = String.join(",", brandData);
        fileManager.saveDataToFile(data);
    }

    @Override
    public List<Brand> search(Predicate<Brand> predicate) throws Exception {
        return brandList.stream()
                .filter(brand -> predicate.test(brand))
                .collect(Collectors.toList());
    }
}
