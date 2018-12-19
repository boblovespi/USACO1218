import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Willi on 12/17/2018.
 */
public class Convention
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader("convention.in"));
		StringTokenizer sc = new StringTokenizer(br.readLine());

		int numOfCows = Integer.parseInt(sc.nextToken());
		int numOfBusses = Integer.parseInt(sc.nextToken());
		int busSize = Integer.parseInt(sc.nextToken());

		sc = new StringTokenizer(br.readLine());

		int[] cowArrivals = new int[numOfCows];

		for (int i = 0; i < numOfCows; i++)
		{
			cowArrivals[i] = Integer.parseInt(sc.nextToken());
		}

		Arrays.sort(cowArrivals);
		ArrayList<ArrInfo> longestTime = new ArrayList<>(numOfCows);
		int firstCowArrivalTime = 0;
		int lastCowArrivalTime = 0;

		for (int i = 0; i < numOfBusses; i++)
		{
			// make sure is not an edge case (we can still fit the whole entire bus):
			if (i * busSize + busSize - 1 < numOfCows - 1)
			{
				firstCowArrivalTime = cowArrivals[i * busSize];
				// when does the last cow arrive
				lastCowArrivalTime = cowArrivals[i * busSize + busSize - 1];

				//if (longestTime < lastCowArrivalTime - firstCowArrivalTime)
				//	longestTime = lastCowArrivalTime - firstCowArrivalTime;
			} else // is an edge case, need logic
			{
				firstCowArrivalTime = cowArrivals[i * busSize];
				// when does the last cow arrive
				lastCowArrivalTime = cowArrivals[numOfCows - 1];

				//if (longestTime < lastCowArrivalTime - firstCowArrivalTime)
				//	longestTime = lastCowArrivalTime - firstCowArrivalTime;
				break;
			}

		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention.out")));
		pw.print(longestTime);
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}
}

class ArrInfo
{
}
