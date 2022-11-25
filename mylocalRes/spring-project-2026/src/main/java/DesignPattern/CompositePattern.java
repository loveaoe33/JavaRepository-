package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class CompositePattern {

	
	public abstract class Component{
		String name;
		List<Component> componentList =new ArrayList<>();
		
	   public Component(String name) {
		   this.name=name;
	   }
	   
	   public void add(Component component) {
		   componentList.add(component);
	   }
	   public void remove(Component component) {
		   componentList.remove(component);
	   }
	   
	   public void display(int depth) {
		   for(int i=0;i<depth;i++) {
			   System.out.print("-");
		   }
		   System.out.println(this.name );
		   for(Component c: componentList) {
			   c.display(depth+2);
		   }

	   }
	   
	   
	   public void command(String command) {
		   System.out.println(this.name+""+command);
		   for(Component c:componentList) {
			   c.command(command);
		   }
	   }
	}
	
	
	public class Composite extends Component{
		public Composite(String name) {
			super(name);
		}
		
	}
	
	public class Leaf extends Component{
		public Leaf(String name) {
			super(name);
		}
		
		public void add(Component component) {
			 System.out.println("Leaf cant add component");
		}
		public void remove(Component component) {
			System.out.println("Leaf cant remove component");
		}
		
		public void display(int depth) {
			super.display(depth);
		}
	}
	
	
	public void Call() {
		Component big = new Composite("大公司");

        Component mid1 = new Composite("子公司1");
        Component mid2 = new Composite("子公司2");

        Component small1 = new Leaf("部門1");
        Component small2 = new Leaf("部門2");
        Component small3 = new Leaf("部門3");
        Component small4 = new Leaf("部門4");

        big.add(mid1);
        big.add(mid2);

        mid1.add(small1);
        mid1.add(small2);

        mid2.add(small3);
        mid2.add(small4);

        Component small5 = new Leaf("部門5");

        small1.add(small5);

        System.out.println("------- 大公司 -------");
//        公司階層
        big.display(2);
//        大老闆發命令所有人都會接收到
        big.command("開發快一點");

        System.out.println("------- 子公司 -------");

        mid2.display(1 );
//        子公司命令只有子公司下面的階層可以接收到
        mid2.command("大老闆說Q4要完成");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompositePattern Com=new CompositePattern();
		Com.Call();
	}

}
