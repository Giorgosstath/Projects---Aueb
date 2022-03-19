import java.io.*; 
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Comparison
{	

	public static int disk_count( Disk[] x )
	{
		int disks_used=0;    //counts how many disks are used to store folders
		for (int i=0;i<x.length;i++)
		{
			if(x[i].getFreeSpace()<1000000)
				disks_used++; // if disk was used, used + 1
		}// for
		return disks_used;
	}// method Greedyy
	
	public static void alg_compare(int N)
	{
		int sum1=0;  //sum of used space for algorith 1
		int sum2=0;   //sum of used space for algorith 2
		Disk[] d1,d2;   // 2 arrays type disk
		int x=0;     //variable to store the number of used disks
		int i=1;
		if(N==500)
			i=11;
		if(N==1000)
			i=21;
		for(int k=i; k<=i+9; k++)
		{
			File file = new File("C:/Users/user/Desktop/3170152_3170124/data/File" +k+".txt");
			d1=Greedy.CreateDisks(file); //call createDisks to make a disk array using file name.
			if (d1!=null)     //if array d1 is not null
			{
				x = disk_count(d1); //print info about disks
				sum1+=x;
			}//if
			d2=Sort.CreateDisks(file); //call createDisks to make a disk array using file name.
			if (d2!=null)     //if array d2 is not null
			{
				x = disk_count(d2); //method to print info about disks
						sum2+=x;
			}//if
		}//for
		System.out.println("The average disks used of algorith 1 for N=100 is: "+(double)sum1/10);
		System.out.println("The average disks used of algorith 2 for N=100 is: "+(double)sum2/10);
		System.out.println("");
	}
	
	public static void main(String[] args)
	{	
		int N=100;
		alg_compare(N);   //compare the 2 algorithms for N=100
		N=500;
		alg_compare(N);   //compare the 2 algorithms for N=100
		N=1000;
		alg_compare(N);    //compare the 2 algorithms for N=100
	}//main
}