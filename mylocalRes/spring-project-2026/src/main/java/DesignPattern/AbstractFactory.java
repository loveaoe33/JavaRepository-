package DesignPattern;

public class AbstractFactory {
	public abstract class Audi {
		private String brand;
		private String type;

		public Audi() {
			this.brand = "Audi";
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getBrand() {
			return brand;
		}

		public String getType() {
			return type;
		}

	}

	public abstract class BMW {
		private String brand;
		private String type;

		public BMW() {
			this.brand = "Audi";
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getBrand() {
			return brand;
		}

		public String getType() {
			return type;
		}
	}

	public class AudiSUV extends Audi {
		public AudiSUV() {
			super();
			setType("SUV");
		}

	}

	public class AudiJeep extends Audi {
		public AudiJeep() {
			super();
			setType("Jeep");
		}
	}

	public class BMWSUV extends BMW {
		public BMWSUV() {
			super();
			setType("SUV");
		}
	}

	public class BMWJeep extends BMW {
		public BMWJeep() {
			super();
			setType("Jeep");
		}
	}

	public abstract class AbstractFactorys {
      public abstract Audi createAudi();
      public abstract BMW createBMW();
	}
	
	public class JeepFactory extends AbstractFactorys{

		@Override
		public Audi createAudi() {
			// TODO Auto-generated method stub
		
			return new AudiJeep();
		}

		@Override
		public BMW createBMW() {
			// TODO Auto-generated method stub
			return new BMWJeep();
		}
		
	}
	
	public class SUVFactory extends AbstractFactorys {
	    @Override
	    public Audi createAudi() {
	        return new AudiSUV();
	    }

	    @Override
	    public BMW createBMW() {
	        return new BMWSUV();
	    }
	}
	
    public void Call() {
    	AbstractFactorys FactorySUV=new SUVFactory();
    	System.out.println("--------SUV---------");
    	Audi suvAudi=FactorySUV.createAudi();
    	System.out.println(suvAudi.brand+"的"+suvAudi.getType());
    	BMW suvbmw= FactorySUV.createBMW();
    	System.out.println(suvbmw.brand+"的"+suvbmw.getType());
    	
    	System.out.println("--------SUV---------");
    	AbstractFactorys FactoryJepp=new JeepFactory();

    	
     	Audi JeepAudi=FactoryJepp.createAudi();
    	System.out.println(JeepAudi.brand+"的"+JeepAudi.getType());
    	BMW Jeepbmw= FactoryJepp.createBMW();
    	System.out.println(Jeepbmw.brand+"的"+Jeepbmw.getType());

    	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractFactory AF=new AbstractFactory();
		AF.Call();
	}

}
