import java.util.Comparator;


public class ShortestPathCost implements Comparator<Route> {

	@Override
	public int compare(Route r1, Route r2) {
		return Double.compare(r1.getMiles(), r2.getMiles());
	}

}
