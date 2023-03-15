package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class FactoryAndSingle {

	public abstract class IDiscountStrategy {
		double discount;
		private IDiscountStrategy () {}
		public IDiscountStrategy (double discount) {
			this.discount=discount;
		}
		
		abstract public double getValue(double value) ;
	}
	
	
	//減價
	public class MinusDiscount  extends IDiscountStrategy{
       
		public MinusDiscount(double discount) {
			super(discount);
		}
		@Override
		public double getValue(double value) {
			// TODO Auto-generated method stub
			return value-discount;
		}
		
	}
	
	//打折
	public class MultiplyStrategy extends IDiscountStrategy {
	    public MultiplyStrategy(double discount) {
	        super(discount);
	    }

		@Override
		public double getValue(double value) {
			// TODO Auto-generated method stub
			return value*discount;
		}
		
	}
	
	
	public class NonDiscount extends IDiscountStrategy {
	    public NonDiscount(double discount) {
	        super(discount);
	    }
		@Override
		public double getValue(double value) {
			// TODO Auto-generated method stub
			return value;
		}
	}
	
	//飲料介面
	public abstract class Drink{
		IDiscountStrategy discountStrategy = new NonDiscount(1);
		double value;	
		private Drink() {};
		public Drink(double value) {
			this.value=value;
		}
		public Drink(double value,IDiscountStrategy discountStrategy) {
			this.value=value;
			this.discountStrategy=discountStrategy;
		}
		
		public double getValue() {
			return discountStrategy.getValue(value);
		}
	}
	
	//奶茶
	public class MilkTea extends Drink{
		public MilkTea(double value) {
			super(value);
		}
		
	    public MilkTea(double value, IDiscountStrategy discountStrategy) {
	        super(value, discountStrategy);
	    }
	    
	    public double getValue() {
	    	return super.getValue();
	    }
	}
	
	
	//咖啡
	public class Coffee extends Drink {
	    public Coffee(double value) {
	        super(value);
	    }

	    public Coffee(double value, IDiscountStrategy discountStrategy) {
	        super(value, discountStrategy);
	    }

	    @Override
	    public double getValue() {
	        return super.getValue();
	    }
	}
	
	
	public class DrinkOrder{
		private List<Drink> drinkList = new ArrayList<>();
		
		public void addDrink(Drink drink) {
			drinkList.add(drink);
		}
		
		public void removeDrink(Drink drink) {
			drinkList.remove(drink);
		}
		
		private double totalPrice() {
			double totalPrice=0;
			for(Drink d:drinkList) {
				totalPrice=totalPrice+d.getValue();
			}
			return totalPrice;
		}
		
		public double getTotalPrice(IDiscountStrategy discountStrategy) {
			double totalPrice=totalPrice();
			
			return discountStrategy.getValue(totalPrice);
			
		}
	}
	
	public void Call() {
		DrinkOrder drinkOrder=new DrinkOrder();
		drinkOrder.addDrink(new Coffee(165));
		drinkOrder.addDrink(new MilkTea(55,new MinusDiscount(20) ));
		double price=drinkOrder.getTotalPrice(new NonDiscount(0));
		 System.out.println(price);
		 
		 price=drinkOrder.getTotalPrice(new MultiplyStrategy(0.9));
		 System.out.println(price);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FactoryAndSingle FA=new FactoryAndSingle();
		FA.Call();
	}

}
