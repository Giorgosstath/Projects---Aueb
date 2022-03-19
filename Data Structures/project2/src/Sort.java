import java.io.*; 
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sort 
{
	public static int[] quicksort(int [] a,int p,int r) 
	{
		 if (r <= p) return null;
		 int i = partition(a, p, r);
		 quicksort(a, p, i-1);
		 quicksort(a, i+1, r); 
		 return a;
	}
	
	private static int partition(int [] a,int p,int r)
	{ int i = p-1, j = r; int v = a[r];
		for (;;)
		{
			while (less(v,a[++i]));
			while (less(a[--j],v))
				if (j == p) break;
			if (i >= j) break;
			exch(a, i, j);
		}
		exch(a, i, r);
		return i;
	}
	
	private static void exch(int [] a,int x,int y)
	{
		int temp = a[x];
		a[x]=a[y];
		a[y]=temp;
	}
	
	private static boolean less(int a,int b){return a<b;}
	

	public static void main(String [] args) throws FileNotFoundException
	{			  
		File file = new File(args[0]);  //read a file from cmd
		Disk[] d=CreateDisks(file); //call createDisks to make a disk array using file name.
		if (d!=null)     //if file is not null
			Greedy.Greedyy(d); //method to print info about disks. Calling greedyy from class greedy(same method)
	}//main		
	
	
	public static Disk[] CreateDisks (File file)
	{
		Disk[] d=null;   //an array of Disk objects
		int x=0;  // used to save object in each line of txt file
		int line_counter=0; //lines in txt file, MAX number of folders needed --> if every folder is 1 tb			
		try
		{
			Scanner s= new Scanner(file); //read file
			boolean SizeGuard=true; //guard to check if the size of folders is right, 0<x<1000000
			while (s.hasNext())
			{
				x=Integer.parseInt(s.next()); // txt is string 
		 		if ((x<0) || (x>1000000))
				{
					SizeGuard=false; 
					System.out.print("Error!There was a problem in the folder's size.Make sure the size is greater than 0 and less than 1000000. \n Ending programm..... \n");
					break;
				}// if x<0/x>1m
				else	
					line_counter++;				
		    }//while
			s.close();
			if(SizeGuard)
			{
				int[] Folders= new int[line_counter]; //create int array to store txt info 
				d=new Disk[line_counter];             //create disk array
				for (int i=0;i<line_counter;i++)
					d[i]=new Disk(i);                //initialize array
				Scanner s2= new Scanner(file);
				int current_F_Size=0; //current folder in txt 
				int count=0; // count number of current folder 
				while(s2.hasNext())
				{
					current_F_Size=Integer.parseInt(s2.next());
					Folders[count]=current_F_Size;
					count++;		// next folder, count ++	   
				}// while
			    s2.close();   			 
			    quicksort(Folders,0,line_counter-1);	   // sort the Folders array from highest value to lowest
			    count=0; //count number of current folder 
			    while (count<d.length)
			    { // while count number of current folder < than total folders 
				    boolean Found=false; //disk found 			  
				    for (int i=0;i<line_counter;i++)
				    {
					    if (!Found)
					    { //if current folder hasnt been stored yet
							if (Folders[count]<=d[i].getFreeSpace())
							{ //if current Folder can be stored in current (i) disc
								d[i].setSpace(Folders[count]); // change remaining FreeSpace sing setSpace method.
								d[i].add(Folders[count]); //add current folder to current disk's list
								Found=true; //current folder found a disk to be stored, so it cant be stored again.
								count++;
							}//if folder size <= current disk
					    }// if !found
				    }//for
			    }//while       		  
            }//if guard				
		}//try
		catch (FileNotFoundException e)
		{
			System.out.print("Error!File not found!");   // if file not found  
		}//catch exeption
		return d;			
	} //method createDisks
}//class

