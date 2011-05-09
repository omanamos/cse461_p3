import java.util.Comparator;


public class EconCost extends Cost implements Comparator<Route> {

	/**
	 * Compares first on route miles, then on ISP name, then on city name.
	 */
	@Override
	public int compare(Route r1, Route r2) {		
		int rtn = Double.compare(r1.getCost(), r2.getCost());
		return rtn == 0 ? super.compare(r1, r2) : rtn;
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
