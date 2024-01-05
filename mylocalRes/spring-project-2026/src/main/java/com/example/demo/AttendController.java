package com.example.demo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import Personnel_Attend.Appli_form;
import Personnel_Attend.Department;
import Personnel_Attend.Employee;
import Personnel_Attend.PasswordEncryption;
import Personnel_Attend.SQLSERVER;
import Personnel_Attend.TimeData;
import Personnel_Attend.testServer;
import net.sf.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;

@ComponentScan("Personnel_Attend")
@RestController
public class AttendController<Json> {

	private final Department department;
	private final Employee employee;
	private final TimeData timeData;
	private final SQLSERVER sqlserver;
	private final Appli_form appli_form;
	private final PasswordEncryption PassEncry;
	private HashMap<Integer, JsonNode> Ret_Data = new HashMap();
//	public AttendController(testServer test) {
//		this.test=test;
//	}
//	

//	@CrossOrigin
//	@GetMapping("AttendController/test")
//	public String test() {
//	    String x=test.get();
//		return x;
//
//	}
//	@CrossOrigin
//	@GetMapping("AttendController/test2")
//	public String test2() {
//	    String x=test.set();
//		return x;
//
//	}
//	

	@Autowired
	public AttendController(SQLSERVER sqlserver, Department department, Employee employee, TimeData timeData,
			Appli_form appli_form, PasswordEncryption PassEncry) {
		this.department = department;
		this.sqlserver = sqlserver;
		this.employee = employee;
		this.timeData = timeData;
		this.appli_form = appli_form;
		this.PassEncry = PassEncry;
	}

	@CrossOrigin
	@GetMapping("AttendController/Insert_Department") // 新增部門OK
	public String Insert_Department(String Deaprtment) {
		if (Deaprtment.isEmpty() || Deaprtment == null) {
			return "Deaprtment value Cant Empty..";
		} else {
			LocalDateTime currenDate = LocalDateTime.now();
			String Department_Key = String.format(Deaprtment + "_%s", currenDate.toString());
			Department department = Department.builder().Department(Deaprtment).Department_Key(Department_Key)
					.Child_Department_Key(Department_Key + "s").build();
			return sqlserver.Insert_Department(department);
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Insert_Employee") // 新增員工
	public String Insert_Employee() {
		LocalDateTime currenDate = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(currenDate);
		Date date = new Date(timestamp.getTime());

		String Time_Log_Key = String.format("E0012" + "_%s", currenDate.toString());
		Employee employee = Employee.builder().Emp_ID("E0012").Password("love20720").Emp_Name("黃立帆3")
				.Department_Key("資訊室_2023-12-28T18:06:55.350722100").Account_Lv(0).Create_Time(date).Create_Name("黃立帆2")
				.build();
		TimeData timeData = TimeData.builder().Emp_Key("E0012").Last_Time(0).Time_Pon_Mark("Account_Init")
				.Time_Log_Key(Time_Log_Key).Update_Time(date).Time_Event("Time_Init").Time_Mark("初始化").Insert_Time(0)
				.Old_Time(0).New_Time(0).Update_Time(date).Attend_Key("0").build();
		return sqlserver.Insert_Employee(employee, timeData);
	}

//	@CrossOrigin
//	@GetMapping("AttendController/Update_Time")  //時間更新測試
//	public String Update_Time() {	
//		System.out.println("AttendController的記憶體位置:"+timeData);
//
//		LocalDateTime  currenDate=LocalDateTime.now();
//        Timestamp timestamp = Timestamp.valueOf(currenDate);
//        Date date = new Date(timestamp.getTime());
//		String Time_Log_Key=String.format("E0010"+"_%s", currenDate.toString());
//		TimeData timeData=TimeData.builder().Emp_Key("E0010").Last_Time(0).Time_Pon_Mark("").Time_Log_Key("E0010_2023-12-31T14:26:33.173907800").Update_Time(date).Time_Event("加班").Time_Mark("加班測試").Insert_Time(-9).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key("").build();
//		return sqlserver.Insert_TimeData_Update(timeData);
//		
//	}
	@CrossOrigin
	@GetMapping("AttendController/Insert_TimeData") // 申請資料
	public String Insert_TimeData() {

		Appli_form appli_form = Appli_form.builder().Emp_Key("E0011").Department("資訊室").Reason("加班").Appli_Time(5)
				.Last_Time(0).Apli_Total(0).Reason_Mark("機器維修").Review_ID_Key(null).Appli_Date(null).Review_Date(null)
				.Check_State("No_Process").build();
		return sqlserver.Attend_TimeData(appli_form);
	}

	@CrossOrigin
	@GetMapping("AttendController/Attend_TimeData") // 審核資料
	public String Attend_TimeData() {
		LocalDateTime currenDate = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(currenDate);
		Date date = new Date(timestamp.getTime());
		String Mark = "審核測試"; // 審核註記
		String Review_ID_Key = String.format("E0010_%s_%s", "E0011", timestamp.toString()); // Key規則 "管理人"_"申請者"_時間戳
		Appli_form appli_form = Appli_form.builder().id(2).Check_State("NPass").Review_Result("NPass")
				.Review_Manager("黃立帆").Review_ID_Key(Review_ID_Key).Review_Date(date).Review_Time(date).build();
		TimeData timeData = TimeData.builder().Emp_Key("E0011").Last_Time(0).Time_Pon_Mark("")
				.Time_Log_Key("E0011_2024-01-03T16:24:30.757747100").Update_Time(date).Time_Event("加班").Time_Mark(Mark)
				.Insert_Time(-70).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key(Review_ID_Key).build();

		return sqlserver.Update_Review(appli_form, timeData);
	}

	@CrossOrigin
	@GetMapping("AttendController/Login_Employee") // 帳號登入
	public JsonNode Login_Employee() throws JsonMappingException, JsonProcessingException {
//		PassEncry.Password_Check();
		JsonNode jsonNode = null;
		Employee employee = Employee.builder().Emp_ID("E0012").Password("love20720").build();
		ObjectMapper objectMapper = new ObjectMapper();
		if (sqlserver.Login_Employee(employee).equals("false")) {
			System.out.println(sqlserver.Login_Employee(employee));
			jsonNode = null;
		} else {
			System.out.println("xx");
			jsonNode = objectMapper.readTree(sqlserver.Login_Employee(employee));
		}
		return jsonNode;
	}

	@CrossOrigin
	@GetMapping("AttendController/Update_Employee") // 更改帳號密碼更新
	public String Change_Employee() throws JsonMappingException, JsonProcessingException {
//		PassEncry.Password_Check();
		Employee employee = Employee.builder().Emp_ID("E0012").Password("love20320").build();
		return sqlserver.Update_Employee(employee);
	}

	@CrossOrigin
	@GetMapping("AttendController/Admin_Search_TimeData") // 調閱申請資料
	public HashMap Admin_Search_TimeData()
			throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = null;
		Ret_Data.clear();
		int Key = 0;
		ArrayList<String> Admin_Search_TimeData = new ArrayList();
		if (sqlserver.Search_TimeData(Admin_Search_TimeData).equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();

			for (String str : Admin_Search_TimeData) {
				Key++;
				jsonNode = objectMapper.readTree(str);
				Ret_Data.put(Key, jsonNode);
			}
			return Ret_Data;
		}

		return null;

	}

	@GetMapping("AttendController/Excel_All_TimeData_Post") // 所有員工報表輸出
	public HashMap Excel_All_TimeData_Post() throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = null;
		Ret_Data.clear();
		int Key = 0;
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		if (sqlserver.Excel_All_TimeData_Post(Emplyee_Excel_Data).equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();

			for (String str : Emplyee_Excel_Data) {
				Key++;
				jsonNode = objectMapper.readTree(str);
				Ret_Data.put(Key, jsonNode);
			}
			return Ret_Data;
		}
		return null;
	}

	@CrossOrigin
	@GetMapping("AttendController/ Emp_Search_TimeData") // 調閱已審核資料
	public String Emp_Search_TimeData(@RequestBody JSONObject Employee_SearchTime_Post) {
		return "";
	}

	@GetMapping("AttendController/Excel_Employee_TimeData_Post") // 當月審核報表輸出
	public String Excel_Employee_TimeData_Post(@RequestBody JSONObject SearchEmployee_TimeData_Post) {
		return "";
	}

	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_TimeData_Post") // 查詢員工資料
	public String SearchEmployee_TimeData_Post(@RequestBody JSONObject SearchEmployee_TimeData_Post) {
		return "";
	}

}
