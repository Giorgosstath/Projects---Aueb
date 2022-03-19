import java.util.*;
import java.io.*;
import java.net.*;

public class Test1{
	public static void main(String [] args)
	{
		BrokerClass b2 = new BrokerClass(1100);

		try{
			b2.connect(680);
			b2.acceptConnectionBroker();
			//b2.acceptConnectionPublisher();
			b2.start();
			System.out.println("testing");
			b2.acceptChanges();
			//b2.acceptConnectionConsumer();
			//b2.acceptRegisterConsumer();
			//b2.sendQuery();
			//b2.pull("a");
			//b2.sendConsumer("a");
		}
		catch(Exception e){}
	}
}