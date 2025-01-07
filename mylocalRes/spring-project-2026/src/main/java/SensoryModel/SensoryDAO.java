package SensoryModel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;
import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.FileTrans.PathName;

@Component
public class SensoryDAO {
	private AbstractSQL sqlSetting = null;
	private String SQLConnectingSetting = "jdbc:mysql://192.168.2.147:3307/drugsql?serverTimezone=UTC";
	private String SQLAccount = "root";
	private String SQLPassword = "love20720";
	private String SensoryString = "select * from sensorTable ORDER BY id DESC";
	private String PostDateString = "insert into sensorTable(id,SensorKey,SensorTile,SensorContext,SensorDate,SensorEmp,Url,fileUrl,QrcodeUrl) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,? FROM sensorTable";
	private String UpdateString = "Update sensorTable SET %S= '%S' where id=%S";
	private String DeleteSensory = "";

	public ArrayList<Sensory> SensoryData = new ArrayList<Sensory>();
	public  ArrayList<Sensory> SensoryDataTemp = new ArrayList<Sensory>();

	public SensoryDAO() throws SQLException {
		initSensoryData();
	}

	public void initSensoryData() throws SQLException {
		
		try {
			if (sqlSetting == null) {
				sqlSetting = new SQLStringSetting(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			} else {
				sqlSetting.ReSettSQL(SensoryString, SQLConnectingSetting, SQLAccount, SQLPassword);
			}
			if(SensoryDataTemp.size()>0) {
				SensoryDataTemp.clear();
			}else {
				
				SensoryDataTemp.addAll(sqlSetting.SQLCase(CaseSQL.Prinall));
			}
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("InitSensoryData錯誤" + e.toString());
		}
		

	}

	public ArrayList<Sensory> getSensoryData() { // 帶出所有布告資料
		return SensoryDataTemp;
	}

	public ArrayList<Sensory> insertSensoryData() throws SQLException { // 新增布告資料

		try {
			sqlSetting.ReSettSQL(PostDateString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.PostDate);
			return SensoryData;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("insertSensortData錯誤" + e.toString());
		} finally {
			initSensoryData();

		}
		return null;
	}

	public ArrayList<Sensory> deleteSensoryData(String DeleteSensory) throws SQLException { // 刪除布告資料

		try {
			sqlSetting.ReSettSQL(DeleteSensory, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.DeleteOne);
			return SensoryData;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("deleteSensortData錯誤" + e.toString());
		} finally {
			initSensoryData();

		}
		return null;
	}

	public ArrayList<Sensory> queryOne(String SensoryOneString) { // 帶出單筆

		try {
			sqlSetting.ReSettSQL(SensoryOneString, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.PrintOne);

			return SensoryData;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("queryOne錯誤" + e.toString());
		}finally {

		}
		return null;

	}

	public String updateSensortData() { // 更新布告資料

		try {
			initSensoryData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("updateSensortData錯誤" + e.toString());
		}
		return "Sucess";
	}

	public ArrayList<Sensory> areaSensortData(String SensoryArea) { // 帶入類別布告資料

		try {
			sqlSetting.ReSettSQL(SensoryArea, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.PrinClass);
			return SensoryData;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("areaSensortData錯誤" + e.toString());
		}finally {

		}
		return null;
	}

	public ArrayList<Sensory> fileUpload(MultipartFile file, MultipartFile Qr, String SenSoryId)
			throws NoSuchAlgorithmException, IOException, SQLException { 
		String ProcessCode;
//		String FilePath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\vue\\newvue\\public\\SensoryFile\\";
//		String QrPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\vue\\newvue\\public\\SensoryQr\\";
		String FilePath = "/app/SensoryFile";
		String QrPath = "/app/SensoryQr";

		SimpleDateFormat sdFormate = new SimpleDateFormat("hh:mm:ss");
		Date date = new Date();
		String StrDate = sdFormate.format(date);
		if (file != null) {
			ProcessCode = FileTrans.FileProcess(file, Qr, SenSoryId, FilePath, UpdateString, PathName.FilePath,
					StrDate);
			sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
		}
		if (Qr != null) {
			ProcessCode = FileTrans.FileProcess(file, Qr, SenSoryId, QrPath, UpdateString, PathName.QrPath, StrDate);
			sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
			SensoryData = sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
		}
		return null;

	}
}
