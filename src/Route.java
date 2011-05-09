//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for a given Route in a routing table.
//Keeps track of the list of POPs that make up the route,
//along with the mileage and cost in $ of the route.

import java.util.*;

public class Route {
    private POP destination;  //the end of this route
    private double cost; //the cost in $
    private List<POP> route; //the POPs in the route
    private double costMiles; //the miles of this route
    
    //create a new default route, used
    //when a POP first wakes up.  Only knows
    //how to reach itself as the destination.
    public Route(POP destination){
        this.destination = destination;
        cost = 0;
        costMiles = 0;
        route = new LinkedList<POP>();
        route.add(destination);
    }
    
    //Create a new route based on an existing route,
    //appending a new start POP to the beginning of the route
    public Route(Route other, POP start, double additionalCost, double additionalMiles){
        this.destination = other.destination;
        cost = other.cost + additionalCost;
        costMiles = other.costMiles + additionalMiles;
        route = new LinkedList<POP>();
        route.add(start);
        route.addAll(other.route);
    }
    
    //Add a hop to this route
    public void addHop(POP next, double cost, double miles){
        route.add(next);
        this.cost += cost;
        this.costMiles += miles;
    }
    
    public POP getFirst(){
    	return this.route.get(0);
    }
    
    //get the cost of this route in Miles
    public double getMiles(){
        return costMiles;
    }
    
    //get the cost of this route in $
    public double getCost(){
        return cost;
    }
    
    //get the destination of this route
    public POP getDestination(){
        return destination;
    }
    
    //Look at the hops in this route
    public List<POP> getHops(){
        List<POP> copy = new LinkedList<POP>();
        copy.addAll(route);
        return copy;
    }
    
    //Display the route, POP by POP.
    //Also displays the cost and mileage.    
    public String toString(){
        String result = "";
        result += route.get(0);
        
        for(int i=1; i<route.size(); i++){
            result += " -> " + route.get(i);
        }
        
        double costRounded = Math.round(cost * 100) / 100.0;
        long milesRounded = Math.round(costMiles);
        
        result += ". Cost of route: $" + costRounded + ", " + milesRounded + " miles.";
        
        return result;  
    }
    
    //Simple just so it works.  Two routes are equal if they
    //have the same toString.
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }
}
