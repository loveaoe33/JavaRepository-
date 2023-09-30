package Personnel;

public class EmployeeModel extends T_Class {
    
	private int id;
	private String Account;
	private String Password;
	private String ArticleClass;
	private String AccountLevel;
	private String Department;
	private String CreateDate;

	public EmployeeModel() {
	};

	public void reSerConstruct() {
		super.Upload_Check = "";
		this.id=0;
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
}