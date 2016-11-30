
public abstract class Conjugation {
	protected static final int ID = 0;
	protected String word = "";
	protected Matrix matrix;
	
	public String getWord(){
		return word;
	}
	public String getParamCSV(){
		return "";
	}
	public Matrix getMatrix(){
		return matrix;
	}
	public int getId(){
		return ID;
	}
}
