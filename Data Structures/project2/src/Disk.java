import java.util.*;

public class Disk implements Comparable<Disk>
{
	private int free_space=1000000;
	private int id;
	private List folders;   //create a list called Folders
	
	public Disk(int id)
	{
		this.id=id;
		folders = new List();
	}// constructor
	
	public int getFreeSpace() {return this.free_space;}// get free space
	
	public void setSpace(int x) {free_space=free_space - x ;} //setSpace
	
	public int getId() {return id;}
	
	public void setId(int id){this.id=id;}
	
	public List getFolders(){return folders;}
	
	public String toString()
	{
		return ("id  " + (id+1) + " free space " + getFreeSpace() + ": "+ folders);
	}
	
	public void add(int x) {folders.insertAtFront(x);}
	
	public int compareTo( Disk A)
	{
		if (this.free_space>A.getFreeSpace())
			return 1;
		else if (this.free_space< A.getFreeSpace())
			return -1;
		else
			return 0;
	}//compareTo
}//class