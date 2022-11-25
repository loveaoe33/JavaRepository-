package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class IteratorPattern {
	public interface Iterator {
		public Object First();

		public Object Next();

		public boolean IsDone();

		public Object CurrenItem();
	}

	public interface Aggregate {
		public Iterator CreateIterator();
	}

	public class Employee {
		String name;
		String title;

		public Employee(String name, String title) {
			this.name = name;
			this.title = title;
		}
	}

	public class CompanyA implements Aggregate {

		List<Employee> employees = new ArrayList<>();

		public CompanyA() {
			employees.add(new Employee("小萱", "業務"));
			employees.add(new Employee("小尉", "工程師"));
			employees.add(new Employee("小陞", "程序猿"));
			employees.add(new Employee("阿偉", "專案管理"));
			employees.add(new Employee("小詩", "吉祥物"));
			employees.add(new Employee("阿農", "分析兼司機"));
		}

		@Override
		public Iterator CreateIterator() {
			// TODO Auto-generated method stub
			return new CompanaAIterator(this);
		}


	}
	
	public class CompanaAIterator implements Iterator {
		private CompanyA companyA;
		private int current = 0;

		public CompanaAIterator(CompanyA companyA) {

			this.companyA = companyA;
		}

		@Override
		public Object First() {
			// TODO Auto-generated method stub
			return companyA.employees.get(current);
		}

		@Override
		public Object Next() {
			// TODO Auto-generated method stub
			Object ret = null;
			 current++;
			if (current < companyA.employees.size()) {
				ret = companyA.employees.get(current);
			}
			return ret;
		}

		@Override
		public boolean IsDone() {
			// TODO Auto-generated method stub
			return current >= companyA.employees.size() ? true : false;
		}

		@Override
		public Object CurrenItem() {
			// TODO Auto-generated method stub
			return companyA.employees.get(current);
		}

	}
	
	public void Call() {
		CompanyA co=new CompanyA();
		Iterator it=co.CreateIterator();
		 while(!it.IsDone()){
	            System.out.println( ((Employee)it.CurrenItem()).name + " : " +  ((Employee)it.CurrenItem()).title + " 發薪水囉～");
	            it.Next();
	        }

	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IteratorPattern it = new IteratorPattern();
   it.Call();
	}

}
