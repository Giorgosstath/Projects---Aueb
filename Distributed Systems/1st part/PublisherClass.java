import java.util.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;


public class PublisherClass extends Thread implements Publisher{
    ChannelName channelName;
	List<BrokerInfo> brokersInfo;
	Socket s = null;
	Socket p = null;
	VideoFile video=new VideoFile();
	ObjectOutputStream out = null;
    ObjectInputStream in = null;
	Scanner sc1=new Scanner(System.in);
	ArrayList<VideoFile> VideoQ = new ArrayList<VideoFile>();
	
	public static void main(String [] args){
		new PublisherClass(new ChannelName("channel1")).connect();
	}
	
	public ChannelName getChannelName(){return channelName;}
	
    public PublisherClass()
    {
    	brokersInfo = new ArrayList<BrokerInfo>();
    	channelName=null;
    }

    public PublisherClass(ChannelName channelName)
    {
        this.channelName=channelName;
		brokersInfo = new ArrayList<BrokerInfo>();
    }
	
	
    public void addHashTag(String h)
    {
		channelName.addHashTag(h);
    }
	
    public void removeHashTag(String h)
    {
		channelName.removeHashTag(h);
    }
	
	public ArrayList<VideoFile> getQ() 
	{
		return VideoQ;
	}

	
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}
	
	public void connect()
	{
		try{
			s = new Socket("localhost",450);
			PublisherThread p1 =new PublisherThread(s,this,0); //action=0 connect
			p1.start();
			p = new Socket("localhost",450);
			PublisherThread	p2 = new PublisherThread(p,this,1);
			p2.start();
			PublisherThread	p4 = new PublisherThread(new Socket("localhost",500),this,1);
			p4.start();
			PublisherThread	p6 = new PublisherThread(new Socket("localhost",1000),this,1);
			p6.start();
		}
		catch(Exception e){System.out.println(e);}
	}
}//class