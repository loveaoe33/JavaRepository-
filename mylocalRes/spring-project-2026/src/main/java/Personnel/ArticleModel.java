package Personnel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ArticleModel extends T_Class implements  Serializable  {
	private  ObjectMapper objectMapper = new ObjectMapper();
	public ArticleModel() {};
	
	private int id;
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
		this.id=0;
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
	public int getId() {
		return id;
	}
	public ArticleModel setId(int id) {
		this.id = id;
		return this;

	}
    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	@Override
	public ArrayList<String> Return_List() {
		// TODO Auto-generated method stub
		return Article_List;
	}
	
	@Override
    public void ReStruct() {
    	Article_List.clear();
    }


	@Override
	public void Set_List(String t_Class) {
		Article_List.add(t_Class)		;
	}
    
    
	
}