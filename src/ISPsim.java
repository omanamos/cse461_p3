//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Main class for running the Simulator.  Sets up the
//Internet based on file input, then provides
//framework for various route discovery protocols.

import java.util.*;
import java.io.*;

public class ISPsim {

    public static void main(String[] args) throws IOException {
       Map<Route, Route> econResults = simulate(new EconCost());
       System.out.println("------------");
       Map<Route, Route> shortestPathResults = simulate(new ShortestPathCost());

       BufferedWriter costOutput = new BufferedWriter(new FileWriter(new File("EconCost.dat")));
       BufferedWriter milesOutput = new BufferedWriter(new FileWriter(new File("MilesCost.dat")));

       for(Route r : econResults.keySet()){
    	   Route econ = econResults.get(r);
    	   Route shortestPath = shortestPathResults.get(r);
    	   
    	   if(econ.getCost() > shortestPath.getCost())
    		   econ.getCost();
    	   
    	   costOutput.write(econ.getCost() + "," + shortestPath.getCost());
    	   costOutput.newLine();
    	   costOutput.flush();
    	   
    	   milesOutput.write(econ.getMiles() + "," + shortestPath.getMiles());
    	   milesOutput.newLine();
    	   milesOutput.flush();
       }
       
       /*for(int i = 0; i < tuple1.get(0).size(); i++) {
    	   costOutput.write(tuple1.get(0).get(i) + "," + tuple2.get(0).get(i));
           costOutput.newLine();
       }
       
       for(int i = 0; i < tuple1.get(1).size(); i++) {
    	   milesOutput.write(tuple1.get(1).get(i) + "," + tuple2.get(1).get(i));
           milesOutput.newLine();
       }*/

       costOutput.close();
       milesOutput.close();
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
