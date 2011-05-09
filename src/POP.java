//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for a POP (point of presence) in the network graph,
//consisting of a node owned by a given ISP in a given city.
//Each POP maintains its own list of routes to destinations.


import java.util.*;

public class POP {
    private ISP owner;  //the ISP owner of this POP
    private List<Route> routeList; //the list of routes this POP uses to reach destinations
    private City city; //the city this POP is located in
    
    //create a new POP, with
    //default route to itself at cost 0.
    public POP(City city, ISP owner) {
        this.owner = owner;
        routeList = new LinkedList<Route>();
        this.city = city;
        city.addPOP(this);
        Route defaultRoute = new Route(this);
        routeList.add(defaultRoute);    
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
        copy.addAll(routeList);
        return copy;
    }
    
    //Add a route to this POPs route table
    public void addRoute(Route route){
        routeList.add(route);
    }
        
    //Replace an entry in the route table
    //with a new route
    public void replaceRoute(Route current, Route newRoute){
        int index = routeList.indexOf(current);
        if(index == -1 || newRoute == null){
            System.out.println("Error! Current route was not in the POP on call to replaceRoute(), or new route was null");
            System.exit(1);
        } else {
            routeList.set(index, newRoute);
        }
    }
    
    //Remove a route from this POPs route list
    public void removeRoute(Route target){
        routeList.remove(target);
    }
    
    //print this POP's routing table
    public void printRoutingTable(){
        System.out.println("Routing Table for " + this + ":");
        for(Route r : routeList){
            System.out.println("\t" + r);
        }
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
