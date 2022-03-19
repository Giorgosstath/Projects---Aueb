public class Node
{
    protected int data;
    protected Node next;

    /**
     * Constructor. Sets data
     *
     * @param data the data stored
     * @return
     */
    Node(int data)
	{
        this.data = data;
		this.next=null;
    }

    /**
     * Returns this node's data
     */
    public int getData() {return data;}

    /**
     * Get reference to next node
     */
    public Node getNext() {return next;}


    /**
     * Set reference to next node
     */
    public void setNext(Node next) {this.next = next;}
}
