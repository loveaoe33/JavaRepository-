package com.example.demo;

import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.Sensory;
import SensoryModel.SensoryLibra;
import net.sf.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private String DeleteSensory = "";
	private String SQLConnectingSetting = "jdbc:mysql://localhost/drugsql?useUnicode=true&characterEncoding=Big5";
	private String SQLAccount = "root";
	private String SQLPassword = "love20720";
	private String CheckCode="A0738";
	private static ArrayList<Sensory> SensoryAll = new ArrayList<>();

	@CrossOrigin()
	@PostMapping("Sensory/PostData")
	public ArrayList<Sensory> SensoryPostData(@RequestBody JSONObject SensryPOST)
			throws SQLException, ClassNotFoundException {
		SensoryLibra.TransFun(SensryPOST);
		SensoryAll.clear();
		if (sqlSetting != null) {

			sqlSetting.ReSettSQL(PostDateString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PostDate);
			return SensoryAll;
		} else

		{
			AbstractSQL sqlSetting = new SQLStringSetting(PostDateString, SQLConnectingSetting, SQLAccount,
					SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PostDate);
			return SensoryAll;

		}

	}
	@CrossOrigin()
	@PostMapping("Sensory/Code")
	public String CheckCode( @RequestBody Map<String,String> DeleCode) {
		String Check= DeleCode.equals(CheckCode)?"OK":"NOK";
		return Check;
	}
	@CrossOrigin()
	@GetMapping("Sensory/Code/{PassCode}")
	public Map<String, String> CheckCode( @PathVariable String PassCode) {
		String Check= PassCode.equals(CheckCode)?"OK":"NOK";
		Map<String,String> ReTuCheck=new HashMap<>();
		ReTuCheck.put("ReTuCheck", Check);
		return ReTuCheck;
	}
	
	@CrossOrigin()
	@PostMapping("Sensory/test")
	public String test() {
		return "1234";
	}
	   
	@CrossOrigin()
	@PostMapping("Sensory/DeleteSesory")
	public ArrayList<Sensory> DeleteSesory(@RequestBody Map<String,Integer> SensoryID) throws SQLException, ClassNotFoundException {
		int id=SensoryID.get("SensoryID");
		DeleteSensory = "Delete from sensorTable where id=" + id;
		SensoryAll.clear();
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(DeleteSensory, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		} else

		{
			AbstractSQL sqlSetting = new SQLStringSetting(DeleteSensory, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		}
	}

	@CrossOrigin()
	@PostMapping("Sensory/PrintAllSensory")
	public ArrayList<Sensory> QuerySensory() throws ClassNotFoundException, SQLException {
		SensoryAll.clear();
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		} else

		{
			AbstractSQL sqlSetting = new SQLStringSetting(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		}
	}

	@CrossOrigin()
	@PostMapping("Sensory/QuerySensoryOne")
	public ArrayList<Sensory> QuerySensoryOne(@RequestBody Map<String,Integer> SensoryID) throws SQLException, ClassNotFoundException {
		int id=SensoryID.get("SensoryID");
		SensoryOneString = "select * from sensorTable where id=" + id;
		SensoryAll.clear();
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrintOne);
			return SensoryAll;
		} else

		{
			AbstractSQL sqlSetting = new SQLStringSetting(SensoryOneString, SQLConnectingSetting, SQLAccount,
					SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrintOne);
			return SensoryAll;
		}

	}

	@CrossOrigin()
	@PostMapping("Sensory/QueryArea")
	public ArrayList<Sensory> QuerySensoryArea(@RequestBody Map<String,String> SensoryArea) throws SQLException, ClassNotFoundException {
		String SensoryAreaString=SensoryArea.get("SensoryArea");
		SensoryOneString = SensoryAreaString.contains("所有疫情") ?  "select * from sensorTable  ORDER BY SensorDate DESC" : "select * from sensorTable where SensorKey LIKE '%" + SensoryAreaString + "%' ORDER BY SensorDate DESC";
		SensoryAll.clear();
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrinClass);
			return SensoryAll;
		} else {
			AbstractSQL sqlSetting = new SQLStringSetting(SensoryOneString, SQLConnectingSetting, SQLAccount,
					SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrinClass);
			return SensoryAll;

		}
	}
}

//	public static void main(String[] args) throws ClassNotFoundException {
//	// TODO Auto-generated method stub
//	SensoryController Com=new SensoryController();
//	Com.test();
//}
//
