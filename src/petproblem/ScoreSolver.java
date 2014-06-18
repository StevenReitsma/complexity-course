package petproblem;


public class ScoreSolver implements IPetproblemSolver {

	@Override
	public int[] solve(int n, int m, int[][] compatibility) {
		boolean[] todo = new boolean[m];
		for (int i = 0; i < m; i++){
			todo[i] = true;
		}
//		long time = System.currentTimeMillis();
		int [][]scores = createScores(n,m,compatibility);
//		time = System.currentTimeMillis() - time;
//		System.out.println("time for creating scores: " + time);
//		time = System.currentTimeMillis();
		int [][]values = valuematrix(scores,n,m);
//		time = System.currentTimeMillis() - time;
//		System.out.println("time for creating values: " + time);
		int [] result = new int[n];
//		time = System.currentTimeMillis();
		for (int i = 0; i<n; i++){
			int index = -1;
			int highestscore = 0;
			for(int j = 0; j<m; j++){
				if (todo[j])
					if (scores[i][j] > highestscore){
						highestscore = scores[i][j];
						index = j;
					}
					else if (scores[i][j] == highestscore){
						if (index != -1){
							if (values[i][index] > values[i][j]){
								index = j;
							}
						}
					}
					
			}
			if (index == -1){
				result[i] = -1;
			}
			else if (compatibility[i][index] == 0){
				result[i] = -1;
			}
			else{
				result[i] = index;
				todo[index] = false;
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
	
//	private int compareScore(int [][] scores, int i, int j,int n){
//		int score = 0;
//		if (i == n-1){
//			return 0;
//		}
//		else{
//			for (int z = i+1; i<n; i++ ){
//				score += scores[z][j];
//			}
//			return score;
//		}
//
//		
//	}
	
//	private int highestIndex(int ex, int[] rowToCheck)
//	{
//		int highest = 0;
//		for (int i = 0; i < rowToCheck.length; i++){
//			if (i != ex)
//				if (rowToCheck[i] > highest){
//					highest = rowToCheck[i];
//				}
//		}
//	return highest;
//	}
	
	private int[][] createScores(int n, int m, int[][] compatibility){
		int[][] scores = new int[n][m];
		Tuple best = new Tuple(0,0);
		Tuple secondbest = new Tuple(0,0);
		Tuple newbest = new Tuple(0,0);
		Tuple secondnewbest = new Tuple(0,0);
		for (int i = n-1; i >= 0; i--){
			for (int j = m-1; j >= 0; j--){
				if (i == n-1){
					scores[i][j] = compatibility[i][j];
				}
				else{
					if (best.index != j)
						scores[i][j] = compatibility[i][j] + best.value;
					else scores[i][j] = compatibility[i][j] + secondbest.value;
				}
				if (scores[i][j] > newbest.value)
					newbest = new Tuple(j,scores[i][j]);
				else if (scores[i][j] > secondnewbest.value)
					secondnewbest = new Tuple(j,scores[i][j]);
				
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
