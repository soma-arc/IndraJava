
public class Matrix {
	Complex a, b, c, d;
	Matrix(Complex _a, Complex _b, Complex _c, Complex _d){
		a = _a;
	    b = _b;
	    c = _c;
	    d = _d;
	}

	Matrix(double a, double b, double c, double d){
		this.a = new Complex(a, 0);
	    this.b = new Complex(b, 0);
	    this.c = new Complex(c, 0);
	    this.d = new Complex(d, 0);
	}

	public Matrix mult(Matrix n){
		return new Matrix(Complex.add(Complex.mult(a, n.a), Complex.mult(b, n.c)),
	                      Complex.add(Complex.mult(a, n.b), Complex.mult(b, n.d)),
	                      Complex.add(Complex.mult(c, n.a), Complex.mult(d, n.c)),
	                      Complex.add(Complex.mult(c, n.b), Complex.mult(d, n.d)));
	}

	public static Matrix mult(Matrix m, Matrix n){
		return new Matrix(Complex.add(Complex.mult(m.a, n.a), Complex.mult(m.b, n.c)),
						  Complex.add(Complex.mult(m.a, n.b), Complex.mult(m.b, n.d)),
						  Complex.add(Complex.mult(m.c, n.a), Complex.mult(m.d, n.c)),
	                      Complex.add(Complex.mult(m.c, n.b), Complex.mult(m.d, n.d)));
	}
	public Matrix mult(double coefficient){
		return new Matrix(a.mult(coefficient),
	                      b.mult(coefficient),
	                      c.mult(coefficient),
	                      d.mult(coefficient));
	}
	public Matrix mult(Complex coefficient){
		return new Matrix(a.mult(coefficient),
				b.mult(coefficient),
				c.mult(coefficient),
				d.mult(coefficient));
	}
	public String toString(){
		return "{"+ a.toString() +","+ b.toString() +"\n"+ c.toString() +","+ d.toString() +"}";
	}

	public Matrix inverse(){
		Complex one = new Complex(1.0, 0.0);
		return new Matrix(d, b.mult(-1.0), c.mult(-1.0), a).mult(one.div(a.mult(d).sub(b.mult(c))));
	}

	public Complex trace(){
		return a.add(d);
	}
	public Matrix conjugation(Matrix T){
		return T.mult(this).mult(T.inverse());
	}
}
