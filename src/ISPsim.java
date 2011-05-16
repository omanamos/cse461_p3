//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Main class for running the Simulator.  Sets up the
//Internet based on file input, then provides
//framework for various route discovery protocols.

import java.util.*;
import java.io.*;

public class ISPsim {

	private final static String[] isps = {"Comcast", "AtlanticLink", "Sprint", "PacifiCircle", "SouthAtlantic", "NorthBridge"}; 
	
    public static void main(String[] args) throws IOException {
       Map<Route, Route> econResults = simulate(new EconCost());
       System.out.println("------------");
       Map<Route, Route> shortestPathResults = simulate(new ShortestPathCost());

       BufferedWriter costOutput = new BufferedWriter(new FileWriter(new File("EconCost.dat")));
       BufferedWriter milesOutput = new BufferedWriter(new FileWriter(new File("MilesCost.dat")));

       milesOutput.write("Comcast,AtlanticLink,Sprint,PacifiCircle,SouthAtlantic,NorthBridge\n");
       costOutput.write("Comcast,AtlanticLink,Sprint,PacifiCircle,SouthAtlantic,NorthBridge\n");
       for(Route r : econResults.keySet()){
    	   Route econ = econResults.get(r);
    	   Route shortestPath = shortestPathResults.get(r);
    	   
    	   if(econ.getCost() > shortestPath.getCost())
    		   econ.getCost();
    	   
    	   costOutput.write(toString(computeCosts(econ)));
    	   costOutput.newLine();
    	   costOutput.flush();
    	   
    	   milesOutput.write(toString(computeCosts(shortestPath)));
    	   milesOutput.newLine();
    	   milesOutput.flush();
       }
       
       costOutput.close();
       milesOutput.close();
    }
    
    private static <E> String toString(Collection<E> c){
    	String s = "";
    	for(E e : c)
    		s += e + ",";
    	return s.substring(0, s.length() - 1);
    }
    
    private static List<Double> computeCosts(Route r){
    	List<Double> rtn = new ArrayList<Double>();
    	for(String isp : isps)
    		rtn.add(r.getCost(isp));
    	return rtn;
    }
    
    // return [sorted list of $ costs, sorted list of miles cost] 
    private static Map<Route, Route> simulate(Comparator<Route> routeMetric) {
    	Map<Route, Route> rtn = new HashMap<Route, Route>();
    	
    	//Set up the Internet!
        Internet myInternet = new Internet(routeMetric);
                
        int iterations = 0;
        
        for(int i = 0; i < 100; i++) { 
            iterations++;
        	boolean someoneChanged = false;

        	for(POP pop : myInternet.getAllPOPs()){
                boolean changed = pop.propogate();
                if(changed)
                    someoneChanged = true;
        	}

            if(!someoneChanged)
                break;
        }

        System.err.println("iterations!: " + iterations);
        
        // print final routing table
        for(POP pop : myInternet.getAllPOPs()){
            List<Route> routes = pop.getRoutes();
            for(Route r : routes){
            	rtn.put(r, r);
            	System.out.println(r);
            }
        }
        
        return rtn;
    }
}
