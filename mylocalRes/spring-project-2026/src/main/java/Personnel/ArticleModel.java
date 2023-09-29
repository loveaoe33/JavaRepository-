package Personnel;

public class ArticleModel extends T_Class{
	public ArticleModel() {};
	private String  EmpClass;
	private String  ArticleClass;
	private String  ArticleTitle;
	private String  ArticleContext;
	private String  ArticleEmpl;
	private String  ArticleUrl;
	private String  ArticleFileUrl;
	private String  ArticleView;
	private String  ArticleLv;
	private String  ArticleCreate;
	private String  ArticleLock;
	
	public void reSerConstruct() {
		super.Upload_Check="";
		this.EmpClass="";
		this.ArticleClass="";
		this.ArticleTitle="";
		this.ArticleContext="";
		this.ArticleEmpl="";
		this.ArticleUrl="";
		this.ArticleFileUrl="";
		this.ArticleView="";
		this.ArticleLv="";
		this.ArticleCreate="";
		this.ArticleLock="";
		
	}
	public String getEmpClass() {
		return EmpClass;
	}
	public ArticleModel setEmpClass(String empClass) {
		EmpClass = empClass;
		return this;
	}
	public String getArticleClass() {
		return ArticleClass;
	}
	public ArticleModel setArticleClass(String articleClass) {
		ArticleClass = articleClass;
		return this;

	}
	public String getArticleLock() {
		return ArticleLock;
	}
	public ArticleModel setArticleLock(String articleLock) {
		ArticleLock = articleLock;
		return this;

	}
	public String getArticleCreate() {
		return ArticleCreate;
	}
	public ArticleModel setArticleCreate(String articleCreate) {
		ArticleCreate = articleCreate;
		return this;

	}
	public String getArticleLv() {
		return ArticleLv;
	}
	public ArticleModel setArticleLv(String articleLv) {
		ArticleLv = articleLv;
		return this;

	}
	public String getArticleView() {
		return ArticleView;
	}
	public ArticleModel setArticleView(String articleView) {
		ArticleView = articleView;
		return this;

	}
	public String getArticleFileUrl() {
		return ArticleFileUrl;
	}
	public ArticleModel setArticleFileUrl(String articleFileUrl) {
		ArticleFileUrl = articleFileUrl;
		return this;

	}
	public String getArticleEmpl() {
		return ArticleEmpl;
	}
	public ArticleModel setArticleEmpl(String articleEmpl) {
		ArticleEmpl = articleEmpl;
		return this;

	}
	public String getArticleContext() {
		return ArticleContext;
	}
	public ArticleModel setArticleContext(String articleContext) {
		ArticleContext = articleContext;
		return this;

	}
	public String getArticleTitle() {
		return ArticleTitle;
	}
	public ArticleModel setArticleTitle(String articleTitle) {
		ArticleTitle = articleTitle;
		return this;

	}
	public String getArticleUrl() {
		return ArticleUrl;
	}
	public ArticleModel setArticleUrl(String articleUrl) {
		ArticleUrl = articleUrl;
		return this;

	}

	
}