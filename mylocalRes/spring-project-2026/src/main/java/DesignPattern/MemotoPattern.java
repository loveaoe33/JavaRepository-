package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class MemotoPattern {

	public class ComputerMemoto{
		String system;
		String hardisk;
		String userDoc;
		String userApp;
		public ComputerMemoto(String system,String hardisk,String userDoc,String userApp) {
			this.system=system;
			this.hardisk=hardisk;
			this.userDoc=userDoc;
			this.userApp=userApp;
		}
		
		public String getSystem() {
			return system;
		}
		public void setSystem(String system) {
			this.system=system;
		}
		public String getHardisk() {
			return hardisk;
		}
		public void setHardisk(String hardisk) {
			this.hardisk=hardisk;
		}
		
		public String getUserDoc() {
			return userDoc;
		}
		public void setUserDoc() {
			this.userDoc=userDoc;
		}
		public String getuserAPP() {
			return userApp;
		}
		public void getuserApp(String userApp) {
			this.userApp=userApp;
		}
	}
	
	public class ComputerOriginator {
		private String system="macOs";
		private String hardisk="ssd";
		private String userDoc="desk";
		private String userApp="pages,number...";
		
		public ComputerMemoto save() {
			return new ComputerMemoto(system,hardisk,userDoc,userApp);
		}
		
		
		public void restore(ComputerMemoto computerMemoto) {
			this.system=computerMemoto.system;
			this.hardisk=computerMemoto.hardisk;
			this.userDoc=computerMemoto.userDoc;
			this.userApp=computerMemoto.userApp;
		}
		
		 public String getSystem() {
		        return system;
		    }

		    public void setSystem(String system) {
		        this.system = system;
		    }

		    public String getHardisk() {
		        return hardisk;
		    }

		    public void setHardisk(String hardisk) {
		        this.hardisk = hardisk;
		    }

		    public String getUserDoc() {
		        return userDoc;
		    }

		    public void setUserDoc(String userDoc) {
		        this.userDoc = userDoc;
		    }

		    public String getUserApp() {
		        return userApp;
		    }

		    public void setUserApp(String userApp) {
		        this.userApp = userApp;
		    }
	}
	
	
	public class ComputerCareTker{
		
		private static final int max=15;
		public List<ComputerMemoto> saves=new ArrayList<>();
		
		public ComputerMemoto getSave(int index) {
			if(index>saves.size()-1) {
				index=saves.size()-1;
			}
			return saves.get(index);
		}
		
		public void saveMemoto(ComputerMemoto computerMemoto) {
			if(saves.size()>max) {
				saves.remove(0);
			}
			saves.add(computerMemoto);
		}
	}
	
	public void  Call() {
		ComputerCareTker careTaker = new ComputerCareTker();
        ComputerOriginator originator = new ComputerOriginator();
        careTaker.saveMemoto(originator.save());
        System.out.println(careTaker.getSave(0).getSystem());
        
        originator.setSystem("Android");
        careTaker.saveMemoto(originator.save());
        System.out.println(careTaker.getSave(1).getSystem());
        System.out.println(originator.system);
        originator.restore(careTaker.getSave(0)); //復原
        System.out.println(originator.system);  //

        
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MemotoPattern me=new MemotoPattern();
		me.Call();

	}

}
