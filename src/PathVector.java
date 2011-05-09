import java.util.Map;
import java.util.HashMap;

public class PathVector {
	
	private Map<POP, Route> v;

	public PathVector(POP p){
		this.v = new HashMap<POP, Route>();
		
		for(POP n : p.getNeighbors()){
			this.v.put(n, new Route(n));
		}
	}

	public void update(PathVector other){
		
	}
}