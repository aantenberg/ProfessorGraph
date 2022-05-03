package classfetch;

import java.util.List;

import graph.Graph;

public class Client {

    /**
     * Demo of functionality of CourseCatalog and PennClass. Feel free to comment/uncomment what you
     * want to see different functionality.
     */
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\parra\\Downloads\\chromedriver_win32\\chromedriver.exe");

        // Gets the list of all courses as PennClass objects
        List<PennClass> list = CourseCatalog.getClasses();
        
        Graph g = new Graph();

        for (int i = 0; i < list.size(); i++) {
            PennClass p = list.get(i);
            
            g.addEdgesForClass(p);

            // Prints the name of this class (ex: "NETS 150")
            //System.out.println(p);

            // Prints the Penn Course Review URL suffix of this class (ex: "NETS-150")
            //System.out.println(p.getCourseReviewUrlSuffix());
        }
        
        System.out.println(g.bfsCCs());
        System.out.println(g.bfsMaxCC());
        System.out.println(g.findMostBetweennessCentral());

        // Gets the list of all courses as strings (just showing there's a string version too lol)
//        List<String> strings = CourseCatalog.getClassesStr();
    }
}
