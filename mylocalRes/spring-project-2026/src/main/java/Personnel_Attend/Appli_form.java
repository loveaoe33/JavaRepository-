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
	String Review_Date;
	String Check_State;
}
