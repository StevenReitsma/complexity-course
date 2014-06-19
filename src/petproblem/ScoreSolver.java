package petproblem;


/**
 * @author Robbert v.d Gugten & Steven Reitsma
 * Solves the pet problem by assigning scores to every combination. Picking the best scores top-down gives the solution. Complexity O(n*m)
 */
public class ScoreSolver implements IPetproblemSolver {

	@Override
	public int[] solve(int n, int m, int[][] compatibility) {
		boolean[] done = new boolean[m];

		int [][]scores = createScores(n,m,compatibility);
		int [][]values = valuematrix(scores,n,m);
		int [] result = new int[n];
		for (int i = 0; i<n; i++){
			int index = -1;
			int highestscore = 0;
			for(int j = 0; j<m; j++){
				if (!done[j])                                         //check if the pet hasn't been picked yet
					if (scores[i][j] > highestscore){
						highestscore = scores[i][j];
						index = j;
					}
					else if (scores[i][j] == highestscore){         //take the row where the least points will be lost
						if (index != -1){                           
							if (values[i][index] > values[i][j]){
								index = j;
							}
						}
					}
					
			}
			if (index == -1){ //if the index has not been changed it means every pet has been chosen so maximum has been reached
				result[i] = -1;
			}
			else if (compatibility[i][index] == 0){
				result[i] = -1;
			}
			else{
				result[i] = index;
				done[index] = true;
			}
		}
		int score = 0;
		for (int i = 0; i < result.length; i++)
			score += result[i] == -1 ? 0 : 1;
		
		System.out.println("Score: " + score);
//		time = System.currentTimeMillis() - time;
//		System.out.println("time for creating values: " + time);
		return result;
	}
	
	/**
	 * @param scores: A matrix with a score for each coordinate, higher is better
	 * @param n: The number of children
	 * @param m: The number of pets
	 * @return a valuematrix where every coordinate determines the score that will be lost when assigning the pet to the child.
	 */
	private int[][] valuematrix(int [][]scores, int n, int m){
		int[][]values= new int [n][m];
		for (int i = n-1; i >= 0; i--){
			for (int j = m-1; j >= 0; j--){
				if (i == n-1){
					values[i][j] = scores[i][j];
				}
				else{
					values[i][j] = scores[i][j] + values[i+1][j];
				}
			}
		}
//		for (int i = 0; i < n; i++){
//			for (int j = 0; j<m; j++){
//				System.out.print(values[i][j] + " ");
//			}
//			System.out.println();
//		}
		return values;
	}
	
	
	/**
	 * @param n: The number of children
	 * @param m: The number of pets
	 * @param compatibility: The compatibility matrix 
	 * @return a score matrix that determines the score for each coordinate if pet is assigned to the child. 
	 * It checks the best score of the previous row (in this case the next row because we start at the bottom of the matrix) but with excluding the current pet index. 
	 * The score in the compatibility matrix is then added.
	 */
	private int[][] createScores(int n, int m, int[][] compatibility){
		int[][] scores = new int[n][m];
		Tuple best = new Tuple(0,0);
		Tuple secondbest = new Tuple(0,0);
		Tuple newbest = new Tuple(0,0);
		Tuple secondnewbest = new Tuple(0,0);
		for (int i = n-1; i >= 0; i--){
			for (int j = m-1; j >= 0; j--){
				if (i == n-1){ //bottom row
					scores[i][j] = compatibility[i][j];
				}
				else{
					if (best.index != j)
						scores[i][j] = compatibility[i][j] + best.value;
					else scores[i][j] = compatibility[i][j] + secondbest.value;
				}
				if (scores[i][j] > newbest.value)                   //set the best value of the current row
					newbest = new Tuple(j,scores[i][j]);
				else if (scores[i][j] > secondnewbest.value)        //set the second best value of the current row. 
					secondnewbest = new Tuple(j,scores[i][j]);		//second best is needed if the pet index in current row is the same as the best score index in the previous row
					
				
			}
			best = newbest;
			secondbest = secondnewbest;
			
		}
//		for (int i = 0; i < n; i++){
//			for (int j = 0; j<m; j++){
//				System.out.print(scores[i][j] + " ");
//			}
//			System.out.println();
//		}
		return scores;
		
	}
}
