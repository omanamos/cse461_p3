//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for a POP (point of presence) in the network graph,
//consisting of a node owned by a given ISP in a given city.
//Each POP maintains its own list of routes to destinations.


import java.util.*;

public class POP {
    private ISP owner;  //the ISP owner of this POP
    private Map<POP, Route> routes; //the list of routes this POP uses to reach destinations
    private City city; //the city this POP is located in
    private Comparator<Route> routeMetric;
    
    //create a new POP, with
    //default route to itself at cost 0.
    public POP(City city, ISP owner, Comparator<Route> comparator) {
        this.owner = owner;
        routes = new HashMap<POP, Route>();
        this.city = city;
        this.routeMetric = comparator;
        city.addPOP(this);
        Route defaultRoute = new Route(this);
        routes.put(this, defaultRoute);    
    }
    
    //Get a list of all my neighbors,
    //both via ISP and local POPs
    public List<POP> getNeighbors(){
        List<POP> neighbors = new LinkedList<POP>();
        neighbors.addAll(ispNeighbors());
        neighbors.addAll(cityNeighbors());
        return neighbors;
    }
    
    //Get all the other POPs via my ISP
    public List<POP> ispNeighbors(){
        List<POP> all = owner.getPOPs();
        return removeMe(all);        
    }
    
    //Get the other POPs in the same city
    public List<POP> cityNeighbors(){
        List<POP> all = city.getPOPs();
        return removeMe(all);
    }

    //get the ISP owner of this pop
    public ISP getOwner(){
        return owner;
    }
    
    //get the city of this POP
    public City getCity(){
        return city;
    }
    
    //Look at the routes from this POP
    public List<Route> getRoutes(){
        List<Route> copy = new LinkedList<Route>();
        copy.addAll(routes.values());
        return copy;
    }
    
    //Add a route to this POPs route table
    private void putRoute(Route route){
        routes.put(route.getDestination(), route);
    }
    
    //print this POP's routing table
    public void printRoutingTable(){
        System.out.println("Routing Table for " + this + ":");
        for(Route r : routes.values()){
            System.out.println("\t" + r);
        }
    }
    
    //Send out our path vector to all neighbors
    //return true if any of my neighbors ended up updating their path vectors
    public boolean propogate() {
    	boolean changed = false;
    	for(POP p : this.getNeighbors()) {
    		changed = p.receiveUpdate(this) || changed;
    	}
    	return changed;
    }
    
    //Receive a path vector update from a neighbor
    //return true if our path vector was modified
    public boolean receiveUpdate(POP other) {
    	boolean changed = false;
    	
    	for(Route r : other.getRoutes()) {
    		Route potentialRoute = new Route(r, this, other.city.miles(this.city), EconCost.getCost(r.getDestination(), other, this));
    		
    		if(!this.routes.containsKey(potentialRoute.getDestination())) {
    			putRoute(potentialRoute);
    			changed = true;
    		} else {
    			Route currentRoute = this.routes.get(potentialRoute.getDestination());
        		
        		if(this.routeMetric.compare(potentialRoute, currentRoute) < 0) {
        			putRoute(potentialRoute);
        			changed = true;
        		}
    		}
    	}
    	
    	return changed;
    }
    
    //Prints as the ISP name and the city of the POP
    public String toString(){
        return owner.getName() + " in " + city.toString();
    }
    
    //Simple just so it works.  Two POP's are the
    //same if they have the same toString (same
    //city and ISP).
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }
    
    //remove myself from a list of POPs
    private List<POP> removeMe(List<POP> all){
        List<POP> neighbors = new LinkedList<POP>();
        for(POP pop : all){
            if(!pop.equals(this)){
                neighbors.add(pop);
            }    
        }
        
        return neighbors;    
    }
}
