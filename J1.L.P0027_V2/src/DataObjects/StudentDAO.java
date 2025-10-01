package DataObjects;

import Core.Entities.Student;
import Core.Interfaces.IMountainDAO;
import Core.Interfaces.IStudentDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAO implements IStudentDAO{
    private final List<Student> studentList = new ArrayList<>();
    private final FileManager fileManager;
    IMountainDAO mountainDAO; 
    
    public StudentDAO(String fileName) throws Exception {
        this.fileManager = new FileManager(fileName);
        mountainDAO = new MountainDAO();
        loadDataFromFile();
    }
    
    public void loadDataFromFile() throws Exception {
        String studentID, name, phoneNumber, email, mountainCode;

        try {
            studentList.clear();
            List<String> stuData = fileManager.readDataFromFile();
            for (String e : stuData) {
                if (e.trim().isEmpty()) {
                    continue;
                }
                List<String> students = Arrays.asList(e.split(","));
                studentID = students.get(0).trim();
                name = students.get(1).trim();
                phoneNumber = students.get(2).trim();
                email = students.get(3).trim();
                mountainCode = students.get(4).trim();
                if (getStudentById(studentID) == null && 
                        mountainDAO.getMountainByCode(mountainCode) != null) {
                    Student newStu = new Student(studentID, name, phoneNumber, email, mountainCode);
                    studentList.add(newStu);              
                }
            }
        } catch (Exception ex) {
            throw new Exception("Can not read data from file(Registration). Please check file again.");
        }
    }
    
    @Override
    public List<Student> getStudentList() throws Exception {
        return studentList;
    }

    @Override
    public Student getStudentById(String id) throws Exception {
        Student student = studentList.stream()
                .filter(e -> e.getStudentID().equalsIgnoreCase(id))
                .findAny().orElse(null);
        return student;
    }

    @Override
    public void addStudent(Student student) throws Exception {
        studentList.add(student);
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        Student stu = getStudentById(student.getStudentID());
        if(stu != null) {
            stu.setName(student.getName());
            stu.setPhoneNumber(student.getPhoneNumber());
            stu.setEmail(student.getEmail());
            stu.setMountainCode(student.getMountainCode());
            stu.setTuitionFee(stu.getPhoneNumber());
        }
    }

    @Override
    public void removeStudent(Student student) throws Exception {
        Student stu = getStudentById(student.getStudentID());
        if(stu != null) {
            studentList.remove(stu);
        }
    }

    @Override
    public void saveStudentListToFile() throws Exception {
        List<String> stringObjects = studentList.stream()
                .map(String::valueOf).collect(Collectors.toList());
        String data = String.join("\n", stringObjects);
        fileManager.saveDataToFile(data);
    }

    @Override
    public List<Student> search(Predicate<Student> predicate) throws Exception {
         return studentList.stream()
                .filter(student -> predicate.test(student))
                .collect(Collectors.toList());
    }

    @Override
    public <K> List<K> statistic(Function<Student, String> groupFunc, 
            Function<Map.Entry<String, List<Student>>, K> mapFunc) throws Exception {
        
        Map<String, List<Student>> grouped = studentList.stream()
            .collect(Collectors.groupingBy(groupFunc));
        
        return grouped.entrySet().stream()
                .map(mapFunc)
                .collect(Collectors.toList());
    }  
    
}
