package org.verderbergroup.iot.midi;
import static com.pi4j.io.gpio.RaspiPin.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
/**
 * This example code demonstrates how to perform simple state
 * control of a GPIO pin on the Raspberry Pi.
 *
 * @author Robert Savage
 */
public class DigitalInputPiGpio {

	public static final Pin PINS[] = {
		GPIO_00,GPIO_01,GPIO_02,GPIO_03,GPIO_04,GPIO_05,GPIO_06,GPIO_07,GPIO_08,GPIO_09
		,GPIO_10,GPIO_11,GPIO_12,GPIO_13,GPIO_14,GPIO_15,GPIO_16,GPIO_17,GPIO_18,GPIO_19
		,GPIO_21,GPIO_22,GPIO_23,GPIO_24,GPIO_25,GPIO_26,GPIO_27,GPIO_28,GPIO_29
	};
	public static LinkedHashMap<Pin,GpioPinDigitalInput> inputPins = new LinkedHashMap<Pin,GpioPinDigitalInput>();
	
	public static void main(String[] args) {


        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
        String line;
		try
		{
			line = br.readLine();
	        while(line!=null)
	        {
	        	String values[] = line.split("\\s+");
	        	for(int i=0;i<values.length;i++)
	        	{
	        		try{
	        			String s_pinIndex = values[i].replaceAll("[^0-9]","");
	        			if(s_pinIndex.length()>0)
	        			{
	        				int pinIndex = Integer.parseInt(s_pinIndex);
	        				Pin p = PINS[pinIndex];
	        				if(!inputPins.containsKey(p)){inputPins.put(p, gpio.provisionDigitalInputPin(p));}
	        				System.out.println(""+(inputPins.get(p).getState().isHigh()?1:0) );
	        			}
	        			
	        		}catch(Exception ex){ex.printStackTrace();}
	        	}
	        	line = br.readLine();
	        }
		} catch (IOException e)
		{
			e.printStackTrace();
		}
        gpio.shutdown();
    }
}

