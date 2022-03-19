import java.util.*;
import java.io.*;

public class StringQueueWithOnePointer implements StringQueue
{
	private int size;
	private Node ptr;
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
	
	public StringQueueWithOnePointer(int maxN)
	{
		ptr = null;
		size = 0;
	}
	
	public boolean isEmpty(){return ptr == null;}

	public void put(String item)
	{
		Node t = new Node(item);
		if(isEmpty())
		{
			ptr = t;
			ptr.next = ptr;
		}
		else
		{
			t.next = ptr.next;
			ptr.next = t;
			ptr = t;
		}
		size++;
	}

	public String get() throws NoSuchElementException
	{
		if(isEmpty())
			throw new NoSuchElementException();
		else
		{
			String r_v = ptr.next.item;
			if(size==1)
				ptr=null;
			else
				ptr.next = ptr.next.next;
			size--;
			return r_v;
		}
	}

	public String peek() throws NoSuchElementException
	{
		if(isEmpty())
			throw new NoSuchElementException();
		else
			return ptr.next.item;
	}

	public void printQueue(PrintStream stream)
	{
		Node current = ptr.next;
		do
		{
			System.out.print(current.item+" ");
			current = current.next;
		}
		while(current!=ptr.next);
		System.out.println("");
	}

	public int size(){return size;}
}