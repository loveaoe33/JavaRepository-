package Personnel_Attend;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Router_Config {
	private String Server_Name;
	private String Address;
	private String Port;
	private int Open;

	public String getRouter_JsonString(ResultSet rs) throws SQLException { // 出勤系統出勤資料

		return String.format("{\"id\": \"%s\", \"Server_Name\": \"%s\", \"Address\": \"%s\", \"Port\": \"%s\", \"Open\": \"%s\"}", rs.getInt("id"),
				rs.getString("Server_Name"), rs.getString("Address"), rs.getString("Port"), rs.getInt("Open"));
	}

}
