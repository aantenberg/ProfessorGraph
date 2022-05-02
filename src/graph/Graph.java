package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classfetch.PennClass;

public class Graph {
    private Map<String, List<String>> adjacencyList;
    
    public Graph() {
        adjacencyList = new HashMap<String, List<String>>();
    }
    
    public void addEdge(String first, String second) {
        if(!this.adjacencyList.containsKey(first)) { //todo make sure no duplicate edges are added
            this.adjacencyList.put(first, new ArrayList<String>());
        }
        
        if(!this.adjacencyList.containsKey(second)) {
            this.adjacencyList.put(second, new ArrayList<String>());
        }
        
        this.adjacencyList.get(first).add(second);
        this.adjacencyList.get(second).add(first);
    }
    
    public void addEdgesForClass(PennClass c) {
        List<String> professors = c.getProfessors();
        
        for(int i = 0; i < professors.size(); i++) {
            for(int j = i+1; j < professors.size(); j++) {
                System.out.println(i);
                System.out.println(j);
                System.out.println(69);
                this.addEdge(professors.get(i), professors.get(j));
            }
        }
        
    }
}
