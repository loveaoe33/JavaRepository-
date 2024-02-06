package com.example.demo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("AttendController/Init") // 初始化帶出
	public <T> T Init() throws JsonProcessingException {


			return sqlserver.Init_Data();
	}
	
	
	@CrossOrigin
	@PostMapping("AttendController/Insert_Department") // 新增部門OK
	public String Insert_Department(@RequestBody JSONObject Depart_POST) {
		String Department_Value =Depart_POST.getString("Department_Value");
		if ( Department_Value==null || Department_Value.isEmpty()) {
			return "Deaprtment value Cant Empty..";
		} else {
			LocalDateTime currenDate = LocalDateTime.now();
			String Department_Key = String.format(Department_Value + "_%s", currenDate.toString());
			Department department = Department.builder().Department(Department_Value).Department_Key(Department_Key)
					.Child_Department_Key(Department_Key + "s").build();
			return sqlserver.Insert_Department(department);
		}

	}
	@CrossOrigin
	@PostMapping("AttendController/Get_Department_Employee")   //取得部門員工
	public <T> T Get_Department_Employee(@RequestBody JSONObject Depart_Key_POST)
	{
		
		return sqlserver.getEmployee(Depart_Key_POST.getString("Depart_Key_POST"));
	}
	
	@CrossOrigin
	@PostMapping("AttendController/get_Emp_LstTime")   //取得員工時數
	public double  get_Emp_LstTime(@RequestBody JSONObject Emp_Key_POST)
	{
		
		return sqlserver.get_LstTime(Emp_Key_POST.getString("Emp_Key"));
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

		Appli_form appli_form = Appli_form.builder().Emp_Key("E0010").Department("資訊室").Reason("補休").Appli_Time(-20)
				.Last_Time(0).Apli_Total(0).Reason_Mark("補休").Review_ID_Key(null).Appli_Date(null).Review_Date(null)
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
		String Review_ID_Key = String.format("E0010_%s_%s", "E0010", timestamp.toString()); // Key規則 "管理人"_"申請者"_時間戳
		Appli_form appli_form = Appli_form.builder().id(4).Check_State("Pass").Review_Result("Pass")
				.Review_Manager("黃立帆").Review_ID_Key(Review_ID_Key).Review_Date(date).Review_Time(date).build();
		TimeData timeData = TimeData.builder().Emp_Key("E0010").Last_Time(0).Time_Pon_Mark("")
				.Time_Log_Key("E0010_2023-12-31T14:26:33.173907800").Update_Time(date).Time_Event("補休").Time_Mark(Mark)
				.Insert_Time(-20).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key(Review_ID_Key).build();

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
	@GetMapping("AttendController/Admin_Search_TimeData") // 調閱申請審核/未審核資料_All
	public HashMap Admin_Search_TimeData() throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = null;
		HashMap<Integer, JsonNode> Ret_Data_S = new HashMap();

		int Key = 0;
		ArrayList<String> Admin_Search_TimeData = new ArrayList();
		if (sqlserver.Search_TimeData(Admin_Search_TimeData, 1, "資訊室", "No_Process").equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();

			for (String str : Admin_Search_TimeData) {
				Key++;
				jsonNode = objectMapper.readTree(str);
				Ret_Data_S.put(Key, jsonNode);
			}
			return Ret_Data_S;
		}

		return null;

	}

	@GetMapping("AttendController/Excel_Employee_TimeData_Post") // 當月審核報表輸出
	public HashMap Admin_Search_TimeDataM() throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = null;
		Ret_Data.clear();
		int Key = 0;
		ArrayList<String> Admin_Search_TimeData = new ArrayList();
		if (sqlserver
				.Admin_Search_TimeDataM(Admin_Search_TimeData, "E0010", "資訊室", "No_Process", "2024-01-01", "2024-01-31")
				.equals("Sucess")) {
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

	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_History") // 查詢歷史申請All log
	public HashMap SearchEmployee_History() throws JsonMappingException, JsonProcessingException {
		ArrayList<String> SearchEmployee_History = new ArrayList();
		HashMap<Integer, JsonNode> Ret_Data_H = new HashMap();
		JsonNode jsonNode = null;
		int Key = 0;
		if (sqlserver.SearchEmployee_History(SearchEmployee_History, "E0010", "資訊室").equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();
			for (String str : SearchEmployee_History) {
				Key++;
				jsonNode = objectMapper.readTree(str);
				Ret_Data_H.put(Key, jsonNode);
			}
			return Ret_Data_H;
		}

		return null;
	}

	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_HistoryM") // 查詢歷史申請月份log
	public HashMap SearchEmployee_HistoryM() throws JsonMappingException, JsonProcessingException {
		ArrayList<String> SearchEmployee_HistoryM = new ArrayList();
		JsonNode jsonNode = null;
		Ret_Data.clear();
		int Key = 0;
		if (sqlserver.SearchEmployee_HistoryM(SearchEmployee_HistoryM, "E0010", "資訊室", "2024-01-01", "2024-01-03")
				.equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();
			for (String str : SearchEmployee_HistoryM) {
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
		HashMap<Integer, JsonNode> Ret_Data_M = new HashMap();

		int Key = 0;
		String Emp_Key = "E0010";
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		if (sqlserver.Excel_All_TimeData_Post(Emplyee_Excel_Data, "E00105", "資訊室").equals("Sucess")) {
			ObjectMapper objectMapper = new ObjectMapper();
			for (String str : Emplyee_Excel_Data) {
				Key++;
				jsonNode = objectMapper.readTree(str);
				Ret_Data_M.put(Key, jsonNode);
			}
			return Ret_Data_M;
		}
		return null;
	}

	@CrossOrigin
	@GetMapping("AttendController/Cancel_Appli") // 取消審核資料
	public String Cancel_Appli() {
		timeData.ResConstruct();
		timeData.setId(4);
		timeData.setEmp_Key("E0010");
		timeData.setTime_Log_Key("E0010_2023-12-31T14:26:33.173907800");
		return sqlserver.Cancel_Appli(timeData);
	}

	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_TimeData_Post") // 查詢員工資料
	public String SearchEmployee_TimeData_Post(@RequestBody JSONObject SearchEmployee_TimeData_Post) {
		return "";
	}

}
