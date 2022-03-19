import java.io.*; 
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Greedy
{	    	
	public static void main(String[] args) throws FileNotFoundException
	{		  
		File file = new File(args[0]); 
		//method pairnei ws orisma to file kai kanei return pinaka d[i] etoimo.
		Disk[] d=CreateDisks(file); //call createDisks to make a disk array using file name.
		if (d!=null)     //if array d is not null
			Greedyy(d); //method to print info about disks
	}//main

	
	public static Disk[] CreateDisks ( File file)
	{
		Disk[] d=null;
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
				d=new Disk[line_counter]; 
				for (int i=0;i<line_counter;i++)
					d[i]=new Disk(i); //create Disk array 
				Scanner s2= new Scanner(file);
				int current_F_Size=0;
				while(s2.hasNext())
				{
					boolean Found=false; //disk found
					current_F_Size=Integer.parseInt(s2.next());
					for (int i=0;i<line_counter;i++)
					{
						if (!Found)
						{ //if current folder hasnt been stored yet
							if (current_F_Size<=d[i].getFreeSpace())
							{ //if current Folder can be stored in current (i) disc
								d[i].setSpace(current_F_Size); // change remaining FreeSpace sing setSpace method.
								d[i].add(current_F_Size); //add current folder to current disk's list
								Found=true; //current folder found a disk to be stored, so it cant be stored again.
							}//if folder size <= current disk
						}// if !found
					}//for
				}//while
				  s2.close();             		  
			}//if guard				
		}//try
		catch (FileNotFoundException e){
			  System.out.print("Error!File not found!");			// if file not found print.  
			}//catch exeption
		return d;			
	} //method createDisks	
	
	
	public static void Greedyy( Disk[] x )
	{
		// void methodos(d);
		MaxPQ pq = new MaxPQ(new DiskComparator()); //create priority Q
		int disks_used=0; //counts how many disks are used to store folders
		double size_of_folders=0; //size of all the saved folders.
		int sum=0;
		for (int i=0;i<x.length;i++)
		{
			if(x[i].getFreeSpace()<1000000)
			{ // if disk has been used to store folders
				sum=1000000-x[i].getFreeSpace(); // 1 mil - free space = space used in disk
				size_of_folders+=sum; // sum of folders total size
				disks_used++; // if disk was used, used + 1
			}//if
		}// for
		for(int i=0; i<disks_used; i++)
			pq.add(x[i]); //add to list 
		System.out.println("Sum of all folders = " + size_of_folders/1000000 + " TB");
		System.out.println("Number of disks used = " + disks_used);
		for(int i=0; i<disks_used; i++)
			System.out.println(pq.getMax());
	}// method Greedyy
}//Greedy	