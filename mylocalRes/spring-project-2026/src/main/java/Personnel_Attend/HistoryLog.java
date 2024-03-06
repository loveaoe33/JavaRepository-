package Personnel_Attend;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Service
public class HistoryLog extends SQLOB {
	private  SQLClass sqlclass;
	private final Employee employee=new Employee();
	private String SQL_Str = null;
	private Date date = new Date(0);


	public HistoryLog(SQLClass sqlclass) {
		super(sqlclass);
        this.sqlclass=sqlclass;
		System.out.print("新組件位置:"+System.identityHashCode(sqlclass));
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
	
	public String Get_Employee_Histort(ArrayList<String> SearchEmployee_HistoryString, String Emp_key) {
		SQL_Str="select * from appli_form where Emp_Key='E0010' AND Appli_Date BETWEEN '2024-2-01' AND '2024-2-21'"; //搜尋申請
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {

					SearchEmployee_HistoryString.add(employee.Appli_JsonString(rs));

				} while (rs.next());

				return "Sucess";
			}
			return "false";
		}catch(SQLException e) {
			System.out.println("Get_Employee_Histort錯誤"+e.toString());

			return "false";

		}finally {
			close_SQL();
		}
	


	}
	
	public String Get_Employee_Review(ArrayList<String> SearchEmployee_ReviewString, String Emp_key) {
		 SQL_Str="SELECT \r\n"
				+ "    appli_form.id, \r\n"
				+ "    appli_form.Emp_Key, \r\n"
				+ "    employee.Emp_Name, \r\n"
				+ "    appli_form.Department, \r\n"
				+ "    appli_form.Reason, \r\n"
				+ "\r\n"
				+ "    appli_form.Reason_Mark, \r\n"
				+ "    appli_form.Appli_Date, \r\n"
				+ "    appli_form.Review_Date, \r\n"
				+ "    review_form.Review_Result,\r\n"
				+ "    time_log.Time_Mark,\r\n"
				+ "    time_log.Insert_Time,\r\n"
				+ "	time_log.Old_Time,\r\n"
				+ "    time_log.New_Time,\r\n"
				+ "	time_log.Update_Time\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "FROM \r\n"
				+ "    appli_form \r\n"
				+ "INNER JOIN \r\n"
				+ "    employee ON appli_form.Emp_Key = employee.Emp_ID \r\n"
				+ "INNER JOIN \r\n"
				+ "    review_form ON appli_form.Review_ID_Key = review_form.Review_ID_Key \r\n"
				+ "INNER JOIN \r\n"
				+ "    time_log ON review_form.Review_ID_Key = time_log.Attend_Key \r\n"
				+ "WHERE \r\n"
				+ "    appli_form.Emp_Key='E0010' AND appli_form.Check_State = 'Process'  AND     appli_form.Review_Date between '2024-2-01' AND '2024-2-21';\r\n"
				+ ""; //員工搜尋已審核資料
			Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchEmployee_ReviewString.add(employee.Review_All_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			return "false";
		}catch(SQLException e) {
			System.out.println("Get_Employee_Review錯誤"+e.toString());

			return "false";

		}finally {
			close_SQL();
		}
		
	}
	public String Get_Depart_History(ArrayList<String> SearchDepart_HistoryString, String Depart) {
		 SQL_Str="SELECT \r\n"  //依照部門全部
				+ "\r\n"
				+ "	 employee.Emp_ID,\r\n"
				+ "	employee.Emp_Name,\r\n"
				+ "    department.Department,\r\n"
				+ "    job_time.Last_Time,\r\n"
				+ "    job_time.Special_Date,\r\n"
				+ "    job_time.Time_Pon_Mark,\r\n"
				+ "    job_time.Update_Time,\r\n"
				+ "	time_log.Time_Event,\r\n"
				+ "    time_log.Time_Mark,\r\n"
				+ "    time_log.Insert_Time,\r\n"
				+ "	time_log.Old_Time,\r\n"
				+ "    time_log.New_Time,\r\n"
				+ "	time_log.Update_Time,\r\n"
				+ "    time_log.Attend_Key\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "FROM \r\n"
				+ "    employee \r\n"
				+ "INNER JOIN\r\n"
				+ "    department ON employee.Department_Key=department.Department_Key\r\n"
				+ "INNER JOIN \r\n"
				+ "    job_time ON employee.Emp_ID = job_time.Emp_Key\r\n"
				+ "INNER JOIN \r\n"
				+ "    time_log ON job_time.Time_Log_Key = time_log.Time_Log_Key \r\n"
				+ "\r\n"
				+ "WHERE \r\n"
				+ "   department.Department='資訊室' AND time_log.Update_Time between '2024-2-01' AND '2024-2-21'; \r\n"
				+ "";
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchDepart_HistoryString.add(employee.getEmployeeHistory_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			
			return "false";
		}catch(SQLException e) {
			System.out.println("Get_Employee_Review錯誤"+e.toString());

			return "false";
		}finally {
			close_SQL();

		}
	}
	

	public String  All_Histort(ArrayList<String> SearchAll_HistoryString) {
		 SQL_Str="SELECT \r\n"  //直接全部
					+ "\r\n"
					+ "	 employee.Emp_ID,\r\n"
					+ "	employee.Emp_Name,\r\n"
					+ "    department.Department,\r\n"
					+ "    job_time.Last_Time,\r\n"
					+ "    job_time.Special_Date,\r\n"
					+ "    job_time.Time_Pon_Mark,\r\n"
					+ "    job_time.Update_Time,\r\n"
					+ "	time_log.Time_Event,\r\n"
					+ "    time_log.Time_Mark,\r\n"
					+ "    time_log.Insert_Time,\r\n"
					+ "	time_log.Old_Time,\r\n"
					+ "    time_log.New_Time,\r\n"
					+ "	time_log.Update_Time,\r\n"
					+ "    time_log.Attend_Key\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "FROM \r\n"
					+ "    employee \r\n"
					+ "INNER JOIN\r\n"
					+ "    department ON employee.Department_Key=department.Department_Key\r\n"
					+ "INNER JOIN \r\n"
					+ "    job_time ON employee.Emp_ID = job_time.Emp_Key\r\n"
					+ "INNER JOIN \r\n"
					+ "    time_log ON job_time.Time_Log_Key = time_log.Time_Log_Key \r\n"
					+ "\r\n"
					+ "WHERE \r\n"
					+ " time_log.Update_Time between '2024-2-01' AND '2024-2-21'; \r\n"
					+ "";
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchAll_HistoryString.add(employee.getEmployeeHistory_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}
			
			return "false";
		}catch(SQLException e) {
			System.out.println("Get_Employee_Review錯誤"+e.toString());

			return "false";
		}finally {
			close_SQL();

		}
	}

	
	
	
	

}
