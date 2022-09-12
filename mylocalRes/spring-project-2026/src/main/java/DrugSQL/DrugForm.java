package DrugSQL;

import DrugModel.EmployeeAc;
import DrugModel.DrugDetail;
import DrugModel.DrugFormMRession;
import DrugModel.DrugFormReassion;
import DrugModel.DrugFormPa;
import DrugModel.DrugFormResult;
import DrugModel.DrugFormnursingforerrortable;
import DrugModel.ErrorDrug;
import DrugModel.EvenetProcessFix;
import DrugModel.PharFor;
import DrugModel.libraryFunction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import SQL.jdbcmysql;

@Repository
public class DrugForm {

	private Connection con = null; // 連線
	private Statement stat = null; // SQL字串
	private ResultSet rs = null; // 結果值
	private PreparedStatement pst = null; // 傳入之sql為預儲之字申,需要傳入變數之位置
	private String EmpID = "";
	private String createAccTble = "CREATE TABLE EmpAccount(" + "id INTEGER" + " , EmployeeID VARCHAR(20)"
			+ " , Employee VARCHAR(20)" + " ,JobTitle VARCHAR(20)" + ", ResDate  VARCHAR(20))";
	private String selectAllEmp = "select * from EmpAccount";
	private String insertdbSQL = "insert into EmpAccount(id,EmployeeID,Employee,JobTitle,ResDate,ResTexarea)"
			+ "select ifNull(max(id),0)+1,?,?,?,?,? FROM EmpAccount";

	private String InsertPatable = "insert into Patable(id,PaName,PaGender,PaNumber,PaClass,PaAge,PaDia,paFiD,PaStart,PaEnd,PaNumberKey) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,? FROM Patable";

	private String InsertPharfortable = "insert into Pharfortable(id,PharForKey,EvenForPa,PaForEven) "
			+ "select ifNULL(max(id),0)+1,?,?,? FROM Pharfortable";

	private String InsertErrordrugtable = "insert into errordrugtable(id,ErrorNameKey,ErrorName) "
			+ "select ifNULL(max(id),0)+1,?,? FROM errordrugtable";

	private String InsertNursingforerrortable = "insert into Nursingforerrortable(id,NursingForErrorKey,ErrorEvent) "
			+ "select ifNULL(max(id),0)+1,?,? FROM Nursingforerrortable";

	private String InsertPatabledrug = "insert into patabledrug(id,PaNumberKey,AboutOtherEvent,PreScript,DeliveryProcessEvent,PharMacyEvent,OtherEvent,NursingReEvent,NursingForErrorKey,NursionNonFor,PharFor,WorkStatusProcess,DrugInfoStatusProcess,EnvironmentStatusProcess,PhysiologicalStatusProcess,PersonStatusProcess,CommunicateStatusProcess,OtherStatusProcess,ProcessMethod,Suggest,DrugName,DrugDose,DrugDosage,DrugRouter,DrugFrequency,DrugNumber,ErrorName,EmployeeID,EmployeeName,InsertDate) "
			+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? FROM patabledrug";

	private String selectOrderEmp = "select * from EmpAccount where EmployeeID ='" + this.EmpID + "'";

	public DrugForm() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 註冊driver
			con = DriverManager.getConnection("jdbc:mysql://localhost/drugsql?useUnicode=true&characterEncoding=Big5",
					"root", "love20720");

		} catch (ClassNotFoundException e) {
			System.out.println("DriverCkassNotFound:" + e.toString());

		} catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}

	}

	public String AddDrugEvent(DrugFormPa pa, DrugFormReassion drugreassion, DrugFormResult drugformresult,
			DrugFormMRession drugformmression, EvenetProcessFix evenetprocessfix, DrugDetail drugdetail) {
		try {
//			rivate String InsertPatabledrug = "insert into patabledrug(id,PaNumberKey,AboutOtherEvent,PreScript,DeliveryProcessEvent,PharMacyEvent,"
//					+ "NursingReEvent,NursingForErrorKey,OtherEvent,PharNonFor,NursionNonFor,PharFor,WorkStatusProcess,DrugInfoStatusProcess,EnvironmentStatusProcess,"
//					+ "PhysiologicalStatusProcess,PersonStatusProcess,CommunicateStatusProcess,OtherStatusProcess,ProcessMethod,Suggest,DrugName,DrugDose,DrugDosage,DrugRouter,DrugFrequency,DrugNumber,ErrorName,EmployeeID,EmployeeName,InsertDate) "
//					+ "select ifNULL(max(id),0)+1,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? FROM patabledrug";

			String InsertDatetime = libraryFunction.NowDate();
			pst = con.prepareStatement(InsertPatabledrug);
			pst.setString(1, pa.getPaNumberKey());
			pst.setString(2, drugreassion.getAboutOtherEvent());
			pst.setString(3, drugreassion.getPreScript());
			pst.setString(4, drugreassion.getDeliveryProcessEvent());
			pst.setString(5, drugreassion.getPharMacyEvent());
			pst.setString(6, drugreassion.getNursingReEvent());
			pst.setString(7, drugreassion.getOtherEvent());
			pst.setString(8, drugreassion.getNursingForErrorKey());

			pst.setString(9, drugformresult.getNursionNonFor());
			pst.setString(10, drugformresult.getPharForKey());

			pst.setString(11, drugformmression.getWorkStatusProcess());
			pst.setString(12, drugformmression.getDrugInfoStatusProcess());
			pst.setString(13, drugformmression.getEnvironmentStatusProcess());
			pst.setString(14, drugformmression.getPhysiologicalStatusProcess());
			pst.setString(15, drugformmression.getPersonStatusProcess());
			pst.setString(16, drugformmression.getCommunicateStatusProcess());
			pst.setString(17, drugformmression.getOtherStatusProcess());
			
			
			pst.setString(18, evenetprocessfix.getProcessMethod());
			pst.setString(19, evenetprocessfix.getSuggest());
			
			
			pst.setString(20, drugdetail.getDrugName());
			pst.setString(21, drugdetail.getDrugDose());
			pst.setString(22, drugdetail.getDrugDosage());
			pst.setString(23, drugdetail.getDrugRouter());
			pst.setString(24, drugdetail.getDrugFrequency());
			pst.setString(25, drugdetail.getDrugNumber());
			pst.setString(26, drugdetail.getErrorNameKey());
			
			pst.setString(27, "E0010");
			pst.setString(28, "黃立帆");
			pst.setString(29, InsertDatetime);
            pst.executeUpdate();
            pst.clearParameters();
			
			System.out.println("總表新增完成");
			return "新增完成";
		} catch (SQLException e) {
			return "新增完成";
		}

	}

	public String InsertPatable(DrugFormPa pa) {

		try {
			pst = con.prepareStatement(InsertPatable);
			pst.setString(1, pa.getPaName());
			pst.setString(2, pa.getPaGender());
			pst.setInt(3, pa.getPaNumber());
			pst.setString(4, pa.getPaClass());
			pst.setInt(5, pa.getPaAge());
			pst.setString(6, pa.getPaDia());
			pst.setString(7, pa.getPaFiD());
			pst.setString(8, pa.getPaStart());
			pst.setString(9, pa.getPaEnd());
			pst.setString(10, pa.getPaNumberKey());
			pst.executeUpdate();
			pst.clearParameters();
			Close();
			
			
			System.out.println("病歷資料新增完成");
			return "病歷資料新增完成";

		} catch (SQLException e) {
			return e.toString();
		}

	}

	public String InsertPharfortable(PharFor Ph) {

		try {
			pst = con.prepareStatement(InsertPharfortable);
			pst.setString(1, Ph.getPharForKey());
			pst.setString(2, Ph.getPaForEven());
			pst.setString(3, Ph.getEvenForPa());
			pst.executeUpdate();
			pst.clearParameters();
			Close();

			System.out.println("已給藥事件新增完成");
			return "已給藥事件新增完成";
		} catch (SQLException e) {
			return e.toString();
		}

	}

	public String InsertErrordrugtable(ErrorDrug Drug) {

		try {
			pst = con.prepareStatement(InsertErrordrugtable);
			pst.setString(1, Drug.getErrorNameKey());
			pst.setString(2, Drug.getErrorName());
			pst.executeUpdate();
			pst.clearParameters();
			
			System.out.println("錯誤藥名新增完成");
			Close();
			return "錯誤藥名新增完成";

		} catch (SQLException e) {
			return e.toString();
		}

	}

	public String InsertNursingforerrortable(DrugFormnursingforerrortable DFNF) {

		try {
			pst = con.prepareStatement(InsertNursingforerrortable);
			pst.setString(1, DFNF.getNursingForErrorKey());
			pst.setString(2, DFNF.getErrorEvent());
			pst.executeUpdate();
			pst.clearParameters();
			Close();
			System.out.println("給錯藥物事件新增完成");
			return "給錯藥物事件新增";

		} catch (SQLException e) {
			return e.toString();
		}

	}

	public String InsertPatabledrug() {
		HashMap<String, String> ReEmpData = new HashMap<>();
		ReEmpData = (HashMap<String, String>) SelectEmpData("E0010");
		System.out.println(ReEmpData.size());
		if (ReEmpData.size() >= 2) {
			System.out.println(ReEmpData.get("EmpID"));
		} else {
			System.out.println(ReEmpData.get("ErrorEmpName"));
			return "找不到此員工";
		}
		return "123";
	}

	public void CreateTable() {
		try {
			stat = con.createStatement();
			stat.executeUpdate(createAccTble);
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	public String AddEmp(EmployeeAc employeeAc) {
		try {

			this.EmpID = employeeAc.getEmployeeID();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from EmpAccount where EmployeeID='" + EmpID + "'");
			int i = 0;
			while (rs.next()) {
				i++;
			}
			if (employeeAc.getEmployeeID() == "" || employeeAc.getEmployee() == "" || employeeAc.getJobTitle() == "") {
				return "資料填寫不完全，不可為空值";

			} else if (i > 0) {
				return "員工編號以重複!!";

			}
			pst = con.prepareStatement(insertdbSQL);
			pst.setString(1, employeeAc.getEmployeeID());
			pst.setString(2, employeeAc.getEmployee());
			pst.setString(3, employeeAc.getJobTitle());
			pst.setString(4, employeeAc.getResDate());
			pst.setString(5, employeeAc.getResTexarea());
			pst.executeUpdate();
			Close();
			return "ture";

		} catch (SQLException e) {
			return "false";
		}

	}

	public void Close() {
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

	public HashMap<String, String> SelectEmpData(String EmployeeID) {
		HashMap<String, String> EmpMap = new HashMap<>();
		try {

			stat = con.createStatement();
			rs = stat.executeQuery("select * from EmpAccount where EmployeeID='" + EmployeeID + "'");
			int i = 0;
			String QEmployeeID = "";
			String Employee = "";
			while (rs.next()) { // 一定要用while,資料會從取出的前一筆開始算!
				i++;
				QEmployeeID = rs.getString("EmployeeID");
				Employee = rs.getString("Employee");
			}
			if (i > 0) {

				EmpMap.put("EmpID", QEmployeeID);
				EmpMap.put("EmpName", Employee);
				System.out.println(EmpMap);
				return EmpMap;
			} else {
				EmpMap.put("ErrorEmpName", "無此員工");
				return EmpMap;
			}
		} catch (SQLException e) {
			EmpMap.put("SQLErrpr", "資料庫查詢錯誤");

			System.out.println(e.toString());
			return EmpMap;
		} finally {
			Close();
		}

	}

	public String AccountCheck(String EmployeeID) {

		try {
			stat = con.createStatement();
			rs = stat.executeQuery("select * from EmpAccount where EmployeeID='" + EmployeeID + "'");
			int i = 0;
			while (rs.next()) {
				i++;
			}
			if (i > 0) {
				return "編號重複";
			} else {
				return "編號可使用";
			}
		} catch (SQLException e) {
			return "錯誤" + e.toString();
		} finally {
			Close();
		}

	}

	public Map<String, String> QueryOne(String EmpID) {

		try {
			HashMap<String, String> EmpRe = new HashMap<>();
			stat = con.createStatement();
			rs = stat.executeQuery("select * from EmpAccount where EmployeeID='" + EmpID + "'");
			int i = 0;
			while (rs != null & rs.next()) {
				i++;
				System.out.println(rs.getString("EmployeeID") + "\t\t" + rs.getString("Employee"));
				String ReEmpID = rs.getString("EmployeeID");
				String ReEmployee = rs.getString("Employee");
				String ReTotal = "員編:" + ReEmpID + "員工:" + ReEmployee;
				EmpRe.put("AcSucess", ReTotal);
			}

			if (i > 0) {
				return EmpRe;
			} else {
				EmpRe.put("AcMiss", "無此帳號");
				return EmpRe;
			}

		} catch (SQLException e) {
			HashMap<String, String> map = new HashMap<>();
			map.put("Error", e.toString());
			return map;
		} finally {
			Close();
		}

	}

	public void Update() {

	}

	public static void main(String[] args) {
		// 測看看是否正常
		DrugForm drugForm = new DrugForm();
//	    drugForm.CreateTable();
//		EmployeeAc d = new EmployeeAc("12", "12", "12", "12", "12");
//		String x = drugForm.AddEmp(d);
//	    test.dropTable(); 
//	    test.createTable(); 
//	    test.insertTable("yku", "12356"); 
//	    test.insertTable("yku2", "7890"); 
//	    test.SelectTable(); 
//		HashMap<String, String> map = new HashMap<>();
//		map = (HashMap<String, String>) drugForm.QueryOne("E0010");
		drugForm.InsertPatabledrug();

	}

}
