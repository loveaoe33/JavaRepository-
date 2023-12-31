package Personnel_Attend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

public interface SQL_Interface {
	public void Construct(SQLClass sqlclass);
	public void ResSQL() throws ClassNotFoundException;
	public void close_SQL();
	public void SQL_Conn();
	
}
