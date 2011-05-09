import java.util.Comparator;


public class ShortestPathCost implements Comparator<Route> {

	@Override
	public int compare(Route r1, Route r2) {
		int rtn = Double.compare(r1.getMiles(), r2.getMiles());
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

}
