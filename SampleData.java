import java.util.ArrayList;

/**
 * SampleData.java - Sample test data and usage examples
 * This class provides example students and scenarios to test the Grade Management System
 */

public class SampleData {
    
    /**
     * Get sample students for testing
     * Usage: Create these students to test various features
     */
    public static Student[] getSampleStudents() {
        // Student 1: High performer
        Student student1 = new Student("Ravi Kumar");
        student1.setMark(0, 95);  // Mathematics
        student1.setMark(1, 92);  // English
        student1.setMark(2, 88);  // Science
        student1.setMark(3, 85);  // Social Studies
        student1.setMark(4, 90);  // Computer Science
        // Expected: Average 90.0, Grade: A (Excellent)

        // Student 2: Good performer
        Student student2 = new Student("Priya Singh");
        student2.setMark(0, 85);  // Mathematics
        student2.setMark(1, 88);  // English
        student2.setMark(2, 82);  // Science
        student2.setMark(3, 80);  // Social Studies
        student2.setMark(4, 85);  // Computer Science
        // Expected: Average 84.0, Grade: B (Good)

        // Student 3: Average performer
        Student student3 = new Student("Amit Patel");
        student3.setMark(0, 75);  // Mathematics
        student3.setMark(1, 78);  // English
        student3.setMark(2, 72);  // Science
        student3.setMark(3, 70);  // Social Studies
        student3.setMark(4, 75);  // Computer Science
        // Expected: Average 74.0, Grade: C (Average)

        // Student 4: Below average performer
        Student student4 = new Student("Zara Khan");
        student4.setMark(0, 65);  // Mathematics
        student4.setMark(1, 62);  // English
        student4.setMark(2, 58);  // Science
        student4.setMark(3, 60);  // Social Studies
        student4.setMark(4, 55);  // Computer Science
        // Expected: Average 60.0, Grade: D (Below Average)

        // Student 5: Failing student
        Student student5 = new Student("Vikram Sharma");
        student5.setMark(0, 45);  // Mathematics
        student5.setMark(1, 38);  // English
        student5.setMark(2, 42);  // Science
        student5.setMark(3, 35);  // Social Studies
        student5.setMark(4, 40);  // Computer Science
        // Expected: Average 40.0, Grade: F (Failed)

        // Student 6: Mixed performance (passed subjects but overall failed)
        Student student6 = new Student("Neha Gupta");
        student6.setMark(0, 50);  // Mathematics - Pass
        student6.setMark(1, 48);  // English - Pass
        student6.setMark(2, 35);  // Science - Fail
        student6.setMark(3, 32);  // Social Studies - Fail
        student6.setMark(4, 30);  // Computer Science - Fail
        // Expected: Average 39.0, Grade: F (Failed), Passed 2/5 subjects

        // Student 7: Consistent performer
        Student student7 = new Student("Arjun Verma");
        student7.setMark(0, 80);  // Mathematics
        student7.setMark(1, 80);  // English
        student7.setMark(2, 80);  // Science
        student7.setMark(3, 80);  // Social Studies
        student7.setMark(4, 80);  // Computer Science
        // Expected: Average 80.0, Grade: B (Good)

        // Student 8: Inconsistent performer (some very high, some very low)
        Student student8 = new Student("Divya Iyer");
        student8.setMark(0, 95);  // Mathematics - Excellent
        student8.setMark(1, 92);  // English - Good
        student8.setMark(2, 45);  // Science - Pass
        student8.setMark(3, 40);  // Social Studies - Pass (barely)
        student8.setMark(4, 38);  // Computer Science - Fail
        // Expected: Average 62.0, Grade: D (Below Average)

        return new Student[]{student1, student2, student3, student4, student5, student6, student7, student8};
    }

    /**
     * Demonstration of how to use the system programmatically
     */
    public static void demonstrateSystem() {
        System.out.println("=== GRADE MANAGEMENT SYSTEM - SAMPLE DATA DEMO ===\n");

        // Create grade manager
        GradeManager manager = new GradeManager();

        // Add sample students
        Student[] students = getSampleStudents();
        for (Student student : students) {
            manager.addStudent(student);
        }

        System.out.println("✓ Added " + manager.getTotalStudents() + " sample students\n");

        // Display class statistics
        System.out.println("--- CLASS STATISTICS ---");
        System.out.printf("Total Students: %d\n", manager.getTotalStudents());
        System.out.printf("Class Average: %.2f\n", manager.getClassAverage());
        System.out.printf("Pass Percentage: %.2f%%\n", manager.getPassPercentage());
        System.out.printf("Fail Percentage: %.2f%%\n\n", (100 - manager.getPassPercentage()));

        // Display top and lowest performers
        System.out.println("--- TOP & LOWEST PERFORMERS ---");
        Student topStudent = manager.getTopStudent();
        if (topStudent != null) {
            System.out.printf("Top Student: %s (Average: %.2f)\n", topStudent.getName(), topStudent.calculateAverage());
        }
        Student lowestStudent = manager.getLowestStudent();
        if (lowestStudent != null) {
            System.out.printf("Lowest Student: %s (Average: %.2f)\n\n", lowestStudent.getName(), lowestStudent.calculateAverage());
        }

        // Display grade distribution
        System.out.println("--- GRADE DISTRIBUTION ---");
        int[] distribution = manager.getGradeDistribution();
        String[] grades = {"A", "B", "C", "D", "F"};
        String[] descriptions = {"Excellent", "Good", "Average", "Below Average", "Failed"};
        
        for (int i = 0; i < grades.length; i++) {
            double percentage = (distribution[i] * 100.0) / manager.getTotalStudents();
            System.out.printf("Grade %s (%s): %d students (%.2f%%)\n", 
                grades[i], descriptions[i], distribution[i], percentage);
        }

        System.out.println("\n--- FAILED STUDENTS ---");
        ArrayList<Student> failedStudents = manager.getFailedStudents();
        if (failedStudents.isEmpty()) {
            System.out.println("No failed students");
        } else {
            for (int i = 0; i < failedStudents.size(); i++) {
                Student s = failedStudents.get(i);
                System.out.printf("%d. %s - Average: %.2f\n", i+1, s.getName(), s.calculateAverage());
            }
        }

        System.out.println("\n--- AT-RISK STUDENTS (30-40% average) ---");
        ArrayList<Student> atRiskStudents = manager.getAtRiskStudents();
        if (atRiskStudents.isEmpty()) {
            System.out.println("No at-risk students");
        } else {
            for (int i = 0; i < atRiskStudents.size(); i++) {
                Student s = atRiskStudents.get(i);
                System.out.printf("%d. %s - Average: %.2f\n", i+1, s.getName(), s.calculateAverage());
            }
        }

        System.out.println("\n--- RANKING REPORT ---");
        ArrayList<Student> ranked = manager.getStudentsByAverageDescending();
        for (int i = 0; i < ranked.size(); i++) {
            Student s = ranked.get(i);
            System.out.printf("%d. %s - Average: %.2f (Grade: %s)\n", 
                i+1, s.getName(), s.calculateAverage(), s.getGrade());
        }

        System.out.println("\n--- SAMPLE STUDENT DETAIL ---");
        Student sampleStudent = manager.searchStudent("Ravi Kumar");
        if (sampleStudent != null) {
            System.out.println(sampleStudent.toString());
        }

        System.out.println("\n=== END OF DEMO ===");
    }

    /**
     * Test various scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== TEST SCENARIOS ===\n");

        // Test 1: Validation
        System.out.println("Test 1: Mark Validation");
        System.out.println("Is 95 valid? " + Student.isValidMarkRange(95));     // true
        System.out.println("Is 101 valid? " + Student.isValidMarkRange(101));   // false
        System.out.println("Is -5 valid? " + Student.isValidMarkRange(-5));     // false
        System.out.println("Is 50 valid? " + Student.isValidMarkRange(50));     // true

        // Test 2: Grade Calculation
        System.out.println("\nTest 2: Grade Calculation");
        Student testStudent = new Student("Test Student");
        
        // Scenario 1: Grade A
        testStudent.setMark(0, 95);
        testStudent.setMark(1, 92);
        testStudent.setMark(2, 91);
        testStudent.setMark(3, 89);
        testStudent.setMark(4, 93);
        System.out.printf("Marks: 95, 92, 91, 89, 93 -> Average: %.2f, Grade: %s\n", 
            testStudent.calculateAverage(), testStudent.getGrade());

        // Scenario 2: Grade F
        testStudent.setMark(0, 35);
        testStudent.setMark(1, 38);
        testStudent.setMark(2, 40);
        testStudent.setMark(3, 32);
        testStudent.setMark(4, 30);
        System.out.printf("Marks: 35, 38, 40, 32, 30 -> Average: %.2f, Grade: %s\n", 
            testStudent.calculateAverage(), testStudent.getGrade());

        // Test 3: Search Functionality
        System.out.println("\nTest 3: Search Functionality");
        GradeManager manager = new GradeManager();
        Student s1 = new Student("Alice");
        Student s2 = new Student("Bob");
        manager.addStudent(s1);
        manager.addStudent(s2);
        
        System.out.println("Search 'Alice': " + (manager.searchStudent("Alice") != null ? "Found" : "Not Found"));
        System.out.println("Search 'Charlie': " + (manager.searchStudent("Charlie") != null ? "Found" : "Not Found"));
        System.out.println("Search 'alice' (case-insensitive): " + (manager.searchStudent("alice") != null ? "Found" : "Not Found"));

        System.out.println("\n=== END OF TEST SCENARIOS ===");
    }

    /**
     * Main method to run demos
     */
    public static void main(String[] args) {
        // Run demonstration
        demonstrateSystem();
        
        // Run test scenarios
        testScenarios();
    }
}
