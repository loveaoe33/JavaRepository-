package RSAExample;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class TxtProcess {
    public static void BuilderTxt(String content) {
    	try {
    		int Random=0;
    		Random=(int)(Math.random()*10);
    		FileWriter fw=new FileWriter("Builder"+Random+".txt");
    		fw.write(content);
    		fw.flush();
    		fw.close();
    		System.out.println("BuilderTxt Sucess");
    	}catch(Exception e) {
    		System.out.println("Something Error");
    	}
    	
    }
    
    public void ReadTxt(){
    	try {
			String content = "";
    		FileReader fw=new FileReader("Builder.txt");
    		BufferedReader br = new BufferedReader(fw);
    		while(br.ready()) {
    		    content = br.readLine();
    			System.out.println("Ready... read txt");
    			System.out.println("-------------");
    			System.out.println(content);
    			System.out.println("-------------");
    		}
    		BuilderTxt(content);
    	} catch(Exception e) {}

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TxtProcess t=new TxtProcess();
		t.ReadTxt();
	}

}
