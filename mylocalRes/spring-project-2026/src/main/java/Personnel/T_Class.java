package Personnel;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.collections.map.HashedMap;

import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;
import DrugSQL.SQLStringSetting.CaseSQL;

public abstract class T_Class{
	protected ArrayList <String> Article_List=new ArrayList();
	 public String Upload_Check="";
	 public abstract void reSerConstruct();
	 public abstract ArrayList<String> Return_List();
	 public abstract void Set_List(String t_Class);
	 public abstract void ReStruct();
}