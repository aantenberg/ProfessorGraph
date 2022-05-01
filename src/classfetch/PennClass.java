package classfetch;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PennClass {

    private final String className;
    private List<String> professors;

    /**
     * Initializes a PennClass object
     * @param className the name of the class (ex: "NETS 150")
     */
    public PennClass(String className) {
        this.className = className;
        professors = new LinkedList<>();
    }

    /**
     * Getter for className instance variable
     * @return the name of this class (ex: "NETS 150")
     */
    public String getClassName() {
        return className;
    }

    /**
     * Getter for the list of professors
     * @return the list of professors of this class as strings
     */
    public List<String> getProfessors() {
        return professors;
    }

    /**
     * Setter for the list of professors
     * @param professors the list of professors
     */
    public void setProfessors(List<String> professors) {
        this.professors = professors;
    }

    /**
     * Adds professor to this class' list of professors
     * @param professor the name of the professor to add to this class' professor list
     */
    public void addProfessor(String professor) {
        professors.add(professor);
    }

    /**
     * Gets the class name in the form of a suffix for Penn Course Review. For example, because
     * the Penn Course Review URL for NETS 150 is "https://penncoursereview.com/course/NETS-150",
     * this function would return "NETS-150" for the NETS 150 instance of PennClass.
     * @return The class name in Penn Course Review URL suffix form
     */
    public String getCourseReviewUrlSuffix() {
        return className.toUpperCase().replace(" ", "-");
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PennClass pennClass = (PennClass) o;
        return className.equals(pennClass.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, professors);
    }

}
