import java.util.Comparator;


public class EconCost implements Comparator<Route> {

	@Override
	public int compare(Route r1, Route r2) {		
		return Double.compare(r1.getCost(), r2.getCost());
	}

	public static double getCost(POP from, POP to) {
		return from.getOwner().equals(to.getOwner()) ? from.getCity().norm(to.getCity()) : 0.5;
	}
}