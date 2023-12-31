package Personnel_Attend;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
@Scope("prototype")
public class SQLClass {
	private String conn_Str;
	private String Account;
	private String Password;
	private String sql_Str;
	public SQLClass() {
		this.conn_Str="jdbc:mysql://localhost/personnel_Attend?serverTimezone=UTC";
		this.Account="root";
		this.Password="love20720";
		this.sql_Str="";
	}
}