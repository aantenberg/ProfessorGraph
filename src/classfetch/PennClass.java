package classfetch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import url.URLGetter;

public class PennClass {
    private final static String COURSE_REVIEW_URL = "https://penncoursereview.com/course/";
    private final String className;
    private List<String> professors;
    private final WebDriver driver;

    /**
     * Initializes a PennClass object
     * @param className the name of the class (ex: "NETS 150")
     */
    public PennClass(String className, WebDriver driver) {
        this.className = className;
        professors = new LinkedList<>();
        this.driver = driver;

        loadProfessors();
    }

    /**
     * Function to scrape penn course review to find the professors who have taught this class
     */
    private void loadProfessors() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get(COURSE_REVIEW_URL + getCourseReviewUrlSuffix());
    
            WebElement name_box = driver.findElement(By.className("rt-tbody"));
    
            List<String> professors = Arrays.asList(name_box.getText().split("\n"));

            // Since professor ratings are contained in the same html elements as professor names,
            // the above list contains professors and ratings. This expression filters out ratings
            this.professors = professors.stream().filter(p -> !p.substring(0, 1)
                    .matches("\\d")).collect(Collectors.toList());
        } catch (Exception e) {
            professors = new ArrayList<String>();
        }
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
        return className.toUpperCase().replace(" ", "-").replace("Â", "");
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
