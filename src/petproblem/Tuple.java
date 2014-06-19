package petproblem;

/**
 * @author Robbert v.d Gugten & Steven Reitsma
 * A simple tuple class where a pet index has a value
 */
public class Tuple {
	
	public int value;
	public int index;
	
	public Tuple(int index, int value){
		this.value = value;
		this.index = index;
	}
}
