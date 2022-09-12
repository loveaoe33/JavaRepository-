package DrugModel;

public class PharFor {

	
	String PharForKey;
	String EvenForPa;
	String PaForEven;
	
	
	
	public PharFor(String pharForKey, String evenForPa, String paForEven) {
	
		PharForKey = pharForKey;
		EvenForPa = evenForPa;
		PaForEven = paForEven;
	}
	public String getPharForKey() {
		return PharForKey;
	}
	public void setPharForKey(String pharForKey) {
		PharForKey = pharForKey;
	}
	public String getEvenForPa() {
		return EvenForPa;
	}
	public void setEvenForPa(String evenForPa) {
		EvenForPa = evenForPa;
	}
	public String getPaForEven() {
		return PaForEven;
	}
	public void setPaForEven(String paForEven) {
		PaForEven = paForEven;
	}
}
