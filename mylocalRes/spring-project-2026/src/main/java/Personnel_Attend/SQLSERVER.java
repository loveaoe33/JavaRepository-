package Personnel_Attend;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import Personnel.EmployeeModel;

@Service
public class SQLSERVER extends SQLOB {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final SQLClass sqlclass;
	private final Employee employee;
	private final Department department;
	private final TimeData timeData;
	private final Appli_form appli_form;
	private final PasswordEncryption PassEncry;
	private Date date = new Date(0);
	private String Result = null;
	private String SQL_Str = null;
	static ArrayList<String> Announcement_List = new ArrayList();

	@Autowired
	public SQLSERVER(SQLClass sqlclass, Department department, Employee employee, TimeData timeData,
			Appli_form appli_form, PasswordEncryption PassEncry) {

		super(sqlclass);
		this.sqlclass = sqlclass;
		this.department = department;
		this.employee = employee;
		this.timeData = timeData;
		this.appli_form = appli_form;
		this.PassEncry = PassEncry;
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

	public boolean Check_Employee(String Emp_Account) {
		boolean Emp_Check = false;
		SQL_Str = "select * from Employee where  Emp_ID=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Account);
			rs = pst.executeQuery();
			if (rs.next()) {
				Emp_Check = false;
			} else {
				Emp_Check = true;
			}
		} catch (SQLException e) {
			System.out.println("Check_Employee錯誤" + e.getMessage());

		} finally {
			close_SQL();
		}
		return Emp_Check;

	}

	public String Insert_Employee(Employee employee_Par, TimeData timeData_Par) {
		Result = null;
		try {
			if (Check_Employee(employee_Par.getEmp_ID())) {
				SQL_Str = "insert into employee(id,Emp_ID,Password,Emp_Name,Department_Key,Account_Lv,Create_Time,Create_Name)"
						+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM employee";
				Res_SQL(SQL_Str);
				PreparedStatement Employee_Pst = con.prepareStatement(sqlclass.getSql_Str());

				Employee_Pst = con.prepareStatement(sqlclass.getSql_Str());
				Employee_Pst.setString(1, employee_Par.getEmp_ID());
				Employee_Pst.setString(2, PassEncry.hashedPassword(employee_Par.getPassword()));
				Employee_Pst.setString(3, employee_Par.getEmp_Name());
				Employee_Pst.setString(4, employee_Par.getDepartment_Key());
				Employee_Pst.setInt(5, employee_Par.getAccount_Lv());
				Employee_Pst.setDate(6, employee_Par.getCreate_Time());
				Employee_Pst.setString(7, employee_Par.getCreate_Name());

				if (Insert_TimeData_New(timeData_Par).equals("Sucess")) {

					Result = "Sucess";
				} else {
					return Result = "Insert_TimeData_New Error..";
				}
				Employee_Pst.executeUpdate();
				Employee_Pst.clearParameters();
			} else {
				Result = "Account Repeat..";

			}
		} catch (SQLException e) {
			System.out.println("帳號新增錯誤" + e.getMessage());

		} finally {
			close_SQL();
			return Result;

		}

	}

	public String Insert_Department(Department department_Par) { // 新增部門OK
		Result = null;
		SQL_Str = "insert into department(id,Department_Key,Department,Child_Department_Key)"
				+ "select ifNull(max(id),0)+1,?,?,? FROM department";
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, department_Par.getDepartment_Key());
			pst.setString(2, department_Par.getDepartment());
			pst.setString(3, department_Par.getChild_Department_Key());
			pst.executeUpdate();
			pst.clearParameters();
			Result = "Sucess";

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("部門新增錯誤" + e.getMessage());
			Result = "false";
		} finally {
			close_SQL();
			return Result;
		}

	}

	public boolean TimeData_Compare(TimeData timeData_Par) {
		double Last_Time = 0;
		SQL_Str = "select * from job_time  where Emp_Key=?";
		sqlclass.setSql_Str(SQL_Str);
//		Res_SQL(SQL_Str);
		System.out.println("TimeData_Compare的記憶體位置" + timeData_Par);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, timeData_Par.getEmp_Key());
			rs = pst.executeQuery();

			Last_Time = (rs.next()) ? rs.getDouble("Last_Time") : null;

			if (Last_Time + timeData_Par.getInsert_Time() > 0) {
				timeData_Par.setTime_Pon_Mark("Positive");
			} else {
				timeData_Par.setTime_Pon_Mark("Negative");
			}
			return true;

		} catch (SQLException e) {
			System.out.println("TimeData_Compare錯誤" + e.getMessage());
			return false;

		} finally {
			timeData_Par.setOld_Time(Last_Time);
			timeData_Par.setNew_Time(Last_Time + timeData_Par.getInsert_Time());

		}
	}

	public boolean Insert_TimeLog(String TimeLog, TimeData timeData_Par, String Switch) { // Insert_Time_Log
		sqlclass.setSql_Str(TimeLog);
		double New_Time = timeData_Par.getOld_Time() + timeData_Par.getInsert_Time();
		timeData_Par.setNew_Time(New_Time);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, timeData_Par.getTime_Log_Key());
			pst.setString(2, timeData_Par.getTime_Event());
			if (Switch.equals("init")) {
				pst.setString(3, "Init");
			} else if (Switch.equals("Review")) {
				pst.setString(3, "Cancel");

			} else {
				pst.setString(3, timeData_Par.getTime_Mark());
			}
			pst.setDouble(4, timeData_Par.getInsert_Time());
			pst.setDouble(5, timeData_Par.getOld_Time());
			pst.setDouble(6, timeData_Par.New_Time);
			pst.setDate(7, timeData_Par.getUpdate_Time());
			pst.setString(8, timeData_Par.getAttend_Key());

			pst.executeUpdate();
			pst.clearParameters();
			return true;
		} catch (SQLException e) {
			System.out.println("Insert_TimeLog錯誤" + e.getMessage());
			return false;
		} finally {
		}
	}

	public String Insert_TimeData_New(TimeData timeData_Par) { // JOB_Time new
		Result = null;
		String SQL_Str_JobTime = "insert into job_time(id,Emp_Key,Last_Time,Time_Pon_Mark,Time_Log_Key,Update_Time)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,? FROM job_time;";
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,? FROM time_log;";
		sqlclass.setSql_Str(SQL_Str_JobTime);
		try {
			PreparedStatement TimeData_pst = con.prepareStatement(sqlclass.getSql_Str());

			if (Insert_TimeLog(SQL_Str_TimeLog, timeData_Par, "Init")) {

				TimeData_pst.setString(1, timeData_Par.getEmp_Key());
				TimeData_pst.setDouble(2, timeData_Par.getLast_Time());
				TimeData_pst.setString(3, timeData_Par.getTime_Pon_Mark());
				TimeData_pst.setString(4, timeData_Par.getTime_Log_Key());
				TimeData_pst.setDate(5, timeData_Par.getUpdate_Time());
				TimeData_pst.executeUpdate();
				TimeData_pst.clearParameters();

				Result = "Sucess";
			} else {
				return Result = "false";
			}

		} catch (SQLException e) {
			System.out.println("JOB_Time update錯誤" + e.getMessage());
			Result = "false";
		} finally {
			return Result;

		}
	}

	public String Insert_TimeData_Update(Appli_form appli_from, TimeData timeData_Par) { // JOB_Time update
		Result = null;
		String SQL_Str_JobTime = "Update job_time SET Last_Time=?,Time_Pon_Mark=?,Update_Time=? where Emp_Key=?";
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,? FROM time_log";
		if (!TimeData_Compare(timeData_Par)) {// Time Compare
			return "false";
		}
		Res_SQL(SQL_Str_JobTime);
		System.out.println("AttendController的記憶體位置:" + timeData_Par);

		try {
			PreparedStatement TimeData_Update_pst = con.prepareStatement(sqlclass.getSql_Str());
			if (appli_from.getCheck_State().equals("Pass") && Insert_TimeLog(SQL_Str_TimeLog, timeData_Par, "Pass")
					&& appli_from.getReview_ID_Key() != "") {

				TimeData_Update_pst.setDouble(1, timeData_Par.getNew_Time());
				TimeData_Update_pst.setString(2, timeData_Par.getTime_Pon_Mark());
				TimeData_Update_pst.setDate(3, timeData_Par.getUpdate_Time());
				TimeData_Update_pst.setString(4, timeData_Par.getEmp_Key());
				TimeData_Update_pst.executeUpdate();
				TimeData_Update_pst.clearParameters();
				Result = "Sucess";

			} else if (appli_from.getCheck_State().equals("NPass")) {
				Insert_TimeLog(SQL_Str_TimeLog, timeData_Par, "NPass");
				Result = "Sucess_No_Update";

			}
		} catch (SQLException e) {
			Result = "false";
			System.out.println("JOB_Time update錯誤" + e.getMessage());
		} finally {

			Update_Appli_form(appli_from);
			timeData_Par.ResConstruct();
			appli_from.ResConstruct();
			return Result;
		}
	}

	public String Update_Employee(Employee employee_Par) {
		SQL_Str = "Update Employee SET Password=? where Emp_ID=?";
		Res_SQL(SQL_Str);
		Result = null;
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, PassEncry.hashedPassword(employee_Par.getPassword()));
			pst.setString(2, employee_Par.getEmp_ID());
			pst.executeUpdate();
			Result = "Sucess";
		} catch (SQLException e) {
			Result = "false";
			System.out.println("Update_Employee錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;

		}

	}

	public String Login_Employee(Employee employee_Par) {
		SQL_Str = "select * from employee where Emp_ID=?";
		Result = "false";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			String Account_Process = (employee_Par.getEmp_ID()).replace("--", "");
			pst.setString(1, Account_Process);
			rs = pst.executeQuery();
			if (rs.next()) {
				String Hash = rs.getString("Password");
				if (PassEncry.Password_Check(employee_Par.getPassword(), Hash)) {
					Result = String.format(
							"{\"Emp_ID\": \"%s\", \"Emp_Name\": \"%s\", \"Department_Key\": \"%s\", \"Account_Lv\": %d}",
							rs.getString("Emp_ID"), rs.getString("Emp_Name"), rs.getString("Department_Key"),
							rs.getInt("Account_Lv"));
				}
				return Result;
			} else {
				return Result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Login_Employee錯誤" + e.getMessage());
			return Result;

		} finally {
			close_SQL();

		}
	}

	public Double get_LstTime(String Emp_Key) {
		Result = null;
		double Result;
		SQL_Str = "select Last_Time from job_Time where Emp_Key=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);

			pst.executeQuery();
			rs = pst.executeQuery();

			if (rs.next()) {
				Result = rs.getDouble("Last_Time");
			} else {
				Result = (Double) null;
			}
			return Result;
		} catch (SQLException e) {
			System.out.println("get_LstTime錯誤" + e.getMessage());
			Result = (Double) null;
			return Result;

		} finally {
			close_SQL();

		}

	}

	public String Employee_LstTime(Appli_form appli_form_Par) {
		Result = null;
		SQL_Str = "select Last_Time from job_Time where Emp_Key=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, appli_form_Par.Emp_Key);
			pst.executeQuery();
			rs = pst.executeQuery();

			if (rs.next()) {
				appli_form_Par.Last_Time = rs.getDouble("Last_Time");
				appli_form_Par.Appli_Date = Date_Time();
				appli_form_Par.Apli_Total = appli_form_Par.getAppli_Time() + appli_form_Par.getLast_Time();
				Result = "Sucess";
			} else {
				Result = "false";
			}

		} catch (SQLException e) {
			Result = "false";
			System.out.println("Employee_LstTime錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;

		}
	}

	public String Attend_TimeData(Appli_form appli_form_Par) { // 積休申請
		if (Employee_LstTime(appli_form_Par).equals("Sucess")) {
			Result = null;
			SQL_Str = "insert into appli_form(id,Emp_Key,Department,Reason,Appli_Time,Last_Time,Apli_Total,Reason_Mark,Review_ID_Key,Appli_Date,Review_Date,Check_State)"
					+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,?,?,? FROM appli_form";
			Res_SQL(SQL_Str);
			try {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, appli_form_Par.getEmp_Key());
				pst.setString(2, appli_form_Par.getDepartment());
				pst.setString(3, appli_form_Par.getReason());
				pst.setDouble(4, appli_form_Par.getAppli_Time());
				pst.setDouble(5, appli_form_Par.getLast_Time());
				pst.setDouble(6, appli_form_Par.getApli_Total());
				pst.setString(7, appli_form_Par.getReason_Mark());
				pst.setString(8, appli_form_Par.getReview_ID_Key());
				pst.setDate(9, appli_form_Par.getAppli_Date());
				pst.setDate(10, null);
				pst.setString(11, appli_form_Par.getCheck_State());
				pst.executeUpdate();
				pst.clearParameters();
				Result = "Sucess";
			} catch (SQLException e) {

				System.out.println("Attend_TimeData錯誤" + e.getMessage());
				Result = "false";
			} finally {
				close_SQL();
				return Result;
			}
		} else {
			return "Cant Catch Employee_LstTime..";
		}
	}

	public void Update_Appli_form(Appli_form appli_form) {
		SQL_Str = "Update appli_form SET Review_ID_Key=?,Review_Date=?,Check_State=? where id=?";

		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, appli_form.getReview_ID_Key());
			pst.setDate(2, appli_form.getReview_Date());
			pst.setString(3, "Process");
			pst.setInt(4, appli_form.getId());
			pst.executeUpdate();
			pst.clearParameters();

		} catch (SQLException e) {
			System.out.println("Update_Appli_form錯誤" + e.getMessage());

		} finally {
			close_SQL();
		}

	}

	public boolean Insert_Review(Appli_form appli_form) {
		Result = null;
		SQL_Str = "insert into review_form(id,Review_ID_Key,Review_Manager,Review_Time,Review_Result)"
				+ "select ifNull(max(id),0)+1,?,?,?,? FROM review_form";
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, appli_form.getReview_ID_Key());
			pst.setString(2, appli_form.getReview_Manager());
			pst.setDate(3, appli_form.getReview_Time());
			pst.setString(4, appli_form.getReview_Result());
			pst.executeUpdate();
			pst.clearParameters();
			return true;

		} catch (SQLException e) {

			System.out.println("Insert_Review錯誤" + e.getMessage());
			return false;
		} finally {
			close_SQL();
		}
	}

	public String Review_TimeData(Appli_form appli_form, TimeData timeData) {
		Result = null;
		SQL_Str = "Select * from appli_form where id=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setInt(1, appli_form.id);
			pst.executeQuery();
			rs = pst.executeQuery();
			if (rs.next()) {
				appli_form.setEmp_Key(rs.getString("Emp_Key"));
				appli_form.setAppli_Time(rs.getDouble("Appli_Time"));
				appli_form.setLast_Time(rs.getDouble("Last_Time"));
				appli_form.setApli_Total(rs.getDouble("Apli_Total"));

			}
			Result = Insert_TimeData_Update(appli_form, timeData);
		} catch (SQLException e) {

			System.out.println("Review_TimeData錯誤" + e.getMessage());
			Result = "false";
		} finally {
			close_SQL();
			return Result;

		}
	}

	public String Update_Review(Appli_form appli_form, TimeData timeData) {

		if (appli_form.getCheck_State().equals("Pass") && Insert_Review(appli_form)) {
			timeData.setTime_Mark("Pass_" + timeData.Time_Mark);
		} else if (appli_form.getCheck_State().equals("NPass") && Insert_Review(appli_form)) {
			timeData.setTime_Mark("NPass_" + timeData.Time_Mark);

		} else {
			return "Check_State No Update...";
		}
		return Review_TimeData(appli_form, timeData);

	}

	public String Excel_All_TimeData_Post(ArrayList<String> emplyee_Excel_Data, String Emp_key, String Department) {

		if (get_Emp_Lv(Emp_key) == 99) {

			return "false";

		} else if (get_Emp_Lv(Emp_key) == 0) {
			SQL_Str = "	SELECT  employee.id,employee.Emp_Name, employee.Emp_ID,employee.Account_Lv ,department.Department,job_time.Last_Time,job_time.Time_Pon_Mark,job_time.Update_Time\r\n"
					+ "		from employee\r\n" + "		INNER JOIN department\r\n"
					+ "		ON  employee.Department_Key=department.Department_Key\r\n" + "		INNER JOIN job_time\r\n"
					+ "		ON employee.Emp_ID=job_time.Emp_Key";
		} else {
			SQL_Str = "	SELECT  employee.id,employee.Emp_Name, employee.Emp_ID,employee.Account_Lv ,department.Department,job_time.Last_Time,job_time.Time_Pon_Mark,job_time.Update_Time\r\n"
					+ "		from employee\r\n" + "		INNER JOIN department\r\n"
					+ "		ON  employee.Department_Key=department.Department_Key\r\n" + "		INNER JOIN job_time\r\n"
					+ "		ON employee.Emp_ID=job_time.Emp_Key where Department='" + Department + "'";
		}

		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				emplyee_Excel_Data.add(employee.Execll_All_JsonString(rs));
				while (rs.next()) {
					emplyee_Excel_Data.add(employee.Execll_All_JsonString(rs));
				}
			}
			return "Sucess";

		} catch (SQLException e) {
			System.out.println("Excel_All_TimeData_Post錯誤" + e.getMessage());

			return "false";

		} finally {
			close_SQL();
		}

	}

	public String Admin_Search_TimeDataM(ArrayList<String> emplyee_Appli_Data, String Emp_key, String Department,
			String KeyValue, String Date_Key_Start, String Date_Key_End) {
		;
		if (get_Emp_Lv(Emp_key) == 99) {
			return "false";

		} else if (get_Emp_Lv(Emp_key) == 0) {
			SQL_Str = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE Check_State = ? AND Appli_Date BETWEEN '" + Date_Key_Start + "'  AND '" + Date_Key_End
					+ "'";

		} else {
			SQL_Str = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE Check_State = ? AND Department='" + Department + "' AND Appli_Date BETWEEN'"
					+ Date_Key_Start + "'  AND '" + Date_Key_End + "'";
		}

		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, KeyValue);
			rs = pst.executeQuery();

			if (rs.next()) {
				do {
					emplyee_Appli_Data.add(employee.Appli_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			return "false";

		} catch (SQLException e) {
			System.out.println("Excel_All_TimeData_Post錯誤" + e.getMessage());

			return "false";

		} finally {
			close_SQL();
		}

	}

	public String Search_TimeData(ArrayList<String> emplyee_Appli_Data, int Emp_Lv, String Department,
			String KeyValue) {
		SQL_Str = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
				+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
				+ "WHERE Check_State = ? AND Department='" + Department + "'";
		if (Emp_Lv == 0) {
			SQL_Str = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE Check_State = ?";
		}

		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, KeyValue);
			rs = pst.executeQuery();

			if (rs.next()) {
				do {
					emplyee_Appli_Data.add(employee.Appli_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			return "false";

		} catch (SQLException e) {

			System.out.println("Search_TimeData錯誤" + e.getMessage());
			return "false";

		} finally {
			close_SQL();
		}

	}

	// 查詢歷史申請所有log
	public String SearchEmployee_History(ArrayList<String> SearchEmployee_HistoryString, String Emp_key,
			String Department) {
		if (get_Emp_Lv(Emp_key) == 99) {
			return "false";

		} else if (get_Emp_Lv(Emp_key) == 0) {
			SQL_Str = "SELECT employee.id, employee.Emp_Name, employee.Emp_ID, job_time.Time_Log_Key, Time_Log.Time_Event, "
					+ "appli_form.department, appli_form.Reason_Mark, appli_form.Appli_Date, Time_Log.Time_Mark, job_time.Last_Time, "
					+ "Time_Log.Insert_Time, Time_Log.Old_Time, Time_Log.New_Time, Time_Log.Update_Time, Time_Log.Attend_Key, "
					+ "review_form.Review_Manager, review_form.Review_Time, review_form.Review_Result "
					+ "FROM employee " + "INNER JOIN job_time ON employee.Emp_ID = job_time.Emp_Key "
					+ "INNER JOIN time_log ON job_time.Time_Log_Key = Time_Log.Time_Log_Key "
					+ "INNER JOIN review_form ON Time_Log.Attend_Key = review_form.Review_ID_Key "
					+ "INNER JOIN Appli_form ON review_form.Review_ID_Key = Appli_form.Review_ID_Key";

		} else {
			SQL_Str = "SELECT employee.id, employee.Emp_Name, employee.Emp_ID, job_time.Time_Log_Key, Time_Log.Time_Event, "
					+ "appli_form.department, appli_form.Reason_Mark, appli_form.Appli_Date, Time_Log.Time_Mark, job_time.Last_Time, "
					+ "Time_Log.Insert_Time, Time_Log.Old_Time, Time_Log.New_Time, Time_Log.Update_Time, Time_Log.Attend_Key, "
					+ "review_form.Review_Manager, review_form.Review_Time, review_form.Review_Result "
					+ "FROM employee " + "INNER JOIN job_time ON employee.Emp_ID = job_time.Emp_Key "
					+ "INNER JOIN time_log ON job_time.Time_Log_Key = Time_Log.Time_Log_Key "
					+ "INNER JOIN review_form ON Time_Log.Attend_Key = review_form.Review_ID_Key "
					+ "INNER JOIN Appli_form ON review_form.Review_ID_Key = Appli_form.Review_ID_Key"
					+ "where department='" + Department + "'";
		}
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchEmployee_HistoryString.add(employee.Time_Log_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			return "false";
		} catch (SQLException e) {

		} finally {
			close_SQL();
		}
		return "false";
	}

	// 查詢歷史申請月份log
	public String SearchEmployee_HistoryM(ArrayList<String> SearchEmployee_HistoryStringM, String Emp_key,
			String Department, String Date_Key_Start, String Date_Key_End) {
		if (get_Emp_Lv(Emp_key) == 99) {
			return "false";

		} else if (get_Emp_Lv(Emp_key) == 0) {
			SQL_Str = "SELECT employee.id, employee.Emp_Name, employee.Emp_ID, job_time.Time_Log_Key, Time_Log.Time_Event, "
					+ "appli_form.department, appli_form.Reason_Mark, appli_form.Appli_Date, Time_Log.Time_Mark, job_time.Last_Time, "
					+ "Time_Log.Insert_Time, Time_Log.Old_Time, Time_Log.New_Time, Time_Log.Update_Time, Time_Log.Attend_Key, "
					+ "review_form.Review_Manager, review_form.Review_Time, review_form.Review_Result "
					+ "FROM employee " + "INNER JOIN job_time ON employee.Emp_ID = job_time.Emp_Key "
					+ "INNER JOIN time_log ON job_time.Time_Log_Key = Time_Log.Time_Log_Key "
					+ "INNER JOIN review_form ON Time_Log.Attend_Key = review_form.Review_ID_Key "
					+ "INNER JOIN Appli_form ON review_form.Review_ID_Key = Appli_form.Review_ID_Key "
					+ "where Review_Time BETWEEN'" + Date_Key_Start + "'  AND '" + Date_Key_End + "'";

		} else {
			SQL_Str = "SELECT employee.id, employee.Emp_Name, employee.Emp_ID, job_time.Time_Log_Key, Time_Log.Time_Event, "
					+ "appli_form.department, appli_form.Reason_Mark, appli_form.Appli_Date, Time_Log.Time_Mark, job_time.Last_Time, "
					+ "Time_Log.Insert_Time, Time_Log.Old_Time, Time_Log.New_Time, Time_Log.Update_Time, Time_Log.Attend_Key, "
					+ "review_form.Review_Manager, review_form.Review_Time, review_form.Review_Result "
					+ "FROM employee " + "INNER JOIN job_time ON employee.Emp_ID = job_time.Emp_Key "
					+ "INNER JOIN time_log ON job_time.Time_Log_Key = Time_Log.Time_Log_Key "
					+ "INNER JOIN review_form ON Time_Log.Attend_Key = review_form.Review_ID_Key "
					+ "INNER JOIN Appli_form ON review_form.Review_ID_Key = Appli_form.Review_ID_Key "
					+ "where department='" + Department + "'AND Review_Time BETWEEN'" + Date_Key_Start + "'  AND '"
					+ Date_Key_End + "'";
		}
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchEmployee_HistoryStringM.add(employee.Time_Log_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			return "false";
		} catch (SQLException e) {

		} finally {
			close_SQL();
		}
		return "false";
	}

	public String Cancel_jobTime(TimeData timeData) {

		if (!TimeData_Compare(timeData)) {
			return "false";
		}
		SQL_Str = "Update job_time SET Last_Time=?,Time_Pon_Mark=?,Update_Time=? where Emp_Key=?";

		sqlclass.setSql_Str(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());

			pst.setDouble(1, timeData.getNew_Time());
			pst.setString(2, timeData.getTime_Pon_Mark());
			pst.setDate(3, timeData.getUpdate_Time());
			pst.setString(4, timeData.getEmp_Key());
			pst.executeUpdate();
			pst.clearParameters();
			return "Sucess";

		} catch (SQLException e) {
			System.out.println("Cancel_jobTime update錯誤" + e.getMessage());
			return "false";

		} finally {

		}

	}

	public String Cancel_timeLog(TimeData timeData) {

		SQL_Str = "select * from time_log where Attend_Key=?";
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,? FROM time_log;";

		String Time_LogKey = "";
		sqlclass.setSql_Str(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, timeData.getAttend_Key());
			rs = pst.executeQuery();
			if (rs.next()) {
				timeData.setTime_Log_Key(rs.getString("Time_Log_Key"));
				timeData.setTime_Event("Review");
				timeData.setTime_Mark("取消");
				timeData.setInsert_Time(-rs.getDouble("Insert_Time"));
				timeData.setUpdate_Time(Date_Time());

				if (Cancel_jobTime(timeData).equals("Sucess") && Insert_TimeLog(SQL_Str_TimeLog, timeData, "Review")) { // 這裡順序須注意，有空改良
					return "Scuess";
				}
			}
			return "false";
		} catch (SQLException e) {
			System.out.println("Cancel_timeLog錯誤" + e.getMessage());
			return "false";

		} finally {

		}

	}

	public String Cancel_Review(TimeData timeData) {
		if (Cancel_timeLog(timeData).equals("Scuess")) {
			try {
				SQL_Str = "Update review_form SET Review_Result=?,Review_Time=? where Review_ID_Key=?";
				sqlclass.setSql_Str(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, "NPass");
				pst.setDate(2, timeData.getUpdate_Time());
				pst.setString(3, timeData.getAttend_Key());
				pst.executeLargeUpdate();

				return "Scuess";
			} catch (SQLException e) {
				System.out.println("Cancel_Review錯誤" + e.getMessage());

			} finally {

			}
		}

		return "false";
	}

	public String Cancel_Appli(TimeData timeData) {
		SQL_Str = "select * from appli_form where id=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setInt(1, timeData.getId());
			rs = pst.executeQuery();

			if (rs.next()) {
				timeData.setAttend_Key(rs.getString("Review_ID_Key"));
				if (Cancel_Review(timeData).equals("Scuess")) {

					return "Sucess";

				}
				return "false";

			}
		} catch (SQLException e) {
			System.out.println("Cancel_Appli錯誤" + e.getMessage());

		} finally {
			close_SQL();
		}

		return "false";
	}
//	public String SearchEmployee_HistoryM() {
//		try {
//
//		} catch (SQLException e) {
//			System.out.println("SearchEmployee_HistoryM錯誤" + e.getMessage());
//
//		} finally {
//			close_SQL();
//
//		}
//		return null;
//	}

	public int get_Emp_Lv(String Emp_Key) {
		SQL_Str = "select Account_Lv from employee where Emp_ID=?";
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("Account_Lv");
			}
			return 99;
		} catch (SQLException e) {
			System.out.println("get_Emp_Lv錯誤" + e.getMessage());
			return 99;
		} finally {
			close_SQL();

		}
	}

	public void Admin_Change_Employee() {

	}

	public void Change_Employee() {

	}

	public <T> T getEmployee(String Depart) { // 部門員工
		String Emp_String = "";
		SQL_Str = "select * from employee where Department_Key=?";
		employee.Emp_List.clear();
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Depart);
			rs = pst.executeQuery();
			if (rs.next()) {

				do {

					employee.Emp_List.add(employee.getEmployee_JsonString(rs));

				} while (rs.next());
				return (T) employee.Emp_List;

			} else {
				return (T) "none";

			}
		} catch (SQLException e) {
			System.out.println("getEmployee錯誤" + e.getMessage());
			return (T) "Error";
		} finally {
			close_SQL();
		}
	}
	

	public String Announcement(String Key,String Announcement_String)  { // 公告欄處理
		String Announcement_Str = (Key.equals("Insert"))?"insert into announcement(id,Title,Context,Create_Time,Create_Name)"
				+ "select ifNull(max(id),0)+1,?,?,?,? FROM announcement" :"Delet * from announcement where id=?";
		Res_SQL(Announcement_Str);
		try {
			if(Key.equals("Insert")) {
				String Announcement_Spllite[]=Announcement_String.split(",");
				System.out.println("字串" + Announcement_String);

				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Announcement_Spllite[1]);
				pst.setString(2, Announcement_Spllite[2]);
				pst.setDate(3, Date_Time());
				pst.setString(4, Announcement_Spllite[0]);
			
				
			}else if(Key.equals("Delete")) {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setInt(1,Integer.parseInt(Announcement_Str));
			}
			pst.executeUpdate();
			pst.clearParameters();
			return "Sucess";
		} catch (SQLException e) {
			System.out.println("Announcement錯誤" + e.getMessage());
			return "false";

		} finally {

			close_SQL();

		}

	}
	

	
	public <T> T Announcement_Init_Data() throws JsonProcessingException { // 初始化布告欄要帶出的資料
		SQL_Str = "select * from announcement";
		String Announ_Str="";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
  
				do {	
					Announ_Str=String.format(
							"{\"id\": \"%d\",\"Title\": \"%s\", \"Context\": \"%s\", \"Create_Time\": \"%s\", \"Create_Name\": \"%s\"}",
							rs.getInt("id"), rs.getString("Title"), rs.getString("Context"), rs.getString("Create_Time"),
							rs.getString("Create_Name"));
					if(!Announcement_List.contains(Announ_Str)) {
						Announcement_List.add(Announ_Str);
					}
	
				} while (rs.next());
			}
			return (T) Announcement_List;
		} catch (SQLException e) {
			System.out.println("Announcement_Init_Data錯誤" + e.getMessage());
			return null;

		} finally {

			close_SQL();

		}

	}
	
	public <T> T Init_Data() throws JsonProcessingException { // 初始化部門要帶出的資料
		SQL_Str = "select * from Department";
		department.Department_List.clear();
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {

				do {
					department.Department_List.add(rs.getString("Department_Key"));
				} while (rs.next());
			}
			return (T) department.Department_List;
		} catch (SQLException e) {
			System.out.println("Init_Data錯誤" + e.getMessage());
			return null;
		} finally {
			close_SQL();
		}

	}

}
