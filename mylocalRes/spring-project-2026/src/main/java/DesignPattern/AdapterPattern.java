package DesignPattern;

public class AdapterPattern {

  public class BlackMen{
	  String name;
	  public BlackMen(String name) {
		  this.name=name;
	  }
	  
	  public void sayEnglish() {
		  System.out.println("Yo~What's Up Nigga~");
	  }
	  public void sayHello() {
		  System.out.println("Hello I living in Ka. My Nmae is"+this.name+".");
	  }
  }
  
  public abstract class People{
	  String name;
	  public People(String name) {
		  this.name=name;  }
	  public abstract void hello();
	  public abstract void selfIntro();
	  public String getName() {  return name;
	  }
  }
  
  public class BlackMenTranslator extends People{

	public BlackMenTranslator(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void hello() {
		System.out.println(super.getName()+":吃飽沒");
		
	}

	@Override
	public void selfIntro() {
		System.out.println("大家好我是"+super.getName()+"我現在在台北工作");		
	}
	  
  }
  
	public class TaiwanMen{
		private People people;
		public TaiwanMen(People people) {
			this.people=people;
		}
		public void hello() {
			people.hello();
		}
		public void selfIntro() {
			people.selfIntro();
		}
	}
	
  
  public void Call() {
	  BlackMen black=new BlackMen("老黑");
	  black.sayEnglish();
	  black.sayHello();
	  TaiwanMen taiwanMen=new TaiwanMen(new BlackMenTranslator(black.name) );
	  taiwanMen.hello();
	  taiwanMen.selfIntro();
	  
  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdapterPattern ada=new AdapterPattern();
		ada.Call();

	}
}


