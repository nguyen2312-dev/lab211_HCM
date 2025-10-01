package Core.Interfaces;
 
import Core.Entities.Student;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;


public interface IStudentDAO {
    List<Student> getStudentList() throws Exception ;
    Student getStudentById(String id) throws Exception ;
    void addStudent(Student student) throws Exception ;
    void updateStudent(Student student) throws Exception ;
    void removeStudent(Student student) throws Exception ;
    void saveStudentListToFile() throws Exception ;
    List<Student> search(Predicate<Student> predicate) throws Exception ;
    <K> List<K> statistic(Function<Student, String> groupFunc, 
            Function<Map.Entry<String, List<Student>>, K> mapFunc) throws Exception;
}
