package DesignPattern;

public class FactoryPattern {
public interface Product{
	public void describe();
}

public interface Factory{
	public Product getProduct();
}

public class FrenchFries implements  Product{
	String state="鹽巴";
	protected FrenchFries() {};
	protected FrenchFries(String state) {this.state=state;}
	public void describe() 
	{
		System.out.println("我是"+ state +"薯條");}
	
}

public class FrenchFriedFactory implements  Factory{
	
	
	public Product getProduct() {
		return new FrenchFries();
	}
	
	public Product getProduct(String state) {
		return new FrenchFries(state);

	}
}

public void CallMethod() 
{
	Factory FriesFac=new FrenchFriedFactory();
	Product fries=FriesFac.getProduct();
	Product myfiles=((FrenchFriedFactory) FriesFac).getProduct("特殊口味");	
fries.describe();
myfiles.describe();
	}
public static void main(String[] args) 
{
	FactoryPattern x=new FactoryPattern();
	x.CallMethod();

	
}
}

