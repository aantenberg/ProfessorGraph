package classfetch;

import url.URLGetter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseCatalog {
    private final static String COURSE_CATALOG_URL = "https://catalog.upenn.edu/courses/";
    private final static URLGetter url = new URLGetter(COURSE_CATALOG_URL);
    private static List<String> classesStr = new LinkedList<>();
    private static List<PennClass> classes = new LinkedList<>();

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
     * Gets a list of all classes on the Penn Course catalog, either as a list of strings or
     * as a list of PennClass objects
     * @param returnStrings boolean that is true if the function should return a list of strings,
     *                      false if the function should return a list of PennClass objects
     * @return a list of Strings of PennClass objects representing Penn's classes
     */
    private static List getClasses(boolean returnStrings) {
        List<String> departmentSuffixes = getDepartmentUrlSuffixes();
        String classNamePatternTemplate =
                "<p class=\"courseblocktitle noindent\"><strong>([^<]*)";
        Pattern classNamePattern = Pattern.compile(classNamePatternTemplate);
        for (int i = 0; i < departmentSuffixes.size(); i++) {
            System.out.print('\r');
            System.out.print("Getting " + departmentSuffixes.get(i) + " classes...");
            URLGetter departmentURL = new URLGetter(COURSE_CATALOG_URL + departmentSuffixes.get(i));
            ArrayList<String> page = departmentURL.getContents();
            for (String line : page) {
                Matcher m = classNamePattern.matcher(line);
                if (m.find()) {
                    String[] className = m.group(1).split(" ");
                    classesStr.add(className[0]);
                    classes.add(new PennClass(className[0]));
                }
            }
        }
        System.out.print('\r');
        System.out.println("All courses loaded.");
        if (returnStrings) {
            return classesStr;
        }
        return classes;
    }

    /**
     * Getter for all classes on course catalog as strings
     * @return a list of strings representing all classes on the Penn course catalog
     */
    public static List<String> getClassesStr() {
        return classesStr.isEmpty() ? getClasses(true) : classesStr;
    }

    /**
     * Getter for all classes on course catalog as PennClass objects
     * @return a list of PennClass objects representing all classes on the Penn course catalog
     */
    public static List<PennClass> getClasses() {
        return classes.isEmpty() ? getClasses(false) : classes;
    }

}
