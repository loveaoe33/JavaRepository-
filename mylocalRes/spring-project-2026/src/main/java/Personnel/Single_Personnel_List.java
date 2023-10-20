package Personnel;

import java.util.ArrayList;
import java.util.Collection;
public class Single_Personnel_List  extends T_Class {
    public void Set_List(String t_Class){
    	Article_List.add(t_Class);      
    }
    
    public void ReStruct() {
    	Article_List.clear();
    }

	@Override
	public void reSerConstruct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> Return_List() {
		// TODO Auto-generated method stub
		return Article_List;
	}

//	public Collection<? extends T_Class> Return_List() {
//		// TODO Auto-generated method stub
//    	return Article_List;
//	}

}
