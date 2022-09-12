package DrugModel;

public class DrugDetail {
 private String DrugName;
 private String DrugDose;
 private String DrugDosage;
 private String DrugRouter;
 private String DrugFrequency;
 private String DrugNumber ;
 private String ErrorNameKey;
 

 
 
public DrugDetail(String drugName, String drugDose, String drugDosage, String drugRouter, String drugFrequency,
		String drugNumber, String errorNameKey) {
	DrugName = drugName;
	DrugDose = drugDose;
	DrugDosage = drugDosage;
	DrugRouter = drugRouter;
	DrugFrequency = drugFrequency;
	DrugNumber = drugNumber;
	ErrorNameKey = errorNameKey;
}
public String getDrugName() {
	return DrugName;
}
public void setDrugName(String drugName) {
	DrugName = drugName;
}
public String getDrugDose() {
	return DrugDose;
}
public void setDrugDose(String drugDose) {
	DrugDose = drugDose;
}
public String getDrugDosage() {
	return DrugDosage;
}
public void setDrugDosage(String drugDosage) {
	DrugDosage = drugDosage;
}
public String getDrugFrequency() {
	return DrugFrequency;
}
public void setDrugFrequency(String drugFrequency) {
	DrugFrequency = drugFrequency;
}
public String getDrugNumber() {
	return DrugNumber;
}
public void setDrugNumber(String drugNumber) {
	DrugNumber = drugNumber;
}
public String getErrorNameKey() {
	return ErrorNameKey;
}
public void setErrorNameKey(String errorNameKey) {
	ErrorNameKey = errorNameKey;
}
public String getDrugRouter() {
	return DrugRouter;
}
public void setDrugRouter(String drugRouter) {
	DrugRouter = drugRouter;
}



}
