package com.example.demo;

import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.FileTrans;
import SensoryModel.FileTrans.PathName;
import SensoryModel.Sensory;
import SensoryModel.SensoryLibra;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import DesignPattern.CompositePattern;
import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;

@RestController
public class SensoryController {
	private AbstractSQL sqlSetting;
	private String PostDateString = "insert into sensorTable(id,SensorKey,SensorTile,SensorContext,SensorDate,SensorEmp,Url,fileUrl,QrcodeUrl) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,? FROM sensorTable";
	private String SensoryString = "select * from sensorTable ORDER BY SensorDate DESC";
	private String SensoryOneString = "";
	private String DeleteSensory = "";  
	private String SQLConnectingSetting = "jdbc:mysql://localhost/drugsql?serverTimezone=UTC";
	private String SQLAccount = "root";
	private String SQLPassword = "love20720";
	private String CheckCode="A0738";
	
	private String UpdateString="Update sensorTable SET %S= '%S' where id=%S";
	private static ArrayList<Sensory> SensoryAll = new ArrayList<>();


	@CrossOrigin
	@PostMapping("Sensory/UpLoadFile")
	public ArrayList<Sensory> upload_file(MultipartFile file,MultipartFile Qr,String SenSoryId) throws IllegalStateException, IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException   {
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		    String ProcessCode;
		    String FilePath="C:\\Users\\loveaoe33\\Desktop\\vue\\newvue\\public\\SensoryFile\\";
		    String QrPath="C:\\Users\\loveaoe33\\Desktop\\vue\\newvue\\public\\SensoryQr\\";
		    SimpleDateFormat sdFormate=new SimpleDateFormat("hh:mm:ss");
		    Date date=new Date();
		    String StrDate=sdFormate.format(date);
		    if(sqlSetting==null)
		    {
				   sqlSetting = new SQLStringSetting(PostDateString, SQLConnectingSetting, SQLAccount,
						SQLPassword);
		    }	    
		    		    

	        if(file!=null) {
			      ProcessCode=FileTrans.FileProcess(file,Qr, SenSoryId, FilePath, UpdateString, PathName.FilePath,StrDate);
				  sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
				  SensoryAll=sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
	        }
	        if(Qr!=null)
	        {
			     ProcessCode=FileTrans.FileProcess(file,Qr, SenSoryId, QrPath, UpdateString, PathName.QrPath,StrDate);
				 sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
				 SensoryAll=sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
	        }
	        
	        return SensoryAll;
	
	}
	
	
	@CrossOrigin()
	@PostMapping("Sensory/PostData")
	public ArrayList<Sensory> SensoryPostData(@RequestBody JSONObject SensryPOST)
			throws SQLException, ClassNotFoundException {
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		SensoryLibra.TransFun(SensryPOST);		
			if (sqlSetting != null) {

				sqlSetting.ReSettSQL(PostDateString, SQLConnectingSetting, SQLAccount, SQLPassword);
				SensoryAll = sqlSetting.SQLCase(CaseSQL.PostDate);
				return SensoryAll;
			} else

			{
				 sqlSetting = new SQLStringSetting(PostDateString, SQLConnectingSetting, SQLAccount,
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
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(DeleteSensory, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		} else

		{
			 sqlSetting = new SQLStringSetting(DeleteSensory, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryAll;
		}
	}

	@CrossOrigin()
	@PostMapping("Sensory/PrintAllSensory")
	public ArrayList<Sensory> QuerySensory() throws ClassNotFoundException, SQLException {
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		} else

		{
			sqlSetting = new SQLStringSetting(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.Prinall);
			return SensoryAll;
		}
	}

	@CrossOrigin()
	@PostMapping("Sensory/QuerySensoryOne")
	public ArrayList<Sensory> QuerySensoryOne(@RequestBody Map<String,Integer> SensoryID) throws SQLException, ClassNotFoundException {
		int id=SensoryID.get("SensoryID");
		SensoryOneString = "select * from sensorTable where id=" + id;
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrintOne);
			return SensoryAll;
		} else

		{
			 sqlSetting = new SQLStringSetting(SensoryOneString, SQLConnectingSetting, SQLAccount,
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
	    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
		if (sqlSetting != null) {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryAll = sqlSetting.SQLCase(CaseSQL.PrinClass);
			return SensoryAll;
		} else {
			 sqlSetting = new SQLStringSetting(SensoryOneString, SQLConnectingSetting, SQLAccount,
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
