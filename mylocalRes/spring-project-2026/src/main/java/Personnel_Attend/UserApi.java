package Personnel_Attend;

import java.sql.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;

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
@Component
public class UserApi {
	public String Account;
	public String Password;
	public String Emp_ID;
	public String Emp_Name;
	public String MapName;
	public String OrigDepart;
	public String MapDepart;
	public String CreateName;
	public Date CreateDate;
	public int Account_Lv;

	public void init() {
		this.Password = "";
		this.Emp_ID = "";
		this.Emp_Name = "";
		this.MapName = "";
		this.OrigDepart = "";
		this.MapDepart = "";
		this.CreateName = "";
		this.CreateDate = null;
		this.Account_Lv = 99;
	}
}
