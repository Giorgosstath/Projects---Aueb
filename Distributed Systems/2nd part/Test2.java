import java.util.*;
import java.io.*;
import java.net.*;

public class Test2{
	public static void main(String [] args)
	{		
		BrokerClass b3 = new BrokerClass(680);
		try{
			b3.acceptConnectionBroker();
			b3.acceptConnectionBroker();
			//b3.acceptConnectionPublisher();
			b3.start();
			b3.acceptChanges();
			/*b3.acceptConnectionConsumer();
			b3.acceptRegisterConsumer();
			b3.sendQuery();
			b3.pull("a");
			b3.sendConsumer("a");*/
		}
		catch(Exception e){}
	}
}