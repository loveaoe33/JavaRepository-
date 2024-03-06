package Personnel_Attend;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
@Component
public class Employee {
	private int id;
	private String Emp_ID;
	private String Password;
	private String Emp_Name;
	private String Department_Key;
	private int Account_Lv;
	private Date Create_Time;
	private String Create_Name;
	private String Emp_Key;
	private double Last_Time;
	private String Time_Mark;
	private Date Update_Time;
	private String Time_Log_key;
	ArrayList<String> Emp_List = new ArrayList<>();

	public void ResConstruct() {
		this.Emp_ID = "";
		this.Password = "";
		this.Emp_Name = "";
		this.Department_Key = "";
		this.Account_Lv = 0;
		this.Time_Mark = "";
		this.Create_Time = null;
		this.Emp_Key = "";
		this.Last_Time = 0;
		this.Time_Mark = "";
		this.Update_Time = null;
		this.Time_Log_key = "";

	}

	public String Execll_All_JsonString(ResultSet rs) throws SQLException {
		return String.format(
				"{\"id\": \"%d\",\"Emp_ID\": \"%s\", \"Emp_Name\": \"%s\", \"Department\": \"%s\", \"Account_Lv\": %d, \"Last_Time\": %d, \"Time_Pon_Mark\": \"%s\", \"Update_Time\": \"%s\"}",
				rs.getInt("id"), rs.getString("Emp_ID"), rs.getString("Emp_Name"), rs.getString("Department"),
				rs.getInt("Account_Lv"), rs.getInt("Last_Time"), rs.getString("Time_Pon_Mark"),
				rs.getString("Update_Time"));
	}

	public String Appli_JsonString(ResultSet rs) throws SQLException {  //抓取時間內特定帳戶申請紀錄

		return String.format(
				"{\"id\": \"%d\",\"Emp_Key\": \"%s\", \"Department\": \"%s\", \"Reason\": \"%s\", \"Appli_Time\": %f, \"Last_Time\": %f, \"Apli_Total\": \"%f\", \"Reason_Mark\": \"%s\", \"Review_ID_Key\": \"%s\", \"Appli_Date\": \"%s\", \"Review_Date\": \"%s\", \"Check_State\": \"%s\"}",
				rs.getInt("id"), rs.getString("Emp_Key"),rs.getString("Department"),
				rs.getString("Reason"), rs.getDouble("Appli_Time"), rs.getDouble("Last_Time"),
				rs.getDouble("Apli_Total"), rs.getString("Reason_Mark"), rs.getString("Review_ID_Key"),
				rs.getDate("Appli_Date"), rs.getDate("Review_Date"), rs.getString("Check_State"));
	}
	
	public String Review_All_JsonString(ResultSet rs) throws SQLException {    //抓取時間內特定帳戶審核紀錄
		return String.format(
				"{\"id\": \"%d\",\"Emp_Key\": \"%s\", \"Emp_Name\": \"%s\", \"Department\": \"%s\", \"Reason\": %s, \"Reason_Mark\": %s, \"Appli_Date\": \"%s\", \"Review_Date\": \"%s\", \"Review_Result\": \"%s\", \"Time_Mark\": \"%s\", \"Insert_Time\": \"%f\" , \"Old_Time\": \"%f\", \"New_Time\": \"%f\", \"Update_Time\": \"%s\"   }",
				rs.getInt("id"), rs.getString("Emp_Key"), rs.getString("Emp_Name"), rs.getString("Department"),rs.getString("Reason"), rs.getString("Reason_Mark"), rs.getString("Appli_Date"),rs.getString("Review_Date"),rs.getString("Review_Result"), rs.getString("Time_Mark"), rs.getDouble("Insert_Time"),rs.getDouble("Old_Time"),rs.getDouble("New_Time"),rs.getString("Update_Time"));
	}
	

	public String Time_Log_JsonString(ResultSet rs) throws SQLException {    //抓取Time_Log

		return String.format(
				"{\"id\": \"%d\",\"Emp_ID\": \"%s\", \"Emp_Name\": \"%s\", \"Time_Event\": \"%s\", \"department\": \"%s\",\"Reason_Mark\": \"%s\",\"Appli_Date\": \"%s\",\"Time_Mark\": \"%s\", \"Last_Time\": %f, \"Insert_Time\": %f, \"Old_Time\": %f,\"New_Time\": %f,\"Update_Time\": \"%s\",\"Review_Manager\": \"%s\", \"Review_Time\": \"%s\", \"Review_Result\": \"%s\"}",
				rs.getInt("id"), rs.getString("Emp_ID"), rs.getString("Emp_Name"), rs.getString("Time_Event"),
				rs.getString("department"), rs.getString("Reason_Mark"), rs.getDate("Appli_Date"),
				rs.getString("Time_Mark"), rs.getDouble("Last_Time"), rs.getDouble("Insert_Time"),
				rs.getDouble("Old_Time"), rs.getDouble("New_Time"), rs.getString("Reason_Mark"),
				rs.getDate("Update_Time"), rs.getString("Review_Manager"), rs.getDate("Review_Time"),
				rs.getString("Review_Result"));

	}
	
	public String getEmployeeHistory_JsonString(ResultSet rs) throws SQLException {   //抓取歷年log
		return String.format(
				"{\"Emp_ID\": \"%s\",\"Emp_Name\": \"%s\",\"Department\": \"%s\", \"Last_Time\": \"%f\", \"Special_Date\": \"%f\", \"Time_Pon_Mark\": \"%s\", \"Update_Time\": \"%s\", \"Time_Event\": \"%s\", \"Time_Mark\": \"%s\", \"Insert_Time\": \"%f\", \"Old_Time\": \"%f\", \"New_Time\": \"%f\", \"Update_Time\": \"%s\", \"Attend_Key\": \"%s\"}",
				rs.getString("Emp_ID"), rs.getString("Emp_Name"), rs.getString("Department"), rs.getDouble("Last_Time"), rs.getDouble("Special_Date"), rs.getString("Time_Pon_Mark"), rs.getString("Update_Time"), rs.getString("Time_Event"), rs.getString("Time_Mark"), rs.getDouble("Insert_Time"), rs.getDouble("Old_Time"), rs.getDouble("New_Time"), rs.getString("Update_Time"), rs.getString("Attend_Key"));
	}
	
	
	public String getEmployee_JsonString(ResultSet rs) throws SQLException {

		return String.format(
				"{\"id\": \"%d\",\"Emp_ID\": \"%s\", \"Emp_Name\": \"%s\", \"Department_Key\": \"%s\", \"Account_Lv\": \"%d\" }",
				rs.getInt("id"), rs.getString("Emp_ID"), rs.getString("Emp_Name"), rs.getString("Department_Key"),
				rs.getInt("Account_Lv"));
	}
	public String getJobTime_JsonString(ResultSet rs) throws SQLException {

		return String.format(
				"{\"Emp_Key\": \"%s\",\"Last_Time\": \"%f\", \"Special_Date\": \"%f\", \"Update_Time\": \"%s\"}",
				rs.getString("Emp_Key"), rs.getDouble("Last_Time"), rs.getDouble("Special_Date"), rs.getString("Update_Time"));
	}
	

	
	

}
