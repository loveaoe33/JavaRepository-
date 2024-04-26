package Personnel_Attend;

import java.sql.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder
public class UserApi {
      private String Emp_ID;
      private String OrigName;
      
      private String MapNamp;
	  private String OrigDepart;
	  private String MapDepart;
	  private String CreateName;
	  private Date CreateDate;
	  private String Account; 
	  private String Password;
	  private String email;
	  private int Account_Lv;
	  
}
