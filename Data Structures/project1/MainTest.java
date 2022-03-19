public class MainTest
{
	public static void main(String [] args)
	{
		int N = Integer.parseInt(args[0]);
		StringStackImpl s = new StringStackImpl(N);
		System.out.println(s.size());
		System.out.println(s.isEmpty());
		s.push("George");
		s.push("nikos");
		System.out.println(s.size());
		System.out.println(s.isEmpty());
		System.out.println(s.peek());
		s.printStack(System.out);
		System.out.println(s.pop());
		System.out.println(s.size());
		System.out.println(s.isEmpty());
		System.out.println(s.pop());
		System.out.println(s.isEmpty());
		System.out.println(s.size());
		System.out.println(s.pop());
	}
}