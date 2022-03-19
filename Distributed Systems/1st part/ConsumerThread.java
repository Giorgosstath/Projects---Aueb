import java.util.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ConsumerThread extends Thread{
	ChannelName channel;
	ArrayList<BrokerInfo> brokersInfo;
	Socket socket = null;
	ObjectOutputStream out = null;
    ObjectInputStream in = null;
	int action;
	String hashtag="";
	static String queryToSend="";
	ConsumerClass consumer;
	
	public ConsumerThread(ChannelName s,Socket conSocket,ConsumerClass c,int a){
		channel=s;
		brokersInfo = new ArrayList<BrokerInfo>();
		try {
			out = new ObjectOutputStream(conSocket.getOutputStream());
			in = new ObjectInputStream(conSocket.getInputStream());
		}
		catch (Exception e){e.printStackTrace();}
		consumer = c;
		action= a;
		if(a!=1)
			System.out.println("Connected to server");
	}
	public ConsumerThread(ChannelName s,ConsumerClass c,int a){
		channel=s;
		brokersInfo = new ArrayList<BrokerInfo>();
		consumer = c;
		action= a;
		if(a!=1)
			System.out.println("Connected to server");
	}
	
	
	public void sendQuery(String query){
		try {
			out.writeObject(query);
			out.flush();
		}
		catch(Exception e){e.printStackTrace();}
	}

	public void setQueryToSend(String queryToSend) {
		this.queryToSend = queryToSend;
	}

	public void playData(String s)
    {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
			try {
				byte[] chunk=null;
				int size=in.readInt();
				if(size!=0){
					System.out.println("Pulling a video...");
					int counter=0;
					byte[] allChunks=new byte[size];
					while(counter<size) {
						chunk = (byte[]) in.readObject();
						outputStream.write(chunk);
						counter++;
					}
					allChunks= outputStream.toByteArray( );
					FileOutputStream fff=new FileOutputStream(s);
					fff.write(allChunks);
					System.out.println("Pull completed.");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	public void run(){
		switch(action) {
			case 1:	Scanner readFromKey = new Scanner(System.in);
					while (true) {
						System.out.println("Give the hashtag of the video you want to search.");
						queryToSend = readFromKey.nextLine();
					}

			case 2:
				try {

					out.writeObject("Consumer");
					out.flush();

					brokersInfo = (ArrayList<BrokerInfo>) in.readObject();

					out.writeObject(channel);
					out.flush();
					Thread.sleep(10);
					while (true) {
						Thread.sleep(10);
						if (!queryToSend.equals("")) {
							sendQuery(queryToSend);
							synchronized (this) {
								playData("newNameOfNewVideo.mp4");
							}
							queryToSend = "";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}