package petproblem;

public interface IPetproblemSolver {
	/**
	 * Solves the Pet Problem.
	 * @param n The number of children.
	 * @param m The number of pets.
	 * @param compatibility Matrix containing for every element c[i][j] the value of f(i, j). (See assignment) 
	 *                      For the base case contains either values "0" or "1".
	 *                      For the bonus case might contain any non-negative number.
	 * @return An array with n elements, containing the index the pet assigned to each child.
	 *         Thus result[i] = j means "child i gets pet j".
	 *         If a child gets no pet, assign the value "-1".
	 */
	int[] solve(int n, int m, final int[][] compatibility);
}
