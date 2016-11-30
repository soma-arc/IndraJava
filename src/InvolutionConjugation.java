
public class InvolutionConjugation extends Conjugation{
	protected static final int ID = 4;
	public InvolutionConjugation(){
		word = "I";
		matrix = new Matrix(new Complex(0, 0), new Complex(1, 0),
		  			 		new Complex(1, 0), new Complex(0, 0));
	}
}
