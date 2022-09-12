package DrugModel;

public class ErrorDrug {
	
	String ErrorNameKey;
	String ErrorName;
	
	
	
	public ErrorDrug(String errorNameKey, String errorName) {

		ErrorNameKey = errorNameKey;
		ErrorName = errorName;
	}
	public String getErrorNameKey() {
		return ErrorNameKey;
	}
	public void setErrorNameKey(String errorNameKey) {
		ErrorNameKey = errorNameKey;
	}
	public String getErrorName() {
		return ErrorName;
	}
	public void setErrorName(String errorName) {
		ErrorName = errorName;
	}

}
