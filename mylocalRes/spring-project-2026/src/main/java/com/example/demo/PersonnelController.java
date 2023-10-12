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
import java.util.List;
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
	public String test()
			throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException {
		return "123";

	}

	@CrossOrigin
	@PostMapping("Personnel/UpLoad")
	public ArrayList<T_Class> UpLoad(MultipartFile file, MultipartFile Qr, String ArticleId)
			throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		return PersonSQL.upload_file(file, Qr, ArticleId);

	}

	@CrossOrigin
	@PostMapping("Personnel/Employee_Login") // 完成
	public ArrayList<T_Class> Employee_Login(String Account, String Password)
			throws ClassNotFoundException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Employee_Login(Account, Password);// 輸入帳號密碼
	}

	@CrossOrigin
	@PostMapping("Personnel/Insert_Employee") // 完成
	public ArrayList<T_Class> Insert_Employee(@RequestBody JSONObject Employee_Post)
			throws ClassNotFoundException, SQLException {
//		@RequestBody Map<String,Integer> EmployeePOST

		Object Name = Employee_Post.getJSONObject("Employee_Post").get("Name");
		Object Account = Employee_Post.getJSONObject("Employee_Post").get("Account");
		Object Password = Employee_Post.getJSONObject("Employee_Post").get("Password");
		Object AccountLevel = Employee_Post.getJSONObject("Employee_Post").get("AccountLevel");
		Object Department = Employee_Post.getJSONObject("Employee_Post").get("Department");
		Object CreateDate = Employee_Post.getJSONObject("Employee_Post").get("CreateDate");

		SQLStringSetting.Personnel_Employee.setName(Name.toString()).setAccountLevel(AccountLevel.toString())
				.setArticleClass(Account + "_" + "Chunejen").setAccount(Account.toString()).setPassword(Password.toString())
				.setDepartment(Department.toString()).setCreateDate(CreateDate.toString());
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		return PersonSQL.Insert_Employee();

	}

	@CrossOrigin
	@PostMapping("Personnel/One_Article") // 完成
	public ArrayList<T_Class> One_Article(@RequestBody JSONObject  Article_Post) throws NumberFormatException, ClassNotFoundException, SQLException{
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		String ArticleId=(Article_Post.get("Article_ID")).toString();
		return PersonSQL.One_Article(Integer.parseInt(ArticleId));
	}
	@CrossOrigin
	@PostMapping("Personnel/Insert_Article") // 完成  
	public ArrayList<T_Class> Insert_Article(@RequestBody JSONObject  Article_Post) throws ClassNotFoundException, SQLException {
		Object EmpClass = Article_Post.getJSONObject("Article_Post").get("Article_Session_Account");
		Object ArticleClass = Article_Post.getJSONObject("Article_Post").get("ArticleClass");
		Object ArticleTitle = Article_Post.getJSONObject("Article_Post").get("ArticleTitle");
		Object ArticleContext = Article_Post.getJSONObject("Article_Post").get("ArticleContext");
		Object ArticleEmpl = Article_Post.getJSONObject("Article_Post").get("Article_Session_Name");
		Object ArticleUrl = Article_Post.getJSONObject("Article_Post").get("ArticleUrl");
		Object ArticleLv = Article_Post.getJSONObject("Article_Post").get("ArticleLv");
		Object ArticleCreate =Article_Post.getJSONObject("Article_Post").get("ArticleCreate");
		Object ArticleLock = Article_Post.getJSONObject("Article_Post").get("ArticleLock");

		
		
		SQLStringSetting.Personnel_Article.setId(0).setEmpClass(EmpClass.toString()+"_" + "Chunejen").setArticleClass(ArticleClass.toString())
				.setArticleTitle(ArticleTitle.toString()).setArticleContext(ArticleContext.toString()).setArticleEmpl(ArticleEmpl.toString())
				.setArticleUrl(ArticleUrl.toString()).setArticleLv(ArticleLv.toString())
				.setArticleCreate(ArticleCreate.toString()).setArticleLock(ArticleLock.toString());
		
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();

		return PersonSQL.Insert_Article();

	}

	@CrossOrigin
	@PostMapping("Personnel/Quick_Search") // 完成
	public ArrayList<T_Class> Quick_Search(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {

		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		Object Object_String = EmployeeObject.get("EmployeeObject");
		String[] Split_String = ((String) Object_String).split(",");
		if(Split_String.length<2) {
			return null;
		}
		String Employee_Id = Split_String[0];
		String Quick_Search = Split_String[1];
		return PersonSQL.Quick_Search(Integer.parseInt(Employee_Id), Quick_Search); // 員工的ID找權限與快搜字串

	}

	@CrossOrigin
	@PostMapping("Personnel/Print_Article_Class") // 完成
	public ArrayList<T_Class> Print_Article_Class(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		Object Object_String = EmployeeObject.get("EmployeeObject");
		String[] Split_String = ((String) Object_String).split(",");
		String Employee_Id = Split_String[0];
		String Employee_Department = Split_String[1];
		String Employee_Class = Split_String[2];


		return PersonSQL.Print_Article_Class(Integer.parseInt(Employee_Id), Employee_Class, Employee_Department); // 給id，類別，部門

	}

	@CrossOrigin
	@PostMapping("Personnel/Print_Article") // 完成
	public ArrayList<T_Class> Print_Article(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		Object Object_String = EmployeeObject.get("EmployeeObject");
		String[] Split_String = ((String) Object_String).split(",");
		String Employee_Id = Split_String[0];
		String Employee_Department = Split_String[1];
		return PersonSQL.Print_Article_(Integer.parseInt(Employee_Id), Employee_Department); // 給員工id，部門

	}

	@CrossOrigin
	@PostMapping("Personnel/Update_Vilew") // 完成
	public ArrayList<T_Class> Update_Vilew(String Article_Id, String Employee_Aaccount, String Employee_Name)
			throws ClassNotFoundException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		String FormatString = String.format("%s_%s", Employee_Aaccount, Employee_Name);
		return PersonSQL.Update_Vilew(Integer.parseInt(Article_Id), FormatString); // 給文章編號與員工姓名
	}

	@PostMapping("Personnel/Delete_Article") // 完成
	public ArrayList<T_Class> Delete_Article(String Article_ID, String PassCode)
			throws ClassNotFoundException, SQLException {
		PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
		SQLStringSetting.Pass_Code = PassCode;
		return PersonSQL.Delete_Article(Integer.parseInt(Article_ID)); // 給文章編號

	}

}