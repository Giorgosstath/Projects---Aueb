import java.util.ArrayList;
import java.io.*;

public class VideoFile implements Serializable{
    public String videoName,channelName,dateCreated,length,framerate,frameWidth,frameHeight;
    public byte[] videoFileChunk;
    ArrayList<String> associatedHashtags;

    public VideoFile() //default
    {
        channelName=null;
        videoName=null;
        videoFileChunk=null;
        associatedHashtags=null;
    }

    public VideoFile(String VideoName,String channelName,String dateCreated,String length) // .... consutrctor gia File
    {

    }
	
	public VideoFile(String channelName,byte[] f,String videoName) //default
    {	
		this.channelName=channelName;
		this.videoName=videoName;
		videoFileChunk=f;
		associatedHashtags=new ArrayList();
    }
	public void setChannelName(String name){
        channelName=name;
    }
	
    public void setHashtag(String hashtag){
        associatedHashtags.add(hashtag);
    }
	
	public void addHashtag(String s) 
	{
		associatedHashtags.add(s);
	}
	
	public ArrayList<String> getHashtags()
	{
		return associatedHashtags;
	}
	
	public String getCName(){return channelName;}
	
	public void setChunk(byte[] video){
        videoFileChunk=video;
    }
	public byte[] getChunk() {
		return videoFileChunk;
	}
	public String getTitle() 
	{
		return videoName;
	}		
    /*Logika thelei kai constructor gia publisher px VideoFile(Publisher p)
    {
    } */
}//clas
