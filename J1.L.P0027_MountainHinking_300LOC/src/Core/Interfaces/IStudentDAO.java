package Core.Interfaces;

import java.util.*;
import java.util.function.Predicate;

import Core.Entities.Student;

public interface IStudentDAO {
    List<Student> getStudent() throws Exception;

    Student findStudentById(String found) throws Exception;

    void addStudent(Student student) throws Exception;

    void updateRegisterStudent(Student student) throws Exception;

    void deleteStudentByCode(Student student) throws Exception;

    void writeToFile() throws Exception;

    List<Student> search(Predicate<Student> predicate) throws Exception;

}
