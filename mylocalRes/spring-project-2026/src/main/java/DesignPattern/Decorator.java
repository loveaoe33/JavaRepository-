package DesignPattern;

public class Decorator {

	public class Restaurant {
		public void minimumOrder() {
			System.out.println("一杯飲料");

		}

		public void order() {
			minimumOrder();

		}
	}

	public class BusinessLunch extends Restaurant {
		private Restaurant restaurant;

		public BusinessLunch(Restaurant restaurant) {
			this.restaurant = restaurant;
		}

		private void salad() {
			System.out.println("一盤沙拉");
		}

		private void mainMeal() {
			System.out.println("一份主餐");
		}

		public void order() {
			super.order();
			salad();
			mainMeal();
		}
	}

	public class SimpleCombo extends BusinessLunch {

		public SimpleCombo(Restaurant restaurant) {
			super(restaurant);
			// TODO Auto-generated constructor stub
		}

		private void soup() {
			System.out.println("一份湯品");

		}

		public void order() {
			super.order();
			soup();
		}

	}

	public class FullCombo extends SimpleCombo {

		public FullCombo(Restaurant restaurant) {
			super(restaurant);
		}

		private void sweet() {
			System.out.println("一份甜點");
		}

		@Override
		public void order() {
			super.order();
			sweet();
		}
	}

	// ---------------------
	public class Order {
		public void show() {

		}
	}

	public class Item extends Order {
		protected Order order;

		public void decorate(Order order) {
			this.order = order;
		}

		public void show() {
			if (order != null)
				order.show();

		}
	}

	public class Drink extends Item {
		private void addDrink() {
			System.out.println("一份飲料");
		}

		public void show() {
			super.show();
			addDrink();
		}
	}

	public class MainMeal extends Item {

		private void addMeal() {
			System.out.println("加一份主餐");
		}

		@Override
		public void show() {
			super.show();
			addMeal();
		}
	}

	public class Salad extends Item {

		private void addSalad() {
			System.out.println("一份沙拉");
		}

		@Override
		public void show() {
			super.show();
			addSalad();
		}
	}

	public class Soup extends Item {

		private void addSoup() {
			System.out.println("加一份湯品");
		}

		@Override
		public void show() {
			super.show();
			addSoup();
		}
	}
	public class Set extends Order {
	    protected Order order;

	    public void decorate(Order order){
	        this.order = order;
	    }

	    @Override
	    public void show() {
	        if(order!= null)
	            order.show();
	    }
	}
	
	
	public class SimpleSet extends Set {

	    private void addSet(){
	        Salad salad = new Salad();
	        MainMeal mainMeal = new MainMeal();
	        Drink drink = new Drink();
	        salad.decorate(order);
	        mainMeal.decorate(salad);
	        drink.decorate(mainMeal);
	        this.order = drink;
	    }

	    @Override
	    public void show() {
	        addSet();
	        super.show();
	    }
	}
	public void Call1() {
		Restaurant restaurant = new Restaurant();
		SimpleCombo simpleCombo = new SimpleCombo(restaurant);
		System.out.println("簡餐：");
		simpleCombo.order();
		BusinessLunch businessLunch = new BusinessLunch(restaurant);
		System.out.println("商業午餐：");
		businessLunch.order();

		FullCombo fullCombo = new FullCombo(restaurant);
		System.out.println("全餐：");
		fullCombo.order();
	}

	public void Call2() {
		Order order = new Order();
		Drink drink = new Drink();
		MainMeal mainMeal = new MainMeal();
		Soup soup = new Soup();
		drink.decorate(order);
		mainMeal.decorate(drink);
		soup.decorate(mainMeal);
		soup.show();
	}
	
	public void Call3() {
		SimpleSet simpleSet = new SimpleSet();
        simpleSet.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Decorator de = new Decorator();
		de.Call2();
//		de.Call3();
	}

}
