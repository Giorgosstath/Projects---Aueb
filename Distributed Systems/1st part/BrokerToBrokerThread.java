import java.util.*;
import java.math.BigInteger;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.io.File;

public class BrokerToBrokerThread extends Thread{
	Socket socket = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	BrokerClass broker;
	ArrayList<String> myHashtags = new ArrayList<String>();
	
	public BrokerToBrokerThread(Socket s, BrokerClass b){
		this.socket = s;
		this.broker = b;
		try{
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e){System.out.println(e);}
	}
	
	public void exchangeInfo(){
		try{
			//out.writeObject(broker.brokersInfo.get(0));
			//out.flush();
			
			broker.brokersInfo.add((BrokerInfo)in.readObject());
		}
		catch(Exception e){System.out.println(e);}
	}
	
	public void receiveHashtags()
	{
		try{
			ArrayList<String> temp = (ArrayList<String>)in.readObject();
			for(String s : temp){
				if(broker.hashTopic(s) == broker.port){
					myHashtags.add(s);
				}
			}
			System.out.println("My own hashtags: "+myHashtags);
		}
		catch(Exception e){}
	}
	
	
	public void run(){
		try{
			out.writeObject("Broker Thread");
			out.flush();
		}
		catch(Exception e){System.out.println(e);}
		
		exchangeInfo();
		
		while(true){
			receiveHashtags();
		}
	}
}