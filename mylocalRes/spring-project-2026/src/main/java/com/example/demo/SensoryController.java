package com.example.demo;

import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.FileTrans;
import SensoryModel.FileTrans.PathName;
import SensoryModel.Sensory;
import SensoryModel.SensoryDAO;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import DesignPattern.CompositePattern;
import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ComponentScan(basePackages = { "SensoryModel" })
@RestController
public class SensoryController {
	private AbstractSQL sqlSetting;
	private String PostDateString = "insert into sensorTable(id,SensorKey,SensorTile,SensorContext,SensorDate,SensorEmp,Url,fileUrl,QrcodeUrl) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,? FROM sensorTable";
	private String SensoryString = "select * from sensorTable ORDER BY id DESC";
	private String SensoryOneString = "";
	private String DeleteSensory = "";
	private String SQLConntetingSetting="jdbc:mysql://192.168.2.203:3307/drugsql?serverTimezone=UTC";
	private String SQLAccount = "root";
	private String SQLPassword = "love20720";
	private String CheckCode = "A0738";
	private String UpdateString = "Update sensorTable SET %S= '%S' where id=%S";
	private ArrayList<Sensory> SensoryAll = new ArrayList<>();
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); /* log調用 */

	private SensoryDAO sensoryDAO;
	private final Lock lock = new ReentrantLock();

	@Autowired
	public SensoryController(SensoryDAO sensoryDAO) {

		this.sensoryDAO = sensoryDAO;
	}

	@CrossOrigin
	@PostMapping("Sensory/UpLoadFile")
	public ArrayList<Sensory> upload_file(MultipartFile file, MultipartFile Qr, String SenSoryId)
			throws IllegalStateException, IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		try {
			lock.lock();
			return sensoryDAO.fileUpload(file,Qr,SenSoryId);
		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin()
	@PostMapping("Sensory/PostData")
	public ArrayList<Sensory> SensoryPostData(@RequestBody JSONObject SensryPOST)
			throws SQLException, ClassNotFoundException {
		try {
			lock.lock();
			SensoryLibra.TransFun(SensryPOST);
			return sensoryDAO.insertSensoryData();
		} finally {

			lock.unlock();

		}

	}

//	
//	@CrossOrigin()
//	@PostMapping("Sensory/Code")
//	public String CheckCode( @RequestBody Map<String,String> DeleCode) {
//		String Check= DeleCode.equals(CheckCode)?"OK":"NOK";
//		return Check;
//	}

	@CrossOrigin()
	@GetMapping("Sensory/Code/{PassCode}")
	public Map<String, String> CheckCode(@PathVariable String PassCode) {
		try {

			lock.lock();

			String Check = PassCode.equals(CheckCode) ? "OK" : "NOK";
			Map<String, String> ReTuCheck = new HashMap<>();
			ReTuCheck.put("ReTuCheck", Check);
			return ReTuCheck;
		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin()
	@PostMapping("Sensory/test")
	public String test() {
		return "1234";
	}

	@CrossOrigin()
	@PostMapping("Sensory/DeleteSesory")
	public ArrayList<Sensory> DeleteSesory(@RequestBody Map<String, Integer> SensoryID)
			throws SQLException, ClassNotFoundException {

		try {
			lock.lock();
			int id = SensoryID.get("SensoryID");
			DeleteSensory = "Delete from sensorTable where id=" + id;
			return sensoryDAO.deleteSensoryData(DeleteSensory);
		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin()
	@PostMapping("Sensory/PrintAllSensory")
	public ArrayList<Sensory> QuerySensory() throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			return sensoryDAO.getSensoryData();
		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin()
	@PostMapping("Sensory/QuerySensoryOne")
	public ArrayList<Sensory> QuerySensoryOne(@RequestBody Map<String, Integer> SensoryID)
			throws SQLException, ClassNotFoundException {

		try {
			lock.lock();
			int id = SensoryID.get("SensoryID");
			SensoryOneString = "select * from sensorTable where id=" + id ;
			return sensoryDAO.queryOne(SensoryOneString);
		} finally {
			lock.unlock();

		}
	}

	@CrossOrigin()
	@PostMapping("Sensory/QueryArea")
	public ArrayList<Sensory> QuerySensoryArea(@RequestBody Map<String, String> SensoryArea)
			throws SQLException, ClassNotFoundException {

		try {
			lock.lock();
			String SensoryAreaString = SensoryArea.get("SensoryArea");
			SensoryOneString = SensoryAreaString.contains("所有疫情")
					? "select * from sensorTable  ORDER BY SensorDate DESC"
					: "select * from sensorTable where SensorKey LIKE '%" + SensoryAreaString
							+ "%' ORDER BY SensorDate DESC";
			return sensoryDAO.areaSensortData(SensoryOneString);

		} finally {
			lock.unlock();

		}

	}
}

//	public static void main(String[] args) throws ClassNotFoundException {
//	// TODO Auto-generated method stub
//	SensoryController Com=new SensoryController();
//	Com.test();
//}
//
