/**
 * Student class represents a student with their marks in different subjects
 * Stores student name and array of marks
 */
public class Student {
    private String name;
    private double[] marks; // Array to store marks for different subjects
    private String[] subjects; // Array to store subject names
    private static final int PASS_MARK = 40;
    private static final int TOTAL_SUBJECTS = 5; // Default number of subjects

    /**
     * Constructor to initialize a student with name and marks
     * @param name Student's name
     * @param marks Array of marks for different subjects
     * @param subjects Array of subject names
     */
    public Student(String name, double[] marks, String[] subjects) {
        this.name = name;
        this.marks = marks;
        this.subjects = subjects;
    }

    /**
     * Alternative constructor with default number of subjects
     * @param name Student's name
     */
    public Student(String name) {
        this.name = name;
        this.marks = new double[TOTAL_SUBJECTS];
        this.subjects = new String[TOTAL_SUBJECTS];
        initializeDefaultSubjects();
    }

    /**
     * Initialize default subject names
     */
    private void initializeDefaultSubjects() {
        String[] defaultSubjects = {"Mathematics", "English", "Science", "Social Studies", "Computer Science"};
        for (int i = 0; i < Math.min(subjects.length, defaultSubjects.length); i++) {
            subjects[i] = defaultSubjects[i];
        }
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getMarks() {
        return marks;
    }

    public void setMark(int index, double mark) {
        if (isValidMarkRange(mark)) {
            this.marks[index] = mark;
        }
    }

    public double getMark(int index) {
        return marks[index];
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    /**
     * Validate if mark is in valid range (0-100)
     * @param mark The mark to validate
     * @return true if mark is valid, false otherwise
     */
    public static boolean isValidMarkRange(double mark) {
        return mark >= 0 && mark <= 100;
    }

    /**
     * Calculate average marks for the student
     * @return Average of all marks
     */
    public double calculateAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return marks.length > 0 ? sum / marks.length : 0;
    }

    /**
     * Find the highest mark
     * @return Highest mark among all subjects
     */
    public double getHighestMark() {
        double highest = marks[0];
        for (double mark : marks) {
            if (mark > highest) {
                highest = mark;
            }
        }
        return highest;
    }

    /**
     * Find the lowest mark
     * @return Lowest mark among all subjects
     */
    public double getLowestMark() {
        double lowest = marks[0];
        for (double mark : marks) {
            if (mark < lowest) {
                lowest = mark;
            }
        }
        return lowest;
    }

    /**
     * Get grade based on average marks
     * @return Grade letter (A, B, C, D, F)
     */
    public char getGrade() {
        double average = calculateAverage();
        if (average >= 90) return 'A';
        else if (average >= 80) return 'B';
        else if (average >= 70) return 'C';
        else if (average >= 60) return 'D';
        else return 'F';
    }

    /**
     * Get detailed grade description
     * @return Grade with description
     */
    public String getGradeDescription() {
        char grade = getGrade();
        switch (grade) {
            case 'A':
                return "A (Excellent)";
            case 'B':
                return "B (Good)";
            case 'C':
                return "C (Average)";
            case 'D':
                return "D (Below Average)";
            case 'F':
                return "F (Failed)";
            default:
                return "N/A";
        }
    }

    /**
     * Get number of subjects passed
     * @return Count of subjects with marks >= PASS_MARK
     */
    public int getPassedSubjects() {
        int count = 0;
        for (double mark : marks) {
            if (mark >= PASS_MARK) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get number of subjects failed
     * @return Count of subjects with marks < PASS_MARK
     */
    public int getFailedSubjects() {
        return marks.length - getPassedSubjects();
    }

    /**
     * Generate string representation of student data
     * @return Formatted student information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student: ").append(name).append("\n");
        sb.append("Average: ").append(String.format("%.2f", calculateAverage())).append("\n");
        sb.append("Grade: ").append(getGradeDescription()).append("\n");
        sb.append("Highest Mark: ").append(String.format("%.2f", getHighestMark())).append("\n");
        sb.append("Lowest Mark: ").append(String.format("%.2f", getLowestMark())).append("\n");
        sb.append("Passed Subjects: ").append(getPassedSubjects()).append("/").append(marks.length);
        return sb.toString();
    }
}
