import java.util.Comparator;


public class Cost implements Comparator<Route>{

	/**
	 * Compares first on ISP name, then on city name.
	 */
	@Override
	public int compare(Route r1, Route r2) {
		POP neighbor1 = r1.getFirst();
		POP neighbor2 = r2.getFirst();
		
		if(neighbor1.getOwner().equals(neighbor2.getOwner())){ //POPs in the same ISP, try comparing on city names
			return neighbor1.getCity().getName().compareTo(neighbor2.getCity().getName());
		}else{ //compare on ISP
			return neighbor1.getOwner().getName().compareTo(neighbor2.getOwner().getName());
		}
	}
	
}
