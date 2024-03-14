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
	private SQLClass sqlclass;
	private final Employee employee = new Employee();
	private String SQL_Str = null;
	private Date date = new Date(0);

	public HistoryLog(SQLClass sqlclass) {
		super(sqlclass);
		this.sqlclass = sqlclass;
		System.out.print("新組件位置:" + System.identityHashCode(sqlclass));
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

	public String Get_Employee_Histort(ArrayList<String> SearchEmployee_HistoryString, String Emp_key,String Start,String End) {
		SQL_Str = 	"SELECT appli_form.id,appli_form.Emp_Key, employee.Emp_Name, appli_form.Department, appli_form.Reason, appli_form.Appli_Time, appli_form.Last_Time, appli_form.Apli_Total, appli_form.Reason_Mark, appli_form.Review_ID_Key, appli_form.Appli_Date, appli_form.Review_Date, appli_form.Check_State "
				+ "FROM appli_form " + "INNER JOIN employee ON appli_form.Emp_Key = employee.Emp_ID "
				+ "where Emp_Key=? AND Appli_Date BETWEEN ? AND ?";//申請範圍
	
		
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_key);
			pst.setString(2, Start);
			pst.setString(3, End);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {

					SearchEmployee_HistoryString.add(employee.Appli_JsonString(rs));

				} while (rs.next());

				return "Sucess";
			}
			return "false";
		} catch (SQLException e) {
			System.out.println("Get_Employee_Histort錯誤" + e.toString());

			return "false";

		} finally {
			close_SQL();
		}

	}

	public String Get_Employee_Review(ArrayList<String> SearchEmployee_ReviewString, String Emp_key,String Start,String End) { //審核範圍
		
		
		SQL_Str = "SELECT " + "appli_form.id, " + "appli_form.Emp_Key, " + "employee.Emp_Name, "
				+ "appli_form.Department, " + "appli_form.Reason, " + "appli_form.Appli_Time, "
				+ "appli_form.Last_Time, " + "appli_form.Apli_Total, " + "appli_form.Reason_Mark, "
				+ "appli_form.Review_ID_Key, " + "appli_form.Appli_Date, " + "appli_form.Review_Date, "
				+ "appli_form.Check_State, " + "review_form.Review_ID_Key,  " + "review_form.Review_Result, " +  "time_log.Time_Mark " 
				+ "FROM " + "appli_form " + "INNER JOIN " + "employee ON appli_form.Emp_Key = employee.Emp_ID "
				+ "INNER JOIN " + "review_form ON appli_form.Review_ID_Key = review_form.Review_ID_Key "
		        + "INNER JOIN time_log ON review_form.Review_ID_Key=time_log.Attend_Key " +"WHERE "
				+ "appli_form.Emp_Key=? AND appli_form.Check_State = 'Process' AND time_log.Time_Mark!='Cancel' AND     appli_form.Review_Date between ? AND ?\r\n";
		
	

		
		
		
		
		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Emp_key);
			pst.setString(2, Start);
			pst.setString(3, End);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchEmployee_ReviewString.add(employee.Review_JsonString(rs));
				} while (rs.next());


				return "Sucess";
			}
			return "false";
		} catch (SQLException e) {
			System.out.println("Get_Employee_Review錯誤" + e.toString());

			return "false";

		} finally {
			close_SQL();
		}

	}

	public String Get_Depart_History(ArrayList<String> SearchDepart_HistoryString, String Depart,String Start,String End) {
		String [] Depart_Splite =Depart.split("_");   
		SQL_Str = "SELECT " +
	            "employee.Emp_ID, " +
	            "employee.Emp_Name, " +
	            "department.Department, " +
	            "job_time.Last_Time, " +
	            "job_time.Special_Date, " +
	            "job_time.Time_Pon_Mark, " +
	            "job_time.Update_Time, " +
	            "time_log.Time_Event, " +
	            "time_log.Time_Mark, " +
	            "time_log.Insert_Time, " +
	            "time_log.Old_Time, " +
	            "time_log.New_Time, " +
	            "time_log.Update_Time, " +
	            "time_log.Attend_Key " +
	        "FROM " +
	            "employee " +
	        "INNER JOIN " +
	            "department ON employee.Department_Key = department.Department_Key " +
	        "INNER JOIN " +
	            "job_time ON employee.Emp_ID = job_time.Emp_Key " +
	        "INNER JOIN " +
	            "time_log ON job_time.Time_Log_Key = time_log.Time_Log_Key " +
	        "WHERE " +
	            "department.Department = ? " +
	            "AND time_log.Update_Time BETWEEN ? AND ? order by \r\n"
	            + "	employee.Emp_ID ASC, time_log.Update_Time DESC";


		Res_SQL(SQL_Str);
		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Depart_Splite[0]);
			pst.setString(2, Start);
			pst.setString(3, End);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchDepart_HistoryString.add(employee.getEmployeeHistory_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}

			return "false";
		} catch (SQLException e) {
			System.out.println("Get_Depart_History錯誤" + e.toString());

			return "false";
		} finally {
			close_SQL();

		}
	}

	public String All_Histort(ArrayList<String> SearchAll_HistoryString,String Start,String End) {		
		SQL_Str = "SELECT " +
	            "employee.Emp_ID, " +
	            "employee.Emp_Name, " +
	            "department.Department, " +
	            "job_time.Last_Time, " +
	            "job_time.Special_Date, " +
	            "job_time.Time_Pon_Mark, " +
	            "job_time.Update_Time, " +
	            "time_log.Time_Event, " +
	            "time_log.Time_Mark, " +
	            "time_log.Insert_Time, " +
	            "time_log.Old_Time, " +
	            "time_log.New_Time, " +
	            "time_log.Update_Time, " +
	            "time_log.Attend_Key " +
	        "FROM " +
	            "employee " +
	        "INNER JOIN " +
	            "department ON employee.Department_Key = department.Department_Key " +
	        "INNER JOIN " +
	            "job_time ON employee.Emp_ID = job_time.Emp_Key " +
	        "INNER JOIN " +
	            "time_log ON job_time.Time_Log_Key = time_log.Time_Log_Key " +
	        "WHERE " +
	            "time_log.Update_Time BETWEEN ? AND ? order by \r\n"
	            + "	employee.Emp_ID ASC, time_log.Update_Time DESC";
	        
		

		
		
		
		Res_SQL(SQL_Str);

		try {
			pst = con.prepareStatement(sqlclass.getSql_Str());
			pst.setString(1, Start);
			pst.setString(2, End);
			rs = pst.executeQuery();
			if (rs.next()) {
				do {
					SearchAll_HistoryString.add(employee.getEmployeeHistory_JsonString(rs));
				} while (rs.next());

				return "Sucess";
			}

			return "false";
		} catch (SQLException e) {
			System.out.println("Get_Employee_Review錯誤" + e.toString());

			return "false";
		} finally {
			close_SQL();

		}
	}

}
