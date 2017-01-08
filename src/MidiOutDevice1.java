import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.ShortMessage;



public class MidiOutDevice1
{

	public MidiOutDevice1()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			MidiDevice device = MidiSystem.getMidiDevice(MidiInDevice1.MidiCommon.getMidiDeviceInfo(1));
			device.open();
			String line = br.readLine();
			while(line!=null)
			{
				String[] lineparts = line.trim().split("\\s+");
				if(lineparts.length==4)
				{
					ShortMessage sm = new ShortMessage();
					sm.setMessage(Integer.parseInt(lineparts[1]), Integer.parseInt(lineparts[0]), Integer.parseInt(lineparts[2]), Integer.parseInt(lineparts[3]));
					device.getTransmitter().getReceiver().send(sm, -1);
				}
				line = br.readLine();
			}
			device.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new MidiOutDevice1();

	}

}
