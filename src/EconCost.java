import java.util.Comparator;


public class EconCost implements Comparator<Route> {

	@Override
	public int compare(Route r1, Route r2) {		
		int rtn = Double.compare(r1.getCost(), r2.getCost());
		if(rtn == 0){
			POP neighbor1 = r1.getFirst();
			POP neighbor2 = r2.getFirst();
			if(neighbor1.getOwner().equals(neighbor2.getOwner())){
				return neighbor1.getCity().getName().compareTo(neighbor2.getCity().getName());
			}else{
				return neighbor1.getOwner().getName().compareTo(neighbor2.getOwner().getName());
			}
		}else
			return rtn;
	}

	/**
	 * @param dest Destination of the route
	 * @param from neighbor node
	 * @param to this node
	 * @return cost in $ of sending between from and to
	 */
	public static double getCost(POP dest, POP from, POP to) {
		return from.getOwner().equals(to.getOwner()) ? from.getCity().norm(to.getCity()) : 
					dest.getOwner().equals(from.getOwner()) ? 0.0 : 0.5;
	}
}
