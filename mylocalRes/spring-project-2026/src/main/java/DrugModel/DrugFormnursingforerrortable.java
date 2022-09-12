package DrugModel;

public class DrugFormnursingforerrortable {

	private String NursingForErrorKey;
	private String ErrorEvent;
	public DrugFormnursingforerrortable(String nursingForErrorKey, String errorEvent) {
		NursingForErrorKey = nursingForErrorKey;
		ErrorEvent = errorEvent;
	}
	public String getNursingForErrorKey() {
		return NursingForErrorKey;
	}
	public void setNursingForErrorKey(String nursingForErrorKey) {
		NursingForErrorKey = nursingForErrorKey;
	}
	public String getErrorEvent() {
		return ErrorEvent;
	}
	public void setErrorEvent(String errorEvent) {
		ErrorEvent = errorEvent;
	}
	
}
