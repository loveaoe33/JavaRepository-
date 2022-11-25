package DesignPattern;

public class StrategyPattern {
public interface IStrategy{
	public int caculate(int a,int b);
} 

public interface IStrategy2{
	
	public int caculate(int km);
}


public class add implements IStrategy {
	
	
	public int caculate(int a,int b) {
		return a+b;
	}
}
	
   public class minus implements IStrategy{
		public int caculate(int a,int b) {
			return a-b;
		}
   }
	public class multyply implements IStrategy{
		public int caculate(int a,int b) {
			return a*b;
		}
	}
	
	public class divide implements IStrategy{
		public int caculate(int a,int b) {
			return a/b;
		}
	}
	

	public  class Calculator{
		private int now=0;
		private IStrategy iStrategy;
		
      public int execute(int a,int b) {    //策略
    	  return  iStrategy.caculate(a, b);
      }
      
      public void reset() {
    	  this.now=0;
      }
  
      public void setStrategy(String dotype) {  //工廠
    	  switch (dotype){
    		  case "ADD":
    			  this.iStrategy=new add();
    			  break;
    		  case "MINUS":
    			  this.iStrategy=new minus();
    			  break;
    		  case "DIVIDE":
    			  this.iStrategy=new divide();
    			  break;
    		  case "MULTYPLY":
    			  this.iStrategy=new multyply();
    			  break;
    	  }
    	  
      }

      
     }
     
	  public class BusStrategy implements IStrategy2{
	    	 
	    	 public int caculate(int km) {
	    		 int count=0;
	    		 if(km<10) 
	    		 {
	    			 count=1;
	    		 }else if(km>10)
	    		 {
	    			 count=2;
	    		 }
				return count*15;
	    		 
	    	 }


	     }
	     
	     
	     public class MRTStrategy  implements IStrategy2{
	    	 
	    	 public int caculate(int km) {
	    		 int result =0;
	    		 if(km<0) 
	    		 {
	    			 return result;
	    		 }else if(km<=10)
	    		 {
	    			 result=20;
	    		 }else if(km>10) 
	    		 {
	    			 int count=((km-10)/5)+1;
	    			 result=20+5*count;
	    		 }
				return result;
	    		 
	    	 }


	     }
	     
	     public class PriceCalculator{
	    	 IStrategy2 iStrategy2;
	    	 private PriceCalculator() {};
	    	 public PriceCalculator(IStrategy2 iStrategy2) {
	    		 this.iStrategy2=iStrategy2;
	    	 }
	    	 public void setiStrategy(IStrategy2 iStrategy2) {
	    		 this.iStrategy2=iStrategy2;
	    	 }
	    	 public int calculate(int km) {
	    		 return this.calculate(km);
	    	 }
	    	 public int calculate(int km , IStrategy2 strategy){
	    	        this.iStrategy2 = strategy;
	    	        return iStrategy2.caculate(km);
	    	    }
	    	 

	}
	private void CallCalu() {
		 Calculator Ca=new Calculator();
		 Ca.setStrategy("ADD");
   	 System.out.println( Ca.execute(5, 5));
		
	}
	
	private void CallPrice() {
		PriceCalculator Pr=new PriceCalculator();
	 System.out.println( Pr.calculate(5, new MRTStrategy()));

//  	 System.out.println( Pr.calculate(5, new MRTStrategy()));
		
	}
	
	public static void main(String[] args) {
		StrategyPattern St=new StrategyPattern();
		
	    St.CallCalu();
	    St.CallPrice();
	}


}
