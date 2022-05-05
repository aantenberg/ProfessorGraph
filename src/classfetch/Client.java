package classfetch;

import java.util.List;
import java.util.Scanner;

import graph.Graph;

public class Client {

    /**
     * Demo of functionality of CourseCatalog and PennClass. Feel free to comment/uncomment what you
     * want to see different functionality.
     */
    public static void main(String[] args) {

        // Change the filepath below to the filepath of your chrome driver
        System.setProperty("webdriver.chrome.driver", "/Users/andrewantenberg/Downloads/chromedriver");

        // Gets the list of all courses as PennClass objects
        List<PennClass> list = CourseCatalog.getClasses();
        
        Graph g = new Graph();

        for (int i = 0; i < list.size(); i++) {
            PennClass p = list.get(i);
            
            g.addEdgesForClass(p);
        }
        
        System.out.println("CC num:");
        System.out.println(g.bfsCCs());
        System.out.println("Largest CC:");
        System.out.println(g.bfsMaxCC());
        System.out.println("Avg Clustering");
        System.out.println(g.averageClusteringCoefficient());
        System.out.println("Clustering for Swap");
        System.out.println(g.clusteringCoefficient("Swapneel Sheth"));

        // This takes a long time, so it is commented out. Feel free to uncomment if interested!
//        System.out.println("Most Central Person");
//        System.out.println(g.findMostBetweennessCentral());
        System.out.println("Centrality of Swap");
        System.out.println(g.findCentralityForProfessor("Swapneel Sheth"));
        System.out.println(g.getAllNodes());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the names of two professors (separated by a comma and space) to " +
                "find the shortest path between them! Please type the professor's name exactly " +
                "as it appears above.");
        String[] profs = scanner.nextLine().split(", ");
        try {
            System.out.println(g.bfsPath(profs[0], profs[1]));
        } catch (IllegalArgumentException e) {
            System.out.println("Professor is not in the graph.");
        }
    }
}
