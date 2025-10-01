package Core.Interfaces;
 
import Core.Entities.Mountain;
import java.util.List;

public interface IMountainDAO {
    List<Mountain> getMountainList() throws Exception ;
    Mountain getMountainByCode(String code) throws Exception ;
}
