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


public class BrokerClass  extends Thread implements Broker{

    List<ConsumerClass> registeredUsers;
    List<PublisherClass> registeredPublishers;
	ArrayList<BrokerInfo> brokersInfo;
	ArrayList<String> hashtags;
	ServerSocket ss;
	int port;
	InetAddress ip;
	int hashVal;
	VideoFile vv=null;
	ArrayList<byte[]> chunks=new ArrayList<>();
	byte[] chunk=null;
	//String query="";
	boolean flag3 = false;
	static boolean firstTime = true;
	int [] copy = null;
	
    public BrokerClass(int port)
	{
		brokersInfo = new ArrayList<BrokerInfo>();
		registeredPublishers = new ArrayList<PublisherClass>();
		registeredUsers = new ArrayList<ConsumerClass>();
		hashtags = new ArrayList<String>();
		try{
			this.port = port;
			ip = InetAddress.getLocalHost();
			ss = new ServerSocket(port);
		}
		catch(Exception e){};
		calculateKeys();
		brokersInfo.add(new BrokerInfo(port,hashVal,hashtags));
	}
	
	public void setHashtags(ArrayList<String> list)
	{
		brokersInfo.get(0).setHashtags(list);
	}
	
	public void setHashtags(String s)
	{
		brokersInfo.get(0).hashtags.add(s);
	}
	
	public int getPort() {return port;}
	
	public int getHashVal(){return hashVal;}
	
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}
	
	
	public List<PublisherClass> getPublishers()
	{
		return registeredPublishers;
	}
	
	private String getMP5(String s)
	{
		 try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
	public int hashFun(String s) 
	{
		String output = getMP5(s).substring(4,9);
		int demical=Integer.parseInt(output,16);  
		return demical%1000;
	}

	 public int hashTopic(String s)
	 {
		 try{
			Thread.sleep(1000);
		 }
		 catch(Exception e){}
		int val = hashFun(s);
		if(firstTime){
			int [] broker_vals = new int [brokersInfo.size()];
			firstTime = false;
			for(int i=0; i<3; i++){
				broker_vals[i] = brokersInfo.get(i).hashVal;
			}
			for(int j=0; j<2; j++){
				for(int k=j+1; k<3; k++){
					if(broker_vals[j]>broker_vals[k]){
						int temp = broker_vals[j];
						broker_vals[j]=broker_vals[k];
						broker_vals[k]=temp;
					}
				}
			}
			copy = broker_vals;
		}
		int return_value=0;
		int field;
		if(val<copy[0] || val>copy[2])
			field=copy[0];
		else if(val>copy[1] && val<copy[2])
			field=copy[2];
		else
			field=copy[1];
		int counter = 1;
		for(BrokerInfo b: brokersInfo)
		{
			if(field==b.getHashVal())
				return_value=b.getPortNum();
			counter++;
		}
		return return_value;
    }
	
    public void calculateKeys()
    {
			String ipAddress = ip.toString();
			String portNum = String.valueOf(port);
			hashVal = hashFun(ipAddress+portNum);
    }
	
	
	public void acceptConnection(){
			try{
				Socket s = ss.accept();
				BrokerThread bt = new BrokerThread(s,this);
				bt.start();
			}
			catch(Exception e){System.out.println(e);}
	}
	
	public void connectToBroker(int port){
		try{
			Socket s = new Socket("localhost",port);
			BrokerToBrokerThread bt = new BrokerToBrokerThread(s,this);
			bt.start();
		}
		catch(Exception e){System.out.println(e);}
	}
}