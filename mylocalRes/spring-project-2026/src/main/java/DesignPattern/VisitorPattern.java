package DesignPattern;

public class VisitorPattern {
	
public interface Visit{
	public void visit(AirQuality airQuality);
	public void visit(Temperture temperture);
}
 public abstract class Weather{
	 double temperture;
	 double airQuality;
	 public Weather() {
		 temperture=Math.random()*30;
		 airQuality=Math.random()*300;
	 }
	 
	 public abstract void accept(Visit visit);
 }
 
 
 
 public class Temperture extends Weather{
    public Temperture() {
    	super();
    }
	  public String getTempGraph() {
		  return "溫度趨勢";
	  }
	@Override
	public void accept(Visit visit) {
		// TODO Auto-generated method stub
		visit.visit(this);
	}
	 
 }
 
 
 public class AirQuality extends Weather{
	   public AirQuality() {
	    	super();
	    }
	  public String getAirQGraph() {
		  return "空氣品質趨勢";
	  }
	@Override
	public void accept(Visit visit) {
		// TODO Auto-generated method stub
		visit.visit(this);
	}
	 
 }
 
 
 
 public class Man implements Visit{

	@Override
	public void visit(AirQuality airQuality) {
		// TODO Auto-generated method stub
		System.out.println("稍微看一下空氣品質"+airQuality.getAirQGraph());
	}

	@Override
	public void visit(Temperture temperture) {
		// TODO Auto-generated method stub
		System.out.println("關心一下溫度"+temperture.temperture);

	}
	 
 }
 
 public class Women implements Visit{

	@Override
	public void visit(AirQuality airQuality) {
		// TODO Auto-generated method stub
		System.out.println("關心一下空氣品質"+airQuality.airQuality);

	}

	@Override
	public void visit(Temperture temperture) {
		// TODO Auto-generated method stub
		System.out.println("稍微看一下溫度"+temperture.getTempGraph());

	}
	 
 }
 
 public void Call() {
	 Temperture temperture=new Temperture ();
	 AirQuality airQuality=new AirQuality ();
	 
	 Visit man=new Man();
	 Visit woman=new Women();
     System.out.println("----- 測試visit -----");
	 temperture.accept(woman);
	 temperture.accept(man);
	 
     System.out.println("----- 男生 -----");
     man.visit(airQuality);
     man.visit(temperture);
     System.out.println("----- 女生 -----");
     woman.visit(airQuality);
     woman.visit(temperture);
	 
 }
 
 
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VisitorPattern vi=new VisitorPattern();
		vi.Call();
	}

}
