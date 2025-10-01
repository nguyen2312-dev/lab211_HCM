package DataObject;

import java.util.*;
import Core.Entities.Mountain;
import Core.Interfaces.IMountainDAO;

public class MountainDAO implements IMountainDAO {
    private final List<Mountain> mountainList = new ArrayList<>();
    private final FileManager fileManager;
    private final String filename = "MountainList.csv";

    public MountainDAO() throws Exception {
        this.fileManager = new FileManager(filename);
        loadFromFile();
    }

    public void loadFromFile() throws Exception {
        String id, name, province, description;
        try {
            mountainList.clear();
            List<String> mounData = fileManager.readDataFromFile();
            for (String m : mounData) {
                List<String> moun = Arrays.asList(m.split(","));
                id = moun.get(0).trim();
                name = moun.get(1).trim();
                province = moun.get(2).trim();
                description = moun.get(3).trim();

                Mountain mountain = new Mountain(id, name, province, description);
                mountainList.add(mountain);
            }
            if(mountainList.isEmpty()){
                System.out.println("Mountain list is empty");
            }
        } catch (Exception e) {
            System.out.println("Cannot read data from file - Mountain file error");
            // e.printStackTrace();
        }
    }

    @Override
    public Mountain getMountainByCode(String code) throws Exception {
        Mountain mountain = mountainList.stream().filter(m -> m.getMountainCode().equalsIgnoreCase(code)).findAny()
                .orElse(null);
        return mountain;
    }

    @Override
    public List<Mountain> getMountain() throws Exception {
        return mountainList;
    }

}
