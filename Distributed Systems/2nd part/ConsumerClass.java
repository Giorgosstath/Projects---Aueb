import java.util.*;
import java.io.*;
import java.net.*;

public class ConsumerClass implements Consumer {
	List<BrokerInfo> brokersInfo;
	ChannelName channel;
	ArrayList<String> hashtags;
	Socket registerSocket = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;

	public static void main(String [] args)
	{
		Consumer c= new ConsumerClass(new ChannelName());
		c.connect(100);
		c.register("viral");
		c.playData();
	}

	public ConsumerClass(ChannelName c) {
		channel = c;
		brokersInfo = new ArrayList<BrokerInfo>();
	}

	public void register(String s) {
		int port=0;
		for(BrokerInfo b:brokersInfo){
			for(String ht:b.getHashtags()){
				if(ht.equals(s)) {
					port = b.getPortNum();
					break;
				}
			}
		}
		if(port!=0)
		{
			connect(port);
			try{
				out.writeObject(s);
				out.flush();
			}
			catch(Exception e){}
		}
		else
			System.out.println("No such video available.");
	}

    public void disconnect(Broker b,String s)
	{
		try {
			in.close();
			out.close();
			registerSocket.close();
			System.out.println("The consumer socket is shut down!");
		} catch (IOException e) { System.err.println("The consumer socket cannot shut down!"); }
	}
    public void playData()
    {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
			try {
				byte[] chunk=null;
				System.out.println("Pulling a video");
				int size=in.readInt();
				int counter=0;
				byte[] allChunks=new byte[size];
				while(counter<size) {
					chunk = (byte[]) in.readObject();
					outputStream.write(chunk);
					counter++;
				}
				allChunks= outputStream.toByteArray( );
				System.out.println(allChunks.length);
				FileOutputStream fff=new FileOutputStream("new.mp4");
				fff.write(allChunks);
				System.out.println("Exiting register");
			}
			catch (Exception e) {
				System.err.println("You are trying to connect to an unknown host!");
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}

	@Override
	public void connect(int port) {
		try{
			registerSocket = new Socket("localhost", port);
			out = new ObjectOutputStream(registerSocket.getOutputStream());
			in = new ObjectInputStream(registerSocket.getInputStream());
			
			out.writeObject(channel);
			out.flush();
			
			brokersInfo = (ArrayList<BrokerInfo>) in.readObject();
		}
		catch(Exception e) {}
	}

	
	public void discconect()
	{
		try{
			registerSocket.close();
			out.close();
			in.close();
		}
		catch(Exception e){};
	}
}
