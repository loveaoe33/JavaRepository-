package Personnel_Attend;


import java.sql.Date;
import java.util.HashMap;

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
@Component
public class TimeData {

	int id;
	String Emp_Key;   //job_Time
	double Last_Time; //job_Time
	String Time_Pon_Mark; //job_Time
	String Time_Log_Key; //job_Time Log_Time
	String Time_Event;//Log_Time
	String Time_Mark;//Log_Time
	double Insert_Time;//Log_Time
	double Old_Time;//Log_Time
	double New_Time;//Log_Time
	Date Update_Time;//Log_Time
	HashMap<String,String>Time_Log=new HashMap<>();
	public void ResConstruct() {
		this.Emp_Key="";
		this.Last_Time=0;
		this.Time_Pon_Mark="";
		this.Time_Log_Key="";
		this.Time_Event="";
		this.Time_Mark="";
		this.Insert_Time=0;
		this.Old_Time=0;
		this.New_Time=0;
		this.Update_Time=null;
		
	}
}
