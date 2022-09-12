package DrugModel;

import SQL.OOPSQLTable;

public class DrugFormReassion {
	private String AboutOtherEvent;
	private String PreScript;
	private String DeliveryProcessEvent;
	private String PharMacyEvent;
	private String NursingReEvent;
	private String OtherEvent;
    private String NursingForErrorKey;
    
	public DrugFormReassion(String aboutOtherEvent, String preScript, String deliveryProcessEvent, String pharMacyEvent,
			String nursingReEvent, String otherEvent, String nursingForErrorKey) {
		AboutOtherEvent = aboutOtherEvent;
		PreScript = preScript;
		DeliveryProcessEvent = deliveryProcessEvent;
		PharMacyEvent = pharMacyEvent;
		NursingReEvent = nursingReEvent;
		OtherEvent = otherEvent;
		NursingForErrorKey = nursingForErrorKey;
	}
	
	
	public String getAboutOtherEvent() {
		return AboutOtherEvent;
	}

	public void setAboutOtherEvent(String aboutOtherEvent) {
		AboutOtherEvent = aboutOtherEvent;
	}

	public String getPreScript() {
		return PreScript;
	}

	public void setPreScript(String preScript) {
		PreScript = preScript;
	}

	public String getDeliveryProcessEvent() {
		return DeliveryProcessEvent;
	}

	public void setDeliveryProcessEvent(String deliveryProcessEvent) {
		DeliveryProcessEvent = deliveryProcessEvent;
	}

	public String getPharMacyEvent() {
		return PharMacyEvent;
	}


	public void setPharMacyEvent(String pharMacyEvent) {
		PharMacyEvent = pharMacyEvent;
	}

	public String getNursingReEvent() {
		return NursingReEvent;
	}

	public void setNursingReEvent(String nursingReEvent) {
		NursingReEvent = nursingReEvent;
	}

	public String getOtherEvent() {
		return OtherEvent;
	}

	public void setOtherEvent(String otherEvent) {
		OtherEvent = otherEvent;
	}

	

	public static void main(String[] args) {
//		OOPSQLTable table = new OOPSQLTable();
//		String x = "CREATE TABLE PaTableDrug (" + "    id     INTEGER " + "  , PaNumberKey    VARCHAR(20) "
//				+ "  , AboutOtherEvent  VARCHAR(225)" + "  , PreScript  VARCHAR(225)"
//				+ "  , DeliveryProcessEvent  VARCHAR(225)" + "  , PharMacyEvent  VARCHAR(225)"
//				+ "  , NursingReEvent  VARCHAR(225)" + "  , OtherEvent  VARCHAR(225)"
//				+ "  , PharNonFor  VARCHAR(225) "+" ,NursionNonFor  VARCHAR(225)"+",PharFor  VARCHAR(225)"
//				+ ",WorkStatusProcess  VARCHAR(225) "+",DrugInfoStatusProcess  VARCHAR(225)"
//				+ ",EnvironmentStatusProcess  VARCHAR(225) "+",PhysiologicalStatusProcess  VARCHAR(225)"
//				+ ",PersonStatusProcess  VARCHAR(225)"+",CommunicateStatusProcess  VARCHAR(225)"
//				+ ",OtherStatusProcess  VARCHAR(225)"+",ProcessMethod  VARCHAR(225)"
//				+ ",Suggest  VARCHAR(225) "+",DrugName  VARCHAR(225)"
//				+ ",DrugDose  VARCHAR(225)"+",DrugDosage  VARCHAR(225)"
//				+ ",DrugRouter  VARCHAR(225)"+",DrugFrequency  VARCHAR(225)"
//				+ ",DrugNumber  VARCHAR(225)"+",ErrorName  VARCHAR(225)"+",EmployeeID  VARCHAR(225)"+",EmployeeName  VARCHAR(225))";
//
//		table.AddString(x);
		
		
		
//		OOPSQLTable table = new OOPSQLTable();錯誤給藥資料表
//		String x="CREATE TABLE PharForTable (" + "    id     INTEGER " + "  , PharForKey    VARCHAR(20) "+ ",EvenForPa VARCHAR(225)"+",PaForEven  VARCHAR(225))";
//		table.AddString(x);
		
//		
//		OOPSQLTable table = new OOPSQLTable();錯誤藥品名稱資料表
//		String x="CREATE TABLE ErrorDrugTable (" + "    id     INTEGER " + "  , ErrorNameKey    VARCHAR(20) "+ ",ErrorName VARCHAR(225))";
//		table.AddString(x);
		
		
		OOPSQLTable table = new OOPSQLTable();
		String x="CREATE TABLE NursingForErrorTable (" + "    id     INTEGER " + "  , NursingForErrorKey    VARCHAR(20) "+ ",ErrorEvent VARCHAR(225))";
		table.AddString(x);
	}

	public String getNursingForErrorKey() {
		return NursingForErrorKey;
	}

	public void setNursingForErrorKey(String nursingForErrorKey) {
		NursingForErrorKey = nursingForErrorKey;
	}
}
