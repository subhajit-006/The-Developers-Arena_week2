import java.util.ArrayList;

/**
 * GradeManager class manages student records and performs grade-related operations
 * Uses ArrayList for dynamic storage of student records
 */
public class GradeManager {
    private ArrayList<Student> students;
    private static final double PASS_PERCENTAGE = 40.0;

    /**
     * Constructor initializes the ArrayList for storing students
     */
    public GradeManager() {
        this.students = new ArrayList<>();
    }

    /**
     * Add a new student to the system
     * @param student Student object to add
     */
    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
            System.out.println("✓ Student " + student.getName() + " added successfully!");
        }
    }

    /**
     * Search for a student by name
     * @param name Student's name to search
     * @return Student object if found, null otherwise
     */
    public Student searchStudent(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Remove a student by name
     * @param name Name of student to remove
     * @return true if student was removed, false otherwise
     */
    public boolean removeStudent(String name) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equalsIgnoreCase(name)) {
                students.remove(i);
                System.out.println("✓ Student " + name + " removed successfully!");
                return true;
            }
        }
        System.out.println("✗ Student " + name + " not found!");
        return false;
    }

    /**
     * Get total number of students in the system
     * @return Number of students
     */
    public int getTotalStudents() {
        return students.size();
    }

    /**
     * Get all students
     * @return ArrayList of all students
     */
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    /**
     * Calculate class average (average of all students' averages)
     * @return Class average
     */
    public double getClassAverage() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student student : students) {
            sum += student.calculateAverage();
        }
        return sum / students.size();
    }

    /**
     * Find student with highest average
     * @return Student with highest average
     */
    public Student getTopStudent() {
        if (students.isEmpty()) return null;
        Student topStudent = students.get(0);
        for (Student student : students) {
            if (student.calculateAverage() > topStudent.calculateAverage()) {
                topStudent = student;
            }
        }
        return topStudent;
    }

    /**
     * Find student with lowest average
     * @return Student with lowest average
     */
    public Student getLowestStudent() {
        if (students.isEmpty()) return null;
        Student lowestStudent = students.get(0);
        for (Student student : students) {
            if (student.calculateAverage() < lowestStudent.calculateAverage()) {
                lowestStudent = student;
            }
        }
        return lowestStudent;
    }

    /**
     * Get count of students by grade
     * @return Array with count of students for each grade (A, B, C, D, F)
     */
    public int[] getGradeDistribution() {
        int[] distribution = {0, 0, 0, 0, 0}; // A, B, C, D, F
        for (Student student : students) {
            switch (student.getGrade()) {
                case 'A':
                    distribution[0]++;
                    break;
                case 'B':
                    distribution[1]++;
                    break;
                case 'C':
                    distribution[2]++;
                    break;
                case 'D':
                    distribution[3]++;
                    break;
                case 'F':
                    distribution[4]++;
                    break;
            }
        }
        return distribution;
    }

    /**
     * Get percentage of students who passed
     * @return Pass percentage
     */
    public double getPassPercentage() {
        if (students.isEmpty()) return 0;
        int passCount = 0;
        for (Student student : students) {
            if (student.calculateAverage() >= PASS_PERCENTAGE) {
                passCount++;
            }
        }
        return (passCount * 100.0) / students.size();
    }

    /**
     * Get students sorted by average in descending order
     * @return ArrayList of students sorted by average
     */
    public ArrayList<Student> getStudentsByAverageDescending() {
        ArrayList<Student> sorted = new ArrayList<>(students);
        // Simple bubble sort
        for (int i = 0; i < sorted.size(); i++) {
            for (int j = 0; j < sorted.size() - 1 - i; j++) {
                if (sorted.get(j).calculateAverage() < sorted.get(j + 1).calculateAverage()) {
                    Student temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    /**
     * Get students with specific grade
     * @param grade Grade to filter (A, B, C, D, F)
     * @return ArrayList of students with specified grade
     */
    public ArrayList<Student> getStudentsByGrade(char grade) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getGrade() == grade) {
                result.add(student);
            }
        }
        return result;
    }

    /**
     * Get students who failed (average < PASS_PERCENTAGE)
     * @return ArrayList of failed students
     */
    public ArrayList<Student> getFailedStudents() {
        ArrayList<Student> failedStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.calculateAverage() < PASS_PERCENTAGE) {
                failedStudents.add(student);
            }
        }
        return failedStudents;
    }

    /**
     * Get students who passed at least one subject but failed overall
     * @return ArrayList of at-risk students
     */
    public ArrayList<Student> getAtRiskStudents() {
        ArrayList<Student> atRisk = new ArrayList<>();
        for (Student student : students) {
            double average = student.calculateAverage();
            if (average >= PASS_PERCENTAGE - 10 && average < PASS_PERCENTAGE) {
                atRisk.add(student);
            }
        }
        return atRisk;
    }

    /**
     * Update student's mark in a specific subject
     * @param studentName Name of student
     * @param subjectIndex Index of subject
     * @param mark New mark value
     * @return true if updated successfully, false otherwise
     */
    public boolean updateStudentMark(String studentName, int subjectIndex, double mark) {
        if (!Student.isValidMarkRange(mark)) {
            System.out.println("✗ Invalid mark! Please enter a mark between 0 and 100.");
            return false;
        }

        Student student = searchStudent(studentName);
        if (student != null && subjectIndex >= 0 && subjectIndex < student.getMarks().length) {
            student.setMark(subjectIndex, mark);
            System.out.println("✓ Mark updated successfully!");
            return true;
        }
        System.out.println("✗ Student or subject not found!");
        return false;
    }

    /**
     * Get average mark across all students for a specific subject
     * @param subjectIndex Index of subject
     * @return Average mark for the subject
     */
    public double getSubjectAverage(int subjectIndex) {
        if (students.isEmpty() || subjectIndex < 0) return 0;
        
        double sum = 0;
        for (Student student : students) {
            if (subjectIndex < student.getMarks().length) {
                sum += student.getMark(subjectIndex);
            }
        }
        return sum / students.size();
    }

    /**
     * Export student data as formatted string
     * @return Formatted string of all students' data
     */
    public String exportStudentData() {
        if (students.isEmpty()) {
            return "No students in the system.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n========== STUDENT DATA EXPORT ==========\n");
        for (int i = 0; i < students.size(); i++) {
            sb.append((i + 1)).append(". ").append(students.get(i).toString()).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Clear all student data
     */
    public void clearAllStudents() {
        students.clear();
        System.out.println("✓ All student data cleared!");
    }
}
