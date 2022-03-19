import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.*;
import java.nio.file.Files;
import java.io.File;


public class BrokerThread extends Thread{
	Socket socket = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	BrokerClass broker;
	byte[] chunk=null;
	ChannelName channel;
	int size;
	static ArrayList<byte[]> videoToSend = new ArrayList<>();
	static boolean afterPull = false;
	boolean startOfDelivery = true;
	static boolean afterReceiveHash = false;
	static String query = "";
	int broker_port;
	int correct_port;
	static ArrayList<String> receivedHashtags = new ArrayList<String>();
	
	public BrokerThread(Socket socket,BrokerClass b){
		this.socket = socket;
		this.broker = b;
		try{
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e){System.out.println(e);}
		channel = null;
	}
	
	public void pull(String txt)
	{
		try {	
			size=in.readInt();
			System.out.println("Pulling a video...");
			int counter=0;
			while(counter<size) {
				broker.chunk = (byte[]) in.readObject();
				videoToSend.add(broker.chunk);
				counter++;
			}
			afterPull = true;
			System.out.println("Pull completed.");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void sendConsumer(ArrayList<byte[]> chunks)
	{
		try{
			out.writeInt(chunks.size());
			System.out.println("Sending a video...");
			Thread.sleep(2000);
			for (byte[] i : chunks ) {
				out.writeObject(i);
				out.flush();
			}
			System.out.println("Video sent.");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void changeInfo(){
		try{
			//broker.brokersInfo.add((BrokerInfo)in.readObject());
			
			out.writeObject(broker.brokersInfo.get(0));
			out.flush();
		}
		catch(Exception e){System.out.println(e);}
	}
	
	public void sendHashtags()
	{
		try{
			ArrayList<String> list2 = new ArrayList<String>();
			
			for(String s : receivedHashtags)
			{
				int hash = broker.hashTopic(s);
				if(hash==broker.port && !broker.hashtags.contains(s))
					broker.hashtags.add(s);
				else
					list2.add(s);
			}
			out.writeObject(list2);
			out.flush();

			System.out.println("My own hashtags: "+broker.hashtags);			
		}
		catch(Exception e){}
	}
	
	
	public void run(){
		try{
			String input_message = (String)in.readObject();
				switch(input_message){
					case "Publisher hashtags":
						out.writeObject(broker.brokersInfo);
						out.flush();
						
						channel = (ChannelName) in.readObject();
						while(true){
							receivedHashtags = (ArrayList<String>)in.readObject();
							afterReceiveHash = true;
						}

					case "Consumer":
						out.writeObject(broker.brokersInfo);
						out.flush();
						
						channel = (ChannelName) in.readObject();
						while(true){
							query = (String) in.readObject();
							String query1 = query;
							correct_port = broker.hashTopic(query1);
							if(broker.hashTopic(query1) != broker.port){
								out.writeInt(0);
								out.flush();
							}
							startOfDelivery = true;
							while(startOfDelivery && broker.hashTopic(query1) == broker.port ){
								Thread.sleep(10);
								if(afterPull){
									Thread.sleep(10);
									if(broker.hashTopic(query1) == broker.port){
										synchronized(this){
											sendConsumer(videoToSend);
										}
									}
									afterPull = false;
									startOfDelivery = false;
								}
							}
						}
					case "Publisher video":
						out.writeObject(broker.brokersInfo);
						out.flush();
						
						channel = (ChannelName) in.readObject();
						while(true){
							Thread.sleep(10);
							if(!query.equals("") && broker.port== broker.hashTopic(query)){
								out.writeObject(query);
								out.flush();
								synchronized(this){
									pull("vid");
								}
								query = "";
							}
						}
					case "Broker Thread":
						System.out.println("Connected to broker");
						changeInfo();
						
						while(true){
							Thread.sleep(10);
							if(afterReceiveHash){
								sendHashtags();
								afterReceiveHash = false;
							}
						}
				}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
}