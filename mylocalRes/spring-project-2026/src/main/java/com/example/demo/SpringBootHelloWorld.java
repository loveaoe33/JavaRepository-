package com.example.demo;

import DrugSQL.DrugForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import DrugModel.DrugDetail;
import DrugModel.DrugFormMRession;
import DrugModel.DrugFormPa;
import DrugModel.DrugFormReassion;
import DrugModel.DrugFormResult;
import DrugModel.DrugFormnursingforerrortable;
import DrugModel.EmployeeAc;
import DrugModel.ErrorDrug;
import DrugModel.EvenetProcessFix;
import DrugModel.PharFor;
import DrugModel.libraryFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
public class SpringBootHelloWorld {

	HttpSession EmpSession = null;

	@RequestMapping("/")
	public String hello() {
		return "Hey, Spring Boot 的 Hello World ! ";
	}

	@RequestMapping("/test/{id}")
	public String Data(@PathVariable("id") String id)

	{

		return "取得ID為" + id;
	}

	@RequestMapping("/List")
	public List<String> ListData() {
		List<String> Data = new ArrayList<String>();
		Data.add("1");
		Data.add("2");
		Data.add("3");
		return Data;
	}

	@RequestMapping("/oop")
	public int oop() {
		TestOOp Data = new TestOOp();

		return 0;
	}

//	@PostMapping("/AccountCheck")
	@CrossOrigin()
	@RequestMapping("/AccountCheck/{EmployeeID}")
	public String AccountCheck(@PathVariable("EmployeeID") String EmployeeID) {

		DrugForm Ac = new DrugForm();
		String ReCall = Ac.AccountCheck(EmployeeID);

		return ReCall;
	}

//	@PostMapping("/AccountCheck")
//	@CrossOrigin()
//	@PostMapping("/PostAccountCheck")
//	public Map<String,String> PostAccountCheck()
//	{
//		
//		 HashMap<String, String> map = new HashMap<>();
//		    map.put("key", "value");
//		    map.put("foo", "bar");
//		    map.put("aa", "bb");
//		    return map;
//	}

	@CrossOrigin()
	@PostMapping("/PostAccountCheck")
	public String PostAccountCheck(@RequestBody Map<String, String> EmployeeID) {
		DrugForm a = new DrugForm();
		String EID = EmployeeID.get("EmployeeID");
		String Recall = a.AccountCheck(EID);
		return Recall;
	}

	@CrossOrigin()
	@PostMapping("/PostAccountData")
	public String PostAddAcount(@RequestBody Map<String, String> employeeAc) {
		DrugForm a = new DrugForm();
		String InEmpID = employeeAc.get("EmployeeID");
		String InEnpoyee = employeeAc.get("Employee");
		String InJobTitle = employeeAc.get("JobTitle");
		String InResDate = employeeAc.get("ResDate");
		String InResTexarea = employeeAc.get("ResTexarea");
		EmployeeAc b = new EmployeeAc(InEmpID, InEnpoyee, InJobTitle, InResTexarea, InResDate);
		String x = a.AddEmp(b);
		System.out.println(employeeAc);
		System.out.println(InEmpID);
		return x;
//		String Rc=employeeAc.get("EmployeeID")+employeeAc.get("Employee")+employeeAc.get("JobTitle")+employeeAc.get("ResDate")+employeeAc.get("ResTexarea");
//		System.out.println("員工編號:"+InEmpID+"\n員工:"+ InEnpoyee+"\n職稱:"+InJobTitle+"\n日期:"+InResDate+"\n備註:"+ InResTexarea);
	}

	@CrossOrigin()
	@PostMapping("/SelectEmpAccount")
	public Map<String, String> SelectEmpAccount(@RequestBody Map<String, String> SelectEmpAccount) {
//		EmpSession.setAttribute("EmpSession", SelectEmpAccount.get("EmpID"));
		DrugForm a = new DrugForm();
		HashMap<String, String> EmpRe = new HashMap<>();
		EmpRe = (HashMap<String, String>) a.QueryOne(SelectEmpAccount.get("EmpID"));
		return EmpRe;

	}

	@CrossOrigin()
	@PostMapping("/DrugFormData")
	public Map<String, Object> InsertDrugData(@RequestBody Map<String, Object> JsonDrugEventData,
			Object JsonDrugEventRession, Object JsonDrugEventResult, Object JsonDrugEventMayRession,
			Object JsonDrugDetail, Object JsonDrugEventDeal) {
		DrugForm DB = new DrugForm();
		Random Math = new Random();
		libraryFunction ChangArray = new libraryFunction();
		int number;
		int random;
		int number2;
		int number3;
		int number4;
		int random2;

		// --------------------關聯key
		int RandomMath;
		String PharForKey; // 已給藥事件關聯key
		String PaNumberKey; // 病患關聯事件表key
		String NursingForErrorKey;// 給錯藥物事件關聯key
		String ErrorDrugKey;// 給錯的藥物名稱關聯key

		// --------------------事件病歷資歷
		String PaName;// 患者姓名
		String PaGender;// 患者性別
		int PaNumber;// 患者病歷號
		String PaClass;// 患者事件類別
		int PaAge;// 患者年齡
		String PaDia;// 診斷
		String PaFiD;// 患者發現異常日
		String PaStart;// 患者發現開始日
		String PaEnd;// 患者異常結束日

		// -------------------已給藥事件對病患影響
		String EvenForPa; // 事件對病患影響
		String PaForEven;// 病患對事件影響

		// -------------------給藥錯誤事件
		String ErrorEvent; // 錯誤事件有哪些

		// -------------------錯誤事件總表
		// -----------------事件發生原因
//		String AboutOderEvent;// 醫囑相關內容
//		String PreScript;// 處發籤交付內容
//		String DeliveryProcessEvent; // 傳送過程內容
//		String NursingReEvent; // 護理相關內容
//		String PharMacyEvent; // 藥局相關選項
		String OtherEvent; // 其他內容相關補充
		// -----------------事件結果
//		String PharNonFor; // 藥局未給藥
//		String NursionNonFor; // 護理師未給藥

//		String PaForEvent;
//		String EventForPa;
		String PharFor = "";// 對應PharForKey
		// --------------------事件可能發生原因
//		String WorkStatusProcess;// 工作狀態流程因素
//		String DrugInfoStatusProcess;// 藥品/資訊系統因素
//		String EnvironmentStatusProcess;// 環境相關因素
//		String PhysiologicalStatusProcess;// 人員因素相關
//		String PersonStatusProcess;// 病人生理狀態
//		String CommunicateStatusProcess;// 溝通因素相關
		String OtherStatusProcess;// 其他因素
		// ---------------------事件處理與改善
//		String ProcessMethod;// 處理方式
		String Suggest;// 建議
		// ---------------------錯誤藥品名稱
		String DrugName; // 錯誤藥物名稱
		String DrugDose; // 藥物劑量
		String DrugDosage;// 藥物劑型
		String DrugRouter;// 藥物途徑
		String DrugFrequency;// 藥物頻率
		String DrugNumber; // 藥物數量
		String FalseDrug;// 判斷有無勾選
		String ErrorName;// 對應ErrorDrugKey

		// ---------------------紀錄
//		String EmployeeID=(String) EmpSession.getAttribute("EmpSession"); // 輸入員工
		String EmployeeName;// 員工姓名
		String InsertDate;// 輸入日期

		String RanStr = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";

		Map<String, Object> DruDat = new HashMap();

		Object DrugEventData = JsonDrugEventData.get("JsonDrugEventData"); // JSON轉為object
		Object DrugEventRession = JsonDrugEventData.get("JsonDrugEventRession"); // JSON轉為object
		Object DrugEventResult = JsonDrugEventData.get("JsonDrugEventResult"); // JSON轉為object
		Object EventMayRession = JsonDrugEventData.get("JsonDrugEventMayRession"); // JSON轉為object
		Object DrugDetail = JsonDrugEventData.get("JsonDrugDetail"); // JSON轉為object
		Object DrugEventDeal = JsonDrugEventData.get("JsonDrugEventDeal"); // JSON轉為object

		JSONObject JsonData1 = JSONObject.fromObject(DrugEventData); // object轉為另類Json
		JSONObject JsonData2 = JSONObject.fromObject(DrugEventRession); // object轉為另類Json
		JSONObject JsonData3 = JSONObject.fromObject(DrugEventResult); // object轉為另類Json
		JSONObject JsonData4 = JSONObject.fromObject(EventMayRession); // object轉為另類Json
		JSONObject JsonData5 = JSONObject.fromObject(DrugDetail); // object轉為另類Json
		JSONObject JsonData6 = JSONObject.fromObject(DrugEventDeal); // object轉為另類Json
//		String xx=(String )JsonData.get("DrugEventPainName");//Json解析
//		

		PaNumberKey = (String) JsonData1.get("DrugEventPainNumber");
		NursingForErrorKey = (String) JsonData1.get("DrugEventPainNumber");
		ErrorDrugKey = (String) JsonData1.get("DrugEventPainNumber");
		PharForKey = (String) JsonData1.get("DrugEventPainNumber");

		StringBuffer sf = new StringBuffer(PaNumberKey);
		StringBuffer sn = new StringBuffer(NursingForErrorKey);
		StringBuffer se = new StringBuffer(ErrorDrugKey);
		StringBuffer sp = new StringBuffer(PharForKey);

		for (int i = 0; i < 10; i++) {
			number = Math.nextInt(20);
			random = Math.nextInt(20);
			random2 = Math.nextInt(20);
			number2 = number + random;
			number3 = number + random2;
			number4 = number + random + random2;

			sf.append(RanStr.charAt(number));// 患者對應事件識別碼
			sn.append(RanStr.charAt(number2));// 給藥錯誤事件的識別碼
			se.append(RanStr.charAt(number3));// 給藥錯誤的名稱識別碼
			sp.append(RanStr.charAt(number4));// 已給藥事件識別碼
		}

		PaNumberKey = sf.toString();
		NursingForErrorKey = sn.toString();
		ErrorDrugKey = se.toString();
		PharForKey = sp.toString();

		PaName = (String) JsonData1.getString("DrugEventPainName");
		PaGender = (String) JsonData1.getString("DrugEventPainGender");
		PaNumber = (int) JsonData1.getInt("DrugEventPainNumber");
		PaClass = (String) JsonData1.getString("DrugEventPainClassification");
		PaAge = (int) JsonData1.getInt("DrugEventPainAge");
		PaDia = (String) JsonData1.getString("DrugEventPainDiagnosis");
		PaFiD = (String) JsonData1.getString("DrugEventPainDFind");
		PaStart = (String) JsonData1.getString("DrugEventPainStar");
		PaEnd = (String) JsonData1.getString("DrugEventPainEnd");
		DrugFormPa Npa = new DrugFormPa(PaName, PaGender, PaNumber, PaClass, PaAge, PaDia, PaFiD, PaStart, PaEnd,
				PaNumberKey);
		 DB.InsertPatable(Npa);
	

		// ---------------事件原因
		 JSONArray AboutOderEvent = JsonData2.getJSONArray("AboutOderEvent");
		 JSONArray PreScript =  JsonData2.getJSONArray("PrescriptionSignEvent");
		 JSONArray DeliveryProcessEvent =  JsonData2.getJSONArray("DeliveryProcessEvetn");
		 JSONArray NursingReEvent =  JsonData2.getJSONArray("NursingRelatedEvent");
		 JSONArray PharMacyEvent =  JsonData2.getJSONArray("PharmacyEvent");
		 OtherEvent = (String) JsonData2.getString("OtherEvent");
		
		String ReAboutOderEvent=ChangArray.ArrayChange(AboutOderEvent);
		String RePreScript=ChangArray.ArrayChange(PreScript);
		String ReDeliveryProcessEvent=ChangArray.ArrayChange(DeliveryProcessEvent);
		String ReNursingReEvent=ChangArray.ArrayChange(NursingReEvent);
		String RePharMacyEvent=ChangArray.ArrayChange(PharMacyEvent);
	
		
		
		DrugFormReassion DFR = new DrugFormReassion(ReAboutOderEvent, RePreScript, ReDeliveryProcessEvent, RePharMacyEvent,
				ReNursingReEvent, OtherEvent, NursingForErrorKey);
		System.out.println(AboutOderEvent);

		// -------------事件原因護理相關有無給藥
		JSONArray items = JsonData2.getJSONArray("NursingForErrorDrugEvent");
		
		
		if (items.size() > 0) // 代表有給藥錯誤情形
		{
			int i;
			i = items.size();
			String NusForErDrugStr = "";
			for (int x = 0; x < i; x++) {
				NusForErDrugStr = NusForErDrugStr + (x + 1) + ":" + items.get(x);
			}
			DrugFormnursingforerrortable DFNF = new DrugFormnursingforerrortable(NursingForErrorKey, NusForErDrugStr);
			DB.InsertNursingforerrortable(DFNF);

		

		} else {
			DrugFormnursingforerrortable DFNF = new DrugFormnursingforerrortable(NursingForErrorKey, "無");
			DB.InsertNursingforerrortable(DFNF);

//			DFR.setNursingForErrorKey("");
		}
        
		// ---------------事件結果

		JSONArray NursionNonFor = JsonData3.getJSONArray("DrugEventResultContext");
		JSONArray PaForEvent = JsonData3.getJSONArray("DrugEventResultContext2");
		JSONArray EventForPa = JsonData3.getJSONArray("DrugEventResultContext3");
		
		String xX="";
	

	    PharFor=PharForKey;
			
		
		System.out.println(xX);
		System.out.println(PharFor);
		String RecallNursionNonFor = ChangArray.ArrayChange(NursionNonFor);
		String RecallPaForEvent = ChangArray.ArrayChange(PaForEvent);
		String RecallEventForPa = ChangArray.ArrayChange(EventForPa);
		
		if(RecallPaForEvent==""||RecallPaForEvent==null)
		{
			RecallPaForEvent="無";
		}
		if(RecallEventForPa==""||RecallEventForPa==null)
		{
			RecallEventForPa="無";
		}
		
        if(PharFor!=""||PharFor!=null)
        {
        	PharFor ph=new PharFor(PharFor,RecallEventForPa,RecallPaForEvent);
        	DB.InsertPharfortable(ph);
        	
        }
		DrugFormResult DFRT = new DrugFormResult(RecallNursionNonFor, PharFor);
		// ---------------事件可能原因

		JSONArray WorkStatusProcess = JsonData4.getJSONArray("WorkingProcess");
		JSONArray DrugInfoStatusProcess = JsonData4.getJSONArray("DrugInformation");
		JSONArray EnvironmentStatusProcess = JsonData4.getJSONArray("Surroundings");
		JSONArray PhysiologicalStatusProcess = JsonData4.getJSONArray("PatientPhysiology");
		JSONArray PersonStatusProcess = JsonData4.getJSONArray("Personnel");
		JSONArray CommunicateStatusProcess = JsonData4.getJSONArray("Communicate");
		OtherStatusProcess = (String) JsonData4.getString("OtherRession");
		String ReWorkStatusProcess = ChangArray.ArrayChange(WorkStatusProcess);
		String ReDrugInfoStatusProcess = ChangArray.ArrayChange(DrugInfoStatusProcess);
		String ReEnvironmentStatusProcess = ChangArray.ArrayChange(EnvironmentStatusProcess);
		String RePhysiologicalStatusProcess = ChangArray.ArrayChange(PhysiologicalStatusProcess);
		String RePersonStatusProcess = ChangArray.ArrayChange(PersonStatusProcess);
		String ReCommunicateStatusProcess = ChangArray.ArrayChange(CommunicateStatusProcess);
		DrugFormMRession DFRM = new DrugFormMRession(ReWorkStatusProcess, ReDrugInfoStatusProcess, ReEnvironmentStatusProcess,
	    RePhysiologicalStatusProcess, RePersonStatusProcess, ReCommunicateStatusProcess, OtherStatusProcess);

		// ---------------處理及建議

		JSONArray ProcessMethod = JsonData6.getJSONArray("ProcessingMethod"); // 處理方式
		Suggest = (String) JsonData6.getString("Prevention");
		String ReProcessMethod=ChangArray.ArrayChange(ProcessMethod);
		EvenetProcessFix EPF = new EvenetProcessFix(ReProcessMethod, Suggest);

		
		// ---------------藥物明細
		DrugName = (String) JsonData5.getString("DrugName");
		DrugDose = (String) JsonData5.getString("DrugDose");
		DrugDosage = (String) JsonData5.getString("DrugDosage");
		DrugRouter = (String) JsonData5.getString("DrugRouter");
		DrugFrequency = (String) JsonData5.getString("DrugFrequency");
		DrugNumber = (String) JsonData5.getString("DrugNumber");
		FalseDrug = (String) JsonData5.getString("FalseDrug");
		ErrorName = ErrorDrugKey;
        int FalseDrugLen=FalseDrug.length();
		System.out.println("FalseDrug:"+FalseDrugLen);
        if(FalseDrugLen<1)
        {
        	ErrorDrug ED=new ErrorDrug(ErrorName,"無");
            DB.InsertErrordrugtable(ED);

    		System.out.println("有進入:");
     
        }else { ErrorDrug ED=new ErrorDrug(ErrorName,FalseDrug);
        DB.InsertErrordrugtable(ED);
    	System.out.println("沒進入:");}
        
        
        
		DrugDetail DDT = new DrugDetail(DrugName, DrugDose, DrugDosage, DrugRouter, DrugFrequency, DrugNumber,
				ErrorName);
		DB.AddDrugEvent(Npa,DFR,DFRT,DFRM,EPF,DDT);
      
		DruDat.put("123", JsonDrugEventData);

		DruDat.put("Sucess", "Sucess");

	

		System.out.println(PharForKey);
//		System.out.println(var);

//		System.out.println(JsonDrugEventMayRession);
//		System.out.println(JsonDrugDetail);
//		System.out.println(JsonDrugEventDeal);
		return DruDat;
	}
	@CrossOrigin()
	@PostMapping("/PostMainData")
	public ArrayList<Object> MainData()
	{
		DrugForm DB=new DrugForm();
	 return DB.SelectDrugAll();
	}
	
	@CrossOrigin()
	@PostMapping("/PostDelData")
    public HashMap<String, String> DeleteData(@RequestBody Map<String, String> PostID ) 
    {
    	DrugForm DB=new DrugForm();
    	int PaID= Integer.parseInt(PostID.get("PaID"));
    	HashMap<String,String>DeleAlert=new HashMap();
    	String Alert=DB.Delete(PostID.get("PaID"));
    	if(Alert=="此患者刪除成功")
    	{
    		DeleAlert.put("Sucess", Alert);
    	}else
    	{
    		DeleAlert.put("Fail", Alert);
    	}
    	
    	System.out.println(Alert);
    
    	return DeleAlert;
    }
	
	@CrossOrigin()
	@PostMapping("/DetailMainData")
    public HashMap<String, String> DetailMainData(@RequestBody Map<String, String> DetailIdS ) 
    {
		int SelectId=Integer.parseInt(DetailIdS.get("DetailId"));
		DrugForm DB=new DrugForm();
		System.out.println(SelectId);
	
		return DB.DetailMain(SelectId);
		
    }
    
	
	@PostMapping("/post")
	public String PostData(@RequestParam("test") String test) {
		return test;
	}
}
