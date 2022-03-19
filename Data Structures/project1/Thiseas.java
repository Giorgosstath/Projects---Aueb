import java.io.*; 
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Thiseas
{
	    
		
	public static void main(String[] args) throws FileNotFoundException
	{
		boolean guardMegethos=false;
		boolean guardEisodos=false;
		int NumOfRows,NumOfCols,Row,Col; // row = grammi eisodou, col = grammi eksodou, num or rows k num or cols arithmi prwti seira tou txt
		File file = new File(args[0]);
		int Counter=0; // counts the elements of txt file, diladi prepei Counter= 63 ( 9,7 eisodos). counter=lines*columns
		int Columns=0; //counts the collumns 
		int lines=0; // num of lines 
		try {
		Scanner s= new Scanner(file);
		
		NumOfRows=s.nextInt();
		NumOfCols=s.nextInt();
		Row=s.nextInt();
		Col=s.nextInt();
		//count how many elements are inside.
		while (s.hasNext()) {
			Counter++;
			s.next();
			
			}//while
		
		s.close();
		 Scanner s1= new Scanner(file);
		 while (s1.hasNextLine()) // to count how many lines there are in txt. Then we find how many columns and make an array to see if the txt info is right.
		 {
			 s1.nextLine(); //go to next line
			 lines++;
		 }//while
		 s1.close();
		 lines=lines - 2;//2 extra lines in the beginning of txt file (that give us the maze info)
		 Columns=Counter/lines; // counter =lines*columns
		System.out.print("Numer of rows is: " + NumOfRows + ", number of columns is: " + NumOfCols + "\n");
		System.out.print("Entry point is located at: ("+ Row + "," + Col+")");
		
		if ((Columns==NumOfCols) && (lines==NumOfRows)) { // checks if the info given on first line of txt and the size of the maze is the same using guardMegethos.
			guardMegethos=true;
			System.out.print("\n" + "The txt file gives us the right info about the maze. \n");
		}// if prwti grammi = counters
         else { //if its not the same, print message to user.
			 System.out.println("The txt file doesnt give us the right info about the maze. \n");
		 }// else guard megethos			 
		 if (guardMegethos==true) { 
		 String[][] maze=new String[NumOfRows][NumOfCols];  //make a 2D string array NumOfRows*NumOfCols as the maze.
		   Scanner s2=new Scanner(file);
		   //skip the first 4 numbers in txt file so we can start saving the maze in the array created above.
		   s2.next();
		   s2.next();
		   s2.next();
		   s2.next();
		   // save the maze in array 
		   	  for (int i=0;i<NumOfRows;i++) {
			   for (int j=0;j<NumOfCols;j++) {
				   
				  	 maze[i][j]=s2.next();
				  
				   
			   }//for j
		   }//for i
		   s2.close(); //close scanner
		   int count=0; // counts how many "E" there are in txt file
		    for (int i=0;i<NumOfRows;i++){
				for (int j=0;j<NumOfCols;j++){
					if (maze[i][j].equals("E")) {
						count++;
					}// if E				
				}//for j
			}//for i
			  if (maze[Row][Col].equals("E") && count==1) //checks if there is only one E and if it is located in the same location as the txt file's 2nd line using GuardEisodos.
			  {
				  
				  guardEisodos=true;
				  System.out.print("There is only one entry and it is located where the txt file says.\n");
				  
				  
			  }// if gia guardEisodos
			  else { //if its not the same, print mesage to user plus the number of found "E"
				  System.out.print("\n" + "Fix the txt file.There is more/less than one E in txt file OR E is not where it is supposed to be! Number of entries= " + count+"\n");
			  } //else E>1
			     if (guardEisodos) {
					 StringStackImpl stack = new StringStackImpl(Columns*lines); //create the Stack size Columns*lines.
					 stack.push(maze[Row][Col]); //push our E in the stack.
					 boolean pathToExit = true; //guard- while we are still trying to find the exit
					 boolean steps [][] = new boolean[lines][Columns]; // boolean array that changes to true if we have stepped in that location.
					 boolean [][] hasGone = new boolean[lines][Columns];// boolean array that changes to true, if we need to go back to a previous location  because of a NoWay point infront.Changes to true, so it cant go back a second time since it needs to follow a different path.
					 for(int i=0; i<lines; i++)
					 {
						 for(int j=0; j<Columns; j++)
						 {
							 steps[i][j]=false;
							 hasGone[i][j]=false;
						 }
					 }
					 steps[Row][Col]=true;
					 
				  while(pathToExit)
				  {
					 if(Col!=0 && !maze[Row][Col-1].equals("1") && steps[Row][Col-1]==false) //checks if it can move left,if it can move, make it go to that position and push the point in the stack
					 {
						 
						 {
							 steps[Row][--Col]=true;
							 stack.push(maze[Row][Col]);
						 }
					 }
					 
					 else if(Col!=Columns-1 && !maze[Row][Col+1].equals("1") && steps[Row][Col+1]==false) //checks if it can move right,if it can move, make it go to that position and push the point in the stack
					 {
						 
						 {
							 steps[Row][++Col]=true;
							 stack.push(maze[Row][Col]);
						 }
					 }
					 
					 else if(Row!=lines-1 && !maze[Row+1][Col].equals("1") && steps[Row+1][Col]==false) //checks if it can move down,if it can move, make it go to that position and push the point in the stack
					 {
						 
						 {
							 steps[++Row][Col]=true;
							 stack.push(maze[Row][Col]);
						 }
					 }
					 
					else if(Row!=0 && !maze[Row-1][Col].equals("1") && steps[Row-1][Col]==false) //checks if it can move up, if it can move, make it go to that position and push the point in the stack
					 {
						 
						 {
							 steps[--Row][Col]=true;
							 stack.push(maze[Row][Col]);
						 }
					 }
					 
					 else //if it cant move in any direction
					 {
						 if(Row!=0 && maze[Row-1][Col].equals("0") && hasGone[Row-1][Col]==false) //check if it was previously up , if yes change hasGone to true, pop current location and move to previous loc.
						 {
							hasGone[Row][Col]=true;
							Row--;
							stack.pop();
						 }
						else if(Row!=lines-1 && !maze[Row+1][Col].equals("1") && hasGone[Row+1][Col]==false) //check if it was previously down , if yes change hasGone to true, pop current location and move to previous loc.
						{
							hasGone[Row][Col]=true;
							Row++;
							stack.pop();
						}
						else if(Col!=0 && !maze[Row][Col-1].equals("1") && hasGone[Row][Col-1]==false) //check if it was previously left , if yes change hasGone to true, pop current location and move to previous loc.
						{
							hasGone[Row][Col]=true;
							Col--;
							stack.pop();
						}
						else if(Col!=Columns-1 && !maze[Row][Col+1].equals("1") && hasGone[Row-1][Col]==false) //check if it was previously right , if yes change hasGone to true, pop current location and move to previous loc.
						{
							hasGone[Row][Col]=true;
							Col++;
							stack.pop();
						}
					 }
					 
					if(maze[Row][Col].equals("0") && (Row==0 || Row==lines-1 || Col==0 || Col==Columns-1)) //if it has found an exit, print where the exit is located.
					{
						System.out.println("The exit point is at: ("+ Row+","+Col+")");
						pathToExit=false;
					}
					 //if it cant move anywehre else because it already has Gone to every possible location, print that there is no exit in the maze and stop.
					if((Row!=0 && (hasGone[Row-1][Col]==true || !maze[Row-1][Col].equals("0"))) && (Row!=lines-1 && (hasGone[Row+1][Col]==true || !maze[Row+1][Col].equals("0"))) && (Col!=0 && (hasGone[Row][Col-1]==true || !maze[Row][Col-1].equals("0"))) && (Col!=Columns-1 && (hasGone[Row][Col+1]==true || !maze[Row][Col+1].equals("0"))))
				    {
						System.out.println("There is no exit in the maze.");
					    break;
				    }//if no exit 
				  } //while 
				 }//if guardEisodos, se auto to shmeio k oi 2 elenxei, kai an uparxei mono mia E me swstes suntetagmenes kai an oi plirofories pou dinei to txt einai swstoi ara menei mono na vroume tin eksodo tou lavirinthou.
		   
			 
		 }// if guard eisodos
		 else { // if guardMegethos= false --> if maze size is different than info given in line 1 of txt print message 
			 System.out.print("Error.First line of text file should have the right info about the array!\n");
		 }//else txtinfo1
		
		
		
		} //try. If no file is found.
		catch (FileNotFoundException e) {
			System.out.print("Error!File not found!");
		}//catch
	  		}// main	
} //class