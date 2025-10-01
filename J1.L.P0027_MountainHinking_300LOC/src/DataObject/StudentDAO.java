package DataObject;

import java.util.stream.Collectors;

import java.util.function.*;

import java.util.*;
import Core.Entities.Student;
import Core.Interfaces.IStudentDAO;

public class StudentDAO implements IStudentDAO {
    private final List<Student> studentList = new ArrayList<>();
    private final FileManager fileManager;

    public StudentDAO(String filename) throws Exception {
        this.fileManager = new FileManager(filename);
        loadFromFile();
    }

    public void loadFromFile() throws Exception {
        String id, name, phone, email, mountainCode;
        try {
            studentList.clear();
            List<String> stuData = fileManager.readDataFromFile();
            for (String s : stuData) {
                // if (s.trim().isEmpty())
                //     continue;

                List<String> stu = Arrays.asList(s.split(","));
                id = stu.get(0).trim();
                name = stu.get(1).trim();
                phone = stu.get(2).trim();
                email = stu.get(3).trim();
                mountainCode = stu.get(4).trim();

                Student student = new Student(id, name, phone, email, mountainCode);
                studentList.add(student);
                if (studentList.isEmpty()) {
                    throw new Exception("Student list is empty");
                }
            }
        } catch (Exception ex) {
            //throw new Exception("Data missing or error cannot read. Please check the file again");
            System.out.println("\nCannot read data from file - some data is missing or some kind of errors\nPlease check the file again");
        }
    }

    @Override
    public List<Student> getStudent() throws Exception {
        return studentList;
    }

    @Override
    public void addStudent(Student student) throws Exception {
        studentList.add(student);
    }

    @Override
    public void updateRegisterStudent(Student student) throws Exception {
        Student stu = findStudentById(student.getId());
        if (stu != null) {
            stu.setName(student.getName());
            stu.setPhone(student.getPhone());
            stu.setEmail(student.getEmail());
            stu.setMountainCode(student.getMountainCode());
        }
    }

    @Override
    public Student findStudentById(String found) throws Exception {
        if (studentList.isEmpty()) {
            getStudent();
        }
        Student student = studentList.stream()
                .filter(s -> s.getId().equalsIgnoreCase(found))
                .findAny().orElse(null);
        return student;
    }

    @Override
    public void deleteStudentByCode(Student student) throws Exception {
        Student stu = findStudentById(student.getId());
        if (stu != null) {
            studentList.remove(stu);
        }
    }

    @Override
    public void writeToFile() throws Exception {
        List<String> stringObjects = studentList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }

    @Override
    public List<Student> search(Predicate<Student> predicate) throws Exception {
        return studentList.stream()
                .filter(student -> predicate.test(student))
                .collect(Collectors.toList());
    }
}
