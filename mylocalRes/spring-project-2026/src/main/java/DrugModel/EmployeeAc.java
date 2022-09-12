package DrugModel;

import org.springframework.stereotype.Component;

@Component
public class EmployeeAc {
	
	
	private int id;
	private String EmployeeID;
	private String  Employee;
	private String JobTitle;
	private String ResDate;
	private String ResTexarea;
	

	public EmployeeAc(String employeeID, String employee, String jobTitle, String resDate, String resTexarea) {
		EmployeeID = employeeID;
		Employee = employee;
		JobTitle = jobTitle;
		ResDate = resDate;
		ResTexarea = resTexarea;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployee() {
		return Employee;
	}
	public void setEmployee(String employee) {
		Employee = employee;
	}
	public String getJobTitle() {
		return JobTitle;
	}
	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}
	public String getResDate() {
		return ResDate;
	}
	public void setResDate(String resDate) {
		ResDate = resDate;
	}
	public String getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(String employeeID) {
		EmployeeID = employeeID;
	}
	public String getResTexarea() {
		return ResTexarea;
	}
	public void setResTexarea(String resTexarea) {
		ResTexarea = resTexarea;
	}
	

}
