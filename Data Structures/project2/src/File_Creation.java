import java.io.*; 
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class File_Creation
{
	public static void f_creation(int N)
	{
		try
		{
			int x = 0;           //variable for the random numbers
			Random rand = new Random();   //create an object of type Random
			int i=1;                  //the "pointer" of a certain file (example:File5,i=5)
			if(N==500)         //files from 11-20 contain 500 values
				i=11;
			if(N==1000)         //files from 21-30 contain 1000 values
				i=21;
			PrintWriter writer;
			for(int k=i; k<=i+9; k++)
			{
				writer = new PrintWriter("C:/Users/user/Desktop/3170152_3170124/data/File" +k+".txt", "UTF-8");   //create a PrintWriter object in order to create the files
				for(int j=0; j<N; j++)
				{
					x=rand.nextInt(1000001);     // x = a random number between 0 and 1000000
					writer.println(x);
				}//for
				writer.close();   //close the writer
			}//for
		}//try
		catch (IOException e){
			System.out.println("There was an error with a file. Program ending....");
			}//catch
	}
	
	public static void main(String [] args)
	{
		int N = 100;
		f_creation(N);  //create 10 files with 100 values
		N = 500;
		f_creation(N);   //create 10 files with 500 values
		N=1000;
		f_creation(N);   //create 10 files with 1000 values
	}
}