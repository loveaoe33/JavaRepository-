package com.example.demo;
import DrugSQL.SQLStringSetting.CaseSQL;
import Personnel.ArticleModel;
import Personnel.EmployeeModel;
import Personnel.PersonnelDAO;
import SensoryModel.FileTrans;
import SensoryModel.FileTrans.PathName;
import SensoryModel.Sensory;
import SensoryModel.SensoryLibra;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import DesignPattern.CompositePattern;
import DesignPattern.IteratorPattern.Employee;
import DrugSQL.AbstractSQL;
import DrugSQL.SQLStringSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Personnel.T_Class;

@RestController
public class PersonnelController {

	
	
	
	@CrossOrigin
	@PostMapping("Personnel/UpLoad")
	public ArrayList<T_Class> UpLoad(MultipartFile file, MultipartFile Qr, String ArticleId)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		
		return null;
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Employee_Login")
	public ArrayList<T_Class> Employee_Login(@RequestBody JSONObject Update_Vilew_POST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	}
	
		
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Employee")
	public ArrayList<T_Class> Insert_Employee(@RequestBody JSONObject EmployeePOST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Article")
	public ArrayList<T_Class> Insert_Article(@RequestBody JSONObject ArticlePOST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Quick_Search")
	public ArrayList<T_Class> Quick_Search(@RequestBody JSONObject Quick_text_POST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article_Class")
	public ArrayList<T_Class> Print_Article_Class(@RequestBody JSONObject Print_Article_Class_POST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article")
	public ArrayList<T_Class> Print_Article(@RequestBody JSONObject Print_Article_POST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Update_Vilew")
	public ArrayList<T_Class> Update_Vilew(@RequestBody JSONObject Update_Vilew_POST)  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return null;
	
	}
	
	

	
}