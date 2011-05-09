//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for an ISP, consisting of its name and
//the list of POPs that ISP has in the world.

import java.util.*;

public class ISP {
    private String name;  //Name of the ISP
    private List<POP> popList;  //List of owned POPs
    private Comparator<Route> routeMetric;
    
    //Create a new ISP
    public ISP(String name, Comparator<Route> routeMetric){
        popList = new LinkedList<POP>();
        this.name = name;
        this.routeMetric = routeMetric;
    }
     
    //Gives this ISP a POP
    //in the city
    public void createPOP(City city){
        POP thisPOP = new POP(city, this, routeMetric);
        popList.add(thisPOP);
    }
     
    //Get the list of POP's for this ISP
    public List<POP> getPOPs(){
        List<POP> copy = new LinkedList<POP>();
        copy.addAll(popList);
        return copy;
    }
     
    //get name of the ISP
    public String getName(){
        return name;
    }
     
    //String representation is the ISP name
    //along with a list of all the POPs that ISP
    //owns
    public String toString(){
        String result = "";
        result += name + ":\n";
        for(POP pop : popList){
            result += "\t" + pop + "\n";
        }
        
        return result;
    }
    
    //Simple equals method, two ISP's are the same
    //if they have the same name
    public boolean equals(Object o){
        if(o instanceof ISP){
            return this.getName().equals(((ISP)o).getName());
        } else
            return false;
    }    
}
