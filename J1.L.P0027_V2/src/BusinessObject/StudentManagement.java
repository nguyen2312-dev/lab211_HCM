package BusinessObject;

import Core.Entities.Mountain;
import Core.Entities.Student;
import Core.Interfaces.IMountainDAO;
import Core.Interfaces.IStudentDAO;
import DataObjects.MountainDAO;
import Presentation.Menu;
import Utilities.DataInput;
import java.text.DecimalFormat;
import Utilities.DataValidation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collector;

public class StudentManagement {
    IStudentDAO studentDAO;
    boolean isSave;
    IMountainDAO mountainDAO; 
    
    public StudentManagement(IStudentDAO studentDAO) throws Exception {
        this.studentDAO = studentDAO;
        mountainDAO = new MountainDAO();
        isSave = true;
    }
    
    public void processMenu() {
        int choice;
        try {
            do {
                Menu.print("\n====================== MAIN MENU ======================|"
                        + "1. New Registration|2. Update Registration Information|"
                        + "3. Display Registered List|4. Delete Registration Information|"
                        + "5. Search Participants by Name|6. Filter Data by Campus|"
                        + "7. Statistics of Registration Numbers by Location|"
//                        + "10. Display Registered List With Status|"
                        + "8. Save Data to File|9. Exit the Program|Select :");
                choice = Menu.getUserChoice();
                switch (choice) {
                    case 1:
                        addNewStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                    case 3:
                        printAllStudent("No students have registered yet.", studentDAO.getStudentList());
                        break;
                    case 4:
                        deleteStudent();
                        break;
                    case 5: 
                        printStudentsMatchName();
                        break;
                    case 6:
                        printStudentsInCampus();
                        break;
                    case 7:
                        printMountainStatistic();
                        break;
//                    case 10 :
//                        printAllStudent2(studentDAO.getStudentList());
//                        break;
                    case 8:
                        exportToFile();
                        break;
                    case 9:
                        checkSaveBeforeExit();
                        break;
                    default:
                        System.out.println(">>This function is not available.");
                        break;
                }
            } while (choice != 9);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Student inputStudent() throws Exception {
        String studentID = DataInput.getString("Enter the id (HE|SE|DE|QE|CE and 6 numbers):");
        String name = DataInput.getString("Enter the name (2-20 characters):");
        String phoneNumber = DataInput.getString("Enter phone number"
                + "(exactly 10 digits and belong to a Vietnamese network operator):");
        String email = DataInput.getString("Enter the email:");
        displayMountain();
        String mountainCode = DataInput.getString("Enter mountainCode(format: MTxx and matched with table):").toUpperCase();
        return new Student(studentID, name, phoneNumber, email, mountainCode);
    }
    
    public void setNewStudentInfo(Student student) throws Exception {
        String name = DataInput.getString("Enter new name:");
        if (!name.isEmpty()) {
            student.setName(name);
        }
        String phoneNumber = DataInput.getString("Enter new phone number"
                + "(exactly 10 digits and belong to a Vietnamese network operator):");
        if (!phoneNumber.isEmpty()) {
            student.setPhoneNumber(phoneNumber);
        }
        String email = DataInput.getString("Enter new email:");
        if (!email.isEmpty()) {
            student.setEmail(email);
        }
        displayMountain();
        String mountainCode = DataInput.getString("Enter new mountain Code(format: MTxx):").toUpperCase();
        if (!mountainCode.isEmpty()) {
            student.setMountainCode(mountainCode);
        }
    }
    
    public Student getStudent() throws Exception {
        String id = DataInput.getString("Enter student id:");
        Student student = studentDAO.getStudentById(id);
        return student;
    }
    
    public void addNewStudent() {
        try {
            Student student = inputStudent();
            if (studentDAO.getStudentById(student.getStudentID()) != null) {
                System.out.println(">>The student already exists.");
                return;
            }
            studentDAO.addStudent(student);
            isSave = false;
            System.out.println(">>The student has added successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }
    
    public void updateStudent() {
        try {
            Student student = getStudent();
            if (student == null) {
                System.out.println(">>This student has not registered yet.");
                return;
            }
            System.out.println(">>Enter new information to update or press 'ENTER' to skip.");
            setNewStudentInfo(student);
            studentDAO.updateStudent(student);
            isSave = false;
            System.out.println(">>The student has updated successfully.");
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }
    
    public void deleteStudent() {
        try {
            Student student = getStudent();
            if (student == null) {
                System.out.println(">>This student has not registered yet.");
                return;
            }
            showMessage(student);
            boolean isDelete = DataInput.getYesNo("Are you sure you want to delete this registration? (Y/N): ");
            if (isDelete){
                studentDAO.removeStudent(student);
                isSave = false;
                System.out.println(">>The registration has been successfully deleted.");
            } else {
                System.out.println(">>The deletion process is cancelled.");
            }
        } catch (Exception e) {
            System.out.println(">>Error:" + e.getMessage());
        }
    }
    
    public void showMessage(Student student) {
        System.out.println("Student Details:");
        System.out.println("--------------------------------------------------");
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("Name      : " + student.getName());
        System.out.println("Phone     : " + student.getPhoneNumber());
        System.out.println("Mountain  : " + student.getMountainCode());
        System.out.println("Fee       : " + student.getTuitionFee());
        System.out.println("--------------------------------------------------");
    }
    
    public List<Student> searchByName(String value) throws Exception {
        Predicate<Student> predicate = p -> p.getName().toLowerCase()
                .contains(value.toLowerCase());
        List<Student> students = studentDAO.search(predicate);
        return students;
    }
    
    public void printStudentsMatchName() throws Exception {
        String searchName = DataInput.getString("Enter name to search: ");
        List<Student> matchList = searchByName(searchName);
        printAllStudent("No one matches the search criteria!", matchList);
    }
    
    public List<Student> filterByCampus(String value) throws Exception {
        Predicate<Student> predicate = p -> p.getStudentID().substring(0, 2).equalsIgnoreCase(value);
        List<Student> students = studentDAO.search(predicate);
        return students;
    }
    
    public void printStudentsInCampus() throws Exception {
        String campus = DataInput.getString("Enter campus to filter(HE|SE|DE|QE|CE): ");
        if (!DataValidation.checkStringWithFormat(campus.toUpperCase(), "^[HSDQC][E]$")){
            System.out.println("Campus Code is no match! Only have 5 campus(HE|SE|DE|QE|CE)");
            return;
        }
        List<Student> matchList = filterByCampus(campus);
        printAllStudent("No one matches the search criteria!", matchList);
    }
    
    public String formatTuitionFee(double tuitionFee) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String finalTuitionFee = decimalFormat.format(tuitionFee);
        return finalTuitionFee;
    }
    
    public void printAllStudent(String emptyMsg, List<Student> students) {
        if (students.isEmpty()) {
            System.out.println(emptyMsg);
        } else {
            System.out.println("|---------------------------------- STUDENT LIST --------------------------------|");
            System.out.format("| %-12s| %-19s| %-13s| %-14s| %-12s |\n", "Student ID",
                    "Name", "Phone", "Peak Code", "Fee");
            System.out.println("|--------------------------------------------------------------------------------|");
            for (Student student : students) {
                System.out.format("| %-12s| %-19s| %-13s| %-14s| %-12s |\n",
                        student.getStudentID(), student.getName(), student.getPhoneNumber(),
                        student.getMountainCode(), formatTuitionFee(student.getTuitionFee()));
            }
            System.out.println("|--------------------------------------------------------------------------------|");
        }
    }
    
    public List<Student> getStatus() throws Exception {
        double fee = DataInput.getDoubleNumber("Input fee:");
        Predicate<Student> predicate = p -> p.getTuitionFee() >= fee;
        List<Student> students = studentDAO.search(predicate);
        return students;
    }
    
    public void printAllStudent2(List<Student> students) throws Exception {
        List<Student> status = getStatus();
        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
        } else {
            System.out.println("|--------------------------------------- STUDENT LIST -------------------------------------|");
            System.out.format("| %-12s| %-19s| %-13s| %-14s| %-12s | %-7s |\n", "Student ID",
                    "Name", "Phone", "Peak Code", "Fee", "Status");
            System.out.println("|------------------------------------------------------------------------------------------|");
            for (Student student : students) {
                System.out.format("| %-12s| %-19s| %-13s| %-14s| %-12s | %-7s |\n",
                        student.getStudentID(), student.getName(), student.getPhoneNumber(),
                        student.getMountainCode(), formatTuitionFee(student.getTuitionFee()), 
                        (status.contains(student) ? "TRUE" : "FALSE"));
            }
            System.out.println("|------------------------------------------------------------------------------------------|");
        }
    }
    
    
    public List<Object[]> statisticByMountainPeak() throws Exception {
        Function<Student, String> groupFunc = Student::getMountainCode;
        Function<Map.Entry<String, List<Student>>, Object[]> mapFunc = entry -> {
            String mountainCode = entry.getKey();
            int numOfStudent = entry.getValue().size();
            double totalFeeValue = entry.getValue().stream()
                    .mapToDouble(Student::getTuitionFee).sum();
            return new Object[]{mountainCode, numOfStudent, formatTuitionFee(totalFeeValue)};
        };
        return studentDAO.statistic(groupFunc, mapFunc);
    } 
    
    public void printMountainStatistic() throws Exception {
        List<Object[]> statisticByMountain = statisticByMountainPeak();
        System.out.println("Statistics of Registration by Mountain Peak:");
        System.out.println("------------------------------------------------------");
        System.out.println("| Peak Name  | Number of Participants  | Total Cost  | ");
        System.out.println("------------------------------------------------------");
        for (Object[] row : statisticByMountain) {
            String code = (String) row[0];
            int numStu = (int) row[1];
            String totalFee = (String) row[2];
            System.out.format("| %-11s|  %-23d| %-12s|\n", code, numStu, totalFee);
        }
        System.out.println("------------------------------------------------------");
    }
    
    
    
    public void exportToFile() throws Exception {
        studentDAO.saveStudentListToFile();
        isSave = true;
        System.out.println("Registration data has been successfully saved to `registrations.txt`.");
    }
    
    public void checkSaveBeforeExit() throws Exception { 
        if (!isSave) {
            boolean checkSave = DataInput.getYesNo("Do you want to save the changes before exiting? (Y/N)");
            if (checkSave) exportToFile();
        } 
    }
    
    public void displayMountain() throws Exception {
        System.out.println("|---------------------------------- MOUNTAINS LIST ------------------------------------|");
        for (Mountain mountain : mountainDAO.getMountainList()) {
            List<String> descLines = wrapText(mountain.getDescription(), 38);
            if (descLines.isEmpty()) {
                descLines.add(""); 
            }
            System.out.println(String.format("| %-7s | %-20s | %-10s | %-38s |",
                    mountain.getMountainCode(), mountain.getMountainName(),
                    mountain.getProvince(), descLines.get(0)));
            for (int i = 1; i < descLines.size(); i++) {
                System.out.println(String.format("| %-7s | %-20s | %-10s | %-38s |",
                        "", "", "", descLines.get(i)));
            }
            System.out.println("|--------------------------------------------------------------------------------------|");
        }
    }
    
    public static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < text.length(); i += width) {
            lines.add(text.substring(i, Math.min(i + width, text.length())));
        }
        return lines;
    }
}


