public class List
{

    private Node head = null;
    private Node tail = null;

    public List() {}

    /**
     * @return true if list is empty
     */
    public boolean isEmpty() {return head == null;}

    /**
     * Inserts the data at the front of the list
     */
    public void insertAtFront(int data)
	{
        Node n = new Node(data);

        if (isEmpty()) 
		{
            head = n;
            tail = n;
        }
		else
		{
			n.setNext(head);
            head = n;
        }
    }

    /**
     * Returns and removes the data from the list head
     * @throws EmptyListException if the list is empty
     */
    public int removeFromFront() throws EmptyListException
	{
        if (isEmpty())
            throw new EmptyListException();

        int data = head.getData();

        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();
        return data;
    }

    /**
     * Returns and removes the data from the list tail
     * @throws EmptyListException if the list is empty
     */
    public int removeFromBack() throws EmptyListException
	{
        if (isEmpty())
            throw new EmptyListException();

        int data = tail.getData();

        if (head == tail)
            head = tail = null;
        else
		{
            Node iterator = head;
            while (iterator.getNext() != tail)
                iterator = iterator.getNext();

            iterator.setNext(null);
            tail = iterator;
        }
        return data;
    }

    /**
     * Returns list as String
     */
    public String toString()
	{
        if (isEmpty()) {return "List is empty :(";}

        Node current = head;		
		String s="";
		while(current!=null)
		{
			s=s+current.data+" ";
			current=current.next;
		}
		s=s+"\n";
		return s;
    }
}
