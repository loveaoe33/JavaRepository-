package DesignPattern;

import org.springframework.core.io.Resource;

public class BridgePattern {
	
public abstract class View{
 Resources resources;

 public void setResources(Resources resources) {
	 this.resources=resources;
 }
 public abstract void show();
}

public interface Resources{
	public void photo();
	public void snippet();
	public void describe();
}

public class Book implements Resources{

	@Override
	public void photo() {
		 System.out.println("書本的照片");		
	}

	@Override
	public void snippet() {
		System.out.println("書本的簡短說明");		
	}

	@Override
	public void describe() {
		System.out.println("書本詳細說明");		
	}
	
	
}

public class Bag implements Resources{

	@Override
	public void photo() {
		// TODO Auto-generated method stub
		System.out.println("包包的照片");
	}

	@Override
	public void snippet() {
		// TODO Auto-generated method stub
		System.out.println("包包的簡短說明");
	}

	@Override
	public void describe() {
		// TODO Auto-generated method stub
		  System.out.println("包包詳細說明");
	}
	
}

public class FewView extends View{
	public void show() {
		resources.photo();
		resources.snippet();
	}
}

public class FullView extends View{
	public void show() {
		resources.photo();
		resources.describe();
	}


}

public void Call() {
	FewView fewView=new FewView();
	System.out.println("---包包說明----");
	fewView.setResources(new Bag());
	fewView.show();
    fewView.show();
    System.out.println("---- 簡單書本說明 ----");
    fewView.setResources(new Book());
    fewView.show();
    
    
    FullView Fv=new FullView();
    System.out.println("---- 詳細包包說明 ----");
    Fv.setResources(new Bag());
    Fv.show();
    System.out.println("---- 詳細書本說明 ----");
    Fv.setResources(new Book());
    Fv.show();
    
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		BridgePattern br=new BridgePattern();
		br.Call();
	}

}
