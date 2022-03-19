import java.util.*;
import java.io.*;

public class StringQueueImpl implements StringQueue
{
	private int size;
	private Node head,tail;
	private class Node
	{
		private String item;
		private Node next;
		Node(String s)
		{
			item=s;
			next=null;
		}
	}
	
	public StringQueueImpl(int maxN)
	{
		head=null;
		tail=null;
		size = 0;
	}
	
	public boolean isEmpty(){return head==null;}
	
	public void put(String item)
	{
		Node t = tail;
		tail = new Node(item);
		if(isEmpty())
			head=tail;
		else
			t.next=tail;
		size++;
	}
	
	public String get() throws NoSuchElementException
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
	
	public void printQueue(PrintStream stream)
	{
		for(Node t=head; t!=null; t=t.next)
		{
			stream.print(t.item+" ");
		}
	}
	
	public int size(){return size;}
}