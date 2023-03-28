package com.example.demo;
import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.Sensory;
import SensoryModel.SensoryLibra;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DesignPattern.CompositePattern;
import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;

@RestController
public class SensoryController {
	private AbstractSQL sqlSetting;
    private String PostDateString = "insert into sensorTable(id,SensorKey,SensorTile,SensorContext,SensorDate,SensorEmp) "
		+ "select ifNULL(max(id),0)+1,?,?,?,?,? FROM sensorTable";
    private String SensoryString = "select * from sensorTable ORDER BY SensorDate DESC";
    private String SensoryOneString = "";
    private String SQLConnectingSetting="jdbc:mysql://localhost/drugsql?useUnicode=true&characterEncoding=Big5";
    private String SQLAccount="root";
    private String SQLPassword="love20720";
    private static ArrayList<Sensory>SensoryAll=new ArrayList<>();
   


	
	@CrossOrigin()
	@PostMapping("Sensory/PostData")
	public  ArrayList<Sensory> SensoryPostData(@RequestBody JSONObject SensryPOST ) throws SQLException, ClassNotFoundException {
		SensoryLibra.TransFun(SensryPOST);
		SensoryAll.clear();
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
	@PostMapping("Sensory/test")
	public String test() {
		return "1234";
	}
	
	@CrossOrigin()
	@PostMapping()
	public ArrayList<Sensory> DeleteSesory() throws SQLException, ClassNotFoundException{
		
		SensoryAll.clear(); 
		if(sqlSetting!=null) {
			sqlSetting.ReSettSQL(SensoryString, SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		}else
			
		{   
			AbstractSQL sqlSetting=new SQLStringSetting(SensoryString,SQLConnectingSetting,SQLAccount,SQLPassword);
			SensoryAll=sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		}
	}
	@CrossOrigin()
	@PostMapping("Sensory/PrintAllSensory")
	public ArrayList<Sensory> QuerySensory() throws ClassNotFoundException, SQLException {
		SensoryAll.clear(); 
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
	@PostMapping("Sensory/QuerySensoryOne")
	public ArrayList<Sensory> QuerySensoryOne(int SensorId) throws SQLException, ClassNotFoundException {
		SensoryOneString = "select * from sensorTable where id="+SensorId;
		SensoryAll.clear();
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
