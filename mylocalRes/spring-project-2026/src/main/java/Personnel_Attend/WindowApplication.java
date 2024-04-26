package Personnel_Attend;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class WindowApplication extends SQLOB{
	private SQLClass sqlclass;
	private final Employee employee = new Employee();
	private String SQL_Str = null;
	private Date date = new Date(0);
	public WindowApplication(SQLClass sqlclass) {
		super(sqlclass);
		// TODO Auto-generated constructor stub
	}
	
	public void Res_SQL(String Trans_SQLString) {

		sqlclass.setSql_Str(Trans_SQLString);
		try {
			ResSQL();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("字串更新失敗" + e.getMessage());
		}
	}
	

	public String Mapping_Employee() {
		SQL_Str = "insert into empmerge(id,Emp_ID,OrigName,MapName,OrigDepart,MpaDepart,Create_Time,Create_Name)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM empmerge";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.executeUpdate();
			pst.clearParameters();
			return "Sucess";
		} catch (SQLException e) {
			return "fail";

		} finally {
			close_SQL();
		}
	}
	
	public String Clear_Mapping() {
		SQL_Str = "delete * from empmerge where Emp_ID=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.executeUpdate();
			pst.clearParameters();
			return "Sucess";
		} catch (SQLException e) {
			return "fail";

		} finally {
			close_SQL();
		}
	}
	
	
	
	
	
	public String seleSQL(String MonthSwitch,String table,String Depart) {
		String SQL_Str="";
		if(table.equals("time_table") && Depart.equals("Depart")) {
			SQL_Str =(MonthSwitch=="Local")?"select * from '"+table+"' where UserID=? AND MONTH(Start)=MONTH(CURDATE())":"select * from timetable where UserID=? AND Date BETWEEN ? AND ?";
		}
		else if(table.equals("Depart")) {
			SQL_Str ="select * from '"+table+"' where id=?";
			
		}
		return SQL_Str;

	}
	
	public String selectEmpData(ArrayList<String> SearchData,String Emp_Key,String MonthSwitch,Date Start,Date End) {
		SQL_Str=seleSQL(MonthSwitch,"time_table","None");
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs=pst.executeQuery();
			if (rs.next()) {
				do {
					SearchData.add(employee.getEmployeeHistory_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			
		} catch (SQLException e) {

		} finally {
			close_SQL();
		}
		return null;
	}
	
//	public String selectDepData(ArrayList<String> SearchData,String Depart,String MonthSwitch,Date Start,Date End) {
//		SQL_Str=seleSQL("","mapdepart");
//
//		Res_SQL(SQL_Str);
//		try {
//			
//			pst = con.prepareStatement(sqlclass.getSql_Str());
//			String value=(Depart.equals("全部"))?"id":Depart;
//			pst.setString(0, value);
//			rs=pst.executeQuery();
//			if (rs.next()) {
//				do {
//					selectEmpData(SearchData, rs.getString(""), MonthSwitch, End, End)
//					SearchData.add(employee.getEmployeeHistory_JsonString(rs));
//				} while (rs.next());
//
//				return "Sucess";
//			}
//			
//		} catch (SQLException e) {
//
//		} finally {
//			close_SQL();
//		}
//		return null;
//	}
	
}
