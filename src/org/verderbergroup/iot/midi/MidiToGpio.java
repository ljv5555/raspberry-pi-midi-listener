package org.verderbergroup.iot.midi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MidiToGpio
{


	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		S s = new S();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			String line = null;
			try
			{
				line = br.readLine();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			if (line == null)
			{
				break;
			}
			String lineParts[] = line.trim().split("\\s+");
			int iparts[] = new int[4];
			if (lineParts.length >= 4)
			{
				line = "" + lineParts[lineParts.length - 4] + " "
						+ lineParts[lineParts.length - 3] + " "
						+ lineParts[lineParts.length - 2] + " "
						+ lineParts[lineParts.length - 1];
				/*
				 * for(int i=0;i<12;i++) { String si = ""+i;
				 * while(si.length()<2){si="0"+si;} // TODO: set pin[si] to high
				 * / low
				 * 
				 * }
				 */
				String[] onRelayIds = s
						.getKeysForRangesMatchingOnValues(iparts);
				String[] offRelayIds = s
						.getKeysForRangesMatchingOffValues(iparts);

				// on overrides off
				List<String> ori = new ArrayList<String>();
				for (String id : offRelayIds)
				{
					if( !Arrays.asList(onRelayIds).contains(id) )
					{
						ori.add(id);
					}
				}
				offRelayIds = ori.toArray(new String[0]);
				for(String id : onRelayIds)
				{
					System.out.println(id+" "+1);
				}
				for(String id : offRelayIds)
				{
					System.out.println(id+" "+0);
				}
			}

		}

	}

}
