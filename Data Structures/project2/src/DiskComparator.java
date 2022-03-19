import java.util.Comparator;

public class DiskComparator implements Comparator
{
    public int compare(Object t1, Object t2)
	{
        return ((Disk) t1).getFreeSpace() - ((Disk) t2).getFreeSpace();
    }
}
