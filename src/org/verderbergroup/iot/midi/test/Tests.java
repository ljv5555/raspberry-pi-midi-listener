package org.verderbergroup.iot.midi.test;

import java.io.File;
import java.util.LinkedHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.verderbergroup.iot.midi.S;

public class Tests
{

	LinkedHashMap<String,String> backupFiles = new LinkedHashMap<String,String>();
	@Test
	public void testConfigAndTransform()
	{
		backupConfig();
		restoreConfig();
		Assert.assertTrue(true);
	}

	
	public void backupConfig()
	{
		
		File folder = new File("/appconfig/mappings");
		for(File f : folder.listFiles())
		{
			File f2 = new File("/tmp/"+f.getName());
			if(f2.exists()){f2.delete();}
			String fc = S.fileToString(f.getAbsolutePath());
			S.stringToFile(fc, f2);
			backupFiles.put(f.getAbsolutePath(),f2.getAbsolutePath());
			f.delete();
			System.out.println("Copied "+f.getAbsolutePath()+" to "+f2.getAbsolutePath());
		}
	}
	public void restoreConfig()
	{	
		for(String p : backupFiles.keySet())
		{
			String fc = S.fileToString(backupFiles.get(p));
			S.stringToFile(fc, new java.io.File(p));
			System.out.println("Restored "+p+" from "+backupFiles.get(p));
		}
	}
	
}
