import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Willi on 12/16/2018.
 */
public class MixingMilk
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		Scanner sc = new Scanner(new FileReader("mixmilk.in"));

		int b1 = sc.nextInt();
		int b1M = sc.nextInt();
		int b2 = sc.nextInt();
		int b2M = sc.nextInt();
		int b3 = sc.nextInt();
		int b3M = sc.nextInt();

		int mSum = b1M + b2M + b3M;

		// case 1: (all different)
		if (b1 != b2 && b2 != b3 && b1 != b3)
		{
			b2M = Math.min(mSum, b2);
			b1M = Math.min(mSum - b2M, b1);
			b3M = Math.min(mSum - b2M - b1M, b3);
		}
		// case 2: (all same)
		else if (b1 == b2 && b2 == b3)
		{

		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mixmilk.out")));
		pw.println(b1M);
		pw.println(b2M);
		pw.println(b3M);
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}
}
