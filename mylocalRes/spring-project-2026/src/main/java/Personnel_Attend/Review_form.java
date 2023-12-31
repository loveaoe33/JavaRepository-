package Personnel_Attend;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Review_form {

	int id;
	String Review_ID_Key;
	String Review_Manager;
	Date Review_Time;
	String Revire_Result;
	
}
