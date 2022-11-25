package DesignPattern;

public class BuilderPattern {
	public static class MacbookPro{
		private Memory memory;
		private Process process;
		private Storage storage;
		private Graphics graphics;
		private Keyboard keyboard;
		MacbookPro(){};
	
		 public  class Memory{
		        int size;
		        Memory(int size){
		            this.size = size;
		        }
		    }

		    public  class Graphics{
		        String name;
		        Graphics(String name){
		            this.name = name;
		        }
		    }

		    public  class Storage{

		        int size;

		        Storage(int size){
		            this.size = size;
		        }

		    }
		    public  class Keyboard{

		        String language;

		        Keyboard(String language){
		            this.language = language;
		        }

		    }
		    
	 public void setProcess(Process process) 
	 {
		 this.process=process;
	 }
	 public void setMemory(Memory memory) 
	 {
		 this.memory=memory;
	 }
	 public void setStorage(Storage storage) 
	 {
		 this.storage=storage;
	 }
	 public void setGraphics(Graphics graphics) 
	 {
		 this.graphics=graphics;
	 }
	 public void setKeyboard(Keyboard keyboard) 
	 {
		 this.keyboard=keyboard;
	 }
	 
	 public Memory getMemory() 
	 {
		 return memory;
	 }
	 public Process getProcess() 
	 {
		 return process;
	 }
	 public Storage getStorage() 
	 {
		 return storage;
	 }
	 public Graphics getGraphics() 
	 {
		 return graphics;
	 }
	 public Keyboard getKeyboard() 
	 {
		 return keyboard;
	 }
		
	 
	 public String toString() {
		 return "{Mackbook:"+"\n"+"Process:"+this.getProcess().name+"\n"+ "Memory size :"+this.getMemory().size+"GB"+"\n"+"Graphics : "+this.getGraphics().name+"\n"+"Storage size :"+this.getStorage().size+"GB"+"\n"+"Keyboard language:"+this.getKeyboard().language+"}";
	 }
	
	    public  class Process{
	        String name;
	        Process(String name){
	            this.name = name;
	        }
	    }
	    
public abstract class MacbookProBuilder {
	
	protected MacbookPro macbookPro;
	MacbookProBuilder (){
		macbookPro=new MacbookPro();
		
	};
	
	abstract MacbookProBuilder buildCPU(MacbookPro.Process process);
	abstract MacbookProBuilder buildMEMORY(MacbookPro.Memory size);
	abstract MacbookProBuilder buildSTORAGE(MacbookPro.Storage size);
	abstract MacbookProBuilder buildGRAPHICS(MacbookPro.Graphics name);
	abstract MacbookProBuilder buildKEYBOARD(MacbookPro.Keyboard language);

	   MacbookPro build(){
	        return macbookPro;
	    }
	   
}

public class MacbookPro_2018 extends MacbookProBuilder{

	MacbookPro build() {
		return super.build();
	}
	
	MacbookPro_2018(){
		super();
	}
	
	
	@Override
	MacbookProBuilder buildCPU(MacbookPro.Process process) {
		// TODO Auto-generated method stub
		this.macbookPro.setProcess(process);
        return this;
	}

	@Override
	MacbookProBuilder buildMEMORY(MacbookPro.Memory size) {
		// TODO Auto-generated method stub
		this.macbookPro.setMemory(size);
        return this;
	}

	@Override
	MacbookProBuilder buildSTORAGE(MacbookPro.Storage size) {
		// TODO Auto-generated method stub
		   this.macbookPro.setStorage(size);
	        return this;
	}

	@Override
	MacbookProBuilder buildGRAPHICS(MacbookPro.Graphics name) {
		// TODO Auto-generated method stub
		   this.macbookPro.setGraphics(name);
	        return this;
	}

	@Override
	MacbookProBuilder buildKEYBOARD(MacbookPro.Keyboard language) {
		// TODO Auto-generated method stub
	    this.macbookPro.setKeyboard(language);
        return this;
	}
	
}

	public static void main(String[] args) {



	 }
  }

}
