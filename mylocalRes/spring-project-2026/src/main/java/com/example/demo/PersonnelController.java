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
	@GetMapping("Personnel/test")
	public String test() throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException  {
		return "123";
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/UpLoad")
	public ArrayList<T_Class> UpLoad(MultipartFile file, MultipartFile Qr, String ArticleId) throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		return PersonSQL.upload_file(file, Qr, ArticleId);
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Employee_Login")  //完成
	public ArrayList<T_Class> Employee_Login(String Account,String Password) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		
		return PersonSQL.Employee_Login(Account, Password);//輸入帳號密碼
	}
	
		
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Employee")  //完成
	public ArrayList<T_Class> Insert_Employee(String Name,String Account,String Password,String AccountLevel, String Department,String CreateDate ) throws ClassNotFoundException, SQLException  {
//		@RequestBody Map<String,Integer> EmployeePOST
		SQLStringSetting.Personnel_Employee.setName(Name).setAccountLevel(AccountLevel).setArticleClass(Account+"_"+"Chunejen").setAccount(Account).setPassword(Password).setDepartment(Department).setCreateDate(CreateDate);
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		return PersonSQL.Insert_Employee();
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Insert_Article") //完成
	public ArrayList<T_Class> Insert_Article(String EmpClass,String ArticleClass,String ArticleTitle,String ArticleContext,String ArticleEmpl,String ArticleUrl,String ArticleFileUrl,String ArticleView,String ArticleLv,String ArticleCreate,String ArticleLock) throws ClassNotFoundException, SQLException  {
		SQLStringSetting.Personnel_Article.setId(0).setEmpClass(EmpClass)
		.setArticleClass(ArticleClass).setArticleTitle(ArticleTitle)
		.setArticleContext(ArticleContext).setArticleEmpl(ArticleEmpl)
		.setArticleUrl(ArticleUrl).setArticleFileUrl("")
		.setArticleView("").setArticleLv(ArticleLv).setArticleCreate(ArticleCreate).setArticleLock(ArticleLock);
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Insert_Article();
	
	}
	
	@CrossOrigin
	@PostMapping("Personnel/Quick_Search")  //完成
	public ArrayList<T_Class> Quick_Search(String Account_Id,String Quick_Searck) throws ClassNotFoundException, SQLException  {
		
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Quick_Search(Integer.parseInt(Account_Id), Quick_Searck);  //員工的ID找權限與快搜字串
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article_Class")
	public ArrayList<T_Class> Print_Article_Class(@RequestBody JSONObject Print_Article_Class_POST) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Print_Article_Class(0, null, null);   //給id，類別，部門
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Print_Article")   //完成
	public ArrayList<T_Class> Print_Article(String Account_Id,String Department) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Print_Article_(Integer.parseInt(Account_Id), Department);  //給員工id，部門
	
	}
	
	
	@CrossOrigin
	@PostMapping("Personnel/Update_Vilew")   //完成
	public ArrayList<T_Class> Update_Vilew(String Article_Id,String Employee_Aaccount,String Employee_Name) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
        String FormatString=String.format("%s_%s",Employee_Aaccount,Employee_Name);
		return PersonSQL.Update_Vilew(Integer.parseInt(Article_Id), FormatString); //給文章編號與員工姓名  
	}
	
	@PostMapping("Personnel/Delete_Article")  //完成
	public ArrayList<T_Class> Delete_Article(String Article_ID,String PassCode) throws ClassNotFoundException, SQLException  {
		PersonnelDAO PersonSQL= PersonnelDAO.getInstance_SingleSQL();
		SQLStringSetting.Pass_Code=PassCode;
		return PersonSQL.Delete_Article(Integer.parseInt(Article_ID)); //給文章編號
	
	}
	
	

	
}