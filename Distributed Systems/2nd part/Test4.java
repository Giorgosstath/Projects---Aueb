import java.util.*;
import java.io.*;
import java.net.*;

public class Test4{
	public static void main(String [] args)
	{		
		Thread t2 = new PublisherClass(new ChannelName("vw"));
		
		
		try{
			t2.start();
		}
		catch(Exception e){}
	}
}