import java.util.*;
import java.io.*;
import java.net.*;

public class Test3{
	public static void main(String [] args)
	{
		PublisherClass p = new PublisherClass(new ChannelName("mychannel"));
		p.connect(100);;
		VideoFile newVideo=p.addNewVideo();
		p.notifyBrokersForHashTags(newVideo.getHashtags());
		//String query= p.acceptQuery();
		/*for(VideoFile v : p.VideoQ){
			if(v.getHashtags().contains(query)){
				p.push(v);
			}
		}*/

	}
}