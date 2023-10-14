package DrugSQL;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.RowSet;

import Personnel.ArticleModel;
import Personnel.EmployeeModel;
import SensoryModel.Sensory;
import net.sf.json.JSONObject;
import Personnel.T_Class;

public class SQLStringSetting extends AbstractSQL {

	public static enum CaseSQL {
		Prinall, PrintOne, PostDate, DeleteOne, PrinClass, UpLoadUrl, Personnel_Upload, Insert_Employee, Insert_Article,
		Print_Article, Quick_Search, Update_Vilew, Print_Article_Power, Print_Article_Class, Print_Article_Class_Power,
		Delete_Article, Employee_Login,One_Article
	}

	public static Sensory PostData = new Sensory("", "", "", "", "");
	public static ArticleModel Personnel_Article = new ArticleModel(); // 前面塞入物件
	public static EmployeeModel Personnel_Employee = new EmployeeModel();// 前面塞入物件

	private Sensory CallBackData = new Sensory("", "", "", "", "");
	private ArrayList<Sensory> DataArray = new ArrayList<Sensory>();
	public static String Pass_Code = "";
	private static ArrayList<T_Class> DataArray_Pesonnel = new ArrayList<T_Class>(); // Personnel的回傳陣列
	private static T_Class Article_Class = new ArticleModel(); // 確認有無成功用的回傳物件的回傳物件
	private static T_Class Employee_Class = new EmployeeModel(); // 確認有無成功用的回傳物件的回傳物件

	static JSONObject JsonData = new JSONObject();

	protected void Close() {
		try {
			if (stat != null) {
				stat.close();
				stat = null;

			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}

		} catch (SQLException e) {
			System.out.println("close Exception:" + e.toString());
		}
	}

	public SQLStringSetting(String SQLString, String SQLConnectionString, String Account, String Password)
			throws ClassNotFoundException {
		super.SQLString = SQLString;
		super.ConnectionString = SQLConnectionString;
		super.Account = Account;
		super.Password = Password;
		SQLConnection();
	}

	@Override
	protected void SQLConnection() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");// 註冊driver
			con = DriverManager.getConnection(ConnectionString, Account, Password);
		} catch (SQLException e) {
			System.out.println("資料庫設置錯誤" + e.toString());
		} catch (ClassNotFoundException x) {
			System.out.println("物件發生錯誤");

		}

	}

	private ArrayList<Sensory> SQLQueryALL() throws SQLException {

		int i = 0;
		DataArray.clear();
		CallBackData.reSetConstruct();

		try {

			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			if (rs == null) {

				CallBackData.setDataCheck(false);
				DataArray.add(CallBackData);
				return DataArray;

			} else

			{

				while (rs.next()) {
					// 必須產生不同記憶體位置 否則會改到同筆資料
					Sensory CallBackData = new Sensory("", "", "", "", "");
					CallBackData.setId(rs.getInt("id"));
					CallBackData.setSensorContext(rs.getString("SensorContext"));
					CallBackData.setSensorEmp(rs.getString("SensorEmp"));
					CallBackData.setSensorDate(rs.getString("SensorDate"));
					CallBackData.setSensorKey(rs.getString("SensorKey"));
					CallBackData.setSensorTitle(rs.getString("SensorTile"));
					// Url
					CallBackData.setUrl(rs.getString("Url"));
					CallBackData.setFileUrl(rs.getString("FileUrl"));
					CallBackData.setQrcodeUrl(rs.getString("QrcodeUrl"));
					DataArray.add(CallBackData);

				}

				return DataArray;
			}

		} catch (Exception e) {
			System.out.println("資料庫總查詢錯誤" + e.getMessage());
			return null;
		} finally {
			Close();
		}

	}

	private ArrayList<Sensory> SQLQueryOne() throws SQLException {
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			ArrayList<Sensory> DataArray = new ArrayList<>();

			if (!rs.next()) {

				CallBackData.setDataCheck(false);
				DataArray.add(CallBackData);
				return DataArray;

			} else {
				CallBackData.setId(rs.getInt("id"));
				CallBackData.setSensorContext(rs.getString("SensorContext"));
				CallBackData.setSensorEmp(rs.getString("SensorEmp"));
				CallBackData.setSensorDate(rs.getString("SensorDate"));
				CallBackData.setSensorKey(rs.getString("SensorKey"));
				CallBackData.setSensorTitle(rs.getString("SensorTile"));
				// Url
				CallBackData.setUrl(rs.getString("Url"));
				CallBackData.setFileUrl(rs.getString("FileUrl"));
				CallBackData.setQrcodeUrl(rs.getString("QrcodeUrl"));
				DataArray.add(CallBackData);
				return DataArray;
			}

		} catch (Exception e) {
			System.out.println("資料庫單筆查詢錯誤" + e.getMessage());
			return null;
		} finally {

			Close();
		}

	}

	private ArrayList<Sensory> SQLUpLoadeUrl() {
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat = con.createStatement();
			stat.executeUpdate(SQLString);
			CallBackData.setDataCheck(true);
		} catch (Exception e) {
			System.out.println("資料庫更新錯誤" + e.getMessage());

		} finally {
			Close();
		}
		return DataArray;
	}

	private ArrayList<Sensory> SQLPostData() {
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			CallBackData.reSetConstruct();
			pst = con.prepareStatement(SQLString);
			pst.setString(1, PostData.getSensorKey());
			pst.setString(2, PostData.getSensorTitle());
			pst.setString(3, PostData.getSensorContext());
			pst.setString(4, PostData.getSensorDate());
			pst.setString(5, PostData.getSensorEmp());
			pst.setString(6, PostData.getUrl());
			pst.setString(7, PostData.getFileUrl());
			pst.setString(8, PostData.getQrcodeUrl());
			pst.executeUpdate();
			pst.clearParameters();
			CallBackData.setDataCheck(true);
			DataArray.add(CallBackData);
			return DataArray;
		} catch (Exception e) {

			System.out.println("資料庫新增錯誤" + e.getMessage());
			return null;

		} finally {
			Close();
		}

	}

	private ArrayList<Sensory> SQLQueryCalss() throws SQLException {
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			ArrayList<Sensory> DataArray = new ArrayList<>();

			while (rs.next()) {
				Sensory CallBackData = new Sensory("", "", "", "", "");
				CallBackData.setId(rs.getInt("id"));
				CallBackData.setSensorContext(rs.getString("SensorContext"));
				CallBackData.setSensorEmp(rs.getString("SensorEmp"));
				CallBackData.setSensorDate(rs.getString("SensorDate"));
				CallBackData.setSensorKey(rs.getString("SensorKey"));
				CallBackData.setSensorTitle(rs.getString("SensorTile"));
				// Url
				CallBackData.setUrl(rs.getString("Url"));
				CallBackData.setFileUrl(rs.getString("FileUrl"));
				CallBackData.setQrcodeUrl(rs.getString("QrcodeUrl"));
				DataArray.add(CallBackData);
			}
			return DataArray;

		} catch (Exception e) {
			System.out.println("資料庫查詢錯誤" + e.getMessage());

		} finally {

			Close();
		}
		return DataArray;

	}

	private ArrayList<Sensory> DeleteOne() {
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat = con.createStatement();
			stat.executeUpdate(SQLString);
			CallBackData.setDataCheck(true);

		} catch (Exception e) {

			System.out.println("資料庫刪除錯誤" + e.getMessage());

		}
		return DataArray;
	}

	private ArrayList<T_Class> Personnel_Upload() { // Pessonel_檔案處理上傳

		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();
		try {
			stat = con.createStatement();
			stat.executeUpdate(SQLString);
			Article_Class.Upload_Check = "OK"; // 確認檔案有成功上傳與更新資料庫
			DataArray_Pesonnel.add(Article_Class);
			System.out.println("OK");

		} catch (Exception e) {
			System.out.println("資料庫Personnel_Upload錯誤" + e.getMessage());

		} finally {
			Close();
		}
		return DataArray_Pesonnel;

	}

	private ArrayList<T_Class> Insert_Article() {
		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();

		try {
			pst = con.prepareStatement(SQLString);

			pst.setString(1, Personnel_Article.getEmpClass());
			pst.setString(2, Personnel_Article.getArticleClass());
			pst.setString(3, Personnel_Article.getArticleTitle());
			pst.setString(4, Personnel_Article.getArticleContext());
			pst.setString(5, Personnel_Article.getArticleEmpl());
			pst.setString(6, Personnel_Article.getArticleUrl());
			pst.setString(7, Personnel_Article.getArticleFileUrl());
			pst.setString(8, Personnel_Article.getArticleView());
			pst.setString(9, Personnel_Article.getArticleLv());
			pst.setString(10, Personnel_Article.getArticleCreate());
			pst.setString(11, Personnel_Article.getArticleLock());
			pst.executeUpdate();
			pst.clearParameters();
			Article_Class.Upload_Check = "OK";
			DataArray_Pesonnel.add(Article_Class);
			return DataArray_Pesonnel;
		} catch (Exception e) {

			System.out.println("資料庫Insert_Article_錯誤" + e.getMessage());
			return null;

		} finally {
			Personnel_Article.reSerConstruct(); // 新增完需清空
			Close();
		}

	}

	private ArrayList<T_Class> Insert_Employee() {
		DataArray_Pesonnel.clear();
		Employee_Class.reSerConstruct();
		try {
			String Employee_Account_Check = "select * from Employee where Account=?"; // 註冊檢查

			pst = con.prepareStatement(Employee_Account_Check);
			String Account_Process = (Personnel_Employee.getAccount()).replace("--", "");
			pst.setString(1, Account_Process);

			rs = pst.executeQuery();
			if (rs.next()) {
				Employee_Class.Upload_Check = "NOK";

			} else {

				pst = con.prepareStatement(SQLString);
				pst.setString(1, Personnel_Employee.getName());
				pst.setString(2, Personnel_Employee.getAccount());
				pst.setString(3, Personnel_Employee.getPassword());
				pst.setString(4, Personnel_Employee.getArticleClass()); // 特定編碼做Foreign Key
				pst.setString(5, Personnel_Employee.getAccountLevel());
				pst.setString(6, Personnel_Employee.getDepartment());
				pst.setString(7, Personnel_Employee.getCreateDate());
				pst.executeUpdate();
				pst.clearParameters();
				ArrayList<String> DataArray = new ArrayList<String>();
				Employee_Class.Upload_Check = "OK";

			}
			DataArray_Pesonnel.add(Employee_Class);
			return DataArray_Pesonnel;

		} catch (Exception e) {

			System.out.println("資料庫Insert_Employee錯誤" + e.getMessage());
			return null;

		} finally {
			Personnel_Employee.reSerConstruct(); // 新增完需清空
			Close();
		}

	}

	private ArrayList<T_Class> Employee_Login() {
		DataArray_Pesonnel.clear();
		Employee_Class.reSerConstruct();
		try {
			pst = con.prepareStatement(SQLString);
			String Account_Process = (Personnel_Employee.getAccount()).replace("--", "");
			pst.setString(1, Account_Process);
			pst.setString(2, Personnel_Employee.getPassword());
			rs = pst.executeQuery();
			if (rs.next()) {
				((EmployeeModel) Employee_Class).setId(rs.getInt("id")).setName(rs.getString("Employee_Name"))
						.setAccount(rs.getString("Account")).setPassword("")
						.setArticleClass(rs.getString("ArticleClass")).setAccountLevel(rs.getString("AccountLevel"))
						.setDepartment(rs.getString("Department")).setCreateDate(rs.getString("CreateDate"));
				Employee_Class.Upload_Check = "OK";

			} else {
				Employee_Class.Upload_Check = "NOK";

			}
			DataArray_Pesonnel.add(Employee_Class);
			return DataArray_Pesonnel;

		} catch (Exception e) {
			System.out.println("資料庫Employee_Login錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Employee.reSerConstruct();
			Close();
		}

	}

	private ArrayList<T_Class> Quick_Searh() {
		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();
		try {
			pst = con.prepareStatement(SQLString);
			rs = pst.executeQuery();
			while (rs.next()) {
				T_Class Article_Quick_New = new ArticleModel();

				((ArticleModel) Article_Quick_New).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				Article_Quick_New.Upload_Check = "OK";
				DataArray_Pesonnel.add(Article_Quick_New);

			}
			
			return DataArray_Pesonnel;
		} catch (Exception e) {
			System.out.println("資料庫Print_Article錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Employee.reSerConstruct();
			Close();
		}

	}

	private ArrayList<T_Class> Print_Article() {
		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();   //此處不用因為不能使用相同記憶體
		try {

			pst = con.prepareStatement(SQLString);
			pst.setString(1, Personnel_Employee.getDepartment());
			pst.setString(2, Personnel_Employee.getAccountLevel());
			rs = pst.executeQuery();
			while (rs.next()) {
				T_Class Article_New = new ArticleModel();

				((ArticleModel) Article_New).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				Article_New.Upload_Check = "OK";
				DataArray_Pesonnel.add(Article_New);

			}

			return DataArray_Pesonnel;
		} catch (Exception e) {
			System.out.println("資料庫Print_Article錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Employee.reSerConstruct();
			Close();
		}

	}

	private ArrayList<T_Class> Print_Article_Power() {

		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();   //此處不用因為不能使用相同記憶體
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			while (rs.next()) {
				T_Class Article_Power_New = new ArticleModel();
				((ArticleModel) Article_Power_New).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				Article_Power_New.Upload_Check = "OK";
				DataArray_Pesonnel.add(Article_Power_New);

			}

			return DataArray_Pesonnel;

		} catch (Exception e) {
			System.out.println("資料庫Print_Article_Power錯誤" + e.getMessage());
			return null;
		} finally {
			Close();
		}

	}

	private ArrayList<T_Class> Print_Article_Class() {

		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();   //此處不用因為不能使用相同記憶體
		try {
			pst = con.prepareStatement(SQLString);
			pst.setString(1, Personnel_Employee.getArticleClass());
			pst.setString(2, Personnel_Employee.getDepartment());
			pst.setString(3, Personnel_Employee.getAccountLevel());
			rs = pst.executeQuery();
			while (rs.next()) {
				T_Class Article_Class_New = new ArticleModel();

				((ArticleModel) Article_Class_New).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				Article_Class_New.Upload_Check = "OK";
				DataArray_Pesonnel.add(Article_Class_New);
			}

			return DataArray_Pesonnel;

		} catch (Exception e) {
			System.out.println("資料庫Print_Article_Class錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Employee.reSerConstruct();
			Close();
		}

	}

	private ArrayList<T_Class> Print_Article_Class_Power() {

		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct(); //此處不用因為不能使用相同記憶體
		try {
			pst = con.prepareStatement(SQLString);
			pst.setString(1, Personnel_Employee.getArticleClass());
			rs = pst.executeQuery();

			while (rs.next()) {
				T_Class Article_Class_Power_New = new ArticleModel();

				((ArticleModel) Article_Class_Power_New).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				Article_Class_Power_New.Upload_Check = "OK";
				DataArray_Pesonnel.add(Article_Class_Power_New);
			}
			return DataArray_Pesonnel;

		} catch (Exception e) {
			System.out.println("資料庫Print_Article_Class_Power錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Employee.reSerConstruct();
			Close();
		}

	}

	private void Update_View_Number(String Employee, String Viewer, int Article_Id) throws SQLException {
		stat = con.createStatement();
		try {
			if (Viewer.contains(Employee)) {

			} else {
//				Viewer = (Viewer == null || Viewer.equals("")) ? Viewer + Employee + ","
//						: Viewer + "," + Employee + ",";
				Viewer = Viewer + Employee;

				String Update_String = "Update Article SET ArticleView='" + Viewer + "'  where id=" + Article_Id;
				System.out.println(Update_String);

				stat.executeUpdate(Update_String);
				
			}
		} catch (Exception e) {
			
			System.out.println("資料庫Update_View_Number錯誤" + e.getMessage());

		} finally {
			Close();

		}
	}

	private ArrayList<T_Class> Update_Vilew() {
//		ArrayList<T_Class> View_Class=new ArrayList<T_Class>();
//		T_Class View_Result=new ArticleModel();
		String Article_Viewer = "";
		String Article_EmployeeName = "";
		String[] Split_String = SQLString.split(",");
		Article_EmployeeName = Split_String[1];

		int Article_Id = 0;
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(Split_String[0]);
			while (rs.next()) {
				Article_Viewer = rs.getString("ArticleView");
				Article_Id = rs.getInt("id");
			}
			Article_Viewer=(Article_Viewer==null)?"":Article_Viewer;
			Update_View_Number(Article_EmployeeName, Article_Viewer, Article_Id);
//			View_Result.Upload_Check="OK";
//			View_Class.add(View_Result);
			return null;

		} catch (Exception e) {
			System.out.println("資料庫Update_Vilew錯誤" + e.getMessage());
		} finally {
			Close();
		}
		return null;

	}

	public int Employee_LvCheck(int id) {
		String Employee_LvCheck = "select * from Employee where id=" + id; // 權限檢查
		DataArray_Pesonnel.clear();
		Employee_Class.reSerConstruct();
		int Employee_id = 0;
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(Employee_LvCheck);
			if (!rs.next()) {
				return 99;// 找無權限id

			} else {
				Employee_id = Integer.parseInt(rs.getString("AccountLevel"));
				return Employee_id;
			}

		} catch (Exception e) {
			System.out.println("資料庫Employee_LvCheck錯誤" + e.getMessage());
			return 99;// 找無權限id
		} finally {
			Close();
		}

	}
	
	private ArrayList<T_Class> One_Article() {
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			DataArray_Pesonnel.clear();
			
			ArrayList<ArticleModel> DataArray = new ArrayList<>();

			if (!rs.next()) {

				Article_Class.Upload_Check="NOK";
				DataArray_Pesonnel.add(Article_Class);
				return DataArray_Pesonnel;

			} else {
				T_Class One_Article = new ArticleModel();

				((ArticleModel) One_Article).setId(rs.getInt("id")).setEmpClass(rs.getString("EmpClass"))
						.setArticleClass(rs.getString("ArticleClass")).setArticleTitle(rs.getString("ArticleTitle"))
						.setArticleContext(rs.getString("ArticleContext")).setArticleEmpl(rs.getString("ArticleEmpl"))
						.setArticleUrl(rs.getString("ArticleUrl")).setArticleFileUrl(rs.getString("ArticleFileUrl"))
						.setArticleView(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv"))
						.setArticleCreate(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));
				One_Article.Upload_Check = "OK";
				DataArray_Pesonnel.add(One_Article);
				return DataArray_Pesonnel;
				
			}

		} catch (Exception e) {
			System.out.println("資料庫One_Article錯誤" + e.getMessage());
			return null;
		} finally {

			Close();
		}
		
		
	}


	
	
	
	private ArrayList<T_Class> Delete_Article() {

		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();
		try {
			if (Pass_Code.equals("A078")) {
				pst = con.prepareStatement(SQLString);
				pst.setLong(1, Personnel_Article.getId());
				pst.executeUpdate();
				Article_Class.Upload_Check = "OK";
			} else {
				Article_Class.Upload_Check = "NOK";

			}
			DataArray_Pesonnel.add(Article_Class);
			return DataArray_Pesonnel;

		} catch (Exception e) {
			System.out.println("資料庫Delete_Article錯誤" + e.getMessage());
			return null;
		} finally {
			Personnel_Article.reSerConstruct();
			Close();
		}

	}

	@Override
	public ArrayList<Sensory> SQLCase(CaseSQL caseSQL) throws SQLException {
		// TODO Auto-generated method stub

		switch (caseSQL) {

		case Prinall:
			return SQLQueryALL();
		case PrintOne:
			return SQLQueryOne();
		case PostDate:
			return SQLPostData();
		case DeleteOne:
			return DeleteOne();
		case PrinClass:
			return SQLQueryCalss();
		case UpLoadUrl:
			return SQLUpLoadeUrl();
		}

		return null;
	}

	@Override
	public ArrayList<T_Class> SQLCase_Personnel(CaseSQL caseSQL) throws SQLException {
		// TODO Auto-generated method stub

		switch (caseSQL) {
		case Insert_Employee:
			return Insert_Employee();
		case Insert_Article:
			return Insert_Article();
		case Personnel_Upload:
			return Personnel_Upload();
		case Print_Article:
			return Print_Article();
		case Update_Vilew:
			return Update_Vilew();
		case Quick_Search:
			return Quick_Searh();
		case Print_Article_Power:
			return Print_Article_Power();
		case Print_Article_Class:
			return Print_Article_Class();
		case Print_Article_Class_Power:
			return Print_Article_Class_Power();
		case Delete_Article:
			return Delete_Article();
		case Employee_Login:
			return Employee_Login();
		case One_Article:
			return One_Article();

		}

		return null;
	}

	@Override
	public void ReSettSQL(String SQLString, String SQLConnectionString, String Account, String Password) {
		// TODO Auto-generated method stub
		super.ConnectionString = SQLConnectionString;
		super.SQLString = SQLString;
		super.Account = Account;
		super.Password = Password;
	}

}
