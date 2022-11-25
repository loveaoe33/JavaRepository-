package DesignPattern;





public class TemplateMethodPattern {

	public abstract class BoosMethos
	{
		private String bossThink() { 
			return "我覺得這東西應該這樣做。:" ;
		}
		
		private String bossRedo() {
			return "你們這個跟我想做得不一樣重新做。:";
		};
		
		
		abstract String UI_Design();

	    abstract String systemAnalysis();

	    abstract String systemDesign();

	    abstract String programming();

	    abstract String test();
	    
	    
	    public void CompelteSoft() 
	    {
	    	System.out.println(bossThink()+UI_Design()+"\n"+bossThink()+systemAnalysis()+"\n"+ bossThink()+systemDesign()+"\n"+bossThink()+programming()+"\n"+bossThink()+test());
	    }
	    public void redoSoftware() 
	    {
	    	System.out.println(bossRedo());
	    	CompelteSoft();
	    }
	}
	
 public class SoftManager extends BoosMethos{

	@Override
	String UI_Design() {
		// TODO Auto-generated method stub
		  System.out.println("找一個UI Design做");
	        return "UI成果";
	}

	@Override
	String systemAnalysis() {
		// TODO Auto-generated method stub
		System.out.println("找一個軟體分析師做");
        return "軟體分析成果";
	}

	@Override
	String systemDesign() {
		// TODO Auto-generated method stub
		 System.out.println("找一個系統設計師做");
	        return "系統設計成果";
	}

	@Override
	String programming() {
		// TODO Auto-generated method stub
		System.out.println("找一個軟體工程師做");
        return "程式成果";
	}

	@Override
	String test() {
		// TODO Auto-generated method stub
		 System.out.println("找一個測試工程師做");
	        return "測試成果";
	}
		
}
 public  void Call() {
		// TODO Auto-generated method stub
		SoftManager softManager =new SoftManager();
		softManager.CompelteSoft();
		softManager.redoSoftware();
	 
 }
	

	public static void main(String[] args) {
   
		TemplateMethodPattern templateMethodPattern =new TemplateMethodPattern();
		templateMethodPattern.Call();
	}

}
