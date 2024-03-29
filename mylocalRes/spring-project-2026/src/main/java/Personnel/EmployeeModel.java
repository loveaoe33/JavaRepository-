package Personnel;

import java.util.ArrayList;

public class EmployeeModel extends T_Class {
    
	private int id;
	private String Account;
	private String Password;
	private String ArticleClass;
	private String AccountLevel;
	private String Department;
	private String CreateDate;
	private String Name;

	public EmployeeModel() {
	};

	public void reSerConstruct() {
		super.Upload_Check = "";
		this.id=0;
		this.setName("");
		this.Account = "";
		this.Password = "";
		this.ArticleClass = "";
		this.AccountLevel = "";
		this.Department = "";
		this.CreateDate = "";
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public EmployeeModel setCreateDate(String createDate) {
		CreateDate = createDate;
		return this;
	}

	public String getDepartment() {
		return Department;
	}

	public EmployeeModel setDepartment(String department) {
		Department = department;
		return this;
	}

	public String getAccountLevel() {
		return AccountLevel;
	}

	public EmployeeModel setAccountLevel(String accountLevel) {
		AccountLevel = accountLevel;
		return this;
	}

	public String getArticleClass() {
		return ArticleClass;
	}

	public EmployeeModel setArticleClass(String articleClass) {
		ArticleClass = articleClass;
		return this;
	}

	public String getPassword() {
		return Password;
	}

	public EmployeeModel setPassword(String password) {
		Password = password;
		return this;
	}

	public String getAccount() {
		return Account;
	}

	public EmployeeModel setAccount(String account) {
		Account = account;
		return this;
	}

	public int getId() {
		return id;
	}

	public EmployeeModel setId(int id) {
		this.id = id;
		return this;

	}

	public String getName() {
		return Name;
	}

	public EmployeeModel setName(String name) {
		Name = name;
		return this;
	}

	@Override
	public ArrayList<String> Return_List() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Set_List(String t_Class) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ReStruct() {
		// TODO Auto-generated method stub
		
	}
}