package DesignPattern;

public class StatePattern {
    public abstract class State{
    	public abstract  String tempToDisplay(Double temperture);
    	public abstract String vibToDisplay(Double vibration);
    	public abstract String tempToSave(Double temperture);
    	public abstract String vibToSave(Double vibration);
    	
    	public String twoDecPlaces(Double value) {
    		return String.format("%.2f", value);
    	}
    	
    	public String saveForm(Double value) {
    		return String.format("%f", value);
    	}
    }
    
    
    //公制算法
    public class Metric extends State{

		@Override
		public String tempToDisplay(Double temperture) {
			// TODO Auto-generated method stub
			 System.out.println("顯示公制");
			 
			 return twoDecPlaces(temperture);
		}

		@Override
		public String vibToDisplay(Double vibration) {
			// TODO Auto-generated method stub
			 System.out.println("顯示公制");
			
			 return twoDecPlaces(vibration);
		}

		@Override
		public String tempToSave(Double temperture) {
			System.out.println("儲存公制");
			return saveForm(temperture);
		}

		@Override
		public String vibToSave(Double vibration) {
			System.out.println("儲存公制");
			return saveForm(vibration);
		}
    	
    	
    }
    
    public class British extends State{

		@Override
		public String tempToDisplay(Double temperture) {
			// TODO Auto-generated method stub
			System.out.println("顯示英制");

			return twoDecPlaces(temperture*9/5+32);
		}

		@Override
		public String vibToDisplay(Double vibration) {
			// TODO Auto-generated method stub
			System.out.println("顯示英制");
			
			return twoDecPlaces(vibration*25.4);
		}

		@Override
		public String tempToSave(Double temperture) {
			// TODO Auto-generated method stub
			System.out.println("儲存英制");

			return saveForm((temperture-32)*5/9);
		}

		@Override
		public String vibToSave(Double vibration) {
			// TODO Auto-generated method stub
			System.out.println("儲存英制");

			return saveForm(vibration/25.4);
		}
    	
    }
    
    
    public class MetricSystem{
    	private State state;
    	
    	public void setState(final State state) {
    		this.state=state;
    	}
    	
    	public  void tempView(Double temp) {
    		System.out.print(state.tempToDisplay(temp));
    	}
    	public void vibView(Double vid) {
    		System.out.println(state.vibToDisplay(vid));
    	}
    	public void tempSave(Double temp) {
    		System.out.println(state.tempToSave(temp));

    	}
    	public void vibSave(Double vib) {
    		System.out.println(state.vibToSave(vib));
    	}
    }
    
    public void Call() {
    	MetricSystem Me=new MetricSystem();
    	Me.setState(new Metric());
    	Me.tempView(50.0);
		System.out.println("\n");
    	Me.vibView(10.0);
    	Me.tempSave(50.0);
    	Me.vibSave(10.0);
    	Me.setState(new British());
    	Me.tempView(50.0);
		System.out.println("\n");
    	Me.vibView(10.0);
    	Me.tempSave(50.0);
    	Me.vibSave(10.0);
    	
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StatePattern St=new StatePattern();
		St.Call();

	}

}
