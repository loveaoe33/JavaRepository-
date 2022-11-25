package DesignPattern;

import java.util.ArrayList;
import java.util.List;

import DesignPattern.ObserverPattern.IObserver;
import DesignPattern.ObserverPattern.IObserverable;
import DesignPattern.ObserverPattern.PodcastA;
import DesignPattern.ObserverPattern.Student;

public class ObserverPattern {

	public interface IObserverable {
	    public void add(IObserver observer);
	    public void remove(IObserver observer);
	    public void notifyObservers();
	}

	public interface IObserver {
	    public void update();
	}
	
	
	public class PodcastA implements IObserverable {

	    List<IObserver> list = new ArrayList<>();

	    //節目名稱
	    String name = "英文廣播";

	    @Override
	    public void add(IObserver observer) {
	        list.add(observer);
	    }

	    @Override
	    public void remove(IObserver observer) {
	        list.remove(observer);
	    }

	    public String getName() {
	        return this.name;
	    }

	    @Override
	    public void notifyObservers() {

	        for (IObserver o: list) {
	            o.update();
	        }
	    }

	}
	
	
	public class PodcastB implements IObserverable
	{
		List<IObserver> list=new ArrayList<>();
        String name="國文廣播";
		   public void add(IObserver observer) {
		        list.add(observer);
		    }

		    @Override
		    public void remove(IObserver observer) {
		        list.remove(observer);
		    }

		    public String getName() {
		        return this.name;
		    }

		    @Override
		    public void notifyObservers() {

		        for (IObserver o: list) {
		            o.update();
		        }
		    }
		
	}
	
	
	public class Student implements IObserver {

	    IObserverable observerable;

	    public Student(IObserverable observerable){
	        this.observerable = observerable;
	    }

	    @Override
	    public void update() {
	        System.out.println("聽了" + ((PodcastA) observerable).getName());
	    }

	}
	
	
    public void test(){
        IObserverable podcast = new PodcastA();
     
        IObserver student = new Student(podcast);
        podcast.add(student);

        //預設節目是英文廣播
        podcast.notifyObservers();

        //節目變為今年流行歌
        ((PodcastA) podcast).name = "今年流行歌";

        podcast.notifyObservers();
    }
    
    


	    
	    public static void main(String[] args) 
	    {
	    	
	    	ObserverPattern test1=new ObserverPattern();
	    	test1.test();
	

	    	
	    }
}
