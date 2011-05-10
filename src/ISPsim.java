//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Main class for running the Simulator.  Sets up the
//Internet based on file input, then provides
//framework for various route discovery protocols.

import java.util.*;
import java.io.*;


public class ISPsim {

    public static void main(String[] args) throws IOException {
       // III HHHHAAAATTTTEEEE JJJJAAAVVVVAAA !!!!
       List<List<Double>> tuple1 = simulate(new EconCost());
       List<List<Double>> tuple2 = simulate(new ShortestPathCost());

       BufferedWriter costOutput = new BufferedWriter(new FileWriter(new File("EconCost.dat")));
       BufferedWriter milesOutput = new BufferedWriter(new FileWriter(new File("MilesCost.dat")));

       for(int i = 0; i < tuple1.get(0).size(); i++) {
    	   costOutput.write(tuple1.get(0).get(i) + "," + tuple2.get(0).get(i));
           costOutput.newLine();
       }
       
       for(int i = 0; i < tuple1.get(1).size(); i++) {
    	   milesOutput.write(tuple1.get(1).get(i) + "," + tuple2.get(1).get(i));
           milesOutput.newLine();
       }

       costOutput.close();
       milesOutput.close();
    }
    
    // return [sorted list of $ costs, sorted list of miles cost] 
    private static List<List<Double>> simulate(Comparator<Route> routeMetric) {
    	List<List<Double>> tuple = new ArrayList<List<Double>>();
    	tuple.add(new ArrayList<Double>());
    	tuple.add(new ArrayList<Double>());
    	
    	//Set up the Internet!
        Internet myInternet = new Internet(routeMetric);
                
        int iterations = 0;

        for(;;) { 
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
            	tuple.get(0).add(r.getCost());
            	tuple.get(1).add(r.getMiles());
            }
        }
        
        return tuple;
    }
}
