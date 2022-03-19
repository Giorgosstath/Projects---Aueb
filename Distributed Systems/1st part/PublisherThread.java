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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PublisherThread extends Thread 
{
	Socket socket=null;
	PublisherClass publisher;
	ObjectOutputStream out = null;
    ObjectInputStream in = null;
	List<BrokerInfo> brokersInfo;
	int action;
	int port;
	Scanner sc1=new Scanner(System.in);
	
	public PublisherThread(Socket s,PublisherClass p,int a){
		this.socket = s;
		this.publisher=p;
		this.action = a;
		brokersInfo = p.brokersInfo;
		try{
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e){System.out.println(e);}
		if(a!=0)
			System.out.println("Connected to server");
		
	}//constructor thread 
	
	//---------------------------------------------------

	private ArrayList<byte[]> generateChunks(byte [] videoFile){ //kane chunks to vid.
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
	
	
	public static byte[] readVideo(String s) 
    {
		byte[] b;
		try { 
		    b=Files.readAllBytes(Paths.get(s));
        	return b;	
		}
        catch (Exception e3) {
            System.out.println("Error while converting file into bytes.");  			 
			e3.printStackTrace();
		}//catch
		return null;
    }
	
	//--------------------------------------------------------------------------------------------
	
	public void notifyBrokersForHashTags(ArrayList<String> video_hashtags)
    {
		try{
			out.writeObject(video_hashtags);
			out.flush();
		}
		catch(Exception e){e.printStackTrace();}
    }
	
	//action 1 //isws thelei allages logo ths metavlhths publisher
	public VideoFile addNewVideo(){ //pare path tou vid, dhmiourgise ta chunks,diavase ta hashtags tou video kai prosthese to vid stin lista me ta diathesima video tou kanaliou
		ArrayList<byte []> chunks=null;
		System.out.println("Give file path");
		String path=sc1.nextLine();
		byte [] video= readVideo(path);
		VideoFile v=new VideoFile(publisher.getChannelName().getCName(),video,path); //(String channelName,byte[] f,videoName)
		chunks=generateChunks(video);
		System.out.println("Give the hashtags associated with the video!");
		while (true) {
			System.out.println("Type 0 to exit");
			String Vidhashtag=sc1.nextLine(); //hashtag
			if (Vidhashtag.equals("0")) break;
			publisher.getChannelName().addHashTag(Vidhashtag);
			v.addHashtag(Vidhashtag);
		}
		System.out.println("Video hashtag's " + path + ":");
		System.out.println(v.getHashtags());
		publisher.VideoQ.add(v);
		return v;
	}
	
	
	public void push(VideoFile v)
    {
		System.out.println("Sendind a video...");
    	ArrayList<byte []> chunks=null;	
		try {
			chunks=generateChunks(v.getChunk());		
			out.writeInt(chunks.size());
			out.flush();
			for(byte [] i: chunks) {
				if(i!=null) {

					out.writeObject(i);
					out.flush();
				}
			}
			System.out.println("Video sent");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
	
	/*public void discconect() //pote????????????
	{
		try{
			s.close();
			out.close();
			in.close();
		}
		catch(Exception e){};
	}*/

	public int getSocketPort() {
		return socket.getPort();
	}

	public void run()
	{
		try{
			switch (action) 
			{
				case 0:
						out.writeObject("Publisher hashtags");
						out.flush();
						
						brokersInfo = (ArrayList<BrokerInfo>) in.readObject();
						
						out.writeObject(publisher.channelName);
						out.flush();
						while(true){
							System.out.println("If you want to upload a new video press 1.");
							try{
								BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
								String answer = reader.readLine();
								if(answer.equals("1")){
									VideoFile videofile = addNewVideo();
									notifyBrokersForHashTags(videofile.getHashtags());
								}
							}
							catch(Exception e){System.out.println(e);}
						}
				case 1:	
						out.writeObject("Publisher video");
						out.flush();
						
						brokersInfo = (ArrayList<BrokerInfo>) in.readObject();
						
						out.writeObject(publisher.channelName);
						out.flush();
						try{
							while(true){
								String server_message = (String)in.readObject();
									for(VideoFile video : publisher.VideoQ){
										for(String s : video.associatedHashtags){
											if(s.equals(server_message)){
												synchronized(this){
													push(video);
												}
											}
										}
									}
							}
						}
						catch(Exception e){System.out.println(e);}
			}//switch
		}//try
		catch(Exception e){System.out.println(e);}
	}//run
}//class 