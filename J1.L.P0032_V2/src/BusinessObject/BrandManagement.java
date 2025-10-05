package BusinessObject;

import Core.Entities.Brand;
import Core.Interfaces.IBrandDAO;
import Presentation.Menu;
import Utilities.DataInput;
import java.util.List;
import java.util.function.Predicate;

public class BrandManagement {

    IBrandDAO brandDAO;

    public BrandManagement(IBrandDAO brandDAO) throws Exception {
        this.brandDAO = brandDAO;
    }

    public void processMenuForBrand(int choice) {
        try {
            switch (choice) {
                case 1:
                    printBrandMenu(brandDAO.getBrands());
                    break;
                case 2:
                    addNewBrand();
                    break;
                case 3:
                    showBrandInfo();
                    break;
                case 4:
                    updateBrand();
                    break;
                case 5:
                    printBrandLessThanPrice();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Brand inputBrand() throws Exception {
        String brandID = DataInput.getString("Enter the brand id:");
        if (brandDAO.getBrandById(brandID) != null) {
            System.out.println(">>The brand already exists.");
            return null;
        }
        String brandName = DataInput.getString("Enter the brand name:");
        String soundBrand = DataInput.getString("Enter the sound manufacturer:");
        double price = DataInput.getDoubleNumber("Enter the brand price:");
        return new Brand(brandID, brandName, soundBrand, price);
    }

    //------------------------------------------------
    public void setNewBrandInfo(Brand brand) throws Exception {
        String brandName = DataInput.getString("Enter new brand name:");
        if (!brandName.isEmpty()) {
            brand.setBrandName(brandName);
        }
        String soundBrand = DataInput.getString("Enter new sound brand:");
        if (!soundBrand.isEmpty()) {
            brand.setSoundBrand(soundBrand);
        }
        double price = DataInput.getDoubleNumber("Enter new brand price:");
        if (price > 0) {
            brand.setPrice(price);
        }
    }

    //------------------------------------------------
    public void addNewBrand() {
        try {
            Brand brand = inputBrand();
            if (brand == null) {
                return;
            }
            brandDAO.addBrand(brand);
            Menu.isSave = false;
            System.out.println(">>The brand has added successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    //------------------------------------------------
    public Brand getBrand() throws Exception {
        String id = DataInput.getString("Enter brand id:");
        Brand brand = brandDAO.getBrandById(id);
        return brand;
    }

    //------------------------------------------------
    public void updateBrand() {
        try {
            Brand brand = getBrand();
            if (brand == null) {
                System.out.println(">>This brand does not exist!");
                return;
            }
            System.out.println(">>Enter new information to update or press 'ENTER' to skip.");
            setNewBrandInfo(brand);
            brandDAO.updateBrand(brand);
            Menu.isSave = false;
            System.out.println(">>The brand has updated successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }

    public List<Brand> filterBrandLessThanPrice(double value) throws Exception {
        Predicate<Brand> predicate = s -> s.getPrice() <= value;
        List<Brand> students = brandDAO.search(predicate);
        return students;
    }

    public void printBrandLessThanPrice() throws Exception {
        double searchName = DataInput.getDoubleNumber("Enter price to filter: ");
        List<Brand> matchList = filterBrandLessThanPrice(searchName);
        printBrandMenu(matchList);
    }

    //------------------------------------------------
    public void printBrandMenu(List<Brand> brands) {
        if (brands.isEmpty()) {
            System.out.println("No brands available.");
            return;
        }
        System.out.println("\n|============================== BRANDS LIST ==============================|");
        System.out.println(String.format("| %-8s | %-30s | %-16s | %-8s |",
                "BRAND ID", "BRAND NAME", "SOUND BRAND", "PRICE"));
        System.out.println("|=========================================================================|");
        for (Brand brand : brands) {
            System.out.println(String.format("| %-8s | %-30s | %-16s | %-8s |",
                    brand.getBrandID(), brand.getBrandName(),
                    brand.getSoundBrand(), brand.getPrice() + "B"));
            System.out.println("|-------------------------------------------------------------------------|");
        }
        System.out.println("\nTOTAL: " + brands.size() + " brand(s).");
    }

    //------------------------------------------------
    public void showBrandInfo() throws Exception {
        Brand brand = getBrand();
        if (brand == null) {
            System.out.println("This brand does not exist!");
        } else {         
            System.out.println("Brand Details:");
            System.out.println("--------------------------------------------------");
            System.out.println("Brand ID   : " + brand.getBrandID());
            System.out.println("Brand Name : " + brand.getBrandName());
            System.out.println("Sound Brand: " + brand.getSoundBrand());
            System.out.println("Price      : " + brand.getPrice());
            System.out.println("--------------------------------------------------");
        }
    }
    
    public void exportToFile() throws Exception {
        brandDAO.saveBrandListToFile();
        System.out.println("Brand data has been successfully saved to `brands.txt`.");
    }
}
