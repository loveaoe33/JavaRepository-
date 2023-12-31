package Personnel_Attend;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor	
@Component
public class Department {

	int id;
	String Department_Key;
	String Department;
	String Child_Department_Key;
	String Child_Department;
	ArrayList<String> Department_List=new ArrayList<>();
	
}
