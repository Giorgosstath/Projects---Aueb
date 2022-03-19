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
	VideoFile video=new VideoFile();
	ObjectOutputStream out = null;
    ObjectInputStream in = null;
	Scanner sc1=new Scanner(System.in);
	ArrayList<VideoFile> VideoQ = new ArrayList<VideoFile>();

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
	
    public void push(VideoFile v) //(String s,Value v)-->dexete to videoFile kai to stelnei se chunks 
    {
		int correct_port = hashTopic(channelName.getCName());
		System.out.println(correct_port);
		
		System.out.println("bainw push");
    	ArrayList<byte []> chunks=null;	
		System.out.println("1");
		try {			
			//System.out.println("Sending" + v.getTitle() + "of channel: " + v.getCName() + " with chunk size of: " + chunks.size());
			chunks=generateChunks(v.getChunk());
			System.out.println("2");			
			out.writeInt(chunks.size());
			out.flush();
			System.out.println("2");
			for(byte [] i: chunks) {
				if(i!=null) {
					//System.out.println(i);
					out.writeObject(i);
					out.flush();
				}
			}

			System.out.println("Exiting push");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
		 
	public boolean socketIsclosed()
	{
		return s.isClosed();
	}
	
    public void notifyBrokersForHashTags(ArrayList<String> video_hashtags)
    {
		try{
			out.writeObject(video_hashtags);
			out.flush();
		}
		catch(Exception e){e.printStackTrace();}
    }
	
	
    public static byte[] readVideo(String s) //kanonika ithele ArrayList<Value> sto orisma, isws auto den tha nai gia metatropi 1 mono video alla olwn twn video tou publisher??
    {
		byte[] b;
		try { 
		    b=Files.readAllBytes(Paths.get(s)); //vale ston pinaka chunks to video se bytes.
        	return b;	
		}
        catch (Exception e3) {
            System.out.println("Error kata tin metatropi tou file se bytes");  			 
			e3.printStackTrace();
		}//catch
		return null;
    }

	
	public List<BrokerInfo> getBrokersInfo()
	{
		return brokersInfo;
	}
	
	public void connect(int i)
	{
		try {
			InetAddress ip = InetAddress.getLocalHost();
			s = new Socket("localhost",i);
            
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
			
			brokersInfo = (ArrayList<BrokerInfo>) in.readObject();
			
			out.writeObject(channelName);
			out.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ChannelName getChannelName() {return channelName;}
	
	public void discconect()
	{
		try{
			s.close();
			out.close();
			in.close();
		}
		catch(Exception e){};
	}
	
	public void updateNodes()
	{
		
	}
	
	public String acceptQuery()
	{
		try {
			String query = (String) in.readObject();
			return query;
		}
		catch (Exception e){e.printStackTrace();}
		return null;
	}
	
	public VideoFile addNewVideo(){ //diavase apo tin xristi vid, ftiakse antikeimeno videoFile kai valto stin oura pou periexei ola ta video pou exei o sugkekrimenos.
		ArrayList<byte []> chunks=null;
		//pare meros ths push kai valto edw
		System.out.println("Give file path");
		String path=sc1.nextLine();
		byte [] video= readVideo(path);
		System.out.println(video.length);
		VideoFile v=new VideoFile(this.getChannelName().getCName(),video,path); //(String channelName,byte[] f,videoName)
		chunks=generateChunks(video);
		System.out.println(chunks.size());
		System.out.println("Give the hashtagd associated with the video!");
		while (true) {
			System.out.println("Type 0 to exit");
			String Vidhashtag=sc1.nextLine(); //hashtag
			if (Vidhashtag.equals("0")) break;
			this.getChannelName().addHashTag(Vidhashtag);
			v.addHashtag(Vidhashtag);
			System.out.println(hashTopic(Vidhashtag));
		}
		System.out.println("Lista me ta hashtags tou video " + path + ":");
		System.out.println(v.getHashtags());
		VideoQ.add(v); //kane add stin oura tou publisher to videoFile pou eftiakses twra
		return v;
	}
	
	public ArrayList<byte[]> generateChunks(byte [] videoFile){
		ArrayList<byte []> allChunks = new ArrayList<>();
		int chunkSize=1024;
		byte[] chunk =null;
		int size=videoFile.length;
		int curSize=size;
		int start=0;
		int stop=chunkSize;
		while(curSize>chunkSize){
			chunk=Arrays.copyOfRange(videoFile,start,stop);
			allChunks.add(chunk);
			start += chunkSize;
			stop+=chunkSize;
			curSize-=chunkSize;
		}
		if(curSize>0){
			chunk=Arrays.copyOfRange(videoFile,start,size);
			allChunks.add(chunk);
		}
		return allChunks;
	}
	
	/*public void run()
	{
		try{
			connect(100);
			addNewVideo();
			push(VideoQ.get(0));
		}
		catch(Exception e){}
	}*/
}//class