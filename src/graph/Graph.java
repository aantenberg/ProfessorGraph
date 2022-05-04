package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import classfetch.PennClass;

public class Graph {
    private Map<String, Set<String>> adjacencyList;
    
    public Graph() {
        adjacencyList = new HashMap<String, Set<String>>();
    }
    
    public void addEdge(String first, String second) {
        if(!this.adjacencyList.containsKey(first)) {
            this.adjacencyList.put(first, new HashSet<String>());
        }
        
        if(!this.adjacencyList.containsKey(second)) {
            this.adjacencyList.put(second, new HashSet<String>());
        }
        
        this.adjacencyList.get(first).add(second);
        this.adjacencyList.get(second).add(first);
    }
    
    public void addEdgesForClass(PennClass c) {
        List<String> professors = c.getProfessors();
        
        for(int i = 0; i < professors.size(); i++) {
            for(int j = i+1; j < professors.size(); j++) {
                this.addEdge(professors.get(i), professors.get(j));
            }
        }
    }
    
    public int bfsCCs() {
        
        //initialize the tracking data structures
        HashSet<String> visited = new HashSet<String>();
        LinkedList<String> queue = new LinkedList<String>();
        int ccCount = 0;
        
        String start = getNextUnvisited(visited);
        
        visited.add(start);
        queue.add(start);
        
        while(start.length() > 0) {
            //for each node in the queue remove it and add its unvisited neighbors
            while (!queue.isEmpty()) {
                String currNode = queue.poll();
                
                Set<String> edges = this.adjacencyList.get(currNode);
                
                Iterator<String> iter = edges.iterator();
                
                //loop through neighbors and check if they are visited
                while (iter.hasNext()) {
                    String next = iter.next();
                    
                    if (!visited.contains(next)) {
                        visited.add(next);
                        
                        queue.add(next);
                    }
                }
            }
            
            start = getNextUnvisited(visited);
            
            visited.add(start);
            queue.add(start);
            
            ccCount++;
        }
        
        //if we have found the node then return the path we found
        return ccCount;
    }
    
    public int bfsMaxCC() {
        
        //initialize the tracking data structures
        HashSet<String> visited = new HashSet<String>();
        LinkedList<String> queue = new LinkedList<String>();
        int maxCC = 0;
        int curCC = 0;
                
        String start = getNextUnvisited(visited);
        
        visited.add(start);
        queue.add(start);
        
        while(start.length() > 0) {
            //for each node in the queue remove it and add its unvisited neighbors
            while (!queue.isEmpty()) {
                String currNode = queue.poll();
                curCC++;
                
                Set<String> edges = this.adjacencyList.get(currNode);
                
                Iterator<String> iter = edges.iterator();
                
                //loop through neighbors and check if they are visited
                while (iter.hasNext()) {
                    String next = iter.next();
                    
                    if (!visited.contains(next)) {
                        visited.add(next);
                        
                        queue.add(next);
                    }
                }
            }
            
            start = getNextUnvisited(visited);
            
            visited.add(start);
            queue.add(start);
            
            if(curCC > maxCC) {
                maxCC = curCC;
            }
            
            System.out.println("Cur CC: " + curCC);
            
            curCC = 0;
        }
        
        System.out.println("Size : " + adjacencyList.size());
        
        //if we have found the node then return the path we found
        return maxCC;
    }
    
    private String getNextUnvisited(Set<String> visited) {
        Iterator<String> iter = adjacencyList.keySet().iterator();
        
        while(iter.hasNext()) {
            String next = iter.next();
            if(!visited.contains(next)) return next;
        }
        
        return "";
    }
    
    public List<String> bfsPath(String start, String end) { //NOTE: this does not restart in another CC if no path is found
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            throw new IllegalArgumentException();
        }

        //initialize the tracking data structures
        HashSet<String> visited = new HashSet<String>();
        visited.add(start);
        HashMap<String, String> parents = new HashMap<String, String>();
        parents.put(start, null);        
        LinkedList<String> queue = new LinkedList<String>();
        queue.add(start);
        
        //prevents errors when there is no path
        boolean found = false;
        
        //for each node in the queue remove it and add its unvisited neighbors
        while (!queue.isEmpty()) {
            String currNode = queue.poll();
            
            Set<String> edges = this.adjacencyList.get(currNode);
            
            //loop through neighbors and check if they are visited
            Iterator<String> iter = edges.iterator();
            while (iter.hasNext()) {
                String next = iter.next();
                
                if (!visited.contains(next)) {
                    visited.add(next);
                    
                    parents.put(next, currNode);
                    
                    queue.add(next);
                    
                    //if we have reached the end then exit the loop
                    if (next.equals(end)) {
                        queue.clear();
                        found = true;
                        break;
                    }
                }
            }
        }
        
        if (!found) {
            return null;
        }
        
        //if we have found the node then return the path we found
        return backtrack(parents, end);
    }
    
    private List<String> backtrack(Map<String, String> parents, String ending) {
        LinkedList<String> ret = new LinkedList<String>();
        
        String curr = ending;
        
        //add the current node to the start of the path
        while (curr != null) {
            ret.addFirst(curr);
            curr = parents.get(curr);
        }
        
        return ret;
    }
    
    public String findMostBetweennessCentral() {
        HashMap<String, Integer> ccs = getCCs();
        
        Set<String> visited = new HashSet<String>();
        HashMap<String, Integer> betweenness = new HashMap<String, Integer>();
        
        Iterator<String> firstIter = this.adjacencyList.keySet().iterator();
        
        while(firstIter.hasNext()) {
            String s = firstIter.next();
            
            betweenness.put(s, 0);
        }
        
        
        String curr = getNextUnvisited(visited);
        
        while(curr.length() > 0) {
            visited.add(curr);
            
            Iterator<String> iter = this.adjacencyList.keySet().iterator();
            
            while(iter.hasNext()) {
                String next = iter.next();
                
                //if(ccs.get(next) != ccs.get(curr)) continue;
                
                List<String> path = bfsPath(curr, next);
                
                if(path == null) continue;
                
                for (String i : path) {
                    betweenness.put(i, betweenness.get(i) + 1);
                }
            }
            
            getNextUnvisited(visited);
        }
        
        int max = 0;
        String maxString = "";
        
        Iterator<String> lastIter = betweenness.keySet().iterator();
        
        while(lastIter.hasNext()) {
            String s = lastIter.next();
            
            if(betweenness.get(s) > max) {
                max = betweenness.get(s);
                maxString = s;
            }
        }
        
        return maxString;
    }
    
    private HashMap<String, Integer> getCCs(){
        //initialize the tracking data structures
        HashSet<String> visited = new HashSet<String>();
        LinkedList<String> queue = new LinkedList<String>();
        HashMap<String, Integer> ccs = new HashMap<String, Integer>();
        int curCC = 0;
        
        String start = getNextUnvisited(visited);
        
        visited.add(start);
        queue.add(start);
        
        while(start.length() > 0) {
            //for each node in the queue remove it and add its unvisited neighbors
            while (!queue.isEmpty()) {
                String currNode = queue.poll();
                ccs.put(currNode, curCC);
                
                Set<String> edges = this.adjacencyList.get(currNode);
                
                Iterator<String> iter = edges.iterator();
                
                //loop through neighbors and check if they are visited
                while (iter.hasNext()) {
                    String next = iter.next();
                    
                    if (!visited.contains(next)) {
                        visited.add(next);
                        
                        queue.add(next);
                    }
                }
            }
            
            start = getNextUnvisited(visited);
            
            visited.add(start);
            queue.add(start);
            curCC++;
        }
        
        return ccs;
    }

    private double clusteringCoefficient(String node) {
        if (!adjacencyList.containsKey(node)) {
            throw new IllegalArgumentException();
        }
        Set<String> neighbors = adjacencyList.get(node);

        // Count will store the number of edges connecting two neighbors of the input node
        double count = 0;
        for (String neighbor : neighbors) {
            for (String neighbor2 : neighbors) {
                if (!neighbor.equals(neighbor2) &&
                        adjacencyList.get(neighbor).contains(neighbor2)) {
                    count++;
                }
            }
        }

        // We need to do this since the above for loop double-counts
        count /= 2;

        double totalPossibleEdges = 0.5 * neighbors.size() * (neighbors.size() - 1);
        return count / totalPossibleEdges;
    }

    public double averageClusteringCoefficient() {
        Set<String> nodes = adjacencyList.keySet();
        double clusteringCoefficientSum = 0;
        for (String node : nodes) {
            clusteringCoefficientSum += clusteringCoefficient(node);
        }
        return clusteringCoefficientSum / nodes.size();
    }
}
