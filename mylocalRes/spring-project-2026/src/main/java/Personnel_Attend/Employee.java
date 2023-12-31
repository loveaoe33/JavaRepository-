package Personnel_Attend;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
   public void ResConstruct() {
		this.Emp_ID="";
		this.Password="";
		this.Emp_Name="";
		this.Department_Key="";
		this.Account_Lv=0;
		this.Time_Mark="";
		this.Create_Time=null;
		this.Emp_Key="";
		this.Last_Time=0;
		this.Time_Mark="";
		this.Update_Time=null;
		this.Time_Log_key="";

	}
}
