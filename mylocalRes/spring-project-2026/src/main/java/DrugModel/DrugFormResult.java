package DrugModel;

public class DrugFormResult {

private String PharNonFor;
public DrugFormResult( String nursionNonFor, String pharForKey) {
	NursionNonFor = nursionNonFor;
	PharForKey = pharForKey;

}
private String NursionNonFor;
private String PharForKey;
private String EvenFonPa;
private String PaForEvent;

public String getPharNonFor() {
	return PharNonFor;
}
public void setPharNonFor(String pharNonFor) {
	PharNonFor = pharNonFor;
}
public String getNursionNonFor() {
	return NursionNonFor;
}
public void setNursionNonFor(String nursionNonFor) {
	NursionNonFor = nursionNonFor;
}
public String getPharForKey() {
	return PharForKey;
}
public void setPharForKey(String pharForKey) {
	PharForKey = pharForKey;
}
public String getEvenFonPa() {
	return EvenFonPa;
}
public void setEvenFonPa(String evenFonPa) {
	EvenFonPa = evenFonPa;
}
public String getPaForEvent() {
	return PaForEvent;
}
public void setPaForEvent(String paForEvent) {
	PaForEvent = paForEvent;
}

}
