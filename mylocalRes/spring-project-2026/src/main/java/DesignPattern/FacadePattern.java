package DesignPattern;

public class FacadePattern {

  
  public class SubClassOne{
	  public void methodOne() 
	  {
		  
	        System.out.println(1);

	  }
  }
  public class SubClassTwo{
	  public void methodTwo() 
	  {
		  
	        System.out.println(2);

	  }
  }
  public class SubClassThree{
	  public void methodThree() 
	  {
	        System.out.println(3);

		  
	  }
  }
  public class ClassOne{
	  public void methodFour() 
	  {
	        System.out.println(4);

		  
	  }
  }
  
  public class Facade{
	  private SubClassOne one;
	  private SubClassTwo two;
	  private SubClassThree three;
	  private ClassOne four;

	  
	  public Facade() {
		    one = new SubClassOne();
	        two = new SubClassTwo();
	        three = new SubClassThree();
	        four = new ClassOne();
	  }
	  
	  public void Methoda() 
	  {
	        System.out.println("MethodA:");
		  four.methodFour();
		  one.methodOne();
	  }
	  public void Methodb() 
	  {
		  System.out.println("MethodB:");
		  two.methodTwo();
		  three.methodThree();
	  }
  }
  
  
  public void call() {
	  Facade F=new Facade();
	  F.Methoda();
	  F.Methodb();
  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FacadePattern F=new FacadePattern();
		F.call();
	}

}
