import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Willi on 12/17/2018.
 */
public class ConversionV2
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader("convention.in"));
		StringTokenizer sc = new StringTokenizer(br.readLine());

		int numOfCows = Integer.parseInt(sc.nextToken());
		int numOfBusses = Integer.parseInt(sc.nextToken());
		int busSize = Integer.parseInt(sc.nextToken());
		int longestTime = 0;

		sc = new StringTokenizer(br.readLine());

		int[] cowArrivals = new int[numOfCows];

		for (int i = 0; i < numOfCows; i++)
		{
			cowArrivals[i] = Integer.parseInt(sc.nextToken());
		}

		Arrays.sort(cowArrivals);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention.out")));
		pw.print(maxTime(cowArrivals, busSize, numOfBusses));
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}

	static int maxTime(int[] times, int busSize, int busNum)
	{
		if (times.length == 1)
			return 0;
		if (times.length < busNum)
			return 0;
		if (times.length == 2)
			return times[1] - times[0];
		if (times.length == 0)
			return 0;
		// return bruteForceMax(times, busSize, busNum, 0);
		if (minPartitionsNeeded(times.length, busSize) >= busNum)
			return bruteForceMax(times, busSize, busNum, 0);
		int maxDiffLoc = -1;
		int maxDiff = -1;
		for (int i = 0; i < times.length - 1; i++)
		{
			if (times[i + 1] - times[i] > maxDiff)
			{
				maxDiff = times[i + 1] - times[i];
				maxDiffLoc = i;
			}
		}
		int[] first = Arrays.copyOfRange(times, 0, maxDiffLoc + 1);
		int[] second = Arrays.copyOfRange(times, maxDiffLoc + 1, times.length);

		int min = Integer.MAX_VALUE;
		for (int i = minPartitionsNeeded(first.length, busSize);
			 i < times.length - minPartitionsNeeded(second.length, busSize); i++)
		{
			min = Math.min(min, Math.max(maxTime(first, busSize, i), maxTime(second, busSize, busNum - i)));
		}

		return min;
	}

	static int bruteForceMax(int[] times, int busSize, int busNum, int startAt)
	{
		if (startAt == times.length)
			return 0;
		int maxTime = Integer.MAX_VALUE;
		int fstSize = busSize;
		while (minPartitionsNeeded(times.length - startAt - fstSize, busSize) <= busNum - 1
				&& fstSize + startAt - 1 < times.length && fstSize > 0)
		{
			int max = Math.max(
					times[fstSize + startAt - 1] - times[startAt],
					bruteForceMax(times, busSize, busNum - 1, startAt + fstSize));
			maxTime = Math.min(max, maxTime);
			fstSize--;
		}
		return maxTime;
	}

	static int minPartitionsNeeded(int size, int partSize)
	{
		return (int) Math.ceil(((float) size) / ((float) partSize));
	}
}
