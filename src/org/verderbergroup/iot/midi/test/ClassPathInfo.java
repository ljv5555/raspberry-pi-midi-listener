package org.verderbergroup.iot.midi.test;

import java.io.File;





public class ClassPathInfo
{
	public static void main(String args[])
	{
	}
	public static String getClasspath(){
		System.out.println(new File("trunk/lib").getAbsolutePath());
		File f = new File("trunk/lib");
		String rtn = ""+f.getParentFile().getAbsolutePath()+File.separator+"src"
				+File.pathSeparator
				+f.getParentFile().getAbsolutePath()+File.separator+"bin"
				;
		try{
			for(File file : f.listFiles())
			{
				if(rtn.length()>0){rtn+=File.pathSeparator;}
				rtn+=file.getAbsolutePath();
			}
		}catch(Exception ex){}
		return rtn;
	}
}