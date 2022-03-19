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
	int port;
	InetAddress ip;
	int hashVal;
	ServerSocket ss = null;
	VideoFile vv=null;
	Socket sp = null;
	Socket sc = null;
	Socket server = null;
	Socket client = null;
	ObjectOutputStream outPub = null;
	ObjectInputStream inPub = null;
	ObjectOutputStream outCon = null;
	ObjectInputStream inCon = null;
	ObjectOutputStream outSer = null;
	ObjectInputStream inSer = null;
	ObjectOutputStream outCl = null;
	ObjectInputStream inCl = null;
	ArrayList<byte[]> chunks=new ArrayList<>();
	byte[] chunk=null;
	String query;

	
    public BrokerClass(int port)
	{
		brokersInfo = new ArrayList<BrokerInfo>();
		registeredPublishers = new ArrayList<PublisherClass>();
		registeredUsers = new ArrayList<ConsumerClass>();
		hashtags = new ArrayList<String>();
		try{
			ss = new ServerSocket(port);
			this.port = port;
			ip = InetAddress.getLocalHost();
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
	
	public int getHashVal(){return hashVal;}
	
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
		String output = getMP5(s).substring(0,3);
		int demical=Integer.parseInt(output,16);  
		return demical;
	}

    //methods:
	 public int hashTopic(String s)
    {
		int return_value=0;
		int val = hashFun(s);
		int [] broker_vals = new int [brokersInfo.size()];
		int i=0;
		for(BrokerInfo b: brokersInfo)
		{
			broker_vals[i] = b.getHashVal();
			i++;
		}
		Arrays.sort(broker_vals);
		int field;
		if(val<broker_vals[0] || val>broker_vals[2])
			field=broker_vals[0];
		else if(val<broker_vals[1] && val>broker_vals[0])
			field=broker_vals[1];
		else
			field=broker_vals[2];
		for(BrokerInfo b: brokersInfo)
		{
			if(field==b.getHashVal())
				return_value=b.getPortNum();
		}
		return return_value;
    }
	
    public void calculateKeys()
    {
			String ipAddress = ip.toString();
			String portNum = String.valueOf(port);
			hashVal = hashFun(ipAddress+portNum);
    }

	/*public void receiveQuery()
	{
		try {
			query = (String) inCon.readObject();
		}
		catch(Exception e){e.printStackTrace();}
	}*/ //svisimo giati yparxei hdh
	
	public void sendQuery()
	{
		try {
			outPub.writeObject(query);
			outPub.flush();
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void pull(String txt)
	{
		try {
			
			System.out.println("Pulling a video");	
			int size=inPub.readInt();
			System.out.println(size);	
			int counter=0;
			System.out.println("after initialization");	
			while(counter<size) {
				chunk = (byte[]) inPub.readObject();
				chunks.add(chunk);
				counter++;
			}
			readHashtags();
			System.out.println("Exiting pull");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	
	public void sendConsumer(String txt)
	{
		try{
			System.out.println("Sending a video");
			outCon.writeInt(chunks.size());
			for (byte[] i : chunks ) {
				outCon.writeObject(i);
				outCon.flush();
			}
			System.out.println("Server is closing");
			System.out.println("Exiting sendConsumer");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public int getPort() {return port;}
	
    public void notifyPublisher(String s)
    {
		
    }
	
   /* public void notifyBrokersOnChanges(int i)
    {
		try{
				connect(i);
				
				outCl.writeInt(port);
				outCl.flush();
				
				outCl.writeObject(brokersInfo.get(0).hashtags);
				outCl.flush();
				
				discconect();
			}
		catch(Exception e){}
    }*/
	
	public void acceptChanges()
	{
		try{
			//acceptConnectionBroker();

			ArrayList<String> correct_hashtags = (ArrayList<String>)inSer.readObject();
			for(String s : correct_hashtags)
			{
				hashtags.add(s);
			}
		}
		catch(Exception e){}
		for(String s:hashtags){System.out.println(s);}
	}
	
	public void readHashtags()
	{
		try{

			ArrayList<String> receivedHashtags = (ArrayList<String>)inPub.readObject();

			ArrayList<String> list2 = new ArrayList<String>();
			ArrayList<String> list3 = new ArrayList<String>();
			
			for(String s : receivedHashtags)
			{
				int hash = hashTopic(s);
				if(hash==port)
					hashtags.add(s);
				else if(hash==brokersInfo.get(1).getPortNum())
					list2.add(s);
				else
					list3.add(s);
			}
			
			//connect(brokersInfo.get(1).getPortNum());
			outCl.writeObject(list2);
			
			//connect(brokersInfo.get(2).getPortNum());
			outCl.writeObject(list3);
			
		}
		catch(Exception e){}
	}
	
    public void acceptConnectionPublisher() throws IOException {
		try {
			sp = ss.accept();
			outPub = new ObjectOutputStream(sp.getOutputStream());
			inPub = new ObjectInputStream(sp.getInputStream());
			System.out.println("Publisher connected!");

			outPub.writeObject(brokersInfo);
			outPub.flush();

			ChannelName c = (ChannelName) inPub.readObject();
			registeredPublishers.add(new PublisherClass(c));
			System.out.println(c.channelName);
		}
		catch(Exception e){e.printStackTrace();}
	}

	
	public void acceptRegisterConsumer() throws IOException{
		acceptConnectionConsumer();
		try{
			String s = (String)inCon.readObject();
			System.out.println(s);
		}
		catch(Exception e){}
	}
	
	public void acceptConnectionConsumer() throws IOException {
		try{
			sc = ss.accept();
			outCon = new ObjectOutputStream(sc.getOutputStream());
			inCon = new ObjectInputStream(sc.getInputStream());
			System.out.println("Consumer connected!");
			
			ChannelName channel = (ChannelName)inCon.readObject();
			registeredUsers.add(new ConsumerClass(channel));
			
			outCon.writeObject(brokersInfo);
			outCon.flush();
		}
		catch(Exception e){}
    }
	
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}
	
	
	public List<PublisherClass> getPublishers()
	{
		return registeredPublishers;
	}
	
	public void acceptConnectionBroker() throws IOException {
		boolean exist = false;
		try{
			server = ss.accept();
			outSer = new ObjectOutputStream(server.getOutputStream());
			inSer = new ObjectInputStream(server.getInputStream());
			System.out.println("Server connected!");
			
			BrokerInfo info = brokersInfo.get(0);
			outSer.writeObject(info);
			outSer.flush();
			
			info = (BrokerInfo)inSer.readObject();
			for(BrokerInfo b: brokersInfo)
			{
				if(b.getPortNum()==info.getPortNum())
					exist=true;
			}
			if(!exist)
				brokersInfo.add(info);
		}
		catch(Exception e){}
		
    }
	
	public void connect(int i)
	{
		boolean exist = false;
		try{
			client = new Socket("localhost",i);
			outCl = new ObjectOutputStream(client.getOutputStream());
			inCl = new ObjectInputStream(client.getInputStream());
			System.out.println("Connected to server");
			
			BrokerInfo info = (BrokerInfo)inCl.readObject();
			for(BrokerInfo b: brokersInfo)
			{
				if(b.getPortNum()==i)
					exist=true;
			}
			if(!exist)
				brokersInfo.add(info);
			
			info = brokersInfo.get(0);
			outCl.writeObject(info);
			outCl.flush();
		}
		catch(Exception e){}
	}
	
	
	public void discconect()
	{
		try{
			client.close();
			outCl.close();
			inCl.close();
		}
		catch(Exception e){}
	}

	public void sendBroker(String txt)
	{
		try{
			System.out.println("Sending a video");
			outSer.writeInt(chunks.size());
			for (byte[] i : chunks ) {
				outSer.writeObject(i);
				outSer.flush();
			}
			System.out.println("Server is closing");
			System.out.println("Exiting sendConsumer");
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void updateNodes()
	{
	}
	public void run(){
		try{
			System.out.println(brokersInfo.get(1));
			acceptConnectionPublisher();
			readHashtags();
		}
		catch(Exception e){e.printStackTrace(); }
	}
}