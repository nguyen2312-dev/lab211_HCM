package Core.Interfaces;

import java.util.List;

import Core.Entities.Mountain;

public interface IMountainDAO {
    List<Mountain> getMountain() throws Exception;
    
    Mountain getMountainByCode(String code) throws Exception;
}
