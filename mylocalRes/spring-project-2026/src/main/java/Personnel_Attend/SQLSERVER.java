package Personnel_Attend;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import Personnel.EmployeeModel;

@Repository
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
	static ArrayList<String> PassCode_List = new ArrayList();


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
		System.out.print("A組件:"+sqlclass);

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
			Result = "fail";
		} finally {
			close_SQL();
			return Result;
		}

	}

	public boolean TimeData_Compare(TimeData timeData_Par) {
		double Last_Time = 0;
//		SQL_Str = "select * from job_time  where Emp_Key=?";
//		sqlclass.setSql_Str(SQL_Str);
//		Res_SQL(SQL_Str);
		try {
//			pst = con.prepareStatement(sqlclass.getSql_Str());
//			pst.setString(1, timeData_Par.getEmp_Key());
//			rs = pst.executeQuery();

//			Last_Time = (rs.next()) ? rs.getDouble("Last_Time") : null;
			Last_Time = get_LstTime(timeData_Par.getEmp_Key(), "Last_Time");

			if (Last_Time + timeData_Par.getInsert_Time() > 0) {
				timeData_Par.setTime_Pon_Mark("Positive");
			} else {
				timeData_Par.setTime_Pon_Mark("Negative");
			}
			return true;

		} finally {
			timeData_Par.setOld_Time(Last_Time);
			timeData_Par.setNew_Time(Last_Time + timeData_Par.getInsert_Time());

		}
	}

	public String getEmpyoee_Data(String Emp_Key) {
		Result = null;
		SQL_Str = "select * from job_time  where Emp_Key=?";
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);
			rs = pst.executeQuery();
			return Result = (rs.next()) ? employee.getJobTime_JsonString(rs) : "No Select...";
		} catch (SQLException e) {
			System.out.println("getEmpyoee_Data錯誤" + e.getMessage());
			return "false";

		} finally {
			close_SQL();
		}
	}

	public boolean Insert_Special_Log(TimeData timeData_Par, double Lst_Special) throws SQLException { // Insert_Special
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Special_Date,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,? FROM time_log;";
		String SQL_Str_JobTime = "Update job_time SET Special_Date=?,Update_Time=? where Emp_Key=?";
		timeData_Par.setUpdate_Time(Date_Time());
		timeData_Par.setTime_Log_Key(Get_TimeKey(timeData_Par.getEmp_Key()));
		try {
			if (Insert_TimeLog(SQL_Str_TimeLog, timeData_Par, "Special")) {
				Lst_Special = get_LstTime(timeData_Par.getEmp_Key(), "Special_Date");
				sqlclass.setSql_Str(SQL_Str_JobTime);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setInt(1, (int) (Lst_Special + timeData_Par.getSpecial_Date()));
				pst.setDate(2, timeData_Par.getUpdate_Time());
				pst.setString(3, timeData_Par.getEmp_Key());
				pst.executeUpdate();
				return true;
			}
			return false;
		} catch (SQLException e) {
			System.out.println("Insert_Special_Log錯誤" + e.getMessage());
			return false;
		} finally {
			close_SQL();

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
			pst.setDouble(4, timeData_Par.getInsert_Time());
			pst.setDouble(5, timeData_Par.getOld_Time());
			pst.setDouble(6, timeData_Par.New_Time);
			pst.setDouble(7, 0);
			pst.setDate(8, timeData_Par.getUpdate_Time());
			pst.setString(9, timeData_Par.getAttend_Key());

			if (Switch.equals("Init")) {
				pst.setString(3, "Init");
			} else if (Switch.equals("Review")) {
				pst.setString(3, "Cancel");

			} else if (Switch.equals("Special")) {
				pst.setString(3, timeData.getTime_Mark());
				pst.setDouble(7, timeData.getSpecial_Date());

			} else {
				pst.setString(3, timeData_Par.getTime_Mark());
			}

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
		String SQL_Str_JobTime = "insert into job_time(id,Emp_Key,Last_Time,Special_Date,Time_Pon_Mark,Time_Log_Key,Update_Time)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,? FROM job_time;";
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Special_Date,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,? FROM time_log;";
		sqlclass.setSql_Str(SQL_Str_JobTime);
		try {
			PreparedStatement TimeData_pst = con.prepareStatement(sqlclass.getSql_Str());

			if (Insert_TimeLog(SQL_Str_TimeLog, timeData_Par, "Init")) {

				TimeData_pst.setString(1, timeData_Par.getEmp_Key());
				TimeData_pst.setDouble(2, timeData_Par.getLast_Time());
				TimeData_pst.setDouble(3, 0);
				TimeData_pst.setString(4, timeData_Par.getTime_Pon_Mark());
				TimeData_pst.setString(5, timeData_Par.getTime_Log_Key());
				TimeData_pst.setDate(6, timeData_Par.getUpdate_Time());
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
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Special_Date,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,? FROM time_log";
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

	public String Select_Password(String Emp_ID) {
		Result = null;
		SQL_Str = "select Password from Employee  where Emp_ID=?";
		Res_SQL(SQL_Str);
		try {

			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_ID);
			rs = pst.executeQuery();
			if (rs.next()) {
				Result = rs.getString("Password");
			} else {
				Result = "false";

			}
		} catch (SQLException e) {
			Result = "false";
			System.out.println("Select_Password錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;

		}

	}

	public String Super_Update_Employee(String Emp_ID, String NewPassword) {
		SQL_Str = "Update Employee SET Password=? where Emp_ID=?";
		Res_SQL(SQL_Str);
		Result = null;
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, PassEncry.hashedPassword(NewPassword));
			pst.setString(2, Emp_ID);
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

	public String Update_Employee(Employee employee_Par, String oldPassword) {
		String HashPassword = Select_Password(employee_Par.getEmp_ID());
		SQL_Str = "Update Employee SET Password=? where Emp_ID=?";
		Res_SQL(SQL_Str);
		Result = null;
		try {
			if (PassEncry.Password_Check(oldPassword, HashPassword) != true) {
				Result = "fail";
			} else {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, PassEncry.hashedPassword(employee_Par.getPassword()));
				pst.setString(2, employee_Par.getEmp_ID());
				pst.executeUpdate();
				Result = "Sucess";
			}

		} catch (SQLException e) {
			Result = "false";
			System.out.println("Update_Employee錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;

		}

	}

	public String Login_Employee(Employee employee_Par) {
		SQL_Str = "select employee.Emp_ID,employee.Password,employee.Emp_Name,department.Department,employee.Account_Lv,job_time.Last_Time,job_time.Special_Date from employee  INNER JOIN department on employee.Department_Key=department.Department_Key  INNER JOIN  job_time ON employee.Emp_ID =job_time.Emp_Key WHERE employee.Emp_ID=?";
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
					Result = employee.getEmployee_JsonString(rs);
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

	public Double get_LstTime(String Emp_Key, String Switch) {
		Result = null;
		double Result;
		SQL_Str = "select *  from job_Time where Emp_Key=?";
		sqlclass.setSql_Str(SQL_Str);

//		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);
			rs = pst.executeQuery();

			if (rs.next()) {
				Result = rs.getDouble(Switch);
			} else {
				Result = (Double) null;
			}
			return Result;
		} catch (SQLException e) {
			System.out.println("get_LstTime錯誤" + e.getMessage());
			Result = (Double) null;
			return Result;

		} finally {
//			close_SQL();

		}

	}

	public String Employee_LstTime(Appli_form appli_form_Par) {
		Result = null;
		SQL_Str = "select Last_Time from job_Time where Emp_Key=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, appli_form_Par.Emp_Key);
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

	public String Appli_Edit_Print(int id, String Emp_Key) { // 編輯取出
		Result = null;
		SQL_Str = "select * from appli_form where id=? AND Emp_Key=? AND Check_State='No_Process'";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setInt(1, id);
			pst.setString(2, Emp_Key);

			rs = pst.executeQuery();
			Result = (rs.next()) ? employee.Appli_Edit_JsonString(rs) : "fail";
		} catch (SQLException e) {
			System.out.println("Appli_Edit錯誤" + e.getMessage());
			Result = "false";

		} finally {
			close_SQL();
		}
		return Result;
	}

	public String Appli_Edit(Appli_form appli_form_Par) { // 編輯

		if (Employee_LstTime(appli_form_Par).equals("Sucess")) {
			Result = null;
			SQL_Str = "UPDATE appli_form SET Reason=?, Appli_Time=?, Apli_Total=?, Reason_Mark=?, Appli_Date=? WHERE id=? AND Check_State='No_Process'";
			Res_SQL(SQL_Str);

			try {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, appli_form_Par.getReason());
				pst.setDouble(2, appli_form_Par.getAppli_Time());
				pst.setDouble(3, appli_form_Par.getApli_Total());
				pst.setString(4, appli_form_Par.getReason_Mark());
				pst.setDate(5, Date_Time());
				pst.setInt(6, appli_form_Par.getId());
				pst.executeUpdate();
				Result = "Sucess";
			} catch (SQLException e) {
				System.out.println("Appli_Edit錯誤" + e.getMessage());
				Result = "fail";

			} finally {
				close_SQL();
			}
		} else {
			Result = "fail";
		}

		return Result;
	}

	public String Appli_Check(Appli_form appli_form_Par) {
		Result = null;
		SQL_Str = "select * from appli_form where MONTH(appli_form.Appli_Date)=MONTH(CURDATE()) AND Emp_Key=?  AND Check_State='No_Process' ";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, appli_form_Par.getEmp_Key());
			rs = pst.executeQuery();
			Result = (rs.next()) ? "OrderRepeat..." : Attend_TimeData(appli_form_Par);
		} catch (SQLException e) {
			Result = "false";
			System.out.println("Appli_Check錯誤" + e.getMessage());
		} finally {
			close_SQL();
		}
		return Result;
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
				timeData.setInsert_Time(rs.getDouble("Appli_Time"));

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

	public String Get_TimeKey(String Emp_Key) throws SQLException {
		SQL_Str = "select Time_Log_Key from job_time where Emp_Key=?";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getString("Time_Log_Key");
			}
			return "false";
		} catch (SQLException e) {

			return "false";
		} finally {
			close_SQL();
		}
	}

	public String Search_TimeData_Switch(int Emp_Lv, String Department, String KeyValue) {
		String Case_Sql = "";
		System.out.print(KeyValue);
		if (Emp_Lv == 0 && KeyValue.equals("Process")) { // 最高權限抓當月審核資料
			Case_Sql = "SELECT " + "appli_form.id, " + "appli_form.Emp_Key, " + "employee.Emp_Name, "
					+ "appli_form.Department, " + "appli_form.Reason, " + "appli_form.Appli_Time, "
					+ "appli_form.Last_Time, " + "appli_form.Apli_Total, " + "appli_form.Reason_Mark, "
					+ "appli_form.Review_ID_Key, " + "appli_form.Appli_Date, " + "appli_form.Review_Date, "
					+ "appli_form.Check_State, " + "review_form.Review_ID_Key,  " + "review_form.Review_Result, "
					+ " time_log.Time_Mark " + "FROM " + "appli_form " + "INNER JOIN "
					+ "employee ON appli_form.Emp_Key = employee.Emp_ID " + "INNER JOIN "
					+ "review_form ON appli_form.Review_ID_Key = review_form.Review_ID_Key "
					+ "INNER JOIN time_log ON review_form.Review_ID_Key=time_log.Attend_Key "
					+ "WHERE MONTH(appli_form.Review_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'Process' AND time_log.Time_Mark!='Cancel'";

		} else if (Emp_Lv == 0 && KeyValue.equals("No_Process")) {//// 最高權限抓當月申請資料
			Case_Sql = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE  MONTH(appli_form.Appli_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'No_Process' ";

		} else if (Emp_Lv == 1 && KeyValue.equals("Process")) {// 部門主管抓當月審核資料
			Case_Sql = "SELECT " + "appli_form.id, " + "appli_form.Emp_Key, " + "employee.Emp_Name, "
					+ "appli_form.Department, " + "appli_form.Reason, " + "appli_form.Appli_Time, "
					+ "appli_form.Last_Time, " + "appli_form.Apli_Total, " + "appli_form.Reason_Mark, "
					+ "appli_form.Review_ID_Key,  " + "appli_form.Appli_Date, " + "appli_form.Review_Date, "
					+ "appli_form.Check_State, " + "review_form.Review_ID_Key,  " + "review_form.Review_Result, "
					+ " time_log.Time_Mark " + "FROM " + "appli_form " + "INNER JOIN "
					+ "employee ON appli_form.Emp_Key = employee.Emp_ID " + "INNER JOIN "
					+ "review_form ON appli_form.Review_ID_Key = review_form.Review_ID_Key "
					+ "INNER JOIN time_log ON review_form.Review_ID_Key=time_log.Attend_Key "
					+ "WHERE appli_form.Department = '" + Department + "' AND "
					+ "MONTH(appli_form.Review_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'Process' AND time_log.Time_Mark!='Cancel'";

		} else if (Emp_Lv == 1 && KeyValue.equals("No_Process")) { // 部門主管抓當月申請資料
			Case_Sql = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE MONTH(appli_form.Appli_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'No_Process' AND appli_form.Department='"
					+ Department + "' AND  employee.Account_Lv!=0";
		} else if (Emp_Lv == 99 && KeyValue.equals("Process")) { // 員工抓當月審核資料
			Case_Sql = "SELECT " + "appli_form.id, " + "appli_form.Emp_Key, " + "employee.Emp_Name, "
					+ "appli_form.Department, " + "appli_form.Reason, " + "appli_form.Appli_Time, "
					+ "appli_form.Last_Time, " + "appli_form.Apli_Total, " + "appli_form.Reason_Mark, "
					+ "appli_form.Review_ID_Key, " + "appli_form.Appli_Date, " + "appli_form.Review_Date, "
					+ "appli_form.Check_State, " + "review_form.Review_ID_Key,  " + "review_form.Review_Result, "
					+ " time_log.Time_Mark " + "FROM " + "appli_form " + "INNER JOIN "
					+ "employee ON appli_form.Emp_Key = employee.Emp_ID " + "INNER JOIN "
					+ "review_form ON appli_form.Review_ID_Key = review_form.Review_ID_Key "
					+ "INNER JOIN time_log ON review_form.Review_ID_Key=time_log.Attend_Key "
					+ "WHERE MONTH(appli_form.Appli_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'Process'  AND appli_form.Emp_Key=? AND time_log.Time_Mark!='Cancel'";
		} else if (Emp_Lv == 99 && KeyValue.equals("No_Process")) { // 員工抓當月限定申請資料
			Case_Sql = "SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
					+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
					+ "WHERE  MONTH(appli_form.Appli_Date)=MONTH(CURDATE()) AND appli_form.Check_State = 'No_Process'  AND appli_form.Emp_Key=? ";
		}
		return Case_Sql;
	}

	public String Search_TimeData(ArrayList<String> emplyee_Appli_Data, int Emp_Lv, String Department,
			String KeyValue) {

		SQL_Str = Search_TimeData_Switch(Emp_Lv, Department, KeyValue);
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();

			if (rs.next()) {
				do {
					if (KeyValue.equals("Process")) {
						emplyee_Appli_Data.add(employee.Review_JsonString(rs));

					} else {
						emplyee_Appli_Data.add(employee.Appli_JsonString(rs));

					}
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

	public String Search_TimeData_Member(ArrayList<String> emplyee_Appli_Data, String Emp_Key, String KeyValue) {
		SQL_Str = Search_TimeData_Switch(99, Emp_Key, KeyValue);
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_Key);

			rs = pst.executeQuery();

			if (rs.next()) {
				do {

					if (KeyValue.equals("Process")) {
						emplyee_Appli_Data.add(employee.Review_JsonString(rs));

					} else {
						emplyee_Appli_Data.add(employee.Appli_JsonString(rs));

					}
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
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Special_Date,Update_Time,Attend_Key)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,?,?,? FROM time_log;";

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
				SQL_Str = "Update review_form SET Review_Result=?,Review_Time=?,Review_Manager=? where Review_ID_Key=?";
				sqlclass.setSql_Str(SQL_Str);
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, "NPass");
				pst.setDate(2, timeData.getUpdate_Time());
				pst.setString(3, timeData.getManager());
				pst.setString(4, timeData.getAttend_Key());
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

	public int get_Emp_Lv(String Emp_Key) { // 取得登入者權限等級
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

					employee.Emp_List.add(employee.getDepartEmployee_JsonString(rs));

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

	public String Announcement(String Key, String Announcement_String) { // 公告欄處理
		SQL_Str= (Key.equals("Insert"))
				? "insert into announcement(id,Title,Context,Create_Time,Create_Name)"
						+ "select ifNull(max(id),0)+1,?,?,?,? FROM announcement"
				: "Delete  from announcement where id=?";
		Res_SQL(SQL_Str);
		try {
			if (Key.equals("Insert")) {
				String Announcement_Spllite[] = Announcement_String.split(",");
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, Announcement_Spllite[1]);
				pst.setString(2, Announcement_Spllite[2]);
				pst.setDate(3, Date_Time());
				pst.setString(4, Announcement_Spllite[0]);

			} else if (Key.equals("Delete")) {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setInt(1, Integer.parseInt(Announcement_String));
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
	
	public String PassCode(String Key, String PassCode_String) throws JsonProcessingException {  //通行碼處理
		SQL_Str = (Key.equals("Insert"))
				? "insert into pass_code(id,Depart,Passcode,CreateDate,CreateName)"
						+ "select ifNull(max(id),0)+1,?,?,?,? FROM pass_code"
				: "Delete  from pass_code where id=?";
		Res_SQL(SQL_Str);
		try {
			if (Key.equals("Insert")) {
				String PassCode_Spllite[] = PassCode_String.split(",");
				System.out.println("字串" + PassCode_String);

				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setString(1, PassCode_Spllite[0]);
				pst.setString(2, PassCode_Spllite[1]);
				pst.setDate(3, Date_Time());
				pst.setString(4, PassCode_Spllite[2]);

			} else if (Key.equals("Delete")) {
				pst = con.prepareStatement(sqlclass.getSql_Str());
				pst.setInt(1, Integer.parseInt(PassCode_String));

			}
			pst.executeUpdate();
			pst.clearParameters();
			return "Sucess";
		} catch (SQLException e) {
			System.out.println("PassCode錯誤" + e.getMessage());
			return "fail";

		} finally {

			close_SQL();

		}

	}
	
	
	public String PassCodeCheck(String DepartKey, String PassCode_String) throws JsonProcessingException {  //通行碼處理
		SQL_Str="select * from pass_code where Depart= ? ";
		Res_SQL(SQL_Str);
		System.out.println("DepartKey" + DepartKey+"PassCode" + PassCode_String);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, DepartKey);

            rs=pst.executeQuery();
			if (rs.next()) {
		         
				System.out.println(rs.getString("PassCode"));

                if(PassCode_String.equals(rs.getString("PassCode"))) {
        			System.out.println("2");

    				return "Sucess";

                }else {
    				return "fail";
                }
			}
			return "fail";
		} catch (SQLException e) {
			System.out.println("PassCode錯誤" + e.getMessage());
			return "fail";

		} finally {
			close_SQL();
		}

	}
	
	

	public <T> T PassCode_Init_Data() throws JsonProcessingException {  //初始化通行碼要帶出的資料
		PassCode_List.clear();
		String PassCode_Str = "";
		SQL_Str="select * from pass_code ";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {

				do {
					PassCode_Str = String.format(
							"{\"id\": \"%d\",\"Depart\": \"%s\", \"PassCode\": \"%s\", \"CreateDate\": \"%s\", \"CreateName\": \"%s\"}",
							rs.getInt("id"), rs.getString("Depart"), rs.getString("PassCode"),
							rs.getString("CreateDate"), rs.getString("CreateName"));
					PassCode_List.add(PassCode_Str);
				} while (rs.next());
			}
			return (T) PassCode_List;
		} catch (SQLException e) {
			System.out.println("PassCode_Init_Data錯誤" + e.getMessage());
			return (T) "fail";

		} finally {

			close_SQL();

		}
		
	}


	public <T> T Announcement_Init_Data() throws JsonProcessingException { // 初始化布告欄要帶出的資料
		Announcement_List.clear();
		SQL_Str = "select * from announcement";

		String Announ_Str = "";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {

				do {
					Announ_Str = String.format(
							"{\"id\": \"%d\",\"Title\": \"%s\", \"Context\": \"%s\", \"Create_Time\": \"%s\", \"Create_Name\": \"%s\"}",
							rs.getInt("id"), rs.getString("Title"), rs.getString("Context"),
							rs.getString("Create_Time"), rs.getString("Create_Name"));
					Announcement_List.add(Announ_Str);
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

	public String Update_Employee() {

		return null;
	}
     
	
	public <T> T Init_Data(String Depart, String Emp_Key) throws JsonProcessingException { // 初始化部門要帶出的資料
		department.Department_List.clear();
		if (get_Emp_Lv(Emp_Key) == 1) {
			SQL_Str = "select * from department where Department='" + Depart + "'";
		} else if (get_Emp_Lv(Emp_Key) == 0) {
			SQL_Str = "select * from department";
		}
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
