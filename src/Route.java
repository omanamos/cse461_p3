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
        route = new LinkedList<POP>();
        route.add(start);
        route.addAll(other.route);
        
        cost = other.cost + additionalCost;
        costMiles = other.costMiles + additionalMiles;
    }
    
    //Add a hop to this route
    public void addHop(POP next, double cost, double miles){
        route.add(next);
        this.cost += cost;
        this.costMiles += miles;
    }
    
    //get the cost of this route in Miles
    public double getMiles(){
        return costMiles;
    }
    
    //get the cost of this route in $ (relative to the source ISP)
    public double getCost(){
       double cost = 0.0;
       ISP us = this.getSource().getOwner(); // invariant: source is the first hop (hypothetical routes are constructed before they are compared)
       POP previousNode = null;
       
       // a route may traverse our ISP in several points if we pass off to a transit provider, and they eventually pass it back to us (multiple times).
       // thus, we iterate over the entire path.
       for(POP curNode : this.route){
    	   if(previousNode == null){
    		   //Initial condition for setting up the loop
    	   }else if(previousNode.getOwner().equals(us)){ // previous node was owned by us
			   if(curNode.getOwner().equals(us)){ // traveling within the same ISP (normalized distance cost)
				   cost += previousNode.getCity().norm(curNode.getCity());
			   }else if(!curNode.getOwner().equals(this.destination.getOwner())){ // traveling from one ISP to intermediary ISP
				   cost += 0.5;
			   } //else traveling from one ISP to the destination's ISP
    	   }else if(curNode.getOwner().equals(us) && !this.destination.getOwner().equals(us)){ //we are being treated as an intermediary ISP
    		   cost -= 0.5;
    	   } // else, we're not involved, so there's no cost to us
    	   previousNode = curNode;
       }
       
       return cost;
    }
    
    public double getProfit() {
    	double revenue = 1.0; // the customer always pays us $1
    	if(this.destination.getOwner().equals(this.route.get(0).getOwner())) { // the destination customer is also paying us
    		revenue += 1.0;
    	}

    	return revenue - this.getCost();
    }
    
    public double getTotalCost(){
    	 return cost;
    }
    
    //get the destination of this route
    public POP getDestination(){
        return destination;
    }
    
    public POP getSource(){
    	return this.route.get(0);
    }
    
    public POP getSecond(){
    	return this.route.size() > 1 ? this.route.get(1) : null;
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
        
        double costRounded = Math.round(this.getCost() * 100) / 100.0;
        long milesRounded = Math.round(costMiles);
        
        result += ". Cost of route: $" + costRounded + ", " + milesRounded + " miles.";
        
        return result;  
    }
    
    
    public int hashCode(){
    	return this.destination.hashCode();
    }
    
    //Simple just so it works.  Two routes are equal if they
    //have the same toString.
    public boolean equals(Object o){
    	if(o instanceof Route){
    		return this.getSource().equals(((Route)o).getSource()) && this.getDestination().equals(((Route)o).getDestination());
    	}else
    		return false;
        //return this.toString().equals(o.toString());
    }
}
