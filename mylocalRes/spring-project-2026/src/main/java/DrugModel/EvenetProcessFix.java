package DrugModel;

public class EvenetProcessFix {
	private String ProcessMethod;
	private String Suggest;
	
	
	
	public EvenetProcessFix(String processMethod, String suggest) {
		ProcessMethod = processMethod;
		Suggest = suggest;
	}
	public String getProcessMethod() {
		return ProcessMethod;
	}
	public void setProcessMethod(String processMethod) {
		ProcessMethod = processMethod;
	}
	public String getSuggest() {
		return Suggest;
	}
	public void setSuggest(String suggest) {
		Suggest = suggest;
	}

}
