import java.util.ArrayList;
import java.util.Scanner;

/**
 * GradeManagementSystem - Main application class with interactive menu system
 * Provides user interface for managing student grades and generating reports
 */
public class GradeManagementSystem {
    private GradeManager gradeManager;
    private Scanner scanner;
    private static final String SEPARATOR = "==================================================";

    /**
     * Constructor initializes the system
     */
    public GradeManagementSystem() {
        this.gradeManager = new GradeManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main method - Entry point of the application
     */
    public static void main(String[] args) {
        GradeManagementSystem system = new GradeManagementSystem();
        system.run();
    }

    /**
     * Main application loop - displays menu and handles user input
     */
    public void run() {
        boolean running = true;
        System.out.println("\n" + SEPARATOR);
        System.out.println("    WELCOME TO GRADE MANAGEMENT SYSTEM");
        System.out.println(SEPARATOR);

        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleViewStudent();
                    break;
                case 3:
                    handleUpdateMarks();
                    break;
                case 4:
                    handleRemoveStudent();
                    break;
                case 5:
                    handleViewAllStudents();
                    break;
                case 6:
                    handleSearchStudent();
                    break;
                case 7:
                    handlePerformanceAnalysis();
                    break;
                case 8:
                    handleGenerateReports();
                    break;
                case 9:
                    System.out.println("\nThank you for using Grade Management System! Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("✗ Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Display main menu options
     */
    private void displayMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("MAIN MENU");
        System.out.println(SEPARATOR);
        System.out.println("1. Add Student");
        System.out.println("2. View Student Details");
        System.out.println("3. Update Student Marks");
        System.out.println("4. Remove Student");
        System.out.println("5. View All Students");
        System.out.println("6. Search Student");
        System.out.println("7. Performance Analysis");
        System.out.println("8. Generate Reports");
        System.out.println("9. Exit");
        System.out.println(SEPARATOR);
    }

    /**
     * Handle adding a new student
     */
    private void handleAddStudent() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("ADD NEW STUDENT");
        System.out.println(SEPARATOR);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("✗ Student name cannot be empty!");
            return;
        }

        if (gradeManager.searchStudent(name) != null) {
            System.out.println("✗ Student already exists!");
            return;
        }

        Student student = new Student(name);
        double[] marks = new double[5];
        String[] subjects = student.getSubjects();

        System.out.println("\nEnter marks for each subject (0-100):");
        for (int i = 0; i < subjects.length; i++) {
            double mark;
            while (true) {
                mark = getDoubleInput("  " + subjects[i] + ": ");
                if (Student.isValidMarkRange(mark)) {
                    marks[i] = mark;
                    break;
                } else {
                    System.out.println("✗ Invalid mark! Please enter a value between 0 and 100.");
                }
            }
        }

        student.setMark(0, marks[0]);
        student.setMark(1, marks[1]);
        student.setMark(2, marks[2]);
        student.setMark(3, marks[3]);
        student.setMark(4, marks[4]);

        gradeManager.addStudent(student);
    }

    /**
     * Handle viewing specific student details
     */
    private void handleViewStudent() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("VIEW STUDENT DETAILS");
        System.out.println(SEPARATOR);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        Student student = gradeManager.searchStudent(name);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        displayStudentDetails(student);
    }

    /**
     * Display detailed information about a student
     */
    private void displayStudentDetails(Student student) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("STUDENT DETAILS");
        System.out.println(SEPARATOR);
        System.out.println("Name: " + student.getName());
        System.out.println("\nSubject Marks:");
        
        String[] subjects = student.getSubjects();
        double[] marks = student.getMarks();
        
        for (int i = 0; i < subjects.length; i++) {
            System.out.printf("  %d. %s: %.2f\n", (i + 1), subjects[i], marks[i]);
        }

        System.out.println("\n" + SEPARATOR);
        System.out.println("PERFORMANCE SUMMARY");
        System.out.println(SEPARATOR);
        System.out.printf("Average Marks: %.2f\n", student.calculateAverage());
        System.out.printf("Highest Mark: %.2f\n", student.getHighestMark());
        System.out.printf("Lowest Mark: %.2f\n", student.getLowestMark());
        System.out.println("Grade: " + student.getGradeDescription());
        System.out.println("Passed Subjects: " + student.getPassedSubjects() + "/5");
        System.out.println("Failed Subjects: " + student.getFailedSubjects() + "/5");
        System.out.println(SEPARATOR);
    }

    /**
     * Handle updating student marks
     */
    private void handleUpdateMarks() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("UPDATE STUDENT MARKS");
        System.out.println(SEPARATOR);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        Student student = gradeManager.searchStudent(name);
        if (student == null) {
            System.out.println("✗ Student not found!");
            return;
        }

        String[] subjects = student.getSubjects();
        System.out.println("\nSelect subject to update:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.println((i + 1) + ". " + subjects[i]);
        }

        int subjectChoice = getIntInput("Enter subject number: ") - 1;

        if (subjectChoice < 0 || subjectChoice >= subjects.length) {
            System.out.println("✗ Invalid subject selection!");
            return;
        }

        double newMark = getDoubleInput("Enter new mark (0-100): ");

        if (gradeManager.updateStudentMark(name, subjectChoice, newMark)) {
            displayStudentDetails(student);
        }
    }

    /**
     * Handle removing a student
     */
    private void handleRemoveStudent() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("REMOVE STUDENT");
        System.out.println(SEPARATOR);

        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        gradeManager.removeStudent(name);
    }

    /**
     * Handle viewing all students
     */
    private void handleViewAllStudents() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("ALL STUDENTS");
        System.out.println(SEPARATOR);

        ArrayList<Student> students = gradeManager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("✗ No students in the system!");
            return;
        }

        System.out.printf("%-3s %-20s %-12s %-8s %s\n", "No.", "Name", "Average", "Grade", "Status");
        System.out.println(SEPARATOR);

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String status = student.calculateAverage() >= 40 ? "Passed" : "Failed";
            System.out.printf("%-3d %-20s %-12.2f %-8s %s\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage(),
                    student.getGrade(),
                    status);
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Handle searching for a student
     */
    private void handleSearchStudent() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("SEARCH STUDENT");
        System.out.println(SEPARATOR);

        System.out.print("Enter student name (or partial name): ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();

        ArrayList<Student> results = new ArrayList<>();
        for (Student student : gradeManager.getAllStudents()) {
            if (student.getName().toLowerCase().contains(searchTerm)) {
                results.add(student);
            }
        }

        if (results.isEmpty()) {
            System.out.println("✗ No students found matching '" + searchTerm + "'");
            return;
        }

        System.out.println("\nSearch Results:");
        System.out.printf("%-3s %-20s %-12s %-8s\n", "No.", "Name", "Average", "Grade");
        System.out.println(SEPARATOR);

        for (int i = 0; i < results.size(); i++) {
            Student student = results.get(i);
            System.out.printf("%-3d %-20s %-12.2f %-8s\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage(),
                    student.getGrade());
        }
    }

    /**
     * Handle performance analysis menu
     */
    private void handlePerformanceAnalysis() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("PERFORMANCE ANALYSIS");
        System.out.println(SEPARATOR);
        System.out.println("1. View Top Student");
        System.out.println("2. View Lowest Performing Student");
        System.out.println("3. View Class Average");
        System.out.println("4. View Failed Students");
        System.out.println("5. View At-Risk Students");
        System.out.println("6. View Students by Grade");
        System.out.println(SEPARATOR);

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                displayTopStudent();
                break;
            case 2:
                displayLowestStudent();
                break;
            case 3:
                displayClassAverage();
                break;
            case 4:
                displayFailedStudents();
                break;
            case 5:
                displayAtRiskStudents();
                break;
            case 6:
                displayStudentsByGrade();
                break;
            default:
                System.out.println("✗ Invalid choice!");
        }
    }

    /**
     * Display top performing student
     */
    private void displayTopStudent() {
        Student topStudent = gradeManager.getTopStudent();
        if (topStudent == null) {
            System.out.println("✗ No students in the system!");
            return;
        }

        System.out.println("\n" + SEPARATOR);
        System.out.println("TOP PERFORMER");
        System.out.println(SEPARATOR);
        System.out.println("Name: " + topStudent.getName());
        System.out.printf("Average: %.2f\n", topStudent.calculateAverage());
        System.out.println("Grade: " + topStudent.getGradeDescription());
        System.out.println(SEPARATOR);
    }

    /**
     * Display lowest performing student
     */
    private void displayLowestStudent() {
        Student lowestStudent = gradeManager.getLowestStudent();
        if (lowestStudent == null) {
            System.out.println("✗ No students in the system!");
            return;
        }

        System.out.println("\n" + SEPARATOR);
        System.out.println("LOWEST PERFORMER");
        System.out.println(SEPARATOR);
        System.out.println("Name: " + lowestStudent.getName());
        System.out.printf("Average: %.2f\n", lowestStudent.calculateAverage());
        System.out.println("Grade: " + lowestStudent.getGradeDescription());
        System.out.println(SEPARATOR);
    }

    /**
     * Display class average
     */
    private void displayClassAverage() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("CLASS AVERAGE");
        System.out.println(SEPARATOR);
        System.out.printf("Class Average: %.2f\n", gradeManager.getClassAverage());
        System.out.println("Total Students: " + gradeManager.getTotalStudents());
        System.out.println(SEPARATOR);
    }

    /**
     * Display failed students
     */
    private void displayFailedStudents() {
        ArrayList<Student> failedStudents = gradeManager.getFailedStudents();

        System.out.println("\n" + SEPARATOR);
        System.out.println("FAILED STUDENTS");
        System.out.println(SEPARATOR);

        if (failedStudents.isEmpty()) {
            System.out.println("✓ No failed students!");
            System.out.println(SEPARATOR);
            return;
        }

        System.out.printf("%-3s %-20s %-12s\n", "No.", "Name", "Average");
        System.out.println(SEPARATOR);

        for (int i = 0; i < failedStudents.size(); i++) {
            Student student = failedStudents.get(i);
            System.out.printf("%-3d %-20s %-12.2f\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage());
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Display at-risk students
     */
    private void displayAtRiskStudents() {
        ArrayList<Student> atRiskStudents = gradeManager.getAtRiskStudents();

        System.out.println("\n" + SEPARATOR);
        System.out.println("AT-RISK STUDENTS (30-40% average)");
        System.out.println(SEPARATOR);

        if (atRiskStudents.isEmpty()) {
            System.out.println("✓ No at-risk students!");
            System.out.println(SEPARATOR);
            return;
        }

        System.out.printf("%-3s %-20s %-12s\n", "No.", "Name", "Average");
        System.out.println(SEPARATOR);

        for (int i = 0; i < atRiskStudents.size(); i++) {
            Student student = atRiskStudents.get(i);
            System.out.printf("%-3d %-20s %-12.2f\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage());
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Display students by specific grade
     */
    private void displayStudentsByGrade() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("SELECT GRADE");
        System.out.println(SEPARATOR);
        System.out.println("1. Grade A (90-100)");
        System.out.println("2. Grade B (80-89)");
        System.out.println("3. Grade C (70-79)");
        System.out.println("4. Grade D (60-69)");
        System.out.println("5. Grade F (Below 60)");
        System.out.println(SEPARATOR);

        int choice = getIntInput("Enter grade choice: ");
        char grade = ' ';

        switch (choice) {
            case 1:
                grade = 'A';
                break;
            case 2:
                grade = 'B';
                break;
            case 3:
                grade = 'C';
                break;
            case 4:
                grade = 'D';
                break;
            case 5:
                grade = 'F';
                break;
            default:
                System.out.println("✗ Invalid choice!");
                return;
        }

        ArrayList<Student> studentsWithGrade = gradeManager.getStudentsByGrade(grade);

        System.out.println("\n" + SEPARATOR);
        System.out.println("STUDENTS WITH GRADE " + grade);
        System.out.println(SEPARATOR);

        if (studentsWithGrade.isEmpty()) {
            System.out.println("No students with grade " + grade);
            System.out.println(SEPARATOR);
            return;
        }

        System.out.printf("%-3s %-20s %-12s\n", "No.", "Name", "Average");
        System.out.println(SEPARATOR);

        for (int i = 0; i < studentsWithGrade.size(); i++) {
            Student student = studentsWithGrade.get(i);
            System.out.printf("%-3d %-20s %-12.2f\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage());
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Handle generating performance reports
     */
    private void handleGenerateReports() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("GENERATE REPORTS");
        System.out.println(SEPARATOR);
        System.out.println("1. Class Performance Report");
        System.out.println("2. Grade Distribution Report");
        System.out.println("3. Subject-wise Analysis");
        System.out.println("4. Ranking Report");
        System.out.println("5. Full Student Data Export");
        System.out.println(SEPARATOR);

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                generateClassPerformanceReport();
                break;
            case 2:
                generateGradeDistributionReport();
                break;
            case 3:
                generateSubjectAnalysisReport();
                break;
            case 4:
                generateRankingReport();
                break;
            case 5:
                generateFullDataExport();
                break;
            default:
                System.out.println("✗ Invalid choice!");
        }
    }

    /**
     * Generate class performance report
     */
    private void generateClassPerformanceReport() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("CLASS PERFORMANCE REPORT");
        System.out.println(SEPARATOR);

        int totalStudents = gradeManager.getTotalStudents();

        if (totalStudents == 0) {
            System.out.println("No students in the system!");
            return;
        }

        System.out.println("Total Students: " + totalStudents);
        System.out.printf("Class Average: %.2f\n", gradeManager.getClassAverage());
        System.out.printf("Pass Percentage: %.2f%%\n", gradeManager.getPassPercentage());
        System.out.printf("Fail Percentage: %.2f%%\n", (100 - gradeManager.getPassPercentage()));

        Student topStudent = gradeManager.getTopStudent();
        Student lowestStudent = gradeManager.getLowestStudent();

        if (topStudent != null) {
            System.out.printf("Highest Average: %.2f (%s)\n", topStudent.calculateAverage(), topStudent.getName());
        }
        if (lowestStudent != null) {
            System.out.printf("Lowest Average: %.2f (%s)\n", lowestStudent.calculateAverage(), lowestStudent.getName());
        }

        System.out.println(SEPARATOR);
    }

    /**
     * Generate grade distribution report
     */
    private void generateGradeDistributionReport() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("GRADE DISTRIBUTION REPORT");
        System.out.println(SEPARATOR);

        int[] distribution = gradeManager.getGradeDistribution();
        int total = gradeManager.getTotalStudents();

        if (total == 0) {
            System.out.println("No students in the system!");
            return;
        }

        String[] grades = {"A", "B", "C", "D", "F"};
        String[] descriptions = {"Excellent", "Good", "Average", "Below Average", "Failed"};

        for (int i = 0; i < grades.length; i++) {
            double percentage = (distribution[i] * 100.0) / total;
            System.out.printf("Grade %s (%s): %d students (%.2f%%)\n",
                    grades[i], descriptions[i], distribution[i], percentage);
            printProgressBar(percentage);
        }

        System.out.println(SEPARATOR);
    }

    /**
     * Print a text-based progress bar
     */
    private void printProgressBar(double percentage) {
        int barLength = 30;
        int filledLength = (int) (barLength * percentage / 100);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filledLength) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        System.out.println(bar.toString());
    }

    /**
     * Generate subject-wise analysis report
     */
    private void generateSubjectAnalysisReport() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("SUBJECT-WISE ANALYSIS");
        System.out.println(SEPARATOR);

        ArrayList<Student> students = gradeManager.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students in the system!");
            return;
        }

        Student firstStudent = students.get(0);
        String[] subjects = firstStudent.getSubjects();

        System.out.printf("%-20s %-12s %-12s %-12s\n", "Subject", "Average", "Highest", "Lowest");
        System.out.println(SEPARATOR);

        for (int i = 0; i < subjects.length; i++) {
            double subjectAverage = gradeManager.getSubjectAverage(i);

            // Find highest and lowest marks in this subject
            double highest = students.get(0).getMark(i);
            double lowest = students.get(0).getMark(i);

            for (Student student : students) {
                double mark = student.getMark(i);
                if (mark > highest) highest = mark;
                if (mark < lowest) lowest = mark;
            }

            System.out.printf("%-20s %-12.2f %-12.2f %-12.2f\n",
                    subjects[i], subjectAverage, highest, lowest);
        }

        System.out.println(SEPARATOR);
    }

    /**
     * Generate ranking report (students sorted by average)
     */
    private void generateRankingReport() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("STUDENT RANKING REPORT");
        System.out.println(SEPARATOR);

        ArrayList<Student> rankedStudents = gradeManager.getStudentsByAverageDescending();

        if (rankedStudents.isEmpty()) {
            System.out.println("No students in the system!");
            return;
        }

        System.out.printf("%-4s %-20s %-12s %-8s\n", "Rank", "Name", "Average", "Grade");
        System.out.println(SEPARATOR);

        for (int i = 0; i < rankedStudents.size(); i++) {
            Student student = rankedStudents.get(i);
            System.out.printf("%-4d %-20s %-12.2f %-8s\n",
                    (i + 1),
                    student.getName(),
                    student.calculateAverage(),
                    student.getGrade());
        }

        System.out.println(SEPARATOR);
    }

    /**
     * Generate full data export
     */
    private void generateFullDataExport() {
        System.out.println(gradeManager.exportStudentData());
    }

    /**
     * Get integer input from user
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int input = Integer.parseInt(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter a valid number.");
            return -1;
        }
    }

    /**
     * Get double input from user
     */
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            double input = Double.parseDouble(scanner.nextLine().trim());
            return input;
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input! Please enter a valid number.");
            return -1;
        }
    }
}
