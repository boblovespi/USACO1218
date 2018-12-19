import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Willi on 12/16/2018.
 */
public class BucketList
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		Scanner sc = new Scanner(new FileReader("blist.in"));

		int n = sc.nextInt();

		int[] bucketsNeeded = new int[1000];
		Arrays.fill(bucketsNeeded, 0);

		for (int i = 0; i < n; i++)
		{
			int start = sc.nextInt();
			int end = sc.nextInt();
			int amount = sc.nextInt();

			for (int j = start - 1; j < end; j++)
			{
				bucketsNeeded[j] += amount;
			}
		}

		int max = 0;

		for (int i = 0; i < 1000; i++)
		{
			if (bucketsNeeded[i] > max)
				max = bucketsNeeded[i];
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("blist.out")));
		pw.print(max);
		pw.close();

		System.out.println(System.currentTimeMillis() - time);

	}
}
