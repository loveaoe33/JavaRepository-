package Personnel_Attend;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WindowApplication extends SQLOB {
	private SQLClass sqlclass;
	private final Employee employee;
	private String SQL_Str = null;
	private Date date = new Date(0);
	private HashMap<String, String> mapDepart = new HashMap<String, String>();
	private HashMap<String, String> selfDepart = new HashMap<String, String>();
	private ArrayList<String> localPersonnel=new ArrayList<String>();
	private ObjectMapper Windowmapper = new ObjectMapper();


	public WindowApplication(SQLClass sqlclass, Employee employee) {
		super(sqlclass);
		this.employee = employee;
		this.sqlclass = sqlclass;
		Init_EmpDepart(); // 儲存員工管轄權部門
		Init_SelfDepart();// 儲存員工已Mapping資料
		Init_localPersonnel();//儲存當月出勤紀錄
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
    public void Init_localPersonnel() {
		SQL_Str = "select * from time_table ";
		localPersonnel.clear();
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					localPersonnel.add(rs.getString("jsonString"));
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println("Init_localPersonnel錯誤" + e.getMessage());
		} finally {
			close_SQL();
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

	public String updateEmpAccount(String Emp_ID, String Emp_Name, String Department_Key, int Account_Lv) { // 更改人資系統資料
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

	public String Mapping_Employee(UserApi user) { // 新增員工Mapping
		System.out.println(checkDepartExist(user.getEmp_ID(), "selfDepart"));

		try {
			if (checkDepartExist(user.getEmp_ID(), "selfDepart")) {
				SQL_Str = "UPDATE empmerge SET MapName=?, MapDepart=? ,CreateDate=?,CreateName=? where Emp_ID=?";
				System.out.print("測試1");
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				System.out.print(sqlclass.getSql_Str());

				pst.setString(1, user.getMapName());
				pst.setString(2, user.getMapDepart());
				pst.setDate(3, Date_Time());
				pst.setString(4, user.getCreateName());
				pst.setString(5, user.getEmp_ID());

				pst.executeUpdate();
				pst.clearParameters();
				return "Update_Sucess";
			} else {
				SQL_Str = "insert into empmerge(id,Emp_ID,Emp_Name,MapName,OrigDepart,MapDepart,CreateDate,CreateName)"
						+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM empmerge";
				Res_SQL(SQL_Str);
				System.out.print("測試2");
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, user.getEmp_ID());
				pst.setString(2, user.getEmp_Name());
				pst.setString(3, user.getMapName());
				pst.setString(4, user.getOrigDepart());
				pst.setString(5, user.getMapDepart());
				pst.setDate(6, Date_Time());
				pst.setString(7, user.getCreateName());
				pst.executeUpdate();
				pst.clearParameters();
				return "Insert_Sucess";
			}
		} catch (SQLException e) {
			System.out.print("測試錯誤" + e.toString());
			return "SQLfail";
		} finally {
		Init_SelfDepart();
			close_SQL();
		}
	}

	public String Clear_Mapping(String Emp_ID) { // 清除Mapping部門
		try {
			if (checkDepartExist(Emp_ID, "selfDepart")) {
				SQL_Str = "delete * from empmerge where Emp_ID=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Emp_ID);
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

	public boolean UpdateGearing(String Emp_ID, String Depart) {
		try {
			SQL_Str = "Update empmerge SET OrigDepart=?,CreateDate=? where Emp_ID=?";
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Depart);
			pst.setDate(2, Date_Time());
			pst.setString(3, Emp_ID);
			pst.executeUpdate();
			pst.clearParameters();
			return true;
		} catch (SQLException e) {
			System.out.println("UpdateGearing錯誤" + e.getMessage());
			return false;

		} finally {
			close_SQL();
		}
	}

	public String UpdateEmployee(String Emp_ID, String ChangeName, String ChangeDepart, int ChangeLevel) {

		try {
			if (checkDepartExist(Emp_ID, "selfDepart") && UpdateGearing(Emp_ID, ChangeDepart)) {
				SQL_Str = "Update employee SET Emp_Name=?,Department_Key=?,Account_Lv=?,Create_Time=? where Emp_ID=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, ChangeName);
				pst.setString(2, ChangeDepart);
				pst.setInt(3, ChangeLevel);
				pst.setDate(4, Date_Time());

				pst.setString(5, Emp_ID);
				pst.executeUpdate();
				pst.clearParameters();
				Init_SelfDepart();
				return "Sucess";
			}
			return "fail";
		} catch (SQLException e) {
			System.out.println("UpdateEmployee錯誤" + e.getMessage());

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

	public <T> T getMapEmployee(ArrayList<String> empList, String Depart,String Emp_ID,int Account_Lv) { // 帶出出勤系統Mapping資料
		String paramName="";
		if(Account_Lv>=2) {
			SQL_Str="select * from empmerge where Emp_ID=?";
			paramName=Emp_ID;
		}else if(Account_Lv<2){
			SQL_Str="select * from empmerge where MapDepart=?";
			paramName=Depart;
		}
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, paramName);
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
			Init_EmpDepart();
			close_SQL();
		}

	}

	public void Init_EmpDepart() { // 暫存放入管轄權資料
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
			System.out.println("Init_EmpDepart錯誤" + e.getMessage());
		} finally {
			close_SQL();
		}
	}

	public void Init_SelfDepart() // 暫存放入selfDepart資料
	{

		SQL_Str = "select *  from empmerge";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {

				do {
					selfDepart.put(rs.getString("Emp_ID"), rs.getString("MapDepart"));
				} while (rs.next());

			}
		} catch (SQLException e) {
			System.out.println("Init_SelfDepart錯誤" + e.getMessage());
		} finally {
			close_SQL();
		}
	}

	public boolean checkDepartExist(String Emp_ID, String Depart_Switch) { // 確認管轄部門與是否Mapping
		if (Depart_Switch.equals("mapDepart")) {
			return (mapDepart.get(Emp_ID) != "" && mapDepart.get(Emp_ID) != null) ? true : false;

		} else if (Depart_Switch.equals("selfDepart")) {

			return (selfDepart.get(Emp_ID) != "" && selfDepart.get(Emp_ID) != null) ? true : false;
		}
		return false;
	}

	public String selectDepartEmp(String Emp_ID,int Account_Lv) { // 取得管轄權部門
		if (checkDepartExist(Emp_ID, "mapDepart") && Account_Lv<2) {
			return mapDepart.get(Emp_ID);
		} else if (checkDepartExist(Emp_ID, "selfDepart")) {
			return selfDepart.get(Emp_ID);
		} else {
			return "none";
		}

	}

	public String Update_Post_MapDepart(String DepartString, String Emp_ID) throws SQLException {// 更新管轄權部門
		try {
			if (checkDepartExist(Emp_ID, "mapDepart")) {
				SQL_Str = "Update mapdepart SET MapDepart=? WHERE Emp_ID=?";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, DepartString);
				pst.setString(2, Emp_ID);
				System.out.println("A");

			} else {
				SQL_Str = "insert into mapdepart(id,Emp_ID,MapDepart)"
						+ "select ifNull(max(id),0)+1,?,? FROM mapdepart";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Emp_ID);
				pst.setString(2, DepartString);
				System.out.println("B");

			}
			pst.executeUpdate();
			pst.clearParameters();

			Init_EmpDepart();
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

//	public String selectEmpData(ArrayList<String> SearchData, String Emp_Key, String Month_Switch, Date Start, Date End
//			) {
//		SQL_Str = (Month_Switch == "local")
//				? "select * from  time_table where User_Key=? AND MONTH(MapDepart.TimeRecord)=MONTH(CURDATE())"
//				: "select * from  time_table where User_Key=? AND  Appli_Date BETWEEN '" + Start + "'  AND '" + End
//						+ "'";
//		if(selfDepart.get(Emp_Key)!=null) {
//			Res_SQL(SQL_Str);
//			try {
//				pst = con.prepareStatement(sqlclass.getSql_Str());
//				pst.setString(1, selfDepart.get(Emp_Key));
//				rs = pst.executeQuery();
//				if (rs.next()) {
//					do {
//						SearchData.add(employee.getAppEmployeeData_JsonString(rs));
//					} while (rs.next());
//
//					return "Sucess";
//				}
//
//			} catch (SQLException e) {
//
//			} finally {
//				close_SQL();
//			}
//			
//		}
//		return "None data";
//	}

	public String allEmpData(ArrayList<String> SearchData, String Month_Switch,String SelectData,String Depart,String Emp,String Start,
			String End) {
	

		try {
		if(Month_Switch.equals("LocalDate" )&& Start.equals("") && End.equals("")) {
			SQL_Str="select * from  time_table where  MONTH(time_stamp)=MONTH(CURDATE())";
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
		}else if(Month_Switch.equals("RangeDate" )&& SelectData.equals("Emp")){
			DateTimeFormatter formatetr=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Date startDate=Date.valueOf(LocalDate.parse(Start));
			Date endDate=Date.valueOf(LocalDate.parse(End));
			System.out.println("輸出1" + startDate);

	        // 提取月份寫法
			// SQL_Str="select * from  time_table where  name=?  MONTH(time_stamp) BETWEEN  startMonth=? AND endMonth=?" ;
			SQL_Str="select * from  time_table where  name=? AND time_stamp BETWEEN  ? AND ?" ;
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
	        pst.setString(1, Emp);
	        pst.setDate(2, startDate);
	        pst.setDate(3, endDate);

		}else if(Month_Switch.equals("RangeDate" )&& SelectData.equals("Depart")){
			DateTimeFormatter formatetr=DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Date startDate=Date.valueOf(LocalDate.parse(Start));
			Date endDate=Date.valueOf(LocalDate.parse(End));
			System.out.println("輸出2" + startDate);

	        // 提取月份
            // SQL_Str="select * from  time_table where  depart=?  MONTH(time_stamp) BETWEEN  startMonth=? AND endMonth=?" ;
			SQL_Str="select * from  time_table where  depart=?  AND time_stamp BETWEEN  ? AND ?" ;
			Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
	        pst.setString(1, Depart);
	        pst.setDate(2, startDate);
	        pst.setDate(3, endDate);
	      }else {
			return "fail";
		}
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchData.add(employee.getAppEmployeeData_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
		} catch (SQLException e) {
			System.out.println("allEmpData錯誤" + e.getMessage());
            return "fail";
		} finally {
			close_SQL();
		}
		return "none";
	
	}

//	public String selectAllData(ArrayList<String> SearchData,String Month_Switch, Date Start, Date End
//			) {
//		SQL_Str = (Month_Switch == "local")
//				? "select * from  time_table where MONTH(MapDepart.TimeRecord)=MONTH(CURDATE())"
//				: "select * from  time_table where Appli_Date BETWEEN '" + Start + "'  AND '" + End
//						+ "'";
//		Res_SQL(SQL_Str);
//		try {
//			pst = con.prepareStatement(sqlclass.getSql_Str());
//			rs = pst.executeQuery();
//			if (rs.next()) {
//				do {
//					SearchData.add(employee.getAppEmployeeData_JsonString(rs));
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
    public boolean clearSQL() {
    	try {
    		SQL_Str = "Delete from time_table";
    		Res_SQL(SQL_Str);
			pst = con.prepareStatement(sqlclass.getSql_Str());
    		pst.executeUpdate();
    		pst.clearParameters();

        	return true;
    		
    	}catch(SQLException e) {
			System.out.println("clearSQL錯誤" + e.getMessage());
    		return false;
    	}finally {
    		
			close_SQL();

    	}

    }
	public String insertExcel(ArrayList<String> dataList) throws JsonMappingException, JsonProcessingException {
		try {
			if (dataList != null && !dataList.isEmpty() &&clearSQL()) {
				SQL_Str = "insert into time_table(id,depart,name,jsonString,time_stamp,insert_time)" + "select ifNull(max(id),0)+1,?,?,?,?,? FROM time_table";
				Res_SQL(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				con.setAutoCommit(false);
				for (String i : dataList) {
					JsonNode rootdata=Windowmapper.readTree(i);
					JsonNode dateNodeDate=rootdata.get("Date");
					JsonNode dateNodeDepart=rootdata.get("Department");
					JsonNode dateNodeName=rootdata.get("Name");
					pst.setString(1, dateNodeDepart.asText());
					pst.setString(2, dateNodeName.asText());
					pst.setString(3, i);
					pst.setString(4, dateNodeDate.asText());
					pst.setDate(5, Date_Time());
					pst.addBatch();
				}
				pst.executeBatch(); // 執行批量操作
				con.commit();
				pst.clearParameters();

				return "Sucess";
			} else {
				return "fail";
			}

		} catch (SQLException e) {
			System.out.println("insertExcel錯誤" + e.getMessage());
			return "SQLfail";
		} finally {
			close_SQL();
		}
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
