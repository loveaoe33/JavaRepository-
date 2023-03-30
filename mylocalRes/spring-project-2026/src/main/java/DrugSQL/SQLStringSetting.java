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

import SensoryModel.Sensory;
import net.sf.json.JSONObject;

public class SQLStringSetting extends AbstractSQL {

	public static enum CaseSQL {
		Prinall, PrintOne, PostDate,DeleteOne,PrinClass
	}
	public static Sensory PostData=new Sensory("","","","","");
	private  Sensory CallBackData=new Sensory("","","","","");
	private  ArrayList<Sensory> DataArray=new ArrayList<Sensory>();
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

	private ArrayList<Sensory> SQLPostData(Sensory PostData) {
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
			
		}catch(Exception e) {
			
			System.out.println("資料庫刪除錯誤" + e.getMessage());

			
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
			return SQLPostData(PostData);
		case DeleteOne:
			return DeleteOne();
		case PrinClass:
			return 	SQLQueryCalss();
		}

		return null;
	}



	@Override
	public void ReSettSQL(String SQLString, String SQLConnectionString,String Account,String Password) {
		// TODO Auto-generated method stub
	       ConnectionString=SQLConnectionString;
		   SQLString=SQLString;
		   Account=Account;
		   Password=Password;
	}

}
