package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Personnel_Attend.Appli_form;
import Personnel_Attend.Department;
import Personnel_Attend.Employee;
import Personnel_Attend.HistoryLog;
import Personnel_Attend.MergeClass;
import Personnel_Attend.PasswordEncryption;
import Personnel_Attend.SQLClass;
import Personnel_Attend.SQLSERVER;
import Personnel_Attend.TimeData;
import Personnel_Attend.UserApi;
import Personnel_Attend.WindowApplication;
import Personnel_Attend.testServer;
import net.sf.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.sql.Date;

@ComponentScan("Personnel_Attend")
@RestController
public class AttendController<Json> {
	private final Department department;
	private final Employee employee;
	private final TimeData timeData;
	private final SQLSERVER sqlserver;
	private final HistoryLog historylog;
	private final Appli_form appli_form;
	private final PasswordEncryption PassEncry;
	private final WindowApplication window;
	private final MergeClass mergeclass;
	private HashMap<Integer, JsonNode> Ret_Data = new HashMap();
	private ObjectMapper Windowmapper = new ObjectMapper();
	private UserApi userApi;
	private final Lock lock = new ReentrantLock();
	private final Lock lockWindow = new ReentrantLock();

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
	public AttendController(SQLSERVER sqlserver, HistoryLog historylog, Department department, Employee employee,
			TimeData timeData, Appli_form appli_form, PasswordEncryption PassEncry, WindowApplication window,
			MergeClass mergeclass, UserApi userApi) {
		this.department = department;
		this.sqlserver = sqlserver;
		this.historylog = historylog;
		this.employee = employee;
		this.timeData = timeData;
		this.appli_form = appli_form;
		this.window = window;
		this.PassEncry = PassEncry;
		this.mergeclass = mergeclass;
		this.userApi = userApi;

	}

	@CrossOrigin
	@GetMapping("AttendController/Init") // 初始化帶出
	public <T> T Init(@RequestParam("Emp_Key") String Emp_Key, @RequestParam("Depart") String Depart)
			throws JsonProcessingException {
		try {
			lock.lock();
			return sqlserver.Init_Data(Depart, Emp_Key);
		} finally {
			lock.unlock();
		}

	}

//	@CrossOrigin
//	@GetMapping("AttendController/test") // 新增部門OK
//	public String test() {
//        Object obj = new Object();
//        obj
//
//		return null;
//		
//		
//	}
	@CrossOrigin
	@PostMapping("AttendController/Insert_Department") // 新增部門OK
	public String Insert_Department(@RequestBody JSONObject Depart_POST) {
		String Department_Value = Depart_POST.getString("Department_Value");
		try {
			lock.lock();
			if (Department_Value == null || Department_Value.isEmpty()) {
				return "Deaprtment value Cant Empty..";
			} else {
				LocalDateTime currenDate = LocalDateTime.now();
				String Department_Key = String.format(Department_Value + "_%s", currenDate.toString());
				Department department = Department.builder().Department(Department_Value).Department_Key(Department_Key)
						.Child_Department_Key(Department_Key + "s").build();
				return sqlserver.Insert_Department(department);
			}
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Get_Department_Employee") // 取得部門員工
	public <T> T Get_Department_Employee(@RequestBody JSONObject Depart_Key_POST) {

		try {
			lock.lock();
			return sqlserver.getEmployee(Depart_Key_POST.getString("Depart_Key_POST"));
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/get_Emp_LstTime") // 取得員工時數
	public double get_Emp_LstTime(@RequestBody JSONObject Emp_Key_POST) {
		try {
			lock.lock();
			return sqlserver.get_LstTime(Emp_Key_POST.getString("Emp_Key"), "Last_Time");
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Insert_Employee") // 新增員工
	public String Insert_Employee(@RequestBody JSONObject Employee_Object_POST) {

		try {
			lock.lock();
			LocalDateTime currenDate = LocalDateTime.now();
			Timestamp timestamp = Timestamp.valueOf(currenDate);
			Date date = new Date(timestamp.getTime());
			Object Employee_Object = Employee_Object_POST.get("Emp_Object");
			String Time_Log_Key = String.format(
					(((JSONObject) Employee_Object).getString("Emp_Account")).toUpperCase() + "_%s",
					currenDate.toString());
			Employee employee = Employee.builder()
					.Emp_ID((((JSONObject) Employee_Object).getString("Emp_Account")).toUpperCase())
					.Password(((JSONObject) Employee_Object).getString("Emp_Password"))
					.Emp_Name(((JSONObject) Employee_Object).getString("Emp_Name"))
					.Department_Key(((JSONObject) Employee_Object).getString("Emp_Department"))
					.Account_Lv(((JSONObject) Employee_Object).getInt("Emp_Lv")).Create_Time(date)
					.Create_Name(((JSONObject) Employee_Object).getString("Create_Emp")).build();
			TimeData timeData = TimeData.builder()
					.Emp_Key((((JSONObject) Employee_Object).getString("Emp_Account")).toUpperCase()).Last_Time(0)
					.Time_Pon_Mark("Account_Init").Time_Log_Key(Time_Log_Key).Update_Time(date).Time_Event("Time_Init")
					.Time_Mark("初始化").Insert_Time(0).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key("0").build();
			return sqlserver.Insert_Employee(employee, timeData);
		} finally {
			lock.unlock();
		}
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
	@PostMapping("AttendController/Insert_TimeData") // 申請資料
	public String Insert_TimeData(@RequestBody JSONObject Appli_Object_Post) {

		try {
			lock.lock();
			Object Appli_Object = Appli_Object_Post.get("Appli_Object");
			Double Time = (((JSONObject) Appli_Object).getString("Reason").equals("Public_Holi")
					|| ((JSONObject) Appli_Object).getString("Reason").equals("Over_Time"))
							? ((JSONObject) Appli_Object).getDouble("Appli_Time")
							: -(((JSONObject) Appli_Object).getDouble("Appli_Time"));

			Appli_form appli_form = Appli_form.builder().Emp_Key(((JSONObject) Appli_Object).getString("Emp_ID"))
					.Department(((JSONObject) Appli_Object).getString("DepartMent"))
					.Reason(((JSONObject) Appli_Object).getString("Reason")).Appli_Time(Time).Last_Time(0).Apli_Total(0)
					.Reason_Mark(((JSONObject) Appli_Object).getString("ReasonMark")).Review_ID_Key(null)
					.Appli_Date(null).Review_Date(null).Check_State("No_Process").build();
			return sqlserver.Appli_Check(appli_form);
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Edit_Print") // 取出申請單內容
	public String Edit_Print(@RequestParam("id") int id, @RequestParam("Emp_Key") String Emp_Key) {
		try {
			lock.lock();
			return sqlserver.Appli_Edit_Print(id, Emp_Key); // 取出申請單內容
		} finally {
			lock.unlock();
		}
	}

	@CrossOrigin
	@PostMapping("AttendController/Edit_Appli") // 更新申請單內容
	public String Edit_Appli(@RequestBody JSONObject Appli_Object_Post) { // 更改申請單
		try {
			lock.lock();

			Object Appli_Object = Appli_Object_Post.get("Appli_Object");

			Double Time = (((JSONObject) Appli_Object).getString("Reason").equals("Public_Holi")
					|| ((JSONObject) Appli_Object).getString("Reason").equals("Over_Time"))
							? ((JSONObject) Appli_Object).getDouble("Appli_Time")
							: -(((JSONObject) Appli_Object).getDouble("Appli_Time"));

			Appli_form appli_form = Appli_form.builder().id(((JSONObject) Appli_Object).getInt("Appli_id"))
					.Emp_Key(((JSONObject) Appli_Object).getString("Emp_ID"))
					.Department(((JSONObject) Appli_Object).getString("DepartMent"))
					.Reason(((JSONObject) Appli_Object).getString("Reason")).Appli_Time(Time).Last_Time(0).Apli_Total(0)
					.Reason_Mark(((JSONObject) Appli_Object).getString("ReasonMark")).Review_ID_Key(null)
					.Appli_Date(null).Review_Date(null).Check_State("No_Process").build();

			return sqlserver.Appli_Edit(appli_form); // 更新申請單內容
		} finally {
			lock.unlock();
		}
	}

	@CrossOrigin
	@PostMapping("AttendController/Insert_Special_TimeData") // 新增特休
	public boolean Insert_Special_TimeData(@RequestBody JSONObject Emp_Data) throws SQLException {

		try {
			lock.lock();
			timeData.ResConstruct();
			Object Special_Object = Emp_Data.get("Special_Object");
			timeData.Special(((JSONObject) Special_Object).getString("Emp_Key"),
					((JSONObject) Special_Object).getString("State"), ((JSONObject) Special_Object).getString("Remark"),
					((JSONObject) Special_Object).getDouble("Plus_Special"),
					((JSONObject) Special_Object).getString("Manager"));
			Double Lst_Special = sqlserver.get_LstTime(timeData.getEmp_Key(), "Special_Date");
			return sqlserver.Insert_Special_Log(timeData, Lst_Special);
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Select_Emp_Data") // 抓取特休資料
	public String Select_Emp_Data(@RequestBody JSONObject Emp_Data) throws SQLException {
		try {
			lock.lock();
			return sqlserver.getEmpyoee_Data(Emp_Data.getString("Emp_Key"));
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Attend_TimeData") // 審核資料
	public String Attend_TimeData(@RequestBody JSONObject Attend_TimeData_Post) throws SQLException {
		LocalDateTime currenDate = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(currenDate);
		Date date = new Date(timestamp.getTime());
		Object Attend_TimeData = Attend_TimeData_Post.get("Attend_TimeData_Post");
		String Appli_Id = ((JSONObject) Attend_TimeData).getString("Appli_Id"); // 申請id
		String Mark = ((JSONObject) Attend_TimeData).getString("Time_Mark"); // 審核註記
		String Time_Event = ((JSONObject) Attend_TimeData).getString("Time_Event"); // 申請事件
		String Manager = ((JSONObject) Attend_TimeData).getString("Manager"); // 主管
		String Appli_Employee = ((JSONObject) Attend_TimeData).getString("Appli_Employee"); // 申請人
		String Review_ID_Key = String.format("%s_%s_%s", Manager, Appli_Employee, currenDate); // Key規則 "管理人"_"申請者"_時間戳
		String State = ((JSONObject) Attend_TimeData).getString("State"); // 審核狀態
//		int Appli_Time = ((JSONObject) Attend_TimeData).getInt("Appli_Time"); // 申請時間
		String Time_Log_Key = sqlserver.Get_TimeKey(Appli_Employee);// 申請人時間主鍵

		try {
			lock.lock();
			if (Time_Log_Key.equals("false")) {
				return "Error Key...";
			} else {
				Appli_form appli_form = Appli_form.builder().id(Integer.parseInt(Appli_Id)).Check_State(State)
						.Review_Result(State).Review_Manager(Manager).Review_ID_Key(Review_ID_Key).Review_Date(date)
						.Review_Time(date).build();

				TimeData timeData = TimeData.builder().Emp_Key(Appli_Employee).Last_Time(0).Time_Pon_Mark("")
						.Time_Log_Key(Time_Log_Key).Update_Time(date).Time_Event(Time_Event).Time_Mark(Mark)
						.Insert_Time(0).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key(Review_ID_Key).build();

				return sqlserver.Update_Review(appli_form, timeData);
			}

		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Login_Employee") // 帳號登入
	public String Login_Employee(@RequestBody JSONObject LoginObject)
			throws JsonMappingException, JsonProcessingException {
//		PassEncry.Password_Check();
		String Account = ((JSONObject) LoginObject.get("Login_Object")).getString("Account");
		String Password = ((JSONObject) LoginObject.get("Login_Object")).getString("Password");
		JsonNode jsonNode = null;
		Employee employee = Employee.builder().Emp_ID(Account).Password(Password).build();

		try {
			lock.lock();
			if (sqlserver.Login_Employee(employee).equals("false")) {
				return "fail";
			} else {
				return sqlserver.Login_Employee(employee);
			}
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/test2") // 帳號登入
	public String Login_Employee2() throws JsonMappingException, JsonProcessingException {
		return "123";

	}

	@CrossOrigin
	@PostMapping("AttendController/Login_C_Employee") // Application的帳號登入
	public String Login_Employee3(@RequestBody String content) throws JsonMappingException, JsonProcessingException {

		try {
			lockWindow.lock();
			UserApi user = Windowmapper.readValue(content, UserApi.class);
			Employee employee = Employee.builder().Emp_ID(user.getAccount()).Password(user.getPassword()).build();
			if (sqlserver.Login_Employee(employee).equals("false")) {
				return "fail";
			} else {
				return sqlserver.Login_Employee(employee);
			}

		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Update_Employee  ") // Application的員工資料更新
	public JsonNode Update_Employee(@RequestBody String content) throws JsonMappingException, JsonProcessingException {

		try {
			lockWindow.lock();
		} finally {
			lockWindow.unlock();
		}
		return null;

	}

	@CrossOrigin
	@PostMapping("AttendController/Update_Employee") // 更改帳號密碼更新
	public String Change_Employee(@RequestBody JSONObject Update_Objects)
			throws JsonMappingException, JsonProcessingException {
//		PassEncry.Password_Check();
		try {
			lock.lock();
			Object Update_Object = Update_Objects.get("Update_Object_Post");
			Employee employee = Employee.builder()
					.Emp_ID(((JSONObject) Update_Object).getJSONObject("_rawValue").getString("Emp_ID"))
					.Password(((JSONObject) Update_Object).getJSONObject("_rawValue").getString("NewPassword")).build();
			return sqlserver.Update_Employee(employee,
					((JSONObject) Update_Object).getJSONObject("_rawValue").getString("oldPassword"));
		} finally {
			lock.unlock();
		}
	}

	@CrossOrigin
	@GetMapping("AttendController/Super_Change") // 超級更新
	public String Super_Change() {
		return sqlserver.Super_Update_Employee("E0010", "123");
	}

	@CrossOrigin
	@PostMapping("AttendController/Admin_Search_TimeData") // 調閱申請審核/未審核資料_All
	public ArrayList Admin_Search_TimeData(@RequestBody JSONObject Appli_Object_Post)
			throws JsonMappingException, JsonProcessingException {
		try {
			lock.lock();
			ArrayList<JsonNode> Ret_Data_S = new ArrayList();
			ArrayList<String> Admin_Search_TimeData = new ArrayList();
			JsonNode jsonNode = null;
			Object Appli_Object = Appli_Object_Post.get("Appli_Object_Post");

			if (sqlserver.Search_TimeData(Admin_Search_TimeData, ((JSONObject) Appli_Object).getInt("Admin_Lv"),
					((JSONObject) Appli_Object).getString("Export_Depart"),
					((JSONObject) Appli_Object).getString("Export_State")).equals("Sucess")) {
				ObjectMapper objectMapper = new ObjectMapper();

				for (String str : Admin_Search_TimeData) {

					jsonNode = objectMapper.readTree(str);
					Ret_Data_S.add(jsonNode);
				}
				return Ret_Data_S;
			}

			return null;
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Member_Search_TimeData") // 調閱申請審核/未審核資料_All
	public ArrayList Member_Search_TimeData(@RequestBody JSONObject Member_Object_Post)
			throws JsonMappingException, JsonProcessingException {

		try {
			lock.lock();
			ArrayList<JsonNode> Ret_Data_S = new ArrayList();
			ArrayList<String> Member_Search_TimeData = new ArrayList();
			JsonNode jsonNode = null;
			Object Member_TimeData_Object = Member_Object_Post.get("Member_Object");
			if (sqlserver.Search_TimeData_Member(Member_Search_TimeData,
					(((JSONObject) Member_TimeData_Object).getString("Emp_Key")),
					(((JSONObject) Member_TimeData_Object).getString("State"))).equals("Sucess")) {
				ObjectMapper objectMapper = new ObjectMapper();
				for (String str : Member_Search_TimeData) {

					jsonNode = objectMapper.readTree(str);
					Ret_Data_S.add(jsonNode);
				}
				return Ret_Data_S;
			}

			return null;
		} finally {
			lock.unlock();
		}

	}

	@GetMapping("AttendController/Excel_Employee_TimeData_Post") // 當月審核報表輸出 未處理
	public HashMap Admin_Search_TimeDataM() throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = null;
		Ret_Data.clear();
		int Key = 0;

		try {
			lock.lock();
			ArrayList<String> Admin_Search_TimeData = new ArrayList();
			if (sqlserver.Admin_Search_TimeDataM(Admin_Search_TimeData, "E0010", "資訊室", "No_Process", "2024-01-01",
					"2024-01-31").equals("Sucess")) {
				ObjectMapper objectMapper = new ObjectMapper();

				for (String str : Admin_Search_TimeData) {
					Key++;
					jsonNode = objectMapper.readTree(str);
					Ret_Data.put(Key, jsonNode);
				}
				return Ret_Data;
			}

			return null;
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_Appli") // 查詢員工申請紀錄
	public HashMap SearchEmployee_Appli() {
		try {
			lock.lock();
			String Switch = "";
			if (Switch.equals("Admin")) {

			} else if (Switch.equals("Employee")) {

			}

		} finally {
			lock.unlock();

		}
		return Ret_Data;

	}

//	@CrossOrigin
//	@GetMapping("AttendController/SearchEmployee_History") // 查詢歷史申請All log 未處理
//	public HashMap SearchEmployee_History() throws JsonMappingException, JsonProcessingException {
//
//		try {
//			lock.lock();
//			ArrayList<String> SearchEmployee_History = new ArrayList();
//			HashMap<Integer, JsonNode> Ret_Data_H = new HashMap();
//			JsonNode jsonNode = null;
//			int Key = 0;
//			if (sqlserver.SearchEmployee_History(SearchEmployee_History, "E0010", "資訊室").equals("Sucess")) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				for (String str : SearchEmployee_History) {
//					Key++;
//					jsonNode = objectMapper.readTree(str);
//					Ret_Data_H.put(Key, jsonNode);
//				}
//				return Ret_Data_H;
//			}
//
//			return null;
//		} finally {
//			lock.unlock();
//		}
//
//	}
//
//	@CrossOrigin
//	@GetMapping("AttendController/SearchEmployee_HistoryM") // 查詢歷史申請月份log 未處理
//	public HashMap SearchEmployee_HistoryM() throws JsonMappingException, JsonProcessingException {
//
//		try {
//			lock.lock();
//			ArrayList<String> SearchEmployee_HistoryM = new ArrayList();
//			JsonNode jsonNode = null;
//			Ret_Data.clear();
//			int Key = 0;
//			if (sqlserver.SearchEmployee_HistoryM(SearchEmployee_HistoryM, "E0010", "資訊室", "2024-01-01", "2024-01-03")
//					.equals("Sucess")) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				for (String str : SearchEmployee_HistoryM) {
//					Key++;
//					jsonNode = objectMapper.readTree(str);
//					Ret_Data.put(Key, jsonNode);
//				}
//				return Ret_Data;
//			}
//
//			return null;
//		} finally {
//			lock.unlock();
//		}
//
//	}
//
//	@GetMapping("AttendController/Excel_All_TimeData_Post") // 所有員工報表輸出   未處理
//	public HashMap Excel_All_TimeData_Post() throws JsonMappingException, JsonProcessingException {
//		JsonNode jsonNode = null;
//
//		try {
//			lock.lock();
//			HashMap<Integer, JsonNode> Ret_Data_M = new HashMap();
//
//			int Key = 0;
//			String Emp_Key = "E0010";
//			ArrayList<String> Emplyee_Excel_Data = new ArrayList();
//			if (sqlserver.Excel_All_TimeData_Post(Emplyee_Excel_Data, "E00105", "資訊室").equals("Sucess")) {
//				ObjectMapper objectMapper = new OAbjectMapper();
//				for (String str : Emplyee_Excel_Data) {
//					Key++;
//					jsonNode = objectMapper.readTree(str);
//					Ret_Data_M.put(Key, jsonNode);
//				}
//				return Ret_Data_M;
//			}
//			return null;
//		} finally {
//			lock.unlock();
//		}
//
//	}

	@CrossOrigin
	@PostMapping("AttendController/Cancel_Appli") // 取消審核資料 appli的id、管理員、TimeLogKey
	public String Cancel_Appli(@RequestBody JSONObject Attend_TimeData_Post) {

		Object Attend_TimeData = Attend_TimeData_Post.get("Attend_TimeData_Post");
		String Appli_Id = ((JSONObject) Attend_TimeData).getString("Appli_Id"); // 申請id
		String Manager = ((JSONObject) Attend_TimeData).getString("Manager"); // 主管
		String Appli_Employee = ((JSONObject) Attend_TimeData).getString("Appli_Employee"); // 申請人

		try {
			lock.lock();
			timeData.ResConstruct();
			timeData.setId(Integer.parseInt(Appli_Id));
			timeData.setManager(Manager);
			timeData.setEmp_Key(Appli_Employee);
//			timeData.setTime_Log_Key("E0010_2023-12-31T14:26:33.173907800");
			return sqlserver.Cancel_Appli(timeData);
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/SearchEmployee_TimeData_Appli") // 查詢日期申請範圍申請資料
	public <T> T SearchEmployee_TimeData_Appli(@RequestBody JSONObject Member_Object)
			throws JsonMappingException, JsonProcessingException {
		Object MemberSelect = Member_Object.get("Member_Object");

		HistoryLog Log = new HistoryLog(new SQLClass());
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		ArrayList<JsonNode> Ret_Data_S = new ArrayList();
		JsonNode jsonNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if (((JSONObject) MemberSelect).getString("State").equals("申請歷史")) {
			try {
				lock.lock();
				if (Log.Get_Employee_Histort(Emplyee_Excel_Data, ((JSONObject) MemberSelect).getString("Emp_Key"),
						((JSONObject) MemberSelect).getString("Start"), ((JSONObject) MemberSelect).getString("End"))
						.equals("Sucess")) {

					for (String str : Emplyee_Excel_Data) {

						jsonNode = objectMapper.readTree(str);
						Ret_Data_S.add(jsonNode);
					}
					return (T) Ret_Data_S;

				} else {
					return (T) "找無資料";
				}
			} finally {
				lock.unlock();
			}

		} else {
			return (T) "false";

		}

	}

	@CrossOrigin
	@PostMapping("AttendController/SearchEmployee_TimeData_Review") // 查詢日期審核範圍申請資料
	public <T> T SearchEmployee_TimeData_Review(@RequestBody JSONObject Member_Object)
			throws JsonMappingException, JsonProcessingException {

		Object MemberSelect = Member_Object.get("Member_Object");
		HistoryLog Log = new HistoryLog(new SQLClass());
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		ArrayList<JsonNode> Ret_Data_S = new ArrayList();
		JsonNode jsonNode = null;
		ObjectMapper objectMapper = new ObjectMapper();

		if (((JSONObject) MemberSelect).getString("State").equals("審核歷史")) {
			try {
				lock.lock();
				if (Log.Get_Employee_Review(Emplyee_Excel_Data, ((JSONObject) MemberSelect).getString("Emp_Key"),
						((JSONObject) MemberSelect).getString("Start"), ((JSONObject) MemberSelect).getString("End"))
						.equals("Sucess")) {
					for (String str : Emplyee_Excel_Data) {

						jsonNode = objectMapper.readTree(str);
						Ret_Data_S.add(jsonNode);
					}
					return (T) Ret_Data_S;
				} else {
					return (T) "找無資料";
				}
			} finally {
				lock.unlock();
			}

		} else {
			return (T) "false";

		}

	}

	@CrossOrigin
	@PostMapping("AttendController/SearchDepart_TimeData_Log") // 查詢日期範圍部門log
	public <T> T SearchDepart_TimeData_Log(@RequestBody JSONObject Depart_Post_Object)
			throws JsonMappingException, JsonProcessingException {
		Object DepartSelect = Depart_Post_Object.get("Member_Object");
		int Emp_Lv = sqlserver.get_Emp_Lv(((JSONObject) DepartSelect).getString("Emp_Key"));
		HistoryLog Log = new HistoryLog(new SQLClass());
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		ArrayList<JsonNode> Ret_Data_S = new ArrayList();
		JsonNode jsonNode = null;
		ObjectMapper objectMapper = new ObjectMapper();

		if (Emp_Lv != 99 && (Emp_Lv == 0 || Emp_Lv == 1)) {
			try {
				lock.lock();

				if (Log.Get_Depart_History(Emplyee_Excel_Data, ((JSONObject) DepartSelect).getString("Depart"),
						((JSONObject) DepartSelect).getString("Start"), ((JSONObject) DepartSelect).getString("End"))
						.equals("Sucess")) {

					for (String str : Emplyee_Excel_Data) {

						jsonNode = objectMapper.readTree(str);
						Ret_Data_S.add(jsonNode);
					}

					return (T) Ret_Data_S;
				} else {
					return (T) "找無資料";
				}
			} finally {
				lock.unlock();
			}
		} else {
			return (T) "權限不足";

		}

	}

	@CrossOrigin
	@PostMapping("AttendController/SearchDepart_TimeData_AllLog") // 查詢所有log
	public <T> T SearchDepart_TimeData_AllLog(@RequestBody JSONObject ALL_Post_Object)
			throws JsonMappingException, JsonProcessingException {
		Object AllSelect = ALL_Post_Object.get("Member_Object");
		int Emp_Lv = sqlserver.get_Emp_Lv(((JSONObject) AllSelect).getString("Emp_Key"));
		HistoryLog Log = new HistoryLog(new SQLClass());
		ArrayList<String> Emplyee_Excel_Data = new ArrayList();
		ArrayList<JsonNode> Ret_Data_S = new ArrayList();
		JsonNode jsonNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		if (Emp_Lv != 99 && Emp_Lv == 0) {
			try {
				lock.lock();
				if (Log.All_Histort(Emplyee_Excel_Data, ((JSONObject) AllSelect).getString("Start"),
						((JSONObject) AllSelect).getString("End")).equals("Sucess")) {
					for (String str : Emplyee_Excel_Data) {
						jsonNode = objectMapper.readTree(str);
						Ret_Data_S.add(jsonNode);
					}
					return (T) Ret_Data_S;
				} else {
					return (T) "找無資料";
				}
			} finally {
				lock.unlock();
			}
		} else {
			return (T) "權限不足";
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Announcement_List") // 布告欄資料
	public <T> T Announcement_List() throws JsonProcessingException {
		try {
			lock.lock();
			return sqlserver.Announcement_Init_Data();
		} finally {
			lock.unlock();
		}
	}

	@CrossOrigin
	@PostMapping("AttendController/Announcement_Post") // 布告欄資料
	public String announcement(@RequestBody JSONObject Announcement_Post) {
		Object Announcement_Object = Announcement_Post.get("Announcement_Post");

		String Insert_Str = "";
		try {
			lock.lock();
			if (((JSONObject) Announcement_Object).getString("State_Key").equals("Insert")) {
				Insert_Str = String.format("%s,%s,%s", ((JSONObject) Announcement_Object).getString("Emp_Name"),
						((JSONObject) Announcement_Object).getString("Announcement_Title"),
						((JSONObject) Announcement_Object).getString("Announcement_Context"));
				return sqlserver.Announcement("Insert", Insert_Str);
			} else if (((JSONObject) Announcement_Object).getString("State_Key").equals("Delete")) {
				return sqlserver.Announcement("Delete",
						((JSONObject) Announcement_Object).getString("Announcement_Id"));
			}
			return Insert_Str;
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/checkPasscode") // 比對Passcode
	public String checkPasscode(@RequestParam("DepartKey") String Depart, @RequestParam("PassCode") String PassCode)
			throws JsonProcessingException {
		String[] DepartSplit = Depart.split("_");
		return sqlserver.PassCodeCheck(DepartSplit[0], PassCode);
	}

	@CrossOrigin
	@GetMapping("AttendController/getPasscode") // 初始化Passcode
	public <T> T getPasscode() throws JsonProcessingException {
		try {
			lock.lock();

			return sqlserver.PassCode_Init_Data();

		} finally {
			lock.unlock();
		}
	}

	@CrossOrigin
	@PostMapping("AttendController/Passcode") // 處理Passcode
	public String Passcode(@RequestBody JSONObject PassCode_Post) throws JsonProcessingException {
		Object PassCode_Object = PassCode_Post.get("PassObject_Post");

		String Insert_Str = "";
		try {
			lock.lock();
			if (((JSONObject) PassCode_Object).getString("State_Key").equals("Insert")) {
				String[] DepartSplit = ((JSONObject) PassCode_Object).getString("Depart_Select").split("_");
				Insert_Str = String.format("%s,%s,%s", DepartSplit[0],
						((JSONObject) PassCode_Object).getString("Pass_Code"),
						((JSONObject) PassCode_Object).getString("Create_Name"));
				return sqlserver.PassCode("Insert", Insert_Str);
			} else if (((JSONObject) PassCode_Object).getString("State_Key").equals("Delete")) {
				return sqlserver.PassCode("Delete", ((JSONObject) PassCode_Object).getString("Pass_Id"));
			}
			return "fail";
		} finally {
			lock.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/App_Select_PersonnelDepart") // 帶出人資系統的部門 OK
	public <T> T App_Select_PersonnelDepart(@RequestParam String content)
			throws JsonProcessingException, UnsupportedEncodingException {
		content = URLDecoder.decode(content, "UTF-8");
		try {
			lockWindow.lock();
			userApi = Windowmapper.readValue(content, UserApi.class);
			int Account_Lv = userApi.getAccount_Lv();
			if (Account_Lv <= 1) {
				ArrayList<String> DepartList = new ArrayList<String>();
				return window.appInit_Data(DepartList);
			} else {
				return (T) "fail";
			}

		} finally {
			lockWindow.unlock();

		}

	}

	@CrossOrigin
	@GetMapping("AttendController/App_GetPerEmployee") // 帶出人資系統的部門員工 OK
	public <T> T App_GetPerEmployee(@RequestParam String content)
			throws JsonProcessingException, UnsupportedEncodingException {
		content = URLDecoder.decode(content, "UTF-8");
		try {
			lockWindow.lock();
			userApi = Windowmapper.readValue(content, UserApi.class);
			ArrayList<String> empList = new ArrayList<String>();
			return window.getEmployee(empList, userApi.getOrigDepart());
		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Load_EmpDetail") // 帶出人資系統員工明細 OK
	public <T> T Load_EmpDetail(@RequestParam String content)
			throws JsonProcessingException, UnsupportedEncodingException {
		content = URLDecoder.decode(content, "UTF-8");
		try {
			lockWindow.lock();
			userApi = Windowmapper.readValue(content, UserApi.class);
			ArrayList<String> empList = new ArrayList<String>();
			return window.getEmployee(empList, userApi.getOrigDepart());
		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("AttendController/App_Post_MapDepart") // 新增管轄權部門
	public String App_Post_MapDepart(@RequestBody JSONObject Juris_Post)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException, SQLException {
		try {
			lockWindow.lock();
			return window.Update_Post_MapDepart(Juris_Post.getString("Juris_Insert"), Juris_Post.getString("Emp_ID"));

		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/App_Select_MapDepart") // 取得管轄權部門 OK
	public String App_Select_DepartEmp(@RequestParam String content)
			throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		content = URLDecoder.decode(content, "UTF-8");

		try {
			lockWindow.lock();
			userApi = Windowmapper.readValue(content, UserApi.class);
			if (window.selectDepartEmp(userApi.getEmp_ID(), userApi.getAccount_Lv()) == null) {
				return "none";
			} else {
				return window.selectDepartEmp(userApi.getEmp_ID(), userApi.getAccount_Lv());
			}
		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/App_GetMapEmployee") // json字串 帶出map的部門員工 OK
	public <T> T App_GetMapEmployee(@RequestParam("content") String content)
			throws JsonProcessingException, UnsupportedEncodingException {
		ArrayList<String> empList = new ArrayList<String>();
		content = URLDecoder.decode(content, "UTF-8");
		try {
			lockWindow.lock();
			System.out.print("取得員工" + content);

			userApi = Windowmapper.readValue(content, UserApi.class);
			return window.getMapEmployee(empList, userApi.getMapDepart(), userApi.getEmp_ID(), userApi.getAccount_Lv());
		} finally {
			lockWindow.unlock();
		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Clear_Mapping_Employee") // 清除員工資料Mapping OK
	public String Clear_Mapping_Employee(@RequestBody JSONObject Clear_Post) {

		try {

			lockWindow.lock();
		} finally {
			lockWindow.unlock();

		}
		return window.Clear_Mapping("E0010");
	}

	@CrossOrigin
	@PostMapping("AttendController/App_Mapping_Employee") // json檔 新增員工資料Mapping OK
	public String App_Mapping_Employee(@RequestBody String Mapping_Post)
			throws JsonMappingException, JsonProcessingException {
		try {

			lockWindow.lock();
			userApi = Windowmapper.readValue(Mapping_Post, UserApi.class);

			return window.Mapping_Employee(userApi);
		} finally {
			lockWindow.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("AttendController/Update_Pesonnel_Employee") // 更新員工資料 OK
	public String Update_Mapping_Employee(@RequestBody JSONObject Update_Post)
			throws JsonMappingException, JsonProcessingException {
		try {
			lockWindow.lock();

			return window.UpdateEmployee((String) Update_Post.get("Emp_ID"), (String) Update_Post.get("ChangeName"),
					(String) Update_Post.get("ChangeDepart"),
					Integer.parseInt((String) Update_Post.get("ChangeLevel")));

		} finally {
			lockWindow.unlock();

		}
	}

	@CrossOrigin
	@PostMapping("AttendController/postExcelData") // 寫入出勤 insertExcel
	public <T> T postExcelData(@RequestBody String excelData) throws JsonMappingException, JsonProcessingException {
		try {
			lockWindow.lock();
			ArrayList<String> dataList = new ArrayList<String>();
			dataList = Windowmapper.readValue(excelData, ArrayList.class);
			return (T) window.insertExcel(dataList);
		} finally {
			lockWindow.unlock();

		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Select_All_AttendData") // 撈出所有出勤 /本月或區間OK
	public <T> T One_DepartEmpData(@RequestParam String content) throws UnsupportedEncodingException {
		ArrayList<String> dataList = new ArrayList<String>();
		Gson gson = new Gson();
		content = URLDecoder.decode(content, "UTF-8");
		JsonObject data = gson.fromJson(content, JsonObject.class);
		try {
			lockWindow.lock();
			if (window.allEmpData(dataList, data.get("Key").getAsString(), data.get("SelectData").getAsString(),
					data.get("Depart").getAsString(), data.get("SelectEmp").getAsString(),
					data.get("Start_Date").getAsString(), data.get("End_Date").getAsString()).equals("fail")) {
				return (T) "fail";
			} else {
				return (T) dataList;
			}
		} finally {
			lockWindow.unlock();

		}

	}

	@CrossOrigin
	@GetMapping("AttendController/Select_All_JurisData") // 管轄部門區間
	public <T> T Select_All_JurisData(@RequestParam String content) throws UnsupportedEncodingException {
		ArrayList<String> dataList = new ArrayList<String>();
		Gson gson = new Gson();
		content = URLDecoder.decode(content, "UTF-8");
		JsonObject data = gson.fromJson(content, JsonObject.class);
		try {
			lockWindow.lock();
			if (window.All_JurisData(dataList, data.get("SelectEmp").getAsString(),
					data.get("Start_Date").getAsString(), data.get("End_Date").getAsString()).equals("fail")) {
				return (T) "fail";
			} else {
				return (T) dataList;
			}
		} finally {

			lockWindow.unlock();
		}

	}
}
