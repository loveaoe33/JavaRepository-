package DrugModel;
import SQL.OOPSQLTable;
public class DrugFormPa {
	private String PaName;
	private String PaGender;
    private int PaNumber;
    private String PaClass;
    private int PaAge;
    private String PaDia;
    private String PaStart;
    private String PaEnd;
    private String PaNumberKey;
    private String PaFiD;
   

    
    

	public DrugFormPa(String paName, String paGender, int paNumber, String paClass, int paAge, String paDia,
			String paFiD,String paStart, String paEnd, String paNumberKey) {

		PaName = paName;
		PaGender = paGender;
		PaNumber = paNumber;
		PaClass = paClass;
		PaAge = paAge;
		PaDia = paDia;
		PaFiD=paFiD;
		PaStart = paStart;
		PaEnd = paEnd;
		PaNumberKey = paNumberKey;
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
	public int getPaNumber() {
		return PaNumber;
	}
	public void setPaNumber(int paNumber) {
		PaNumber = paNumber;
	}
	public String getPaClass() {
		return PaClass;
	}
	public void setPaClass(String paClass) {
		PaClass = paClass;
	}
	public int getPaAge() {
		return PaAge;
	}
	public void setPaAge(int paAge) {
		PaAge = paAge;
	}
	public String getPaDia() {
		return PaDia;
	}
	public void setPaDia(String paDia) {
		PaDia = paDia;
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

	
	public static void main(String[] args) 
	  { 
		OOPSQLTable table=new OOPSQLTable();
		String x= "CREATE TABLE PaTable (" + 
			    "    id     INTEGER " + 
			    "  , PaName    VARCHAR(20) " + 
			    "  , PaGender  VARCHAR(20)" + 
			    "  , PaNumber  INTEGER(20)" + 
			    "  , PaClass  VARCHAR(20)" + 
			    "  , PaAge  INTEGER(20)" + 
			    "  , PaDia  VARCHAR(20)" + 
			    "  , PaStart  VARCHAR(20)" + 
			    "  , PaEnd  VARCHAR(20))" ;
		
		
		 table.AddString(x);
	  }
	public String getPaNumberKey() {
		return PaNumberKey;
	}
	public void setPaNumberKey(String paNumberKey) {
		PaNumberKey = paNumberKey;
	}
	public String getPaFiD() {
		return PaFiD;
	}
	public void setPaFiD(String paFiD) {
		PaFiD = paFiD;
	}

}
