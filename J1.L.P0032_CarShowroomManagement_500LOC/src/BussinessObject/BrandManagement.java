package BussinessObject;

import Core.Entities.Brand;
import Core.Interfaces.IBrandDAO;
import Presentation.Menu;
import Utilities.Inputter;

import java.util.*;
import java.util.function.Predicate;

public class BrandManagement {
    IBrandDAO brandDAO;

    public BrandManagement(IBrandDAO brandDAO) throws Exception {
        this.brandDAO = brandDAO;
    }

    public Brand inputBrand() throws Exception {
        String id = Inputter.getString("Enter brand ID: ");
        String name = Inputter.getString("Enter brand name: ");
        String sound = Inputter.getString("Enter brand sound: ");
        Double price = Inputter.getDoubleNumber("Enter price: ");
        return new Brand(id, name, sound, price);
    }

    // function 2: Add new brand
    public void addNewBrand() {
        try {
            Brand brand = inputBrand();
            if (brandDAO.findBrandById(brand.getBrandId()) != null) {
                System.out.println("Brand ID already exist");
                return;
            }
            brandDAO.addBrand(brand);
            Menu.isSaved = false;
            System.out.println("Brand added succesfully");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public void setNewBrandInfo(Brand brand) throws Exception {
        String name = Inputter.getString("Enter new name: ");
        if (!name.isEmpty()) {
            brand.setBrandId(name);
        }

        String sound = Inputter.getString("Enter new sound: ");
        if (!sound.isEmpty()) {
            brand.setSoundBrand(sound);
        }

        Double price = Inputter.getDoubleNumber("Enter new price: ");
        if (price > 0) {
            brand.setPrice(price);
        }
    }

    // function 4: Update brand by ID
    public void updateBrand() {
        try {
            Brand brand = getBrand();
            if (brand == null) {
                System.out.println("Brand not found");
                return;
            }

            setNewBrandInfo(brand);
            brandDAO.updateBrandById(brand);
            Menu.isSaved = false;
            System.out.println("Updated successfully");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    // public void displayBrandToDelete() throws Exception {
    //     String match = Inputter.getString("InputID to search: ");
    //     Brand result = brandDAO.findBrandById(match);
    //     showBrandById();
    // }

    //
    public Brand getBrand() throws Exception {
        String id = Inputter.getString("Input ID: ");
        Brand brand = brandDAO.findBrandById(id);
        return brand;
    }

    // function 3: Search brand by ID
    public void showBrandById() throws Exception {
        Brand brand = getBrand();
        if (brand == null) {
            System.out.println("Brand does not exist");
        } else {

            System.out.println("Brand detail:");
            System.out.println("--------------------------------------------------");
            System.out.println("Brand ID   : " + brand.getBrandId());
            System.out.println("Brand Name : " + brand.getBrandName());
            System.out.println("Sound      : " + brand.getSoundBrand());
            System.out.println("Price      : " + brand.getPrice());
            System.out.println("--------------------------------------------------");
        }
    }

    public List<Brand> searchByPrice(double value) throws Exception {
        Predicate<Brand> predicate = (p -> p.getPrice() <= value);
        List<Brand> result = brandDAO.search(predicate);
        return result;
    }

    // function 5: List brand with prices less or equal to value
    public void displayBrandListByPrice() throws Exception {
        Double match = Inputter.getDoubleNumber("Input price to search: ");
        List<Brand> result = searchByPrice(match);
        displayBrandList(result);
    }

    // function 1: List all brand
    public void displayBrandList(List<Brand> brands) throws Exception {
        if (brands.isEmpty()) {
            System.out.println("Brand list is empty");
            return;
        }
        System.out.println("\n|============================== BRANDS LIST ==============================|");
        System.out.println(String.format("| %-8s | %-30s | %-16s | %-8s |",
                "BRAND ID", "BRAND NAME", "SOUND BRAND", "PRICE"));
        System.out.println("|=========================================================================|");
        for (Brand brand : brands) {
            System.out.println(String.format("| %-8s | %-30s | %-16s | %-8s |",
                    brand.getBrandId(), brand.getBrandName(),
                    brand.getSoundBrand(), brand.getPrice() + "B"));
            System.out.println("|-------------------------------------------------------------------------|");
        }
        System.out.println("\nTOTAL: " + brands.size() + " brand(s).");
    }

    // convenience method to retrieve all brands
    public List<Brand> getAllBrands() throws Exception {
        return brandDAO.getBrand();
    }

    // function 12: save car data to file
    public void saveDataToFile() throws Exception {
        System.out.println("Brand saved successfully");
        brandDAO.writeToFile();
    }
}
