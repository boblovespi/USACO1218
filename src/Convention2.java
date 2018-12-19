import java.io.*;
import java.util.*;

/**
 * Created by Willi on 12/17/2018.
 */
public class Convention2
{
	public static void main(String[] args) throws Exception
	{
		long time = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
		StringTokenizer sc = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(sc.nextToken());

		Cow[] cows = new Cow[n];

		for (int i = 0; i < n; i++)
		{
			sc = new StringTokenizer(br.readLine());
			cows[i] = new Cow(Integer.parseInt(sc.nextToken()), Integer.parseInt(sc.nextToken()), i);
		}

		Arrays.sort(cows, Comparator.comparingInt(a -> a.timeArrive));

		Cow currentCow = null;
		TreeSet<Cow> waiting = new TreeSet<>(
				Comparator.comparingInt(a -> a.seniority)); // looked at the javadocs for this
		TreeSet<CowEvent> events = new TreeSet<>((a, b) ->
		{
			if (a.timeArrive == b.timeArrive)
			{
				if (a.isArrive)

					return b.isArrive ? a.cow.seniority - b.cow.seniority : 1;
				else
					return b.isArrive ? -1 : a.cow.seniority - b.cow.seniority;
			}
			return a.timeArrive - b.timeArrive;
		});
		Cow cow;
		// iterate over all the cows and put in when they arrive to the
		for (int i = 0; i < n; i++)
		{
			cow = cows[i];
			events.add(new CowEvent(cow.seniority, cow.timeArrive, true, cow));
		}

		int maxTimeWaited = 0;

		CowEvent event;
		// go through the events and process them
		while (!events.isEmpty())
		{
			event = events.pollFirst();
			// if a cow is arriving
			if (event.isArrive)
			{
				// if no cow is eating
				if (currentCow == null)
				{
					events.add(
							new CowEvent(event.cow.seniority, event.timeArrive + event.cow.timeEat, false, event.cow));
					currentCow = event.cow;
				} else
				{
					waiting.add(event.cow);
				}
			} else
			{
				if (!waiting.isEmpty())
				{
					Cow first = waiting.pollFirst();
					int currentTime = event.timeArrive;
					if (maxTimeWaited < currentTime - first.timeArrive)
						maxTimeWaited = currentTime - first.timeArrive;
					events.add(new CowEvent(first.seniority, currentTime + first.timeEat, false, first));
					currentCow = first;
				} else
				{
					currentCow = null;
				}
			}
		}

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("convention2.out")));
		pw.print(maxTimeWaited);
		pw.close();

		System.out.println(System.currentTimeMillis() - time);
	}
}

class Cow
{
	int timeArrive;
	int timeEat;
	int seniority;
	int timeStartedWaiting = -1;

	public Cow(int timeArrive, int timeEat, int seniority)
	{
		this.timeArrive = timeArrive;
		this.timeEat = timeEat;
		this.seniority = seniority;
	}
}

class CowEvent
{
	int seniority;
	int timeArrive;
	boolean isArrive;
	Cow cow;

	public CowEvent(int seniority, int timeArrive, boolean isArrive, Cow cow)
	{
		this.seniority = seniority;
		this.timeArrive = timeArrive;
		this.isArrive = isArrive;
		this.cow = cow;
	}
}
