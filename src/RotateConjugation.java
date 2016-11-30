
public class RotateConjugation extends Conjugation{
	protected static final int ID = 2;
	private double degree;
	public RotateConjugation(double degree){
		word = "R";
		this.degree = degree;
		double radian = Math.toRadians(degree);
		matrix = new Matrix(new Complex(Math.cos(radian), Math.sin(radian)), new Complex(0, 0),
				  			new Complex(0, 0)           , new Complex(1, 0));
	}
	public String getParamCSV(){
		return degree + "";
	}
}
