package BussinessObject;

import Core.Entities.Mountain;
import Core.Entities.Student;
import Core.Interfaces.IMountainDAO;
import Core.Interfaces.IStudentDAO;
import DataObject.MountainDAO;
import Presentation.Menu;
import Utilities.Inputter;

import java.util.*;
import java.util.function.Predicate;

public class StudentManagement {
    IStudentDAO studentDAO;
    IMountainDAO mountainDAO;
    private boolean isSaved = false;
    private final static Scanner sc = new Scanner(System.in);

    public StudentManagement(IStudentDAO studentDAO) throws Exception {
        this.studentDAO = studentDAO;
        this.mountainDAO = new MountainDAO();
    }

    public void processMenuForStudent() {
        boolean stop = true;
        try {
            do {
                Menu.print("==========STUDENT REGISTRATION SYSTEM==========|1.Register new student"
                        + "|2.Update student information|3.Display registeration list"
                        + "|4.Delete student by ID|5.Search participant by name"
                        + "|6.Filter by campus|7.Generate statistic by mountain"
                        + "|8.Save data to file|9.Exit|11.display with price"
                        + "|===============================================|Select :");
                int choice = Menu.getUserChoice();
                switch (choice) {
                    case 1 -> {
                        addNewStudent();
                    }

                    case 2 -> {
                        updateStudent();
                    }

                    case 3 -> {
                        displayRegisterationList(studentDAO.getStudent());
                    }

                    case 4 -> {
                        deleteStudent();
                    }

                    case 5 -> {
                        try {
                            String value = Inputter.getString("Enter a part of the name or full name: ");
                            List<Student> searchResults = searchByName(value);

                            if (searchResults.isEmpty()) {
                                System.out.println("No students found with name containing: " + value);
                            } else {
                                System.out.println("Search results for: " + value);
                                displayRegisterationList(searchResults);
                            }
                        } catch (Exception e) {
                            System.out.println("Error searching by name: " + e.getMessage());
                        }
                    }

                    case 6 -> {
                        try {
                            String campus = Inputter.getString("Enter campus to filter the list: ");
                            List<Student> filteredStudents = searchByCampus(campus);

                            if (filteredStudents.isEmpty()) {
                                System.out.println("No students found for campus: " + campus);
                            } else {
                                System.out.println("Students from campus: " + campus);
                                displayRegisterationList(filteredStudents);
                            }
                        } catch (Exception e) {
                            System.out.println("Error filtering by campus: " + e.getMessage());
                        }
                    }

                    case 7 -> {
                        generateMountainStatistic();
                    }

                    case 8 -> {
                        saveToFile();
                    }

                    case 9 -> {
                        exitProgram();
                    }

                    // case 11 -> {
                    //     displayWithPrice(studentDAO.getStudent());
                    // }

                    default -> {
                        System.err.println("Invalid choice");
                    }
                }

            } while (stop);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Student inputStudent() throws Exception {
        String id = Inputter.getString("Enter student ID: ");
        String name = Inputter.getString("Enter student name: ");
        String phone = Inputter.getString("Enter student phone number: ");
        String email = Inputter.getString("Enter student email: ");
        displayMountainList();
        String mountainCode = Inputter.getString("Enter mountain code: ");
        return new Student(id, name, phone, email, mountainCode);
    }

    public void addNewStudent() {
        try {
            Student student = inputStudent();
            if (studentDAO.findStudentById(student.getId()) != null) {
                System.out.println("Student already exists");
                return;
            }
            studentDAO.addStudent(student);
            isSaved = true;
            System.out.println("Student has been added successfully");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public void setNewStudentInfo(Student student) throws Exception {
        String name = Inputter.getString("Enter new name: ");
        if (!name.isEmpty()) {
            student.setName(name);
        }

        String phone = Inputter.getString("Enter new phone: ");
        if (!phone.isEmpty()) {
            student.setPhone(phone);
        }

        String email = Inputter.getString("Enter new email: ");
        if (!email.isEmpty()) {
            student.setEmail(email);
        }

        displayMountainList();
        String mountainCode = Inputter.getString("Enter new mountain Code: ");
        if (!mountainCode.isEmpty()) {
            student.setMountainCode(mountainCode);
        }
    }

    public void updateStudent() {
        try {
            String id = Inputter.getString("Enter student ID: ");
            Student student = studentDAO.findStudentById(id);
            if (student == null) {
                System.out.println("Student not found");
                return;
            }
            setNewStudentInfo(student);
            studentDAO.updateRegisterStudent(student);
            isSaved = true;
            System.out.println("Updated successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteStudent() {
        try {
            String id = Inputter.getString("Enter student id: ");
            Student student = studentDAO.findStudentById(id);
            if (student == null) {
                System.out.println("Student not found");
                return;
            }
            showMessage(student);
            System.out.println("Enter Yes or No to delete student");
            Boolean del = Inputter.getYesNo("Please enter 'Y' for YES or 'N' for NO");
            if (del == true) {
                System.out.println("Student deleted successfully");
                studentDAO.deleteStudentByCode(student);
                isSaved = true;
            } else {
                return;
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public void exitProgram() throws Exception {
        if (isSaved) {
            boolean comfirm = Inputter.getYesNo("Do you want to save the changes before exiting? (Y/N): ");
            if (comfirm) {
                saveToFile();
            } else {
                System.out.println("Exiting without saving....");
            }
        } else {
            System.out.println("No unsaved changes. Exiting ....");
        }
        System.exit(0);
    }

    public void showMessage(Student student) {
        System.out.println("Student Details:");
        System.out.println("--------------------------------------------------");
        System.out.println("Student ID: " + student.getId());
        System.out.println("Name      : " + student.getName());
        System.out.println("Phone     : " + student.getPhone());
        System.out.println("Mountain  : " + student.getMountainCode());
        System.out.println("Fee       : " + student.getTuitionFee());
        System.out.println("--------------------------------------------------");
    }

    public void displayMountainList() throws Exception {
        System.out.println("--------------------------------------------------------------------------------");
        for (Mountain mountain : mountainDAO.getMountain()) {
            System.out.printf("%-12s | %-20s | %-15s | %-20s%n",
                    mountain.getMountainCode(), mountain.getMountain(),
                    mountain.getProvince(), mountain.getDescription());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public void displayRegisterationList(List<Student> students) throws Exception {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-20s | %-15s | %-12s | %-15s%n",
                "Student ID", "Name", "Phone", "Peak Code", "Fee");
        System.out.println("--------------------------------------------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-12s | %-20s | %-15s | %-12s |%-15.2f%n",
                    student.getId(), student.getName(),
                    student.getPhone(),
                    student.getMountainCode(), student.getTuitionFee());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }


    //this function below is a reviewed question
    //input a value x that compare with the fee, if any of the fee greater than or equal to x
    //display all information and a status col that show TRUE (fee >= x) or FALSE (fee <= x)
    
    // public void displayWithPrice(List<Student> students) throws Exception {
    //     System.out.println("Input x to find: ");
    //     double result = sc.nextDouble();
    //     System.out.println("--------------------------------------------------------------------------------");
    //     System.out.printf("%-12s | %-20s | %-15s | %-12s | %-15s | %-15s%n",
    //             "Student ID", "Name", "Phone", "Peak Code", "Fee", "Status");
    //     System.out.println("--------------------------------------------------------------------------------");
    //     for (Student s : students) {
    //         if (s.getTuitionFee() >= result) {
    //             String value = "TRUE";
    //             System.out.printf("%-12s | %-20s | %-15s | %-12s |%-15.2f | %-20s%n",
    //                     s.getId(), s.getName(),
    //                     s.getPhone(),
    //                     s.getMountainCode(), s.getTuitionFee(), value);
    //                     break;
    //         }
    //     }
    // }

    public List<Student> searchByName(String value) throws Exception {
        Predicate<Student> predicate = p -> p.getName().toLowerCase().contains(value.toLowerCase());
        List<Student> result = studentDAO.search(predicate);
        return result;
    }

    public List<Student> searchByCampus(String value) throws Exception {
        Predicate<Student> predicate = p -> p.getId().substring(0, 2).equalsIgnoreCase(value);
        List<Student> result = studentDAO.search(predicate);
        return result;
    }

    public void saveToFile() throws Exception {
        System.out.println("Data save successufully");
        isSaved = false;
        studentDAO.writeToFile();
    }

    public Map<String, int[]> calculateMountainStatistic(List<Student> students) {
        Map<String, int[]> mountainStatistic = new HashMap<>();

        for (Student s : students) {
            String mtCode = s.getMountainCode();
            double fee = s.getTuitionFee();

            if (!mountainStatistic.containsKey(mtCode)) {
                mountainStatistic.put(mtCode, new int[] { 0, 0 });
            }
            int[] status = mountainStatistic.get(mtCode);
            status[0]++;
            status[1] += fee;
        }

        return mountainStatistic;
    }

    public void displayMountainStatistic(Map<String, int[]> mountainStatistic) {
        if (mountainStatistic.isEmpty()) {
            System.err.println("No registerations found for any mountain peak.");
        } else {
            System.out.println("Statistics of registeration by Mountain Peak:");
            System.out.println("-------------------------------------------------");
            System.out.printf("%-10s | %-22s | %s\n", "Peak Name", "Number of Participants", "Total Cost ");
            System.out.println("-------------------------------------------------");

            for (Map.Entry<String, int[]> entry : mountainStatistic.entrySet()) {
                String peakCode = entry.getKey();
                int participants = entry.getValue()[0];
                double totalFee = entry.getValue()[1];
                System.out.printf("%-10s | %-22s | %.2f\n", peakCode, participants, totalFee);
            }

            System.out.println("-------------------------------------------------");
        }
    }

    public void generateMountainStatistic() {
        try {
            // Get the list of students from the DAO
            List<Student> students = studentDAO.getStudent();

            // Calculate the statistics
            Map<String, int[]> mountainStatistic = calculateMountainStatistic(students);

            // Display the statistics
            displayMountainStatistic(mountainStatistic);

        } catch (Exception e) {
            System.out.println("Error generating mountain statistics: " + e.getMessage());
        }
    }
}
