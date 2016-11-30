
public class TranslateConjugation extends Conjugation{
	protected static int ID = 3;
	private double x, y;
	public TranslateConjugation(double x, double y){
		word = "A";
		this.x = x;
		this.y = y;
		matrix = new Matrix(new Complex(1, 0), new Complex(x, y),
				  			new Complex(0, 0), new Complex(1, 0));
	}
	public TranslateConjugation(Complex c){
		word = "A";
		matrix = new Matrix(new Complex(1, 0), c,
							new Complex(0, 0), new Complex(1, 0));
		this.x = c.re();
		this.y = c.im();
		
	}
	public String getParamCSV(){
		return x +","+ y;
	}
}
