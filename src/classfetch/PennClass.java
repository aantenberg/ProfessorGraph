package classfetch;

import java.util.LinkedList;
import java.util.List;

public class PennClass {
    private String department;
    private int classNumber;
    private List<String> professors;

    public PennClass(String department, int classNumber) {
        this.department = department;
        this.classNumber = classNumber;
        professors = new LinkedList<>();
    }

    public PennClass(String className) {
        this(className.split(" ")[0], Integer.parseInt(className.split(" ")[2]));
    }

    public String getDepartment() {
        return department;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public List<String> getProfessors() {
        return professors;
    }

    @Override
    public String toString() {
        return department + " " + classNumber;
    }
}
