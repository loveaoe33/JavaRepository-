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

import Personnel_Attend.Appli_form;
import Personnel_Attend.Department;
import Personnel_Attend.Employee;
import Personnel_Attend.SQLSERVER;
import Personnel_Attend.TimeData;
import Personnel_Attend.testServer;
import net.sf.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.sql.Date;


@ComponentScan("Personnel_Attend")
@RestController
public class AttendController {
	

	private  final Department department;
	private  final Employee employee;
	private final TimeData timeData;
	private final SQLSERVER  sqlserver;
	private final Appli_form appli_form;
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
	public AttendController(SQLSERVER  sqlserver,Department department, Employee employee,TimeData timeData,Appli_form appli_form) {
		this.department=department;
		this.sqlserver=sqlserver;
		this.employee=employee;
		this.timeData=timeData;
		this.appli_form=appli_form;
	}
	
	
	
	


	
	@CrossOrigin
	@GetMapping("AttendController/Insert_Department") //新增部門OK
	public String Insert_Department(String Deaprtment) {
		if(Deaprtment.isEmpty()||Deaprtment==null) {
			return "Deaprtment value Cant Empty..";
		}else {
			LocalDateTime  currenDate=LocalDateTime.now();
			String Department_Key=String.format(Deaprtment+"_%s", currenDate.toString());
			Department department=Department.builder().Department(Deaprtment).Department_Key(Department_Key).Child_Department_Key(Department_Key+"s").build();
			return sqlserver.Insert_Department(department);
		}

	}
	@CrossOrigin
	@GetMapping("AttendController/Insert_Employee")  //新增員工
	public String Insert_Employee() {	
		LocalDateTime  currenDate=LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currenDate);
        Date date = new Date(timestamp.getTime());
        
		String Time_Log_Key=String.format("E0011"+"_%s", currenDate.toString());
		Employee employee=Employee.builder().Emp_ID("E0011").Password("love20720").Emp_Name("黃立帆2").Department_Key("資訊室_2023-12-28T18:06:55.350722100").Account_Lv(0).Create_Time(date).Create_Name("黃立帆2").build();
		TimeData timeData=TimeData.builder().Emp_Key("E0011").Last_Time(0).Time_Pon_Mark("Account_Init").Time_Log_Key(Time_Log_Key).Update_Time(date).Time_Event("Time_Init").Time_Mark("初始化").Insert_Time(0).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key("0").build();
		return sqlserver.Insert_Employee(employee,timeData);
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
	@GetMapping("AttendController/Insert_TimeData") //申請資料
	public String Insert_TimeData() {	

		Appli_form appli_form=Appli_form.builder().Emp_Key("E0011").Department("資訊室").Reason("加班").Appli_Time(5).Last_Time(0).Apli_Total(0).Reason_Mark("機器維修").Review_ID_Key(null).Appli_Date(null).Review_Date(null).Check_State("No_Process").build();
		return sqlserver.Attend_TimeData(appli_form);
	}
	@CrossOrigin
	@GetMapping("AttendController/Attend_TimeData") //審核資料
	public String Attend_TimeData() {	
		LocalDateTime  currenDate=LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(currenDate);
        Date date = new Date(timestamp.getTime());
        String Mark="審核測試";  //審核註記
		String Review_ID_Key=String.format("E0010_%s_%s","E0011",timestamp.toString());   //Key規則  "管理人"_"申請者"_時間戳
		Appli_form appli_form=Appli_form.builder().id(2).Check_State("NPass").Review_Result("NPass").Review_Manager("黃立帆").Review_ID_Key(Review_ID_Key).Review_Date(date).Review_Time(date).build();
		TimeData timeData=TimeData.builder().Emp_Key("E0011").Last_Time(0).Time_Pon_Mark("").Time_Log_Key("E0011_2024-01-03T16:24:30.757747100").Update_Time(date).Time_Event("加班").Time_Mark(Mark).Insert_Time(-70).Old_Time(0).New_Time(0).Update_Time(date).Attend_Key(Review_ID_Key).build();

		return sqlserver.Update_Review(appli_form,timeData);
	}
	
	
	@CrossOrigin
	@GetMapping("AttendController/Login_Employee")//帳號登入
	public String Login_Employee(@RequestBody JSONObject Employee_Login_Post) {	
		return "";
	}
	@CrossOrigin
	@GetMapping("AttendController/Change_Employee")//更改帳號密碼
	public String Change_Employee(@RequestBody JSONObject Change_Employee) {	
		return "";
	}
	@CrossOrigin
	@GetMapping("AttendController/Admin_Change_Employee")//管理者更改帳號密碼或權限
	public String Admin_Change_Employee(@RequestBody JSONObject Admin_Change_Employee) {	
		return "";
	}

	
	@CrossOrigin
	@GetMapping("AttendController/Search_TimeData") //調閱資料
	public String Search_TimeData(@RequestBody JSONObject Employee_SearchTime_Post) {	
		return "";
	}

	@CrossOrigin
	@GetMapping("AttendController/EditAttend_TimeData_Post") //編輯審核資料
	public String EditAttend_TimeData_Post(@RequestBody JSONObject EditAttend_TimeData_Post) {	
		return "";
	}
	@CrossOrigin
	@GetMapping("AttendController/SearchEmployee_TimeData_Post") //查詢員工資料
	public String SearchEmployee_TimeData_Post(@RequestBody JSONObject SearchEmployee_TimeData_Post) {	
		return "";
	}
	
	@GetMapping("AttendController/Excel_Employee_TimeData_Post") //員工報表輸出
	public String Excel_Employee_TimeData_Post(@RequestBody JSONObject SearchEmployee_TimeData_Post) {	
		return "";
	}

	
	
	

}


