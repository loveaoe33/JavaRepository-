package DesignPattern;

public class SystemArchitecture {
	enum SugarType{
		REGULAR,LESS,HALF,QUARTER,FREE
	}
	
	enum IceType{
		REGULAR,EASY,FREE,HOT
	}
	
	enum TeaType{
		LEMON,OOLONG,GINGER,HONEY
	}
	enum CoffeeTeaType{
		LATTE,MOCHA,WHITE,BLUE_MOUNTAIN,AMERICANO,ESPRESSO
	}
	public abstract class Drink{
		SugarType sugar;
		IceType ice;
		
		public Drink(SugarType sugar,IceType ice) {
			this.sugar=sugar;
			this.ice=ice;
		}
		
		public SugarType getSugarType() {
			return sugar;
		}
		
		public void setSugar(SugarType sugar) {
			this.sugar=sugar;
		}
		public IceType getIceType() {
			return ice;
		}
		public void setIceType(IceType ice) {
			this.ice=ice;
		}
	}
	
public class Tea extends Drink{

		
		TeaType teaType;
		
		public Tea(SugarType sugar, IceType ice) {
			super(sugar, ice);
			// TODO Auto-generated constructor stub
		}
		
		public void setTeatype(TeaType teaType) {
			this.teaType=teaType;
		}
		public TeaType getTeaType() {
			return teaType;
		}
	}

public class Coffee extends Drink{
    private CoffeeTeaType coffeeTeaType;
	public Coffee(SugarType sugar, IceType ice) {
		super(sugar, ice);
		// TODO Auto-generated constructor stub
	}
	
	public CoffeeTeaType getCoffeeTeaType() {
		return coffeeTeaType;
	}
	public void setCoffee(CoffeeTeaType coffeeTeaType) {
		this.coffeeTeaType=coffeeTeaType;
	}
	
}


public class DrinkShop{
	public Drink Order(String Drink,SugarType sugarType,IceType iceType) {
		Drink.toLowerCase();
		switch(Drink){
		case"coffee":
			return new Coffee(sugarType,iceType);
		case"tea":
			return new Tea(sugarType,iceType);
			default:
				return null;
		}		
		
	}
}

public void Call() {
	DrinkShop Dr=new DrinkShop();
	Coffee coffee= (Coffee)Dr.Order("coffee",SugarType.LESS , IceType.REGULAR);
	coffee.setCoffee(CoffeeTeaType.BLUE_MOUNTAIN);
	
	
	Tea tea=(Tea)Dr.Order("tea",SugarType.LESS , IceType.REGULAR);
	tea.setTeatype(TeaType.LEMON);
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SystemArchitecture sy=new SystemArchitecture();
		sy.Call();
	}

}
