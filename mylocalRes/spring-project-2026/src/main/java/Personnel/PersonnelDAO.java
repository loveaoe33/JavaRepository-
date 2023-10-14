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
//	private String Insert_Employee_String = "insert into Employee(id,Account,Password,ArticleClass,AccountLevel,AccountLevel,CreateDate) "
//			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,? FROM Employee"; // 員工新增
//	private String Insert_Article_String = "insert into Article(id,EmpClass,ArticleClass,ArticleTitle,ArticleContext,ArticleEmpl,ArticleFileUrl,ArticleView,ArticleLv,ArticleCreate,ArticleLock) "
//			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,? FROM Article"; // 文章新增

//	private String Power_Print_Article = "select * from Article";  //最高權限搜尋文章
//	private String Print_Article_ = "select * from Article where (ArticleLock=? or ArticleLock='') AND (ArticleLv=0 or ArticleLv<?)";  //依造LV搜尋文章
//	private String Print_Article_Class = "select * from Article where ArticleClass=? AND (ArticleLock=? or ArticleLock=='') AND (ArticleLv=0 or ArticleLv<?)"; //依造類別與LV搜尋文章
//	private String Quick_Search = "select * from Article where ArticleTitle=? AND (ArticleLock=? or ArticleLock=='') ";  //快搜
//	private String Update_Vilew = "Update Article SET ArticleView=? where id=?";  //更新觀看紀錄
//	private String UpdateString = "Update Article SET %S= '%S' where id=%S"; // 更新文章
//	private String Employee_Login="select * from Employee where Account=? AND Password=?";  //登入檢查
//	private String Employee_LvCheck="select * from Employee where id=?";   //權限檢查
	private static ArrayList<T_Class> File_Upload_Personnel = new ArrayList<>(); // 上傳

	public static PersonnelDAO getInstance_SingleSQL() {
		return SingleClass;
	}

	public ArrayList<T_Class> upload_file(MultipartFile file, MultipartFile Qr, String ArticleId)
			throws IllegalStateException, IOException, NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		String userName=System.getProperty("user.name");
		String UpdateString = "Update Article SET %S= '%S' where id=%S"; // 更新文章
		if (File_Upload_Personnel.isEmpty() || File_Upload_Personnel == null) {
			
			
		} else {
			File_Upload_Personnel.clear();
		}
		
		

		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		String FilePath = "C:\\Users\\"+ userName +"\\Desktop\\vue\\newvue\\public\\PersonnelFile\\";
		SimpleDateFormat sdFormate = new SimpleDateFormat("hh:mm:ss");
		Date date = new Date();
		String StrDate = sdFormate.format(date);
		if (file != null) {
			String ProcessCode = FileTrans.FileProcess(file, Qr, ArticleId, FilePath, UpdateString, PathName.Personnel_Upload,
					StrDate);
			SQL_Process(ProcessCode);
//			File_Upload_Personnel = sqlSetting.SQLCase_Personnel(CaseSQL.Personnel_Upload);
		}

		return 	File_Upload_Personnel = sqlSetting.SQLCase_Personnel(CaseSQL.Personnel_Upload);

	}

	private void SQL_Process(String ResetString) throws ClassNotFoundException {
		if (sqlSetting == null) {
			sqlSetting = new SQLStringSetting(ResetString, SQLConnectingSetting, SQLAccount, SQLPassword);
		} else {
			sqlSetting.ReSettSQL(ResetString, SQLConnectingSetting, SQLAccount, SQLPassword);
		}
	}

	public ArrayList<T_Class> Employee_Login(String Account,String Passowrd) throws ClassNotFoundException, SQLException {
		SQLStringSetting.Personnel_Employee.setAccount(Account).setPassword(Passowrd);
		String Employee_Login="select * from Employee where Account=? AND Password=?";
		SQL_Process(Employee_Login);
		return sqlSetting.SQLCase_Personnel(CaseSQL.Employee_Login);
	}

	public ArrayList<T_Class> Insert_Employee() throws ClassNotFoundException, SQLException {
		String Insert_Employee_String = "insert into Employee(id,Employee_Name,Account,Password,ArticleClass,AccountLevel,Department,CreateDate) "
				+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,? FROM Employee"; // 員工新增
		SQL_Process(Insert_Employee_String);
		return sqlSetting.SQLCase_Personnel(CaseSQL.Insert_Employee);
	}

	public ArrayList<T_Class> Insert_Article() throws ClassNotFoundException, SQLException {
	    String Insert_Article_String = "insert into Article(id,EmpClass,ArticleClass,ArticleTitle,ArticleContext,ArticleEmpl,ArticleUrl,ArticleFileUrl,ArticleView,ArticleLv,ArticleCreate,ArticleLock) "
				+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,?,? FROM Article"; // 文章新增
		SQL_Process(Insert_Article_String);

		return sqlSetting.SQLCase_Personnel(CaseSQL.Insert_Article);
	}
	
	
	
	
	

	public ArrayList<T_Class> Print_Article_(int EmployeId, String Department)
			throws ClassNotFoundException, SQLException {
		SQL_Process("select * from Article");   //後面要拿掉
		
		
		int Employee_Check = ((SQLStringSetting) sqlSetting).Employee_LvCheck(EmployeId); 
		     // 如抽象無定義抽象方法，要找到定義的方法要轉型使用Account搜尋。
		SQLStringSetting.Personnel_Employee.setId(EmployeId).setDepartment(Department).setAccountLevel(Integer.toString(Employee_Check));
		if (Employee_Check == 0) {
			String Power_Print_Article = "select * from Article";
			SQL_Process(Power_Print_Article);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Print_Article_Power);

		} else {
//        	String Print_Article_ = "select * from Article where (ArticleLv=0 or ArticleLv<="+EmployeId+")";  //依造LV搜尋文章
			String Print_Article_ = "select * from Article where (ArticleLock=? or ArticleLock='') AND (ArticleLv=0 or ArticleLv<=?)"; // 依造LV搜尋文章

			SQL_Process(Print_Article_);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Print_Article);

		}

	}

	public ArrayList<T_Class> Print_Article_Class(int EmployeId, String Article_Class, String Department)
			throws ClassNotFoundException, SQLException {
		SQL_Process("select * from Article"); //後面要刪除
		int Employee_Check = ((SQLStringSetting) sqlSetting).Employee_LvCheck(EmployeId);
	 // 如抽象無定義抽象方法，要找到定義的方法要轉型使用Account搜尋。
		SQLStringSetting.Personnel_Employee.setId(EmployeId).setDepartment(Department).setArticleClass(Article_Class).setAccountLevel(Integer.toString(Employee_Check));

		if (Employee_Check == 0) {

			String Power_Print_Article = "select * from Article where ArticleClass=?"; // 依造類別搜尋文章
			SQL_Process(Power_Print_Article);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Print_Article_Class_Power);

		} else {
			String Print_Article_Class = "select * from Article where ArticleClass=? AND (ArticleLock=? or ArticleLock='') AND (ArticleLv=0 or ArticleLv<=?)"; // 依造類別與LV搜尋文章
//            String Print_Article_Class = "select * from Article where ArticleClass="+Article_Class+"AND (ArticleLock="+Department+ "or ArticleLock=='') AND (ArticleLv=0 or ArticleLv<"+EmployeId+")"; //依造類別與LV搜尋文章
			SQL_Process(Print_Article_Class);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Print_Article_Class);

		}

	}

	public ArrayList<T_Class> Quick_Search(int EmployeId, String Key) throws ClassNotFoundException, SQLException {
		SQL_Process("select * from Article"); //後面要刪除
		int Employee_Check = ((SQLStringSetting) sqlSetting).Employee_LvCheck(EmployeId); // 如抽象無定義抽象方法，要找到定義的方法要轉型使用Account搜尋。

		if (Employee_Check == 0) {
			String Quick_Search = "select * from Article where ArticleTitle LIKE '"+ Key + "%'";
			SQL_Process(Quick_Search);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Quick_Search);
		} else {
			String Quick_Search = "select * from Article where ArticleTitle LIKE" + Key
					+ "% AND (ArticleLv=0 or ArticleLv<=" + Employee_Check + ")";
			SQL_Process(Quick_Search);
			return sqlSetting.SQLCase_Personnel(CaseSQL.Quick_Search);
		}

	}

	public ArrayList<T_Class> One_Article(int Article_id) throws SQLException, ClassNotFoundException {
		String Select = "select * from Article where id=" + Article_id ;
		SQL_Process(Select);
		return sqlSetting.SQLCase_Personnel(CaseSQL.One_Article);
	}
	
	public ArrayList<T_Class> Update_Vilew(int id, String Employee_Name) throws SQLException, ClassNotFoundException {
		String Select = "select * from Article where id=" + id + "," + Employee_Name; // 更新觀看紀錄
		SQL_Process(Select);

		return sqlSetting.SQLCase_Personnel(CaseSQL.Update_Vilew);
	}

	public ArrayList<T_Class> Delete_Article(int id) throws SQLException, ClassNotFoundException {
		SQLStringSetting.Personnel_Article.setId(id);
		String Delete_Article = "Delete from Article where id=?";
		SQL_Process(Delete_Article);
		return sqlSetting.SQLCase_Personnel(CaseSQL.Delete_Article);
	}
	
	
	

}
