import java.util.Comparator;


public class ShortestPathCost extends Cost implements Comparator<Route> {

	/**
	 * Compares first on route miles, then on ISP name, then on city name.
	 */
	@Override
	public int compare(Route r1, Route r2) {
		int rtn = Double.compare(r1.getMiles(), r2.getMiles());
		return rtn == 0 ? super.compare(r1, r2) : rtn;
	}

}
