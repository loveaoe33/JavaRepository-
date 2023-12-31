package Personnel_Attend;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import Personnel.EmployeeModel;

@Service
public class SQLSERVER extends SQLOB {
	private SQLClass sqlclass;
	private Employee employee;
	private Department department;
	private TimeData timeData;
	private String Result = null;
	private String SQL_Str = null;

	@Autowired
	public SQLSERVER(SQLClass sqlclass, Department department, Employee employee, TimeData timeData) {

		super(sqlclass);
		this.sqlclass = sqlclass;
		this.department = department;
		this.employee = employee;
		this.timeData = timeData;
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
			System.out.println("帳號驗證錯誤" + e.getMessage());

		} finally {
			close_SQL();
			return Emp_Check;

		}

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
				Employee_Pst.setString(2, employee_Par.getPassword());
				Employee_Pst.setString(3, employee_Par.getEmp_Name());
				Employee_Pst.setString(4, employee_Par.getDepartment_Key());
				Employee_Pst.setInt(5, employee_Par.getAccount_Lv());
				Employee_Pst.setDate(6, employee_Par.getCreate_Time());
				Employee_Pst.setString(7, employee_Par.getCreate_Name());

				if (Insert_TimeData_New(timeData_Par).equals("Sucess")) {
					Employee_Pst.executeUpdate();
					Employee_Pst.clearParameters();
					Result = "Sucess";
				} else {
					Result = "Insert_TimeData_New Error..";
				}

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
		Result = null;
		double Last_Time = 0;
		SQL_Str = "select * from job_time  where Emp_Key=?";
		Res_SQL(SQL_Str);
		System.out.println("TimeData_Compare的記憶體位置" + timeData_Par);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, timeData_Par.getEmp_Key());
			rs = pst.executeQuery();
			
			Last_Time = (rs.next())?rs.getDouble("Last_Time"):0;
			if (Last_Time <= timeData_Par.Insert_Time) {
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
			close_SQL();

		}
	}

	public boolean Insert_TimeLog(String TimeLog, TimeData timeData_Par) { // Insert_Time_Log
		Result = null;
		sqlclass.setSql_Str(TimeLog);
		double New_Time = timeData_Par.getOld_Time() + timeData_Par.getInsert_Time();
		timeData_Par.setNew_Time(New_Time);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, timeData_Par.getTime_Log_Key());
			pst.setString(2, timeData_Par.getTime_Event());
			pst.setString(3, timeData_Par.getTime_Mark());
			pst.setDouble(4, timeData_Par.getInsert_Time());
			pst.setDouble(5, timeData_Par.getOld_Time());
			pst.setDouble(6, timeData_Par.New_Time);
			pst.setDate(7, timeData_Par.getUpdate_Time());
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
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Update_Time)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM time_log;";
		sqlclass.setSql_Str(SQL_Str_JobTime);
		try {
			PreparedStatement TimeData_pst = con.prepareStatement(sqlclass.getSql_Str());
			TimeData_pst.setString(1, timeData_Par.getEmp_Key());
			TimeData_pst.setDouble(2, timeData_Par.getLast_Time());
			TimeData_pst.setString(3, timeData_Par.getTime_Pon_Mark());
			TimeData_pst.setString(4, timeData_Par.getTime_Log_Key());
			TimeData_pst.setDate(5, timeData_Par.getUpdate_Time());
			if (Insert_TimeLog(SQL_Str_TimeLog, timeData_Par)) {
				TimeData_pst.executeUpdate();
				TimeData_pst.clearParameters();
				Result = "Sucess";
			} else {
				Result = "false";
			}
		} catch (SQLException e) {
			System.out.println("JOB_Time update錯誤" + e.getMessage());
			Result = "false";
		} finally {
			return Result;

		}
	}

	public String Insert_TimeData_Update(TimeData timeData_Par) { // JOB_Time update
		Result = null;
		String SQL_Str_JobTime = "Update job_time SET Last_Time=?,Time_Pon_Mark=?,Update_Time=?";
		String SQL_Str_TimeLog = "insert into time_log(id,Time_Log_Key,Time_Event,Time_Mark,Insert_Time,Old_Time,New_Time,Update_Time)"
				+ "select ifNull(max(id),0)+1,?,?,?,?,?,?,? FROM time_log";
		if(!TimeData_Compare(timeData_Par)) {
			return "false";
		}
		TimeData_Compare(timeData_Par);// Time Compare
		Res_SQL(SQL_Str_JobTime);
		System.out.println("AttendController的記憶體位置:" + timeData_Par);

		try {
			PreparedStatement TimeData_Update_pst = con.prepareStatement(sqlclass.getSql_Str());
			if (Insert_TimeLog(SQL_Str_TimeLog, timeData_Par)) {

				TimeData_Update_pst.setDouble(1, timeData_Par.getNew_Time());
				TimeData_Update_pst.setString(2, timeData_Par.getTime_Pon_Mark());
				TimeData_Update_pst.setDate(3, timeData_Par.getUpdate_Time());
				TimeData_Update_pst.executeUpdate();
				TimeData_Update_pst.clearParameters();
				Result = "Sucess";

			} else {
				Result = "false";
				System.out.println("Time_Log update錯誤");
			}

		} catch (SQLException e) {
			Result = "false";
			System.out.println("JOB_Time update錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;
		}
	}

	public String Login_Employee() {
		SQL_Str = "select * from personnel_Attend where Account=? AND Password=?";
		Result = null;
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			String Account_Process = (employee.getEmp_ID()).replace("--", "");
			String Password_Process = (employee.getPassword()).replace("--", "");
			pst.setString(1, Account_Process);
			pst.setString(2, Password_Process);
			rs = pst.executeQuery();
			if (rs.next()) {
				Result = "Sucess";
			} else {
				Result = "false";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("登入驗證錯誤" + e.getMessage());
		} finally {
			close_SQL();
			return Result;

		}
	}

	public void SearchEmployee_TimeData_Post() {

	}

	public void EditAttend_TimeData_Post() {

	}

	public void Attend_TimeData() {

	}

	public void Excel_Employee_TimeData_Post() {

	}

	public void Search_TimeData() {

	}

	public void Admin_Change_Employee() {

	}

	public void Change_Employee() {

	}

	public <T> T Init_Data() { // 初始化要帶出的資料
		SQL_Str = "select * from Department";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				department.Department_List.add(rs.getString("Department")); // 會先移動到第一筆
				while (rs.next()) {
					department.Department_List.add(rs.getString("Department"));
				}
			}

		} catch (SQLException e) {
			System.out.println("初始化部門資料錯誤" + e.getMessage());

		} finally {

			close_SQL();
			System.out.println("部門資料" + department.Department_List);

		}
		return null;

	}

}
