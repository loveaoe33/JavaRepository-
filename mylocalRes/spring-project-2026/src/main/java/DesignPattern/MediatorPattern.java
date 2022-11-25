package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class MediatorPattern {

	
	public interface Company{
		public void addMember(CoWorker coWorker);
		public void introduce(CoWorker coWorker);
		public void workHard();
		public void giveWork(CoWorker coWorker);
	}
	public abstract class CoWorker{
		String name;
		Boss boss;
		public CoWorker(String name){
			this.name=name;
		}
		
		public void CallValue() {
			boss.test();
		}
		public abstract void work();
	}
	
	public class Boss implements Company {
        List<CoWorker> coWorkers;
	
		public Boss() {
			coWorkers=new ArrayList<>();
		}		
		public void addMember(CoWorker coWorker) {
			// TODO Auto-generated method stub
			coWorkers.add(coWorker);
			
			coWorker.boss=this;
		}

		@Override
		public void introduce(CoWorker coWorker) {
			// TODO Auto-generated method stub
			System.out.println("Boss:跟大家介紹這位是" + coWorker.name);
		}

		@Override
		public void workHard() {
			// TODO Auto-generated method stub
			for(CoWorker c:coWorkers) {
				 System.out.println("Boss:" + c.name + "認真點做啊！");
				 c.CallValue();
			}
		}
        
		@Override
		public void giveWork(CoWorker coWorker) {
			// TODO Auto-generated method stub
			System.out.println("Boss:" + coWorker.name + "這個幫忙一下。");
		}
		
		public void test() {
			// TODO Auto-generated method stub
			System.out.println("Boss:test");
		}
		
	}
	
	
	public class PM extends CoWorker{

		public PM(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void work() {
			// TODO Auto-generated method stub
			 System.out.println(this.name + "做PM工作");
		}
		
		public void bossHelp(CoWorker coWorker) {
			this.boss.giveWork(coWorker);
		}
		
	}
	
	public class Programmer extends CoWorker{

		public Programmer(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void work() {
			// TODO Auto-generated method stub
			 System.out.println(this.name + "寫程式");
		}
		
	}
	
	public void Call() {
		 Boss boss = new Boss();

//       各個員工
       PM wei = new PM("小偉");
       Programmer bad = new Programmer("小惡");
       Programmer good = new Programmer("阿仁");

//       把員工加入Boss下面管理
       boss.addMember(wei);
       boss.addMember(bad);
       boss.addMember(good);

//       Boss介紹新來的小惡
       boss.introduce(bad);

//       阿仁自己工作
       good.work();

//       boss請大家專心工作
       boss.workHard();

//       小惡自己做不來
//       PM請老闆幫忙
       wei.bossHelp(bad);

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MediatorPattern Me=new MediatorPattern();
		Me.Call();
	}

}
