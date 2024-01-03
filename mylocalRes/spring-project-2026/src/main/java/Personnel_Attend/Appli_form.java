package Personnel_Attend;

import java.sql.Date;
import java.util.ArrayList;
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
public class Appli_form {

	
	int id;
	String Emp_Key;
	String Department;
	String Reason;
	double Appli_Time;
	double Last_Time;
	double Apli_Total;
	String Reason_Mark;
	String Review_ID_Key;
	Date Appli_Date;
	Date Review_Date;
	String Check_State;
	
	String Review_Manager;
	Date Review_Time;
	String Review_Result;
	
	
	public void ResConstruct() {
		this.Emp_Key="";
		this.Department="";
		this.Reason="";
		this.Appli_Time=0;
		this.Last_Time=0;
		this.Apli_Total=0;
		this.Reason_Mark="";
		this.Review_ID_Key="";
		this.Appli_Date=null;
		this.Review_Date=null;
		this.Check_State=null;
		this.Review_Manager="";
		this.Review_Time=null;
		this.Review_Result="";
		
	}
	
}
