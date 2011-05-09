//ISP Simulator, CSE461 Fall 2009 by Josh Goodwin
//Main class for running the Simulator.  Sets up the
//Internet based on file input, then provides
//framework for various route discovery protocols.

import java.io.*;
import java.util.*;

public class ISPsim {

	public static void main(String[] args){
		//Set up the Internet!
		Internet myInternet = new Internet();
		
		//Print out the Initial ISP structure
		System.out.println(myInternet);
		
		//Print out the initial routing tables
		//(just each node aware of itself)
		List<POP> pops = myInternet.getAllPOPs();
		for(POP pop : pops){
			pop.printRoutingTable();
		}
				
		/*
			Insert code here for route generation.
			Your algorithm should be such that the routes
			in each POP eventually stabilize and converge.
		
		*/
			
	}

}