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
	public ArrayList<T_Class> UpLoad(MultipartFile file, MultipartFile Qr, String ArticleId) throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		return PersonSQL.upload_file(file, Qr, ArticleId);
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Employee_Login")
	public ArrayList<T_Class> Employee_Login(@RequestBody JSONObject Update_Vilew_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		
		return PersonSQL.Employee_Login(null, null);//輸入帳號密碼
	}
	
		
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Employee")
	public ArrayList<T_Class> Insert_Employee(@RequestBody JSONObject EmployeePOST) throws ClassNotFoundException, SQLException  {
		SQLStringSetting.Personnel_Employee.setAccount(null).setAccountLevel(null).setArticleClass(null).setPassword(null).setDepartment(null).setCreateDate(null);
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Insert_Employee();
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Article")
	public ArrayList<T_Class> Insert_Article(@RequestBody JSONObject ArticlePOST) throws ClassNotFoundException, SQLException  {
		SQLStringSetting.Personnel_Article.setId(0).setEmpClass("")
		.setArticleClass("").setArticleTitle("")
		.setArticleContext("").setArticleEmpl("")
		.setArticleUrl("").setArticleFileUrl("")
		.setArticleTitle("").setArticleUrl("")
		.setArticleView("").setEmpClass("");
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Insert_Article();
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Quick_Search")
	public ArrayList<T_Class> Quick_Search(@RequestBody JSONObject Quick_text_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Quick_Search(0, null);  //員工的ID找權限與快搜字串
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article_Class")
	public ArrayList<T_Class> Print_Article_Class(@RequestBody JSONObject Print_Article_Class_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Print_Article_Class(0, null, null);   //給id，類別，部門
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article")
	public ArrayList<T_Class> Print_Article(@RequestBody JSONObject Print_Article_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Print_Article_(0, null);  //給id，部門
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Update_Vilew")
	public ArrayList<T_Class> Update_Vilew(@RequestBody JSONObject Update_Vilew_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Update_Vilew(0, null); //給文章編號與員工姓名  未處理
	
	}
	
	@PostMapping("Personnel/Delete_Article")
	public ArrayList<T_Class> Delete_Article(@RequestBody Map<String,Integer> SensoryID,@RequestBody Map<String,String> 
	Pass_Code) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Delete_Article(0);
	
	}
	
	

	
}