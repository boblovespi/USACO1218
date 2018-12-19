import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Willi on 12/16/2018.
 */
public class BackAndForth
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		Scanner sc = new Scanner(new FileReader("backforth.in"));
		HashSet<Integer> bucketsFirst = new HashSet<>(10);
		HashSet<Integer> bucketsSecond = new HashSet<>(10);
		HashSet<Integer> total = new HashSet<>(100);

		for (int i = 0; i < 10; i++)
		{
			int n = sc.nextInt();
			if (!bucketsFirst.add(n))
				bucketsFirst.add(-n);

		}

		for (int i = 0; i < 10; i++)
		{
			int n = sc.nextInt();
			if (!bucketsSecond.add(n))
				bucketsSecond.add(-n);
		}

		// case 1 (nothing changes):
		total.add(0);

		// case 2 (uses same bucket once):
		total.addAll(getPossibleSumsM(bucketsFirst, bucketsSecond));

		// case 3 (uses dif. bucket):
		total.addAll(getPossibleSumsM(getPossibleSums(bucketsFirst), getPossibleSums(bucketsSecond)));

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("backforth.out")));
		pw.print(total.size());
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}

	static HashSet<Integer> getPossibleSums(HashSet<Integer> first)
	{
		HashSet<Integer> possibleSums = new HashSet<>(100);

		Integer[] array = first.toArray(new Integer[first.size()]);

		for (int i = 0; i < array.length - 1; i++)
		{
			for (int j = i + 1; j < array.length; j++)
			{
				possibleSums.add(Math.abs(array[i]) + Math.abs(array[j]));
			}
		}

		return possibleSums;
	}

	static HashSet<Integer> getPossibleSumsM(HashSet<Integer> first, HashSet<Integer> second)
	{
		HashSet<Integer> possibleSums = new HashSet<>(100);

		Iterator<Integer> firstI = ((HashSet<Integer>) first.clone()).iterator();
		while (firstI.hasNext())
		{
			int x = Math.abs(firstI.next());
			Iterator<Integer> secondI = ((HashSet<Integer>) second.clone()).iterator();
			while (secondI.hasNext())
				possibleSums.add(x - Math.abs(secondI.next()));
		}

		return possibleSums;
	}
}
