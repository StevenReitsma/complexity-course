package petproblem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SumSolver implements IPetproblemSolver {

	public class SimpleEntryValueComparator<T extends Entry> implements Comparator<T>
	{

		@Override
		public int compare(T arg0, T arg1)
		{
			if (!(arg0 instanceof Entry))
				return -2;
			if (!(arg1 instanceof Entry))
				return -3;
			
			Entry a = (Entry) arg0;
			Entry b = (Entry) arg1;
			
			if (a.value < b.value)
				return -1;
			else if (a.value > b.value)
				return 1;
			else
				return 0;
		}
		
	}
	
	public class Entry
	{
		public int key;
		public int value;
		
		public Entry(int key, int value)
		{
			this.key = key;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object other)
		{
			if (other instanceof Entry)
				return key == ((Entry)other).key;
			
			return false;
		}
		
		@Override
		public String toString()
		{
			return key + "=" + value;
		}
	}
	
	@Override
	public int[] solve(int n, int m, int[][] compatibility)
	{
		int[] result = new int[n];
		
		// Set to -1 initially
		for (int i = 0; i < result.length; i++)
			result[i] = -1;
		
		// Sum rows and columns
		PriorityQueue<Entry> row_sums = new PriorityQueue<>(1, new SimpleEntryValueComparator<>());
		PriorityQueue<Entry> col_sums = new PriorityQueue<>(1, new SimpleEntryValueComparator<>());
		
		int[] running_row_sums = new int[n];
		int[] running_col_sums = new int[m];
		
		// Sum
		for (int j = 0; j < m; j++)
		{
			for (int i = 0; i < n; i++)
			{
				running_row_sums[i] += compatibility[i][j];
				running_col_sums[j] += compatibility[i][j];
			}
		}
		
		// Add running sums to queues
		for (int i = 0; i < running_row_sums.length; i++)
			row_sums.add(new Entry(i, running_row_sums[i]));
		
		for (int i = 0; i < running_col_sums.length; i++)
			col_sums.add(new Entry(i, running_col_sums[i]));
		
		// Select smallest sum (in rows/cols)
		// Get 1-indices for that sum's index
		// Select smallest from those indices
		
		while (!row_sums.isEmpty() && !col_sums.isEmpty())
		{
			Entry lowestIndexRow = row_sums.peek();
			Entry lowestIndexCol = col_sums.peek();
			boolean useRow = false;
			
			if (lowestIndexRow.value < lowestIndexCol.value)
				useRow = true;
			
			// Get 1-indices
			if (useRow)
			{
				row_sums.remove();

				// Get smallest
				int smallestValue = Integer.MAX_VALUE;
				Entry smallestKey = null;
				
				for (Entry e : col_sums)
				{
					if (e.value < smallestValue && compatibility[lowestIndexRow.key][e.key] == 1)
					{
						smallestKey = e;
						smallestValue = e.value;
					}
				}

				// No candidates in this list, continue
				if (smallestKey == null)
					continue;
				
				int col_index = smallestKey.key;
				col_sums.remove(smallestKey);
				
				result[lowestIndexRow.key] = col_index;
			}
			else
			{
				col_sums.remove();

				// Get smallest
				int smallestValue = Integer.MAX_VALUE;
				Entry smallestKey = null;
				
				for (Entry e : row_sums)
				{
					if (e.value < smallestValue && compatibility[e.key][lowestIndexCol.key] == 1)
					{
						smallestKey = e;
						smallestValue = e.value;
					}
				}
				
				// No candidates in this list, continue
				if (smallestKey == null)
					continue;

				int row_index = smallestKey.key;
				row_sums.remove(smallestKey);
				
				result[row_index] = lowestIndexCol.key;
			}
		}
		
		int score = 0;
		
		for (int i = 0; i < result.length; i++)
			score += result[i] == -1 ? 0 : 1;
		
		System.out.println("Score: " + score);
		
		return result;
	}

}
