
public class MagnificationConjugation extends Conjugation{
	protected static final int ID = 5;
	private double expansion;
	public MagnificationConjugation(double expansion) {
		word = "C";
		this.expansion = expansion;
		matrix = new Matrix(new Complex(expansion, 0), new Complex(0, 0),
							new Complex(0, 0)	     , new Complex(1, 0));
	}
	public String getParamCSV(){
		return expansion +"";
	}
}
