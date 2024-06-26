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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	private final Lock lock = new ReentrantLock();

	@CrossOrigin
	@GetMapping("Personnel/test")
	public String test()
			throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException {
		String userName = System.getProperty("user.name");
		return userName;

	}

	@CrossOrigin
	@PostMapping("Personnel/UpLoad")
	public ArrayList<T_Class> UpLoad(MultipartFile file, MultipartFile Qr, String Article_Id)
			throws IllegalStateException, NoSuchAlgorithmException, ClassNotFoundException, IOException, SQLException {

		try {
			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			return PersonSQL.upload_file(file, Qr, Article_Id);

		} finally {

			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Employee_Login") // 完成
	public ArrayList<T_Class> Employee_Login(@RequestBody JSONObject Login_Post)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
//			System.out.print(Login_Post);

			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			Object Account = Login_Post.getJSONObject("Login_Post").get("Account");
			Object Password = Login_Post.getJSONObject("Login_Post").get("Password");

			return PersonSQL.Employee_Login((Account).toString(), (Password).toString());// 輸入帳號密碼

		} finally {

			lock.unlock();
		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Insert_Employee") // 完成
	public ArrayList<T_Class> Insert_Employee(@RequestBody JSONObject Employee_Post)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
//			@RequestBody Map<String,Integer> EmployeePOST

			Object Name = Employee_Post.getJSONObject("Employee_Post").get("Name");
			Object Account = Employee_Post.getJSONObject("Employee_Post").get("Account");
			Object Password = Employee_Post.getJSONObject("Employee_Post").get("Password");
			Object AccountLevel = Employee_Post.getJSONObject("Employee_Post").get("AccountLevel");
			Object Department = Employee_Post.getJSONObject("Employee_Post").get("Department");
			Object CreateDate = Employee_Post.getJSONObject("Employee_Post").get("CreateDate");

			SQLStringSetting.Personnel_Employee.setName(Name.toString()).setAccountLevel(AccountLevel.toString())
					.setArticleClass(Account + "_" + "Chunejen").setAccount(Account.toString())
					.setPassword(Password.toString()).setDepartment(Department.toString())
					.setCreateDate(CreateDate.toString());
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			return PersonSQL.Insert_Employee();

		} finally {

			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/One_Article") // 完成
	public ArrayList<T_Class> One_Article(@RequestBody JSONObject Article_Post)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		try {

			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();

			String ArticleId = (Article_Post.get("Article_ID")).toString();
			String[] Split_String = ArticleId.split(",");
			ArticleId = Split_String[0];
			String Employee_Aaccount = Split_String[1];
			String Employee_Name = Split_String[2];
			String FormatString = String.format("%s_%s", Employee_Aaccount, Employee_Name);
			PersonSQL.Update_Vilew(Integer.parseInt(ArticleId), FormatString); // 給文章編號與員工姓名
			return PersonSQL.One_Article(Integer.parseInt(ArticleId));

		} finally {

			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Insert_Article") // 完成
	public ArrayList<T_Class> Insert_Article(@RequestBody JSONObject Article_Post)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			Object EmpClass = Article_Post.getJSONObject("Article_Post").get("Article_Session_Account");
			Object ArticleClass = Article_Post.getJSONObject("Article_Post").get("ArticleClass");
			Object ArticleTitle = Article_Post.getJSONObject("Article_Post").get("ArticleTitle");
			Object ArticleContext = Article_Post.getJSONObject("Article_Post").get("ArticleContext");
			Object ArticleEmpl = Article_Post.getJSONObject("Article_Post").get("Article_Session_Name");
			Object ArticleUrl = Article_Post.getJSONObject("Article_Post").get("ArticleUrl");
			Object ArticleLv = Article_Post.getJSONObject("Article_Post").get("ArticleLv");
			Object ArticleCreate = Article_Post.getJSONObject("Article_Post").get("ArticleCreate");
			Object ArticleLock = Article_Post.getJSONObject("Article_Post").get("ArticleLock");

			SQLStringSetting.Personnel_Article.setId(0).setEmpClass(EmpClass.toString() + "_" + "Chunejen")
					.setArticleClass(ArticleClass.toString()).setArticleTitle(ArticleTitle.toString())
					.setArticleContext(ArticleContext.toString()).setArticleEmpl(ArticleEmpl.toString())
					.setArticleUrl(ArticleUrl.toString()).setArticleLv(ArticleLv.toString())
					.setArticleCreate(ArticleCreate.toString()).setArticleLock(ArticleLock.toString());

			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();

			return PersonSQL.Insert_Article();

		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Quick_Search") // 完成
	public ArrayList<String> Quick_Search(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			Object Object_String = EmployeeObject.get("EmployeeObject");
			String[] Split_String = ((String) Object_String).split(",");
			if (Split_String.length < 2) {
				return null;
			}
			String Employee_Id = Split_String[0];
			String Quick_Search = Split_String[1];
			return PersonSQL.Quick_Search(Integer.parseInt(Employee_Id), Quick_Search).get(0).Return_List(); // 員工的ID找權限與快搜字串

		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Print_Article_Class") // 完成
	public ArrayList<String> Print_Article_Class(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			Object Object_String = EmployeeObject.get("EmployeeObject");
			String[] Split_String = ((String) Object_String).split(",");
			String Employee_Id = Split_String[0];
			String Employee_Department = Split_String[1];
			String Employee_Class = Split_String[2];

			return PersonSQL.Print_Article_Class(Integer.parseInt(Employee_Id), Employee_Class, Employee_Department)
					.get(0).Return_List(); // 給id，類別，部門

		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Print_Article") // 完成
	public ArrayList<String> Print_Article(@RequestBody JSONObject EmployeeObject)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();

			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			Object Object_String = EmployeeObject.get("EmployeeObject");
			String[] Split_String = ((String) Object_String).split(",");
			String Employee_Id = Split_String[0];
			String Employee_Department = Split_String[1];

			return PersonSQL.Print_Article_(Integer.parseInt(Employee_Id), Employee_Department).get(0).Return_List(); // 給員工id，部門

		} finally {

			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Update_Vilew") // 完成
	public ArrayList<T_Class> Update_Vilew(String Article_Id, String Employee_Aaccount, String Employee_Name)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			String FormatString = String.format("%s_%s", Employee_Aaccount, Employee_Name);
			return PersonSQL.Update_Vilew(Integer.parseInt(Article_Id), FormatString); // 給文章編號與員工姓名

		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin
	@PostMapping("Personnel/Delete_Article") // 完成
	public ArrayList<T_Class> Delete_Article(@RequestBody JSONObject ArtilceObject)
			throws ClassNotFoundException, SQLException {

		try {
			lock.lock();
			PersonnelDAO PersonSQL = PersonnelDAO.getInstance_SingleSQL();
			Object Object_String = ArtilceObject.get("Article_ID");
			String[] Split_String = ((String) Object_String).split(",");
			SQLStringSetting.Pass_Code = Split_String[1];
			System.out.println(Split_String[1]);

			return PersonSQL.Delete_Article(Integer.parseInt(Split_String[0])); // 給文章編號

		} finally {
			lock.unlock();

		}

	}

	@CrossOrigin()
	@GetMapping("Personnel/Code/{PassCode}")
	public Map<String, String> CheckCode(@PathVariable String PassCode) {

		try {
			lock.lock();
			String CheckCode = "A078";
			String Check = PassCode.equals(CheckCode) ? "OK" : "NOK";
			Map<String, String> ReTuCheck = new HashMap<>();
			ReTuCheck.put("ReTuCheck", Check);
			return ReTuCheck;

		} finally {
			lock.unlock();

		}

	}

}