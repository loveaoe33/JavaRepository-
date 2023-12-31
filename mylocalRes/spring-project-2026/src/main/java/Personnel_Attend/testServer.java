package Personnel_Attend;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class testServer {
	
	String x="123";
    public String set() {
    	x="456";
    	return x;
    }
    
    public String get() {
    	
        return x;
    }
}
