import java.util.*;
import java.io.*;
import java.net.*;

public class Broker3{
	public static void main(String [] args)
	{
		BrokerClass b1 = new BrokerClass(1000);
		b1.acceptConnection();
		b1.acceptConnection();
		try{
			Thread.sleep(3000);
		}
		catch(Exception e){}
		b1.connectToBroker(450);
		b1.connectToBroker(500);
		while(true){
			b1.acceptConnection();
		}
	}
}