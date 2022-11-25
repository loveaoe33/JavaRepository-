package DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class CommandPattern {

public abstract class Command
{
	Light light;
	public Command(Light light) { this.light=light;}
	public abstract void execute();
	}
	
public class Light{
	
	public void turnOn() { System.out.println("打開燈");};
	public void tureOff() {System.out.println("關燈");};
	public void brighter() {System.out.println("調高亮度");};
	public void daker() {System.out.println("調低亮度");};
}


public class turnOn extends Command
{

	public turnOn(Light light) {
		super(light);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		light.turnOn();
		
	}
}
public class turnOff extends Command
{

	public turnOff(Light light) {
		super(light);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		light.tureOff();
		
	}
}
public class brighter extends Command
{

	public brighter(Light light) {
		super(light);
		light.brighter();
	}

	@Override
	public void execute() {
		light.daker();
		
	}
}
public class daker extends Command
{
	public daker(Light light) {
		super(light);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
	
	
	
	public class Invoker
	{
		private List<Command> commandList=new ArrayList<>();
		public void addCommand(Command command) {commandList.add(command);}
		public void execute() {for(Command command:commandList) {command.execute();} }
	}
	
	
	public  void  CallInvoker()
	{
		Light light=new Light();
		Command turnOn=new turnOn(light);
		Command turnOff=new turnOff(light);
		Command brighter=new brighter(light);
		Command daker=new daker(light);
		
		Invoker invoker=new Invoker();
		invoker.addCommand(turnOn);
		invoker.addCommand(turnOff);
		invoker.addCommand(brighter);
		invoker.addCommand(daker);
		invoker.execute();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommandPattern x=new CommandPattern();
		x.CallInvoker();
	}

}
