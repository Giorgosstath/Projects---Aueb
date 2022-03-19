import java.util.*;
import java.io.*;
import java.net.*;


public class AppNode2{
	public static void main(String [] args)
	{
		try{
			ChannelName channe2 = new ChannelName("channel2");
			PublisherClass p = new PublisherClass(channe2);
			p.start();
			ConsumerClass c = new ConsumerClass(channe2);
			c.start();
		}
		catch(Exception e){System.out.println(e);}
	}
}