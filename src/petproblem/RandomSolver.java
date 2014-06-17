package petproblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/* Generate a random solution which not is necessarily optimal. */
public class RandomSolver implements IPetproblemSolver {
	@Override
	public int[] solve(int n, int m, int[][] compatibility) {
		int[] result = new int[n];
		ArrayList<Integer> possibilities = new ArrayList<Integer>();
		
		// Init list of possible pets
		for(int m_i = 0; m_i < m; m_i++)
			possibilities.add(m_i);
		
		// If number of children is bigger then number of pets
		if(n > m) 
			for(int i = 0; i < n - m; i++)
				possibilities.add(-1); // Add possibility of drawing "no pet"
		
		// Shuffle the available options
		Collections.shuffle(possibilities, new Random());
		
		// Assign a possible pet to all children
		for(int n_i = 0; n_i < n; n_i++)
			result[n_i] = possibilities.get(n_i);
		
		return result;
	}
}