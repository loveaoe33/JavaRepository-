package DrugModel;

//import SQL.OOPSQLTable;

public class DrugFormMRession {
	private String WorkStatusProcess;
	private String DrugInfoStatusProcess;
	private String EnvironmentStatusProcess;
	private String PhysiologicalStatusProcess;
	private String PersonStatusProcess;
	private String CommunicateStatusProcess;
	private String OtherStatusProcess;
	
	

	
	public DrugFormMRession(String workStatusProcess, String drugInfoStatusProcess, String environmentStatusProcess,
			String physiologicalStatusProcess, String personStatusProcess, String communicateStatusProcess,
			String otherStatusProcess) {
	
		WorkStatusProcess = workStatusProcess;
		DrugInfoStatusProcess = drugInfoStatusProcess;
		EnvironmentStatusProcess = environmentStatusProcess;
		PhysiologicalStatusProcess = physiologicalStatusProcess;
		PersonStatusProcess = personStatusProcess;
		CommunicateStatusProcess = communicateStatusProcess;
		OtherStatusProcess = otherStatusProcess;
	}
	public String getWorkStatusProcess() {
		return WorkStatusProcess;
	}
	public void setWorkStatusProcess(String workStatusProcess) {
		WorkStatusProcess = workStatusProcess;
	}
	public String getDrugInfoStatusProcess() {
		return DrugInfoStatusProcess;
	}
	public void setDrugInfoStatusProcess(String drugInfoStatusProcess) {
		DrugInfoStatusProcess = drugInfoStatusProcess;
	}
	public String getEnvironmentStatusProcess() {
		return EnvironmentStatusProcess;
	}
	public void setEnvironmentStatusProcess(String environmentStatusProcess) {
		EnvironmentStatusProcess = environmentStatusProcess;
	}
	public String getPhysiologicalStatusProcess() {
		return PhysiologicalStatusProcess;
	}
	public void setPhysiologicalStatusProcess(String physiologicalStatusProcess) {
		PhysiologicalStatusProcess = physiologicalStatusProcess;
	}
	public String getCommunicateStatusProcess() {
		return CommunicateStatusProcess;
	}
	public void setCommunicateStatusProcess(String communicateStatusProcess) {
		CommunicateStatusProcess = communicateStatusProcess;
	}
	public String getOtherStatusProcess() {
		return OtherStatusProcess;
	}
	public void setOtherStatusProcess(String otherStatusProcess) {
		OtherStatusProcess = otherStatusProcess;
	}
	public String getPersonStatusProcess() {
		return PersonStatusProcess;
	}
	public void setPersonStatusProcess(String personStatusProcess) {
		PersonStatusProcess = personStatusProcess;
	}

	

	

	
	
	
}
