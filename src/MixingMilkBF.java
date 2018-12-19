import java.io.*;
import java.util.Scanner;

/**
 * Created by Willi on 12/16/2018.
 */
public class MixingMilkBF
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		Scanner sc = new Scanner(new BufferedReader(new FileReader("mixmilk.in")));

		int b1 = sc.nextInt();
		int b1M = sc.nextInt();
		int b2 = sc.nextInt();
		int b2M = sc.nextInt();
		int b3 = sc.nextInt();
		int b3M = sc.nextInt();

		Bucket bucket1 = new Bucket(b1, b1M);
		Bucket bucket2 = new Bucket(b2, b2M);
		Bucket bucket3 = new Bucket(b3, b3M);

		for (int i = 0; i < 33; i++)
		{
			bucket1.pourTo(bucket2);
			bucket2.pourTo(bucket3);
			bucket3.pourTo(bucket1);
		}

		bucket1.pourTo(bucket2);

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mixmilk.out")));
		pw.println(bucket1.amt);
		pw.println(bucket2.amt);
		pw.println(bucket3.amt);
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}
}

class Bucket
{
	private final int max;
	public int amt;

	public Bucket(int max, int amt)
	{
		this.max = max;
		this.amt = amt;
	}

	public void pourTo(Bucket other)
	{
		int toPour = Math.min(other.max - other.amt, amt);
		amt -= toPour;
		other.amt += toPour;
	}
}
