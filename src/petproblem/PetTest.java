package petproblem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.TreeSet;

import junit.framework.Assert;

/**
 * Tests conformance of a Solver to constraints.
 * May not test whether a solution returns a correct (optimal) answer.
 */
@RunWith(Parameterized.class)
public class PetTest {
	IPetproblemSolver ps;
	
	public PetTest(IPetproblemSolver ps)
	{
		this.ps = ps;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] solvers = new Object[][] {
				{ new RandomSolver()
				/* Add your own solution here! */
				//, new LastnameSolver()
				}
		};
		return Arrays.asList(solvers);
	}
	
	/**
	 * @param n The number of children.
	 * @param m The number of pets.
	 * @param result
	 */
	private static void checkConformance(int n, int m, final int[] result)
	{
		Assert.assertEquals("There should not suddenly be more or less children.", n, result.length);
		
		TreeSet<Integer> pets = new TreeSet<Integer>();
		for(int i = 0; i < result.length; i++)
		{
			Assert.assertTrue("Pet should exist, or be a no-pet (-1).", result[i] >= -1 && result[i] < m);
			Assert.assertTrue("Pet should not be assigned to multiple children.", result[i] == -1 || pets.add(result[i]));
		}
	}
	
	private static int[][] generateTestMatrix(int n, int m, int maxValue)
	{
		int[][] result = new int[n][m];
		
		Random r = new Random(n * (m+1));
		for(int n_i = 0; n_i < n; n_i++)
			for(int m_i = 0; m_i < m; m_i++)
				result[n_i][m_i] = r.nextInt(maxValue+1);
		
		return result;
	}
	
	private static int computeCompatibility(int[][] matrix, int[] pairing)
	{
		int sum = 0;
		for(int n_i = 0; n_i < pairing.length; n_i++)
		{
			int m_i = pairing[n_i];
			if(m_i == -1)
				continue;
			
			sum += matrix[n_i][m_i];
		}
		
		return sum;
	}
	
	/**
	 * Test whether Solver conforms for a semi-random test set.
	 * Note that these tests do not test whether a solution is optimal.
	 * (If it could do that the test would be a Solver by itself)
	 */
	private void performGeneratedTest(int n, int m)
	{
		int[][] compatibility = generateTestMatrix(n, m, 1);
		int[] result = ps.solve(n, m, compatibility);
		checkConformance(n, m, result);
	}
	
	@Test(timeout = 2000)
	public void testExample() {
		int n = 5, m = 4;
		int[][] compatibility = {
			{1, 1, 0, 0},
			{0, 1, 1, 1},
			{0, 1, 0, 0},
			{1, 0, 1, 0},
			{0, 0, 1, 1}
		};
		
		int[] result = ps.solve(n, m, compatibility);
		checkConformance(n, m, result);
		Assert.assertEquals("Does not give a correct answer", 4, computeCompatibility(compatibility, result));
	}
	
	@Test(timeout = 2000)
	public void testGeneratedSmall() {
		performGeneratedTest(8, 10);
		performGeneratedTest(10, 8);
	}
	
	@Test(timeout = 2000)
	public void testGeneratedMedium() {
		performGeneratedTest(23, 20);
		performGeneratedTest(20, 23);
	}
	
	@Test(timeout = 5000)
	public void testGeneratedLarge() {
		performGeneratedTest(121, 130);
		performGeneratedTest(130, 121);
	}
	
	/**
	 * This timeout is just a suggestion,
	 * your solution is not incorrect if it takes longer,
	 * or if it is not capable to finish in a reasonable amount of time.
	 */
	@Test(timeout = 5000) 
	public void testGeneratedTerrible() {
		performGeneratedTest(2910, 3102);
		performGeneratedTest(3102, 2910);
	}
}