package Personnel_Attend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

public class SQLOB implements SQL_Interface {
	private SQLClass sqlclass;
	protected Connection con = null;
	protected Statement stat = null;
	protected ResultSet rs = null;
	protected PreparedStatement pst = null;
//	String ConnectionString;
//	String SQLString;
//	String Account;
//	String Password;
    
	public SQLOB(SQLClass sqlclass) {
		try{
			this.sqlclass=sqlclass;
			ResSQL();
		}catch(Exception e) {
			System.out.print("資料庫初始化錯誤"+e.toString());
		}
	}
	@Override
	public void Construct(SQLClass sqlclass) {
		// TODO Auto-generated method stub


	}

	@Override
	public void ResSQL() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		SQL_Conn();
	}

	@Override
	public void close_SQL() {
		// TODO Auto-generated method stub
		try {
			if (stat != null) {
				stat.close();
				stat = null;

			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}

		} catch (SQLException e) {
			System.out.println("close Exception:" + e.toString());
		}

	}

	@Override
	public void SQL_Conn() {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");// 註冊driver
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(sqlclass.getConn_Str(), sqlclass.getAccount(),
						sqlclass.getPassword());
			} else if (con != null) {
				con.close();
				con = DriverManager.getConnection(sqlclass.getConn_Str(), sqlclass.getAccount(),
						sqlclass.getPassword());
			}
		} catch (SQLException e) {
			System.out.println("資料庫重新連線錯誤" + e.toString());
		} catch (ClassNotFoundException x) {
			System.out.println("重新連線物件發生錯誤");

		}

	}

}
