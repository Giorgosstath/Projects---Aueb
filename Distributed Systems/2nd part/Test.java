import java.util.*;
import java.io.*;
import java.net.*;

public class Test{
	public static void main(String [] args)
	{
		
		BrokerClass b1 = new BrokerClass(100);

		try{
			b1.connect(680);
			b1.connect(1100);
			//b1.acceptConnectionPublisher();
			b1.start();
			b1.acceptChanges();
			//b1.acceptConnectionConsumer();
			//b1.acceptRegisterConsumer();
			//b1.sendQuery();
			//b1.pull("a");
			//b1.sendConsumer("a");
		}
		catch(Exception e){}
	}
}