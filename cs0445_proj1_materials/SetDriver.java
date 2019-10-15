public class SetDriver
{
	public static void main(String[] args)
	{
		Set leng=new Set(10); //makes sure that Set(int) works as expected
		System.out.println("Leng set");
		System.out.println("Size: "+leng.getSize());
		System.out.println("Empty: "+leng.isEmpty());
		System.out.println("Length: "+leng.length());
		leng.add(10);
		leng.add(5);
		leng.add(42);
		leng.add(54);
		System.out.println("Size: "+leng.getSize());
		System.out.println("Empty: "+leng.isEmpty());
		System.out.println("Length: "+leng.length());
		System.out.println("arr: "); leng.print();
		System.out.println();
		System.out.println("removed:"+leng.remove());
		System.out.println("arr: "); leng.print();
		System.out.println();
		System.out.println("removed 10: "+leng.remove(10));
		System.out.println("arr: "); leng.print();
		System.out.println();
		System.out.println("contains 42: "+leng.contains(42));
		System.out.println("contains 10: "+leng.contains(10));
		leng.clear();
		System.out.println("Size: "+leng.getSize());
		System.out.println("Empty: "+leng.isEmpty());
		System.out.println("arr: "); leng.print();
		System.out.println();

		Set stand=new Set(); //makes sure that Set() works as expected
		System.out.println("stand set");
		System.out.println("Size: "+stand.getSize());
		System.out.println("Empty: "+stand.isEmpty());
		System.out.println("Length: "+stand.length());
		stand.add(10);
		stand.add(5);
		stand.add(42);
		stand.add(54);
		System.out.println("Size: "+stand.getSize());
		System.out.println("Empty: "+stand.isEmpty());
		System.out.println("Length: "+stand.length());
		System.out.println("arr: "); stand.print();
		System.out.println();
		System.out.println("removed:"+stand.remove());
		System.out.println("arr: "); stand.print();
		System.out.println();
		System.out.println("removed 10: "+stand.remove(10));
		System.out.println("arr: "); stand.print();
		System.out.println();
		System.out.println("contains 42: "+stand.contains(42));
		System.out.println("contains 10: "+stand.contains(10));
		stand.clear();
		System.out.println("Size: "+stand.getSize());
		System.out.println("Empty: "+stand.isEmpty());
		System.out.println("arr: "); stand.print();
		System.out.println();

		Object[] in=new Object[] {1,2,3,4,5,6,7,8,9,10}; //makes sure Set(E[]) works as expected
		Set arr=new Set(in);
		System.out.println("Arr set");
		System.out.println("Size: "+arr.getSize());
		System.out.println("Empty: "+arr.isEmpty());
		System.out.println("Length: "+arr.length());
		arr.add(10);
		arr.add(5);
		arr.add(42);
		arr.add(54);
		System.out.println("Size: "+arr.getSize());
		System.out.println("Empty: "+arr.isEmpty());
		System.out.println("Length: "+arr.length());
		System.out.println("arr: "); arr.print();
		System.out.println();
		System.out.println("removed:"+arr.remove());
		System.out.println("arr: "); arr.print();
		System.out.println();
		System.out.println("removed 10: "+arr.remove(10));
		System.out.println("arr: "); arr.print();
		System.out.println();
		System.out.println("contains 42: "+arr.contains(42));
		System.out.println("contains 10: "+arr.contains(10));
		arr.clear();
		System.out.println("Size: "+arr.getSize());
		System.out.println("Empty: "+arr.isEmpty());
		System.out.println("arr: "); arr.print();
		System.out.println();

		Set overflow=new Set(1); //makes sure that the resize method works as expected
		System.out.println("Overflow set");
		System.out.println("Size: "+overflow.getSize());
		System.out.println("Empty: "+overflow.isEmpty());
		System.out.println("Length: "+overflow.length());
		overflow.add(10);
		System.out.println("Length: "+overflow.length());
		overflow.add(5);
		System.out.println("Length: "+overflow.length());
		overflow.add(42);
		System.out.println("Length: "+overflow.length());
		overflow.add(54);
		System.out.println("Size: "+overflow.getSize());
		System.out.println("Empty: "+overflow.isEmpty());
		System.out.println("Length: "+overflow.length());
		System.out.println("arr: "); overflow.print();
		System.out.println();
		System.out.println("removed:"+overflow.remove());
		System.out.println("arr: "); overflow.print();
		System.out.println();
		System.out.println("removed 10: "+overflow.remove(10));
		System.out.println("arr: "); overflow.print();
		System.out.println();
		System.out.println("contains 42: "+overflow.contains(42));
		System.out.println("contains 10: "+overflow.contains(10));
		overflow.clear();
		System.out.println("Size: "+overflow.getSize());
		System.out.println("Empty: "+overflow.isEmpty());
		System.out.println("arr: "); overflow.print();
		System.out.println();

		Set nul=new Set(); //gonna text exceptions
		System.out.println("Nul set");
		nul.add(null);
		nul.add(2);
		System.out.println();
		System.out.println("arr: "); nul.print();
	}
}