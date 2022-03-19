import java.util.*;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsumerClass extends Thread{
	List<BrokerInfo> brokersInfo;
	ChannelName channel;
	ArrayList<String> hashtags;
	ArrayList<BrokerInfo> bInfo;
	ConsumerThread con_thread,con_thread2, con_thread3;
	
	public static void main(String [] args){
		new ConsumerClass(new ChannelName("channel1")).connect();
	}

	public ConsumerClass(ChannelName c) {
		channel = c;
		brokersInfo = new ArrayList<BrokerInfo>();
	}
	
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}


	public void connect() {
		ConsumerThread con_thread,con_thread2, con_thread3;
		try {
			Socket s = new Socket("localhost", 450);
			con_thread = new ConsumerThread(channel, s, this,2);
			con_thread.start();
			Socket s2 = new Socket("localhost", 1000);
			con_thread2 = new ConsumerThread(channel, s2, this,2);
			con_thread2.start();
			Socket s3 = new Socket("localhost", 500);
			con_thread3 = new ConsumerThread(channel, s3, this,2);
			con_thread3.start();
			ConsumerThread con_thread4= new ConsumerThread(channel, this,1);
			con_thread4.start();
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	public void run(){
		connect();
	}
}
