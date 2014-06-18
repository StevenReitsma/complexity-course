package petproblem;

import java.util.ArrayList;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

public class WorstKaasSolver implements IPetproblemSolver{

	@Override
	public int[] solve(int n, int m, int[][] compatibility) {
		
		int highscore = 0;
		ICombinatoricsVector<Coordinate> bestcomb = null;
		System.out.println(m);
		int smallest = Math.min(n,m);
		Coordinate[] coordinates = new Coordinate[n*m];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++){
				coordinates[i*m+j] = new Coordinate(i,j);
			}
		ICombinatoricsVector<Coordinate> originalVector = Factory.createVector(coordinates);
		Generator<Coordinate> gen = Factory.createSimpleCombinationGenerator(originalVector, smallest);
		int countertje = 0;
		//System.out.println("Number of generated ojects; " + Long.toString(gen.getNumberOfGeneratedObjects()));
		for (ICombinatoricsVector<Coordinate> combination : gen) {
			      //System.out.println(combination);
			      //System.out.println("hoi");
			boolean[] childvalues = new boolean[n];
			boolean[] petvalues = new boolean[m];
			int score = 0;
			for (int i = 0; i < n; i++){
				childvalues[i] = false;
			}
			for (int i = 0; i < m; i++){
				petvalues[i] = false;
			}
			for(int i = 0; i < smallest; i++){
				int x = combination.getValue(i).x;
				int y = combination.getValue(i).y;
				if (!childvalues[combination.getValue(i).x])
					if(!petvalues[combination.getValue(i).y])
						score += compatibility[x][y];
				childvalues[i] = true;
				petvalues[i] = true;
			}
			if (score > highscore){
				highscore = score;
				bestcomb = combination;
			}
			
			if (countertje % 10000==0)
				System.out.println("Counter: " + countertje / 29000000000.0);
			countertje++;
		}
		
//		int bestScore = 0;
//		for(int i = 0; i < m; i++){
//			int tempScore = 0;
//			int tempSolve[] = new int[n];
//			for (int j = 0; j < n-1; j++){
//				for (int z = 0; z < m; z++)
//				if (compatibility[j][i] == 1){
//					
//				}
//			}
//		}
		System.out.println(bestcomb + " score of: " + highscore);
		
		int[] results= new int [n];
		for (int i = 0; i < n; i++){
			results[i] = -1;
		}
		for (Coordinate c : bestcomb){
			results[c.x] = c.y;
		}
		return results;
	}

}
