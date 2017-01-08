package org.verderbergroup.iot.midi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SortedSet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


// org.verderbergroup.iot.midi;

public class S
{

	public LinkedHashMap<String, List<String>> getOnRangesByRelayId()
	{
		return onRangesByRelayId;
	}

	public void setOnRangesByRelayId(
			LinkedHashMap<String, List<String>> onRangesByRelayId)
	{
		this.onRangesByRelayId = onRangesByRelayId;
	}

	public LinkedHashMap<String, List<String>> getOffRangesByRelayId()
	{
		return offRangesByRelayId;
	}

	public void setOffRangesByRelayId(
			LinkedHashMap<String, List<String>> offRangesByRelayId)
	{
		this.offRangesByRelayId = offRangesByRelayId;
	}
	private LinkedHashMap<String,List<String>> onRangesByRelayId = null;
	private LinkedHashMap<String,List<String>> offRangesByRelayId = null;
	
	public S()
	{
		LinkedHashMap<String,List<String>> onRangeMap = new LinkedHashMap<String,List<String>>();
		LinkedHashMap<String,List<String>> offRangeMap = new LinkedHashMap<String,List<String>>();
		String configPath[] = {"/appconfig/mappings/",".on",".off"};
		for(int i=0;i<=11;i++)
		{
			String iString = ""+i;
			while(iString.length()<2){iString="0"+iString;}
			onRangeMap.put(iString, new ArrayList<String>());
			offRangeMap.put(iString, new ArrayList<String>());
			String cpOn = configPath[0]+iString+configPath[1];
			String cpOff = configPath[0]+iString+configPath[2];
			try
			{
				if((new File(cpOn)).exists())
				{
					BufferedReader brOn  = new BufferedReader(new FileReader(cpOn));
					while(true)
					{
						String line = brOn.readLine();
						if(line==null){break;}
						line = line.trim();
						if(!"".equals(line)){onRangeMap.get(i).add(line);}
					}
					brOn.close();
				}
				if((new File(cpOff)).exists())
				{
					BufferedReader brOff  = new BufferedReader(new FileReader(cpOff));
					while(true)
					{
						String line = brOff.readLine();
						if(line==null){break;}
						line = line.trim();
						if(!"".equals(line)){offRangeMap.get(i).add(line);}
					}
					brOff.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		offRangesByRelayId = offRangeMap;
		onRangesByRelayId = onRangeMap;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new S();

	}

	public static String jseval(String js)
	{
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine se = sem.getEngineByName("JavaScript");
		String rtn = "";
		try{rtn+=""+se.eval(js);}catch(Exception ex){ex.printStackTrace();}
		return rtn;
	}
	public static boolean inRange(int i, String range)
	{
		boolean rtn = true;
		try{
			if(range.split("-").length>1)
			{
				rtn = rtn && (Integer.parseInt(range.split("-")[0])<=i) && (Integer.parseInt(range.split("-")[1])>=i);
			}
		}catch(Exception ex){ex.printStackTrace();rtn = false;}
		return rtn;
	}
	public static int[] splitInts(String s)
	{
		String[] s2 = s.trim().split("\\s+");
		int[] rtn = new int[s2.length];
		for(int i=0;i<s2.length;i++)
		{
			rtn[i]=Integer.parseInt(s2[i]);
		}
		return rtn;
	}
	public boolean allInRanges(int values[], String ranges[])
	{
		boolean rtn = true;
		for(int i=0;i<values.length && i<ranges.length;i++)
		{
			rtn = rtn && inRange(values[i], ranges[i]);
		}
		return true;
	}
	public String[] getKeysForRangesMatchingOnValues(int[] values)
	{
		LinkedHashSet<String> rtn = new LinkedHashSet<String>();
		for(String relayId : getOnRangesByRelayId().keySet())
		{
			List<String> rangesStrings = getOnRangesByRelayId().get(relayId);
			for(String rangesString : rangesStrings)
			{
				if(allInRanges(values,rangesString.split("\\s+")))
				{
					rtn.add(relayId);
				}
			}
		}
		return rtn.toArray(new String[0]);
	}
	public String[] getKeysForRangesMatchingOffValues(int[] values)
	{
		LinkedHashSet<String> rtn = new LinkedHashSet<String>();
		for(String relayId : getOffRangesByRelayId().keySet())
		{
			List<String> rangesStrings = getOffRangesByRelayId().get(relayId);
			for(String rangesString : rangesStrings)
			{
				if(allInRanges(values,rangesString.split("\\s+")))
				{
					rtn.add(relayId);
				}
			}
		}
		return rtn.toArray(new String[0]);
	}
}
