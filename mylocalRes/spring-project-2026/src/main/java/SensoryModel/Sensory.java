package SensoryModel;

import java.util.List;

public class Sensory {
private String SensorKey;
private String SensorTitle;;
private String SensorContext;
private String SensorDate;
private String SensorEmp;
private Boolean DataCheck;
private int id;


public Sensory(String SensorKey,String SensorTitle,String SensorContext,String SensorDate,String SensorEmp) {
	this.SensorKey=SensorKey;
	this.SensorTitle=SensorTitle;
	this.SensorContext=SensorContext;
	this.SensorDate=SensorDate;
	this.SensorEmp=SensorEmp;
}

public Sensory(Boolean Check) {
	this.SensorKey="";
	this.SensorTitle="";
	this.SensorContext="";
	this.SensorDate="";
	this.SensorEmp="";
	this.DataCheck=Check;
	
}

public void reSetConstruct() {
	this.SensorKey="";
	this.SensorTitle="";
	this.SensorContext="";
	this.SensorDate="";
	this.SensorEmp="";
	this.id=0;
	this.DataCheck=false;
}
public String getSensorKey() {
	return SensorKey;
}
public void setSensorKey(String sensorKey) {
	SensorKey = sensorKey;
}
public String getSensorContext() {
	return SensorContext;
}
public void setSensorContext(String sensorContext) {
	SensorContext = sensorContext;
}
public String getSensorDate() {
	return SensorDate;
}
public void setSensorDate(String sensorDate) {
	SensorDate = sensorDate;
}
public String getSensorEmp() {
	return SensorEmp;
}
public void setSensorEmp(String sensorEmp) {
	SensorEmp = sensorEmp;
}
public String getSensorTitle() {
	return SensorTitle;
}
public void setSensorTitle(String sensorTitle) {
	SensorTitle = sensorTitle;
}

public List<Sensory> getSensorList(){
	return null;
}
public Boolean getDataCheck() {
	return DataCheck;
}
public void setDataCheck(Boolean dataCheck) {
	DataCheck = dataCheck;
}

public int getId() {
	return id;
}
public void setId(int Id) {
	id= Id;
}
}
