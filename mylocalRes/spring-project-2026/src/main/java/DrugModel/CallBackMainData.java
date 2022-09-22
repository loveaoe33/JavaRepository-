package DrugModel;

public class CallBackMainData {
	
	private String PaName ;
	private String  PaGender;
	private String  PaNumber;
	private String PaClass;
	private String PaAge;
	private String PaDia;
	private String PaFiD;
	private String PaStart;
	private String PaEnd;
	private String PaNumberKey;
	private String BuildEmp;
	private String BuildDate;
	private int id;


	public CallBackMainData(int Id,String paName, String paGender, String paNumber, String paClass, String paAge, String paDia,
			String paFiD, String paStart, String paEnd, String paNumberKey, String buildEmp, String buildDate) {
		id=Id;
		PaName = paName;
		PaGender = paGender;
		PaNumber = paNumber;
		PaClass = paClass;
		PaAge = paAge;
		PaDia = paDia;
		PaFiD = paFiD;
		PaStart = paStart;
		PaEnd = paEnd;
		PaNumberKey = paNumberKey;
		BuildEmp = buildEmp;
		BuildDate = buildDate;
	}


	public int getid() {
		return id;
	}


	public void setid(int Id) {
		id = Id;
	}
	
	
	public String getPaName() {
		return PaName;
	}


	public void setPaName(String paName) {
		PaName = paName;
	}


	public String getPaGender() {
		return PaGender;
	}


	public void setPaGender(String paGender) {
		PaGender = paGender;
	}


	public String getPaNumber() {
		return PaNumber;
	}


	public void setPaNumber(String paNumber) {
		PaNumber = paNumber;
	}


	public String getPaClass() {
		return PaClass;
	}


	public void setPaClass(String paClass) {
		PaClass = paClass;
	}


	public String getPaAge() {
		return PaAge;
	}


	public void setPaAge(String paAge) {
		PaAge = paAge;
	}


	public String getPaDia() {
		return PaDia;
	}


	public void setPaDia(String paDia) {
		PaDia = paDia;
	}


	public String getPaFiD() {
		return PaFiD;
	}


	public void setPaFiD(String paFiD) {
		PaFiD = paFiD;
	}


	public String getPaStart() {
		return PaStart;
	}


	public void setPaStart(String paStart) {
		PaStart = paStart;
	}


	public String getPaEnd() {
		return PaEnd;
	}


	public void setPaEnd(String paEnd) {
		PaEnd = paEnd;
	}


	public String getPaNumberKey() {
		return PaNumberKey;
	}


	public void setPaNumberKey(String paNumberKey) {
		PaNumberKey = paNumberKey;
	}


	public String getBuildEmp() {
		return BuildEmp;
	}


	public void setBuildEmp(String buildEmp) {
		BuildEmp = buildEmp;
	}


	public String getBuildDate() {
		return BuildDate;
	}


	public void setBuildDate(String buildDate) {
		BuildDate = buildDate;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
