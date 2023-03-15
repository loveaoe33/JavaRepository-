package DrugSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DrugSQL.SQLStringSetting.CaseSQL;
import SensoryModel.Sensory;

public abstract class AbstractSQL {
	 protected String ConnectionString="";
	 protected String SQLString="";
	 protected String Account="";
	 protected String Password="";
	 protected Connection con=null;
	 protected Statement stat=null;
	 protected ResultSet rs=null;
	 protected PreparedStatement pst=null;
	protected abstract void SQLConnection() throws ClassNotFoundException;
	public abstract ArrayList<Sensory> SQLCase(CaseSQL caseSQL) throws SQLException;
	public abstract String ReSettSQL(String SQLString,String SQLConnectionString,String Account,String Password);
	protected abstract void Close();
	

}
