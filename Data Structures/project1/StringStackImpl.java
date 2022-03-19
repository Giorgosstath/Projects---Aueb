import java.util.*;
import java.io.*;

public class StringStackImpl implements StringStack
{
	private int size;
	private Node head;
	private class Node
	{
		private String item;
		private Node next;
		
		Node(String s,Node n)
		{
			item=s;
			next=n;
		}
	}
	
	public StringStackImpl(int maxN)
	{
		head=null;
		size=0;
	}
	
	public boolean isEmpty() {return head==null;}
	
	public void push(String s)
	{
		head = new Node(s,head);
		size++;
	}
	
	public String pop() throws NoSuchElementException
	{
		if(isEmpty())
			throw new NoSuchElementException();
		else
		{
			String s = head.item;
			head = head.next;
			size--;
			return s;
		}
	}
	
	public String peek() throws NoSuchElementException
	{
		if(isEmpty())
			throw new NoSuchElementException();
		else
		{
			String s = head.item;
			return s;
		}
	}
	
	public void printStack(PrintStream stream)
	{
		for(Node t=head; t!=null; t=t.next)
		{
			stream.print(t.item+" ");
		}
		System.out.println("");
	}
	
	public int size(){return size;}
}