package classfetch;

import url.URLGetter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CourseCatalog {
    private final static String COURSE_CATALOG_URL = "https://catalog.upenn.edu/courses/";
    private final static URLGetter url = new URLGetter(COURSE_CATALOG_URL);

    /**
     * Gets the list of department url suffixes on the penn course catalog website
     * @return A list of strings representing the departments (ex: ["cis", "nets", "zulu"])
     */
    private static List<String> getDepartmentUrlSuffixes() {
        ArrayList<String> page = url.getContents();

        String departmentUrlPatternTemplate = "href=\"/courses/(\\w*)/\">";
        Pattern regionStartPattern = Pattern.compile(departmentUrlPatternTemplate);

        List<String> departments = new LinkedList<>();
        for (String line : page) {
            Matcher m = regionStartPattern.matcher(line);
            while (m.find()) {
                departments.add(m.group(1));
                line = line.replace(m.group(), " ");
            }
        }
        return departments;
    }

    /**
     * Getter for all classes on course catalog as PennClass objects
     * @return a list of PennClass objects representing all classes on the Penn course catalog
     */
    public static List<PennClass> getClasses() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://penncoursereview.com/course/CIS-411");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<PennClass> classes = new LinkedList<>();
        List<String> departmentSuffixes = getDepartmentUrlSuffixes();
        String classNamePatternTemplate =
                "<p class=\"courseblocktitle noindent\"><strong>([^<]*)";
        Pattern classNamePattern = Pattern.compile(classNamePatternTemplate);
        for (int i = 0; i < departmentSuffixes.size(); i++) {

            // Change this to change the department, or remove it altogether to run on all classes
            if(departmentSuffixes.get(i).equalsIgnoreCase("cis")) {
                System.out.print('\r');
                System.out.print("Getting " + departmentSuffixes.get(i) + " classes...");
                URLGetter departmentURL = new URLGetter(COURSE_CATALOG_URL + departmentSuffixes.get(i));
                ArrayList<String> page = departmentURL.getContents();
                for (String line : page) {
                    Matcher m = classNamePattern.matcher(line);
                    if (m.find()) {
                        String[] className = m.group(1).split(" ");
                        classes.add(new PennClass(className[0], driver));
                    }
                }
            }
        }
        System.out.print('\r');
        System.out.println("All courses loaded.");
        return classes;
    }

}
