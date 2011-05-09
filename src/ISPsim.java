//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Main class for running the Simulator.  Sets up the
//Internet based on file input, then provides
//framework for various route discovery protocols.

import java.util.*;

public class ISPsim {

    public static void main(String[] args){
       List<List<Double>> tuple1 = simulate(new EconCost());
       List<List<Double>> tuple2 = simulate(new ShortestPathCost());
       
       for(int i = 0; i < tuple1.get(0).size(); i++) {
    	   System.out.println(tuple1.get(0).get(i) + "," + tuple2.get(0).get(i));
       }
       
       for(int i = 0; i < tuple1.get(1).size(); i++) {
    	   System.err.println(tuple1.get(1).get(i) + "," + tuple2.get(1).get(i));
       }
    }
    
    // return [sorted list of $ costs, sorted list of miles cost] 
    private static List<List<Double>> simulate(Comparator<Route> routeMetric) {
    	List<List<Double>> tuple = new ArrayList<List<Double>>();
    	tuple.add(new ArrayList<Double>());
    	tuple.add(new ArrayList<Double>());
    	
    	//Set up the Internet!
        Internet myInternet = new Internet(routeMetric);
                
        boolean stop = false;
        while(!stop){
        	stop = true;
        	for(POP pop : myInternet.getAllPOPs()){
        		stop = pop.propogate() && stop;
        	}
        }
        
        
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