//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Class for the Internet in the simulation, consisting
//of the set of ISPs as well as the cities in the world.
//The Internet also knows the longest possible path (in miles)
//for normalization purposes.

import java.util.*;
import java.io.*;

public class Internet {
	private List<ISP> ispList; //list of ISP's in this Internet
	private static double maxDistance; //max distance between two cities
	
	public Internet() {
		ispList = new LinkedList<ISP>();
		
		//Get the cities involved in this Internet
		List<City> cities = importCities("LatLongs.txt");
		
		//Calculate maximum distance in city graph
		maxDistance = calcDist(cities);
			
		//create ISPs and give them their
		//respective POPs
		ispList = importISPs("ispList.txt", cities);
	}
	
	//get a list of all POP's in the Internet.
	//This is just a shortcut method for convenience.
	public List<POP> getAllPOPs(){
		List<POP> allPOPs = new LinkedList<POP>();
		for(ISP isp : ispList){
			allPOPs.addAll(isp.getPOPs());
		}
		return allPOPs;
	}
	
	//get the list of ISP's
	public List<ISP> getISPs(){
		List<ISP> copy = new LinkedList<ISP>();
		copy.addAll(ispList);
		return copy;
	}
	
	//Add another ISP to this Internet
	public void addISP(ISP isp){
		ispList.add(isp);
	}
	
	//Gives the max distance, based on input city graph
	public static double getMaxDistance(){
		return maxDistance;
	}
	
	//toString: ISP name with indented POP's.
	public String toString(){
		String result = "";
		for(ISP isp : ispList){
			result += isp + "\n";
		}
		
		return result;
	}
	
	//Caculate max distance in city graph
	private double calcDist(List<City> cities){
		double maxDistance = 0;
		for(City j : cities){
			for(City k : cities){
				double distance = j.miles(k);
			//	System.out.println(j + " to " + k + ": " + distance);
				if(distance > maxDistance){
					maxDistance = distance;
				}
			}
		}
		
		return maxDistance;
	}
	
	//import cities and their latitude/longitude from file
	private List<City> importCities(String filename){
		List<City> cities = new LinkedList<City>();
		try{
			Scanner input = new Scanner(new File(filename));
			while(input.hasNextLine()){
				String name = input.nextLine();
				double lat = Double.parseDouble(input.nextLine());
				double lon = Double.parseDouble(input.nextLine());
				cities.add(new City(name, lat, lon));	
			}
			input.close();
		} catch (Exception e){
			System.out.println("Error while processing Cities input file: " + e);
			System.exit(1);
		}
		
		return cities;	
	}
	
	//import ISPs and their POPs from file
	private List<ISP> importISPs(String filename, List<City> cities){
		List<ISP> isps = new LinkedList<ISP>();
		try{
			Scanner input = new Scanner(new File(filename));
			while(input.hasNextLine()){
				String isp = input.nextLine();
				ISP next = new ISP(isp);
				while(input.hasNextLine()){
					String city = input.nextLine();
					if(!city.equals("#")){
						City fakeCity = new City(city, 0, 0);
						next.createPOP(cities.get(cities.indexOf(fakeCity)));
					} else
						break;
				}
				isps.add(next);
			}
			input.close();
		} catch (Exception e){
			System.out.println("Error while processing ISP input file: " + e);
			System.exit(1);
		}
		
		return isps;	
	}
}

