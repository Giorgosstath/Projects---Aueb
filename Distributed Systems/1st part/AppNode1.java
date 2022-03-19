import java.util.*;
import java.io.*;
import java.net.*;


public class AppNode1{
	public static void main(String [] args)
	{
		try{
			ChannelName channel = new ChannelName("channel1");
			PublisherClass p = new PublisherClass(channel);
			p.start();
			ConsumerClass c = new ConsumerClass(channel);
			c.start();
		}
		catch(Exception e){e.printStackTrace();}
	}
}