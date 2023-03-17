package SensoryModel;

import DrugSQL.SQLStringSetting;
import net.sf.json.JSONObject;

public class SensoryLibra {
	public static void TransFun(JSONObject data) {
		Object TranContextKey=data.getJSONObject("SensryPOST").get("ContextKey");
		Object TranContextTitlet=data.getJSONObject("SensryPOST").get("ContextTitle");
		Object TranContext=data.getJSONObject("SensryPOST").get("Context");
		Object TranContextDate=data.getJSONObject("SensryPOST").get("ContextDate");
		Object TranContextEmp=data.getJSONObject("SensryPOST").get("ContextEmp");
		SQLStringSetting.PostData.setSensorKey(TranContextKey.toString());
		SQLStringSetting.PostData.setSensorTitle(TranContextTitlet.toString());
		SQLStringSetting.PostData.setSensorContext(TranContext.toString());
		SQLStringSetting.PostData.setSensorDate(TranContextDate.toString());
		SQLStringSetting.PostData.setSensorEmp(TranContextEmp.toString());
	}
}
