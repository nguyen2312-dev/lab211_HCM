package DataObjects;

import Core.Entities.Mountain;
import Core.Interfaces.IMountainDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MountainDAO implements IMountainDAO {
    private final List<Mountain> mountainList = new ArrayList<>();
    private final FileManager fileManager;
    
    public MountainDAO() throws Exception {
        this.fileManager = new FileManager("MountainList.csv");
        loadDataFromFile();
    }
    
    public void loadDataFromFile() throws Exception {
        String mountainCode, mountainName, province, description;

        try {
            mountainList.clear();
            List<String> mData = fileManager.readDataFromFile();
            for (String e : mData) {
                List<String> mountains = Arrays.asList(e.split(","));
                mountainCode = mountains.get(0).trim();
                mountainName = mountains.get(1).trim();
                province = mountains.get(2).trim();
                description = mountains.get(3).trim().replace(";;", "");
                Mountain mountain = new Mountain(mountainCode, mountainName, province, description);
                mountainList.add(mountain);
            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file. Please check file again.");
        }
    }

    @Override
    public List<Mountain> getMountainList() throws Exception {
        return mountainList;
    }

    @Override
    public Mountain getMountainByCode(String code) throws Exception {
        return mountainList.stream()
                .filter(m -> m.getMountainCode().equalsIgnoreCase(code))
                .findAny().orElse(null);        
    }
       
}
