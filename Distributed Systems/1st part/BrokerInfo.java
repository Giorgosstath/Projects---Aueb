import java.io.Serializable;
import java.io.*;
import java.util.*;

public class BrokerInfo implements Serializable{
	int portNum,hashVal;
	ArrayList<String> hashtags;
	
	public BrokerInfo(int i,int j){
		portNum=i;
		hashVal=j;
		hashtags = new ArrayList<String>();
	}
	
	public BrokerInfo(int i,int j, ArrayList<String> list){
		portNum=i;
		hashVal=j;
		hashtags = list;
	}
	
	public int getPortNum(){return portNum;}
	
	public int getHashVal(){return hashVal;}
	
	public ArrayList<String> getHashtags(){return hashtags;}
	
	public void setHashtags(ArrayList<String> list){hashtags = list;}
	
	public String toString()
	{
		return "Port: "+portNum+", hashVal: "+hashVal+", hashtags: "+hashtags;
	}
}