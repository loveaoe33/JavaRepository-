package Personnel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.multipart.MultipartFile;

import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;
import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.FileTrans;
import SensoryModel.Sensory;
import SensoryModel.FileTrans.PathName;

public class PersonnelDAO {
	private AbstractSQL sqlSetting;

	private PersonnelDAO() {
	}

	private static PersonnelDAO SingleClass = new PersonnelDAO();
	private String SQLConnectingSetting = "jdbc:mysql://localhost/personnel?serverTimezone=UTC";
	private String SQLAccount = "root";
	private String SQLPassword = "love20720";
	private String Insert_Employee_String = "insert into Employee(id,Account,Password,ArticleClass,AccountLevel,AccountLevel,CreateDate) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,? FROM Employee";
	private String Insert_Article_String = "insert into Article(id,EmpClass,ArticleClass,ArticleTitle,ArticleContext,ArticleEmpl,ArticleFileUrl,ArticleView,ArticleLv,ArticleCreate,ArticleLock) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,? FROM Article";
	private String Power_Print_Article = "select * from Article";
	private String Print_Article_ = "select * from Article where ArticleLock=? or ArticleLock='' ";
	private String Print_Article_Class = "select * from Article where ArticleClass=? AND (ArticleLock=? or ArticleLock=='') ";
	private String Quick_Search = "select * from Article where ArticleTitle=? AND (ArticleLock=? or ArticleLock=='') ";
	private String Update_Vilew = "Update Article SET ArticleView=? where id=?";
	private String UpdateString = "Update Article SET %S= '%S' where id=%S";
	private static ArrayList<T_Class> File_Upload_Personnel = new ArrayList<>();

	public static PersonnelDAO getInstance_SingleSQL() {
		return SingleClass;
	}

	public ArrayList<T_Class> upload_file(MultipartFile file, MultipartFile Qr, String ArticleId)
			throws IllegalStateException, IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {

		if (File_Upload_Personnel.isEmpty() || File_Upload_Personnel == null) {
		} else {
			File_Upload_Personnel.clear();
		}

		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		String FilePath = "C:\\Users\\loveaoe33\\Desktop\\vue\\newvue\\public\\PersonnelFile\\";
		SimpleDateFormat sdFormate = new SimpleDateFormat("hh:mm:ss");
		Date date = new Date();
		String StrDate = sdFormate.format(date);
		if (file != null) {
			String ProcessCode = FileTrans.FileProcess(file, Qr, ArticleId, FilePath, UpdateString, PathName.FilePath,
					StrDate);
			sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
			File_Upload_Personnel = sqlSetting.SQLCase_Personnel(CaseSQL.Personnel_Upload);
		}

		return null;
	}

//		
//		public ArrayList<Sensory> upload_file(MultipartFile file,MultipartFile Qr,String SenSoryId) throws IllegalStateException, IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException   {
//		    if(SensoryAll.isEmpty()||SensoryAll==null){ }else {SensoryAll.clear(); }
//			    String ProcessCode;
//			    String FilePath="C:\\Users\\loveaoe33\\Desktop\\vue\\newvue\\public\\SensoryFile\\";
//			    String QrPath="C:\\Users\\loveaoe33\\Desktop\\vue\\newvue\\public\\SensoryQr\\";
//			    SimpleDateFormat sdFormate=new SimpleDateFormat("hh:mm:ss");
//			    Date date=new Date();
//			    String StrDate=sdFormate.format(date);
//			    if(sqlSetting==null)
//			    {
//					   sqlSetting = new SQLStringSetting(PostDateString, SQLConnectingSetting, SQLAccount,
//							SQLPassword);
//			    }	    
//			    		    
//
//		        if(file!=null) {
//				      ProcessCode=FileTrans.FileProcess(file,Qr, SenSoryId, FilePath, UpdateString, PathName.FilePath,StrDate);
//					  sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
//					  SensoryAll=sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
//		        }
//		        if(Qr!=null)
//		        {
//				     ProcessCode=FileTrans.FileProcess(file,Qr, SenSoryId, QrPath, UpdateString, PathName.QrPath,StrDate);
//					 sqlSetting.ReSettSQL(ProcessCode, SQLConnectingSetting, SQLAccount, SQLPassword);
//					 SensoryAll=sqlSetting.SQLCase(CaseSQL.UpLoadUrl);
//		        }
//		        
//		        return SensoryAll;
//		
//		}

	private void SQL_Process(String ResetString) throws ClassNotFoundException {
		if (sqlSetting == null) {
			sqlSetting = new SQLStringSetting(ResetString, SQLConnectingSetting, SQLAccount, SQLPassword);
		} else {
			sqlSetting.ReSettSQL(ResetString, SQLConnectingSetting, SQLAccount, SQLPassword);
		}
	}

	public String Insert_Employee(EmployeeModel employeeModel) throws ClassNotFoundException, SQLException {
//	   sqlSetting=(sqlSetting==null)? new SQLStringSetting(Insert_Employee_String, SQLConnectingSetting, SQLAccount,SQLPassword):sqlSetting;
		SQL_Process(Insert_Employee_String);
		ArrayList Response = sqlSetting.SQLCase(CaseSQL.Insert_Employee);
		return "";
	}

	public String Insert_Article(ArticleModel articleModel) {
		return "";
	}

	public ArrayList<ArticleModel> Power_Print_Article_() {
		return null;
	}

	public ArrayList<ArticleModel> Print_Article_() {
		return null;
	}

	public ArrayList<ArticleModel> Print_Article_Class() {
		return null;
	}

	public ArrayList<ArticleModel> Quick_Search() {
		return null;
	}

	public String Update_Vilew() {
		return "";
	}

}
