
public class HandyConjugation extends Conjugation{
	protected static final int ID = 1; 
;	private Complex a, b, c, d;
	public HandyConjugation(Complex a, Complex b, Complex c, Complex d){
		word = "T";
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		matrix = new Matrix(a, b,
							c, d);
	}
	public HandyConjugation(Matrix t){
		word = "T";
		this.matrix = t;
		this.a = t.a;
		this.b = t.b;
		this.c = t.c;
		this.d = t.d;
	}
	public String getParamCSV(){
		return a.re() +","+ a.im() +","+ b.re() +","+ b.im() +","+ c.re() +","+ c.im() +","+ d.re() +","+ d.im();
	}
}
