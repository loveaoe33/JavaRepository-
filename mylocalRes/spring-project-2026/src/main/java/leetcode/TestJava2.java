package leetcode;

public class TestJava2 {

	interface IStrategy {
	    public int caculate(int a , int b);
	}
	
	
	public class add implements IStrategy {
		int a=5;
		int b=6;
		   public int caculate(int a, int b) {
		        System.out.println(a+b);
				return a+b;
		    }
	}
	
	
	public class add2
	{
		public void add22 (int c, int d) 
		{
	        System.out.println(c+d);

		}
	}
	
	public void testfun() 
	{
		final IStrategy strategy;
		final add2 addtest;
	
		strategy=new add();
		addtest=new add2();
		addtest.add22(5, 3);
		strategy.caculate(5, 5);
        System.out.println("測試FUN");

	}
	public static void main(String[] args) {
		TestJava2 x=new TestJava2();
		x.testfun();
	}

}
