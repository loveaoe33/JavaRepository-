package com.example.demo;
import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.Sensory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DesignPattern.CompositePattern;
import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;

@RestController
public class SensoryController {
	private AbstractSQL sqlSetting;
    private String PostDateString = "insert into Patable(id,SensorKey,SensorTitle,SensorContext,SensorDate,SensorEmp) "
		+ "select ifNULL(max(id),0)+1,?,?,?,?,? FROM sensorTable";
    private String SensoryString = "select * from sensorTable ";
    private String SensoryOneString = "";
    private String SQLConnectingSetting="jdbc:mysql://localhost/sensorTable?useUnicode=true&characterEncoding=Big5";
    private String SQLAccount="root";
    private String SQLPassword="love20720";


	
	@CrossOrigin()
	@RequestMapping("Sensory/PostData")
	public  ArrayList<Sensory> SensoryPostData(@RequestBody Map<String, String> SensoryJson ) throws SQLException, ClassNotFoundException {
		
		SQLStringSetting.PostData.setSensorKey(SensoryJson.get("SensorKey"));
		SQLStringSetting.PostData.setSensorTitle(SensoryJson.get("SensorTitle"));
		SQLStringSetting.PostData.setSensorContext(SensoryJson.get("SensorContext"));
		SQLStringSetting.PostData.setSensorDate(SensoryJson.get("SensorDate"));
		SQLStringSetting.PostData.setSensorEmp(SensoryJson.get("SensorEmp"));

		ArrayList<Sensory>SensoryAll=new ArrayList<>();
		if(sqlSetting!=null) {
			sqlSetting.ReSettSQL(PostDateString, SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.PostDate);
			return SensoryAll;
		}else
			
		{
			AbstractSQL sqlSetting=new SQLStringSetting(PostDateString,SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.PostDate);
			return SensoryAll;

		}
		

	}
	@CrossOrigin()
	@RequestMapping("Sensory/QuerySensory")
	public ArrayList<Sensory> QuerySensory() throws ClassNotFoundException, SQLException {
		ArrayList<Sensory>SensoryAll=new ArrayList<>();
		if(sqlSetting!=null) {
			sqlSetting.ReSettSQL(SensoryString, SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		}else
			
		{   
			AbstractSQL sqlSetting=new SQLStringSetting(SensoryString,SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		}
	}
	
	@CrossOrigin()
	@RequestMapping("Sensory/QuerySensoryOne")
	public ArrayList<Sensory> QuerySensoryOne(int SensorId) throws SQLException, ClassNotFoundException {
		SensoryOneString = "select * from sensorTable where id="+SensorId;
		ArrayList<Sensory>SensoryAll=new ArrayList<>();
		if(sqlSetting!=null) {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.PrintOne);
			return SensoryAll;
		}else
			
		{   
			AbstractSQL sqlSetting=new SQLStringSetting(SensoryOneString,SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.PrintOne);
			return SensoryAll;
		}
	}
	
	
//	public static void main(String[] args) throws ClassNotFoundException {
//		// TODO Auto-generated method stub
//		SensoryController Com=new SensoryController();
//		Com.test();
//	}
//	
	
}
