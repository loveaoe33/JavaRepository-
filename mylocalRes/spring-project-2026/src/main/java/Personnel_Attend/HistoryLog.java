package Personnel_Attend;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


@Service
public class HistoryLog extends SQLOB {
	private final SQLClass sqlclass;
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
	
	public void Get_Employee_Histort() {
		String AppliSQl="select * from appli_form where Emp_Key='E0010' AND Appli_Date BETWEEN '2024-2-20' AND '2024-2-21'"; //搜尋申請
		String ReviewSQl="SELECT \r\n"
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
		
		String All_Depart_Log="SELECT \r\n"  //依照部門全部
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
				+ "   department.Department='護理部' ;\r\n"
				+ "";

		String All_Log="SELECT \r\n"   //直接全部
				+ "\r\n"
				+ "	 employee.Emp_ID,\r\n"
				+ "	employee.Emp_Name,\r\n"
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
				+ "INNER JOIN \r\n"
				+ "    job_time ON employee.Emp_ID = job_time.Emp_Key\r\n"
				+ "INNER JOIN \r\n"
				+ "    time_log ON job_time.Time_Log_Key = time_log.Time_Log_Key \r\n"
				+ "\r\n"
				+ "WHERE \r\n"
				+ "    employee.Emp_ID='E0010'  AND     job_time.Update_Time between '2024-2-01' AND '2024-2-29';\r\n"
				+ "";
	}
	public void Get_Depart_Histort() {
		
	}
	
	public void Get_Department_Histort() {
		
	}
	public void All_Department_Histort() {
		
	}

	
	
	
	

}
