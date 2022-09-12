package SQL;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import SQL.jdbcmysql;
public class OOPSQLTable extends jdbcmysql {

private void Creatable(String y) 
{
	
    try 
    { 
    	 
      stat = con.createStatement(); 
      stat.executeUpdate(y); 
      System.out.println("資料表新增完成:"+y);
    } 
    catch(SQLException e) 
    { 
      System.out.println("CreateDB Exception :" + e.toString()); 
    } 
    finally 
    { 
    	Close(); 
    } 
}



public void AddString(String x) 
{
	Creatable(x);
	System.out.println("資料表新增:"+x);
}
	
}
