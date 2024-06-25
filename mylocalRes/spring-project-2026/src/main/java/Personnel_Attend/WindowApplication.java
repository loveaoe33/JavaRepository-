package Personnel_Attend;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class WindowApplication extends SQLOB {
	private SQLClass sqlclass;
	private final Employee employee;
	private String SQL_Str = null;
	private Date date = new Date(0);
	private HashMap<String, String> mapDepart = new HashMap<String, String>();
	private HashMap<String, String> selfDepart = new HashMap<String, String>();

	public WindowApplication(SQLClass sqlclass, Employee employee) {
		super(sqlclass);
		this.employee = employee;
		this.sqlclass = sqlclass;
		initEmpDepart(); // 儲存員工管轄權部門
		initSelfDepart();// 儲存員工已Mapping資料
		System.out.print("B組件:" + sqlclass);
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

	public Date Date_Time() {
		LocalDateTime currenDate = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(currenDate);
		date.setTime(timestamp.getTime());
		return date;
	}

	public String Print_Employee(String Emp_Key) {
		String Result = "fail";
		SQL_Str = "select * from empmerge where Emp_ID=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					Result = String.format(
							"{\"Emp_ID\": \"%s\",\"Emp_Name\": \"%s\", \"MapName\": \"%s\", \"OrigDepart\": \"%s\", \"MapDepart\": \"%s\", \"CreateName\": \"%s\", \"CreateDate\": \"%s\"    }",
							rs.getString("Emp_ID"), rs.getDouble("Emp_Name"), rs.getDouble("MapName"),
							rs.getString("OrigDepart"), rs.getString("MapDepart"), rs.getString("CreateName"),
							rs.getString("CreateDate"));
				} while (rs.next());

			}
			return Result;

		} catch (SQLException e) {
			return "fail";
		} finally {
			close_SQL();
		}

	}

	public String updateEmpAccount(String Emp_ID, String Emp_Name, String Department_Key, int Account_Lv) {
		SQL_Str = "UPDATE employee SET Emp_Name=?, Department_Key=?, Account_Lv=? WHERE Emp_ID=? ";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Name);
			pst.setString(2, Department_Key);
			pst.setInt(3, Account_Lv);
			pst.setString(4, Emp_ID);
			pst.executeUpdate();
			return "Sucess";

		} catch (SQLException e) {
			return "SQLfail";

		} finally {

			close_SQL();

		}
	}

	public boolean checkMapping(String Emp_ID) {
		SQL_Str = "select * from empmerge where Emp_ID=?";
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_ID);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
			return true;
		} catch (SQLException e) {
			return false;

		} finally {
			close_SQL();
		}
	}

	public String Mapping_Employee(String Emp_ID, String OrigName, String MapName, String OrigDepart, String MpaDepart,
			String Create_Name) { // 新增員工Mapping
		try {
			if (checkMapping(Emp_ID)) {
				SQL_Str = "insert into empmerge(id,Emp_ID,Emp_Name,MapName,OrigDepart,MpaDepart,Create_Time,Create_Name)"
						+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM empmerge";

				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Emp_ID);
				pst.setString(2, OrigName);
				pst.setString(3, MapName);
				pst.setString(4, OrigDepart);
				pst.setString(5, MpaDepart);
				pst.setDate(6, Date_Time());
				pst.setString(7, Create_Name);
				pst.executeUpdate();
				pst.clearParameters();
				return "Insert_Sucess";
			} else {
				SQL_Str = "UPDATE employee SET Emp_ID=?,Emp_Name=?,MapName=?, OrigDepart=?, MpaDepart=? ,Create_Time=?,Create_Name=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Emp_ID);
				pst.setString(2, OrigName);
				pst.setString(3, MapName);
				pst.setString(4, OrigDepart);
				pst.setString(5, MpaDepart);
				pst.setDate(6, Date_Time());
				pst.setString(7, Create_Name);
				pst.executeUpdate();
				pst.clearParameters();
				return "Update_Sucess";
			}
		} catch (SQLException e) {
			return "SQLfail";
		} finally {
			close_SQL();
		}
	}

	public String Clear_Mapping(String Emp_Key) { // 清除Mapping部門
		try {
			if (checkMapping(Emp_Key)) {
				SQL_Str = "delete * from empmerge where Emp_ID=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Emp_Key);
				pst.executeUpdate();
				pst.clearParameters();
				return "Sucess";
			}
			return "fail";
		} catch (SQLException e) {
			return "SQLfail";

		} finally {
			close_SQL();
		}
	}

	public String UpdateEmployee(String MapName, String MpaDepart, String Emp_Key) {

		try {
			if (checkMapping(Emp_Key)) {
				SQL_Str = "Update empmerge SET MapName=?,MpaDepart=? where Emp_ID=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, MapName);
				pst.setString(2, MpaDepart);
				pst.setString(3, Emp_Key);
				pst.executeUpdate();
				pst.clearParameters();
				return "Sucess";
			}
			return "fail";
		} catch (SQLException e) {
			return "SQLfail";

		} finally {
			close_SQL();
		}
	}

//	public String seleSQL(String MonthSwitch, String table, String mapDepart, String Level) {
//		String SQL_Str = "";
//		if (Level.equals("Admin") && table.equals("empmerge")) { // 找部門特定員工
////			SQL_Str =(MonthSwitch=="Local")?"select * from '"+table+"' where UserID=? AND MONTH(Start)=MONTH(CURDATE())":"select * from timetable where UserID=? AND Date BETWEEN ? AND ?";
//			SQL_Str = "select * from '" + table + "'"; // 找部門所有員工
//		} else if (Level.equals("Manager") && table.equals("empmerge")) {
//			SQL_Str = "select * from '" + table + "' where MapDepart=? OR"; // 找部門所有員工
//		} else if (Level.equals("Employee") && table.equals("empmerge")) {
//			SQL_Str = "select * from '" + table + "' where MapDepart=?"; // 找自己部門自己員工
//
//		} else if (Level.equals("Admin") && table.equals("time_table")) {
//			SQL_Str = "select * from '" + table + "'";// 所有出勤資料
//		} else if (Level.equals("Manager") && table.equals("time_table")) {
//			SQL_Str = "select * from '" + table + "' where Depart=? OR";// 管轄部門出勤資料
//		} else if (Level.equals("Employee") && table.equals("time_table")) {
//			SQL_Str = "select * from '" + table + "' where Emp_Key=? ";// 單一人出勤
//		}
//		return SQL_Str;
//
//	}

	public <T> T appInit_Data(ArrayList<String> DepartList) throws JsonProcessingException { // 帶出人資系統的部門
		SQL_Str = "select * from department";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {

				do {
					DepartList.add(rs.getString("Department_Key"));
				} while (rs.next());
				return (T) DepartList;

			}
			return (T) "none";
		} catch (SQLException e) {
			System.out.println("appInit_Data錯誤" + e.getMessage());
			return (T) "SQLfail";
		} finally {
			close_SQL();
		}

	}

	public <T> T getEmployee(ArrayList<String> empList, String Depart) { // 帶出人資系統的部門員工
		SQL_Str = "select * from employee where Department_Key=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Depart);
			rs = pst.executeQuery();
			if (rs.next()) {

				do {

					empList.add(employee.getDepartEmployee_JsonString(rs));

				} while (rs.next());

				return (T) empList;

			}
			return (T) "none";

		} catch (SQLException e) {
			System.out.println("getEmployee錯誤" + e.getMessage());
			return (T) "SQLfail";
		} finally {
			close_SQL();
		}
	}

	public <T> T getMapEmployee(ArrayList<String> empList, String Depart) { // 帶出出勤系統Mapping資料
		SQL_Str = "select * from empmerge where MapDepart=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Depart);
			rs = pst.executeQuery();

			if (rs.next()) {

				do {

					empList.add(employee.getAppEmployee_JsonString(rs));

				} while (rs.next());
				return (T) empList;

			}
			return (T) "none";

		} catch (SQLException e) {
			System.out.println("getEmployee錯誤" + e.getMessage());
			return (T) "SQLfail";
		} finally {
			close_SQL();
		}
	}

//	public String checkDepart(String Emp_Key) {   //確認mapdepart是否有其他管轄部門
//		String Result = "";
//		SQL_Str = "select * from mapdepart where Emp_ID=?";
//		
//		Res_SQL(SQL_Str);
//		try {
//			pst = con.prepareStatement(sqlclass.getSql_Str());
//			pst.setString(1, Emp_Key);
//			rs = pst.executeQuery();
//			if (rs.next()) {
//				Result = rs.getString("MapDepart");
//				return Result;
//			}
//			return Result;
//		} catch (SQLException e) {
//			return "SQLfail";
//		} finally {
//			close_SQL();
//		}
//
//	}

	public String Insert_MapDepart(MergeClass mergeclass) { // 新增MapDepart
		String Result = "";
		SQL_Str = "insert into mapdepart(id,Emp_ID,Emp_Name,MapName,OrigDepart,MapDepart,CreateName,CreateDate)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,?,?,? FROM empmerge";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, mergeclass.getEmp_ID());
			pst.setString(2, mergeclass.getEmp_Name());
			pst.setString(3, mergeclass.getMapName());
			pst.setString(4, mergeclass.getOrigDepart());
			pst.setString(5, mergeclass.getMapDepart());
			pst.setString(6, mergeclass.getCreateName());
			pst.setDate(7, Date_Time());
			rs = pst.executeQuery();
			return "Sucess";
		} catch (SQLException e) {
			return "SQLfail";
		} finally {
			initEmpDepart();
			close_SQL();
		}

	}

	public void initEmpDepart() { // 暫存放入管轄權資料
		mapDepart.clear();
		SQL_Str = "select * from mapdepart";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					mapDepart.put(rs.getString("Emp_ID"), rs.getString("MapDepart"));
				} while (rs.next());
				
			}
		
					
			
		} catch (SQLException e) {
			System.out.println("initEmpDepart錯誤" + e.getMessage());
		} finally {
			close_SQL();
		}
	}

	public void initSelfDepart() // 暫存放入selfDepart資料
	{

		SQL_Str = "select *  from empmerge";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				selfDepart.put(rs.getString("Emp_ID"), rs.getString("MapDepart"));
			}
		} catch (SQLException e) {
			System.out.println("initSelfDepart錯誤" + e.getMessage());
		} finally {
			close_SQL();
		}
	}

	public boolean checkDepartExist(String Emp_ID) {
		System.out.println("checkDepartExist" +mapDepart.get(Emp_ID));

		return (mapDepart.get(Emp_ID) != "" &&  mapDepart.get(Emp_ID) != null) ? true : false;
	}

	public String selectDepartEmp(String Emp_ID) { // 取得管轄權部門
		if (checkDepartExist(Emp_ID)) {
			return mapDepart.get(Emp_ID);
		} else {
			return selfDepart.get(Emp_ID);
		}

	}

	public String Update_Post_MapDepart(String DepartString,String Emp_ID) throws SQLException {
		System.out.println(checkDepartExist(Emp_ID));

		if (checkDepartExist(Emp_ID)) {
			SQL_Str = "Update mapdepart SET MapDepart=? WHERE Emp_ID=?";
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, DepartString);
			pst.setString(2, Emp_ID);
			System.out.println("A");

		} else {
			SQL_Str = "insert into mapdepart(id,Emp_ID,MapDepart)" + "select ifNull(max(id),0)+1,?,? FROM mapdepart";
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_ID);
			pst.setString(2, DepartString);
			System.out.println("B");

		}
		try {
			
			pst.executeUpdate();
			pst.clearParameters();
			System.out.println("C");

			initEmpDepart();
			return "Sucess";
		} catch (SQLException e) {
			System.out.println("Update_Post_MapDepart錯誤" + e.getMessage());
			return "fail";
		} finally {
			close_SQL();
		}
	}

//	public ArrayList<String> selectDepartEmp(ArrayList<String>SearchData,String Emp_Key,String Level) {
//
//		try {
//			if(checkDepart()!="" && checkDepart()!="SQLfail") {
//				SQL_Str=seleSQL(checkDepart(),"mapdepart",checkDepart(), Level);
//			}else if(checkDepart()!="SQLfail") {
//				SQL_Str=seleSQL(checkDepart(),"mapdepart",mapDepart.get(Emp_Key), Level);
//			}
//			Res_SQL(SQL_Str);
//			pst = con.prepareStatement(sqlclass.getSql_Str());
//			rs=pst.executeQuery();
//			if (rs.next()) {
//				do {
//					SearchData.add(employee.getEmployeeHistory_JsonString(rs));
//				} while (rs.next());
//
//			}
//			return SearchData;
//
//		} catch (SQLException e) {
//
//		} finally {
//			close_SQL();
//		}
//		return null;
//		
//	}

	public String selectEmpData(ArrayList<String> SearchData, String Emp_Key, String Month_Switch, Date Start, Date End,
			String Level) {
		SQL_Str = (Month_Switch == "local")
				? "select * from  time_table where User_Key=? AND MONTH(MapDepart.TimeRecord)=MONTH(CURDATE())"
				: "select * from  time_table where User_Key=? AND  Appli_Date BETWEEN '" + Start + "'  AND '" + End
						+ "'";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchData.add(employee.getAppEmployeeData_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}

		} catch (SQLException e) {

		} finally {
			close_SQL();
		}
		return null;
	}

	public String oneDepartEmpData(ArrayList<String> SearchData, String Emp_Key, String Month_Switch, Date Start,
			Date End, String Level) {
		SQL_Str = (Month_Switch == "local")
				? "select * from  time_table where MapDepart=? AND MONTH(MapDepart.TimeRecord)=MONTH(CURDATE())"
				: "select * from  time_table where MapDepart=? AND  Appli_Date BETWEEN '" + Start + "'  AND '" + End
						+ "'";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchData.add(employee.getAppEmployeeData_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}

		} catch (SQLException e) {

		} finally {
			close_SQL();
		}
		return null;
	}

	public String selectAllData(ArrayList<String> SearchData, String Emp_Key, String Month_Switch, Date Start, Date End,
			String Level) {
		SQL_Str = (Month_Switch == "local")
				? "select * from  time_table where MapDepart=? AND MONTH(MapDepart.TimeRecord)=MONTH(CURDATE())"
				: "select * from  time_table where MapDepart=? AND  Appli_Date BETWEEN '" + Start + "'  AND '" + End
						+ "'";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchData.add(employee.getAppEmployeeData_JsonString(rs));
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
