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
		Prinall, PrintOne, PostDate,DeleteOne,PrinClass,UpLoadUrl,Personnel_Upload,Insert_Employee,Insert_Article,Print_Article,Quick_Search,Update_Vilew
	}
	public static Sensory PostData=new Sensory("","","","","");
	public static ArticleModel Personnel_Article=new ArticleModel();
	public static EmployeeModel Personnel_Employee=new EmployeeModel();
	private  Sensory CallBackData=new Sensory("","","","","");
	private  ArrayList<Sensory> DataArray=new ArrayList<Sensory>();
	
	private  static ArrayList<T_Class> DataArray_Pesonnel=new ArrayList<T_Class>();   //Personnel的回傳陣列
	private  static  T_Class Article_Class =new ArticleModel(); //確認有無成功用的回傳物件的回傳物件
	private  static  T_Class Employee_Class =new EmployeeModel(); //確認有無成功用的回傳物件的回傳物件


	
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

	public SQLStringSetting(String SQLString, String SQLConnectionString,String Account,String Password) throws ClassNotFoundException {
		super.SQLString = SQLString;
		super.ConnectionString = SQLConnectionString;
		super.Account=Account;
		super.Password=Password;
		SQLConnection();
	}

	@Override
	protected void SQLConnection() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");// 註冊driver
			con = DriverManager.getConnection(ConnectionString,Account,Password);
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
			if (rs==null) {

				CallBackData.setDataCheck(false);
				DataArray.add(CallBackData);
				return DataArray;

			} else

			{

				while (rs.next()) {
					//必須產生不同記憶體位置 否則會改到同筆資料
					Sensory CallBackData=new Sensory("","","","","");
					CallBackData.setId(rs.getInt("id"));
					CallBackData.setSensorContext(rs.getString("SensorContext"));
					CallBackData.setSensorEmp(rs.getString("SensorEmp"));
					CallBackData.setSensorDate(rs.getString("SensorDate"));
					CallBackData.setSensorKey(rs.getString("SensorKey"));
					CallBackData.setSensorTitle(rs.getString("SensorTile"));
					//Url
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
					//Url
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
	private ArrayList<Sensory> SQLUpLoadeUrl(){
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat=con.createStatement();
			stat.executeUpdate(SQLString);
		    CallBackData.setDataCheck(true);
		}catch(Exception e) {
			System.out.println("資料庫更新錯誤" + e.getMessage());	

		}finally{
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
		
		}finally{
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

            while(rs.next())
            {
				Sensory CallBackData=new Sensory("","","","","");
				CallBackData.setId(rs.getInt("id"));
				CallBackData.setSensorContext(rs.getString("SensorContext"));
				CallBackData.setSensorEmp(rs.getString("SensorEmp"));
				CallBackData.setSensorDate(rs.getString("SensorDate"));
				CallBackData.setSensorKey(rs.getString("SensorKey"));
				CallBackData.setSensorTitle(rs.getString("SensorTile"));
				//Url
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
	
	private ArrayList<Sensory>DeleteOne(){
		DataArray.clear();
		CallBackData.reSetConstruct();
		try {
			stat=con.createStatement();
			stat.executeUpdate(SQLString);
		    CallBackData.setDataCheck(true);
			
		}catch(Exception e) {
			
			System.out.println("資料庫刪除錯誤" + e.getMessage());

			
		}
		return DataArray;
	}
	
	
	

	
	private ArrayList<T_Class>Personnel_Upload(){    //Pessonel_檔案處理上傳
		
		DataArray_Pesonnel.clear();
		Article_Class.reSerConstruct();
   		try {
			stat=con.createStatement();
			stat.executeUpdate(SQLString);
			Article_Class.Upload_Check="ok";  //確認檔案有成功上傳與更新資料庫
			DataArray_Pesonnel.add(Article_Class);
		}catch(Exception e) {
			System.out.println("資料庫Personnel_Upload錯誤" + e.getMessage());	

		}finally{
			Close();
		}
		return DataArray_Pesonnel;
		
	}
	private ArrayList<T_Class>Insert_Article(){
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
//		    ArrayList<T_Class> DataArray=new ArrayList<String>();
//		    DataArray.add("Success");
            return null;
		} catch (Exception e) {
    
			System.out.println("資料庫Insert_Article_錯誤" + e.getMessage());	
			return null;
		
		}finally{
			Close();
		}
		

	}
	private ArrayList<T_Class> Insert_Employee(){
		try {
			pst = con.prepareStatement(SQLString);
			pst.setString(1, Personnel_Employee.getAccount());
			pst.setString(2, Personnel_Employee.getPassword());
			pst.setString(3, Personnel_Employee.getArticleClass());
			pst.setString(4, Personnel_Employee.getAccountLevel());
			pst.setString(5, Personnel_Employee.getDepartment());
			pst.setString(6, Personnel_Employee.getCreateDate());
			pst.executeUpdate();
			pst.clearParameters();
		    ArrayList<String> DataArray=new ArrayList<String>();
		    DataArray.add("Success");
//            return DataArray;
			return null;

		} catch (Exception e) {
    
			System.out.println("資料庫Insert_Employee錯誤" + e.getMessage());	
			return null;
		
		}finally{
			Close();
		}
		
	}
	private ArrayList<T_Class>Print_Article_(){
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(SQLString);
			while(rs.next()) {
			Personnel_Article.setArticleClass(rs.getString("EmpClass")).setArticleContext(rs.getString("ArticleClass")).setArticleCreate(rs.getString("ArticleTitle")).setArticleEmpl(rs.getString("ArticleContext")).
			setArticleFileUrl(rs.getString("ArticleEmpl")).setArticleLock(rs.getString("ArticleUrl")).setArticleLv(rs.getString("ArticleFileUrl")).setArticleTitle(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv")).
			setArticleView(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));				
			}
		    ArrayList<ArticleModel> DataArray=new ArrayList<ArticleModel>();
		    DataArray.add(Personnel_Article);
//            return DataArray;
			return null;

		}catch(Exception e) {
			System.out.println("資料庫Power_Print_Article_錯誤" + e.getMessage());	
			return null;
		}finally{
			Close();
		}
		
	}
	

//	private ArrayList<ArticleModel>Print_Article_(){
//		try {
//			stat = con.createStatement();
//			rs = stat.executeQuery(SQLString);
//			while(rs.next()) {
//			Personnel_Article.setArticleClass(rs.getString("EmpClass")).setArticleContext(rs.getString("ArticleClass")).setArticleCreate(rs.getString("ArticleTitle")).setArticleEmpl(rs.getString("ArticleContext")).
//			setArticleFileUrl(rs.getString("ArticleEmpl")).setArticleLock(rs.getString("ArticleUrl")).setArticleLv(rs.getString("ArticleFileUrl")).setArticleTitle(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv")).
//			setArticleView(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));				
//			}
//		    ArrayList<ArticleModel> DataArray=new ArrayList<ArticleModel>();
//		    DataArray.add(Personnel_Article);
//            return DataArray;
//		}catch(Exception e) {
//			System.out.println("資料庫Print_Article_錯誤" + e.getMessage());	
//			return null;
//		}finally{
//			Close();
//		}
//		
//	}
//	private ArrayList<Sensory>Print_Article_Class(){
//		try {
//			stat = con.createStatement();
//			rs = stat.executeQuery(SQLString);
//			while(rs.next()) {
//			Personnel_Article.setArticleClass(rs.getString("EmpClass")).setArticleContext(rs.getString("ArticleClass")).setArticleCreate(rs.getString("ArticleTitle")).setArticleEmpl(rs.getString("ArticleContext")).
//			setArticleFileUrl(rs.getString("ArticleEmpl")).setArticleLock(rs.getString("ArticleUrl")).setArticleLv(rs.getString("ArticleFileUrl")).setArticleTitle(rs.getString("ArticleView")).setArticleUrl(rs.getString("ArticleLv")).
//			setArticleView(rs.getString("ArticleCreate")).setEmpClass(rs.getString("ArticleLock"));				
//			}
//		    ArrayList<ArticleModel> DataArray=new ArrayList<ArticleModel>();
//		    DataArray.add(Personnel_Article);
//            return DataArray;
//		}catch(Exception e) {
//			System.out.println("資料庫Print_Article_錯誤" + e.getMessage());	
//			return null;
//		}finally{
//			Close();
//		}
//		
//	}
//	private ArrayList<Sensory>Quick_Search(){
//		try {
//			
//		}catch(Exception e) {
//			
//		}
//		return DataArray;
//		
//	}
	private ArrayList<Sensory>Update_Vilew(){
		try {
			
		}catch(Exception e) {
			
		}
		return DataArray;
		
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
			return 	SQLQueryCalss();
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
			return null;
		case Print_Article:
			return null;
		case Update_Vilew:
			return null;
		}

		return null;
	}
	
	

	
	
	@Override
	public void ReSettSQL(String SQLString, String SQLConnectionString,String Account,String Password) {
		// TODO Auto-generated method stub
	       super.ConnectionString=SQLConnectionString;
	       super.SQLString=SQLString;
	       super.Account=Account;
	       super.Password=Password;
	}

}
