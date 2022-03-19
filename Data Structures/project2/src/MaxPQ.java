import java.util.Comparator;

public class MaxPQ
{
    private Disk[] heap; // the heap to store data in
    private int size; // current size of the queue
    private DiskComparator comparator; // the comparator to use between the objects

    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow


    public MaxPQ(DiskComparator comparator)
	{
        this.heap = new Disk[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }
	
	public int getSize(){return size;}

    /**
     * Inserts the specified element into this priority queue.
     */
    public void add(Disk item)
	{
        if (size == heap.length - 1)
            grow();
        heap[++size] = item;
        swim(size);
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     */
    public Disk peek()
	{
        if (size == 0)
            return null;
        return heap[1];
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    public Disk getMax()
	{
        if (size == 0)
            return null;

        Disk root = heap[1];
        heap[1] = heap[size];
        size--;
        sink(1);
        return root;
    }

    /**
     * Helper function to swim items to the top
     */
    private void swim(int i)
	{
        if (i == 1)
            return;

        int parent = i / 2;   //parent of i
        while (i != 1 && comparator.compare(heap[i], heap[parent]) > 0)
		{
            swap(i, parent);
            i = parent;
            parent = i / 2;
        }
    }

    /**
     * Helper function to swim items to the bottom
     */
    private void sink(int i)
	{
        int left = 2 * i;      //left child of i
        int right = left + 1;     //right child of i

        if (left > size)
            return;
		
        while (left <= size)
		{
            // Determine the largest child of node i
            int max = left;
            if (right <= size)
			{
                if (comparator.compare(heap[left], heap[right]) < 0)
                    max = right;
            }

            // If the heap condition holds, stop. Else swap and go on.
            // child smaller than parent
            if (comparator.compare(heap[i], heap[max]) >= 0)
                return;
            else
			{
                swap(i, max);
                i = max;
                left = i * 2;
                right = left + 1;
            }
        }
    }

    /**
     * Helper function to swap two elements in the heap
     */
    private void swap(int i, int j)
	{
    	Disk tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Helper function to grow the size of the heap
     */
    private void grow()
	{
    	Disk[] newHeap = new Disk[heap.length + AUTOGROW_SIZE];
		for (int i = 0; i <= size; i++)
           newHeap[i] = heap[i];
        heap = newHeap;
    }
}
