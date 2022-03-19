import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class ChannelName implements Serializable{
    public String channelName;
    ArrayList<String> hashtagsPublished;
    ArrayList<VideoFile>  userVideoFilesMap; //HashMap<String,ArrayList<Value>> userVideoFilesMap; htan etsi kanonika

    public ChannelName(){hashtagsPublished = new ArrayList<String>();}

    public ChannelName(String channelName)
    {
      this.channelName=channelName;
	  hashtagsPublished = new ArrayList<String>();
    }

    //getters-setters
    public String getCName()
    {
        return channelName;
    }
    public void setChannelName(String name)
    {
        channelName=name;
		userVideoFilesMap=new ArrayList();
    }
    public  ArrayList<String> getHashtagsPublished()
    {
        return hashtagsPublished;
    }

    public  ArrayList<VideoFile> getuserVideoFilesMap()
    {
        return userVideoFilesMap;
    }
	
	public void addHashTag(String name)
	{
		hashtagsPublished.add(name);
				
	}
	
	/*public void addVideo(VideoFile v) //kane add to video pou pernietai stin lista me ta video tou kanaliou
	{
		userVideoFilesMap.add(b);
				
	}*/
	
	public void removeHashTag(String name)
	{
		hashtagsPublished.remove(name);
	}
	public String getChannelName(){return channelName;}
	
	public String toString(){return channelName;}

}//class