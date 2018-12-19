import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Willi on 12/17/2018.
 */
public class MooyoMooyo
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader("mooyomooyo.in"));
		StringTokenizer sc = new StringTokenizer(br.readLine());

		int size = Integer.parseInt(sc.nextToken());
		int minConnected = Integer.parseInt(sc.nextToken());

		int[][] grid = new int[size][10];

		for (int i = size - 1; i >= 0; i--)
		{
			String s = br.readLine();
			for (int j = 0; j < 10; j++)
			{
				grid[i][j] = Integer.parseInt(s.charAt(j) + "");
			}
		}
		int[] maxHeight = { size - 1 }; // ref var

		// fall(grid, maxHeight);
		doMove(grid, minConnected, maxHeight[0]);

		while (fall(grid, maxHeight))
		{
			doMove(grid, minConnected, maxHeight[0]);
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mooyomooyo.out")));
		for (int i = size - 1; i >= 0; i--)
		{
			for (int j = 0; j < 10; j++)
			{
				pw.print(actual(grid[i][j]));
			}
			pw.println();
		}
		pw.close();

		System.out.println(System.currentTimeMillis() - time);

		// element of -1 = grid is empty column
	}

	private static String actual(int i)
	{
		if (i < 0)
			return "0";
		return "" + i;
	}

	static boolean fall(int[][] grid, int[] maxHeight)
	{
		boolean hasMoved = false;
		for (int i = 0; i < 10; i++)
		{
			// if (grid[0][i] == 0)
			{
				int displacement = 0;
				while (displacement < grid.length)
				{
					if (grid[displacement][i] <= 0)
					{
						int j = displacement;
						while (grid[j][i] <= 0)
						{
							j++;
							if (j == grid.length)
								break;
						}

						if (j == grid.length);
							//grid[0][i] = -1;

						else
						{
							for (int k = j; k < grid.length; k++)
							{
								grid[k - j + displacement][i] = grid[k][i];
								grid[k][i] = 0;
							}
							hasMoved = true;
						}
					}
					++displacement;
				}
			}
		}
		for (int i = maxHeight[0]; i > 0; i--)
		{
			boolean isAllZero = true;
			for (int j = 0; j < 10; j++)
			{
				if (grid[i][j] > 0)
				{
					isAllZero = false;
					break;
				}
			}
			if (!isAllZero)
			{
				maxHeight[0] = Math.min(maxHeight[0], grid.length - 1);
			}
		}
		return hasMoved;
	}

	static void doMove(int[][] grid, int minSize, int maxHeight)
	{
		HashSet<Loc> beenTo = new HashSet<>(1000);
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (beenTo.contains(new Loc(i, j)))
					continue;
				if (grid[i][j] == 0)
					continue;
				beenTo.addAll(removeConnected(grid, minSize, new Loc(i, j)));
			}
		}
	}

	static HashSet<Loc> removeConnected(int[][] grid, int minSize, Loc loc)
	{
		HashSet<Loc> locs = removeConnectedR(grid, minSize, loc, new HashSet<>(1000), grid[loc.i][loc.j]);
		if (locs.size() >= minSize)
			for (Loc loca : locs)
			{
				grid[loca.i][loca.j] = 0;
			}
		return locs;
	}

	static HashSet<Loc> removeConnectedR(int[][] grid, int minSize, Loc loc, HashSet<Loc> beenTo, int toMatch)
	{
		beenTo.add(loc);
		// going north
		if (loc.i + 1 < grid.length)
		{
			Loc north = new Loc(loc.i + 1, loc.j);
			if (!beenTo.contains(north))
			{
				if (grid[north.i][north.j] == toMatch)
				{
					removeConnectedR(grid, minSize, north, beenTo, toMatch);
				}
			}
		}
		// going south
		if (loc.i - 1 > 0)
		{
			Loc south = new Loc(loc.i - 1, loc.j);
			if (!beenTo.contains(south))
			{
				if (grid[south.i][south.j] == toMatch)
				{
					removeConnectedR(grid, minSize, south, beenTo, toMatch);
				}
			}
		}
		// going left
		if (loc.j - 1 > 0)
		{
			Loc left = new Loc(loc.i, loc.j - 1);
			if (!beenTo.contains(left))
			{
				if (grid[left.i][left.j] == toMatch)
				{
					removeConnectedR(grid, minSize, left, beenTo, toMatch);
				}
			}
		}
		// going right
		if (loc.j + 1 < grid[0].length)
		{
			Loc right = new Loc(loc.i, loc.j + 1);
			if (!beenTo.contains(right))
			{
				if (grid[right.i][right.j] == toMatch)
				{
					removeConnectedR(grid, minSize, right, beenTo, toMatch);
				}
			}
		}
		return beenTo;
	}
}

class Loc
{
	int i;
	int j;

	public Loc(int i, int j)
	{
		this.i = i;
		this.j = j;
	}

	@Override
	public int hashCode()
	{
		return i * 9001 + j;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Loc)
			return ((Loc) obj).i == i && ((Loc) obj).j == j;
		return false;
	}
}