//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for a city in the simulation world,
//consisting of its lat/long position, name, and
//the POPs that exist in that city.

import java.util.*;

public class City {
    private String name;
    private double latitude, longitude;
    private List<POP> pops; //POP's in this City
    
    //Create a new city    
    public City(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        pops = new LinkedList<POP>();
    }
    
    public String getName(){
    	return this.name;
    }
    
    //Calculate distance in miles between two cities
    public double miles(City other){
        //convert to radians
        double thisLatRad = latitude * (Math.PI / 180);
        double thisLongRad = longitude * (Math.PI / 180);
        double otherLatRad = other.latitude * (Math.PI / 180);
        double otherLongRad = other.longitude * (Math.PI / 180);
         
        //variables used in the below distance formula
        double firstVal = Math.cos(otherLatRad) * Math.cos(otherLongRad);
        double secondVal = Math.cos(otherLatRad) * Math.sin(otherLongRad);
            
        //Distance formula.  3963.1 is the radius of the Earth in miles (roughly ;)
        double distance = Math.acos(firstVal * Math.cos(thisLatRad) * Math.cos(thisLongRad) +
           secondVal * Math.cos(thisLatRad) * Math.sin(thisLongRad) +
           Math.sin(otherLatRad) * Math.sin(thisLatRad)) * 3963.1;
                                                  
        return distance;
    }
    
    //Calculate the normalized distance between two cities.
    public double norm(City other){
        double normDist = miles(other) / Internet.getMaxDistance();
        return normDist;
    }
    
    //add a POP to this city
    public void addPOP(POP pop){
        pops.add(pop);
    }
    
    //get the POP's in this city
    public List<POP> getPOPs(){
        List<POP> copy = new LinkedList<POP>();
        copy.addAll(pops);
        return copy;
    }
    
    //String representation: just the city's name.
    public String toString(){
        return name;
    }
    
    //Simple equals just so it works.  Two cities
    //are equal if they have the same toString.
    public boolean equals(Object o){
        return this.toString().equals(o.toString());
    }
}
