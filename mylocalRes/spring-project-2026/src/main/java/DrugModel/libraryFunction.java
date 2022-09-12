package DrugModel;

import java.util.Calendar;

import net.sf.json.JSONArray;

public class libraryFunction {

	public String ArrayChange(JSONArray nursionNonFor)
	{
		int ArrayLength=nursionNonFor.size();
		String CallBackString="";
	
		for(int i=0;i<ArrayLength;i++)
		{
			CallBackString=CallBackString+ (i + 1) + ":" + nursionNonFor.get(i);
		}
		return CallBackString;
		
	}

	public static String NowDate() 
	{
		Calendar cal =Calendar.getInstance();
		int y=cal.get(Calendar.YEAR);
		int m=cal.get(Calendar.MONTH);
		int d=cal.get(Calendar.DATE);
		int h=cal.get(Calendar.HOUR_OF_DAY);
		int mi=cal.get(Calendar.MINUTE);
		
        String ND="民國"+y+"年"+m+"月"+d+"日"+h+"時";
        return ND;
	}
	public static void main(String[] args) 
	{

	}
	
}
