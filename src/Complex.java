import java.math.BigDecimal;
import java.math.RoundingMode;


public class Complex {
	  private double re;
	  private double im;
	  public static final Complex INFINITY = new Complex( Double.POSITIVE_INFINITY, 0.0);

	  public Complex(double re, double im) {
	    this.re = re;
	    this.im = im;
	  }

	  public double re() {
	    return re;
	  }
	  public double im() {
	    return im;
	  }
	  
	  public void setRe(double re){
		  this.re = re;
	  }
	  
	  public void setIm(double im){
		  this.im = im;
	  }
	  
	  public Complex add(Complex c) {
	    return new Complex(re + c.re, im + c.im());
	  }
	  public Complex add(double c){
	    return new Complex(re + c, im);
	  }
	  public Complex sub(Complex c) {
	    return new Complex(re - c.re, im - c.im);
	  }

	  public Complex sub(double c){
	    return new Complex(re - c, im);
	  }

	  public Complex mult(Complex c) {
//	    if(c.isInfinity()){
//	      if(re == 0 && im == 0){
//	        return new Complex(0.0, 0.0);
//	      }
//	      if(re == 0){
//	        return new Complex(0.0, re*c.im + im*c.re);
//	      }
//	      if(im == 0){
//	        return new Complex(re*c.re - im*c.im, 0.0);
//	      }
//	    }else if(isInfinity()){
//	      if(c.re == 0 && c.im == 0){
//	        return new Complex(0.0, 0.0);
//	      }
//	      if(c.re == 0){
//	        return new Complex(0.0, re*c.im + im*c.re);
//	      }
//	      if(c.im == 0){
//	        return new Complex(re*c.re - im*c.im, 0.0);
//	      }
//	    }
	    return new Complex(re*c.re - im*c.im, re*c.im + im*c.re);
	  }

	  public Complex mult(double a){
	    return new Complex(re * a, im * a);
	  }

	  public Complex div(Complex c) {
	    double denominator = c.re*c.re + c.im*c.im;
	    //System.out.println(c.re +"  "+ c.im());
	    //System.out.println("denominator"+ denominator);
	    if(denominator == 0){
	      return INFINITY;
	    }else if(denominator == Double.POSITIVE_INFINITY){
	      return new Complex(0.0, 0.0);
	    }
	    //System.out.println(re +" "+ im);
	    return new Complex(
	    (re*c.re + im*c.im)/denominator,
	    (im*c.re - re*c.im)/denominator);
	  }

	  public Complex conjugation() {
	    return new Complex(re, -im);
	  }

	  public double abs() {
	    return (double)Math.sqrt(re*re + im*im);
	  }

	  public double arg() {
	    return (double)Math.atan(im/re);
	  }

	  public String toString() {
	    if (im >= 0)
	      return "(" + re + " + " + im + "i" + ")";
	    else
	      return "(" + re + " - " + -im + "i" + ")";
	 }

	  public boolean isInfinity(){
	    if(re == Double.POSITIVE_INFINITY || im == Double.POSITIVE_INFINITY ||re == Double.NEGATIVE_INFINITY || im == Double.NEGATIVE_INFINITY)
	      return true;
	    return false;
	  }
	  public boolean isZero(){
	    if(re == 0 && im == 0)
	      return true;
	    else
	      return false;
	  }
	  public static Complex div(Complex a, Complex b){
	    return a.div(b);
	  }

	  public static Complex add(Complex a, Complex b){
	    return a.add(b);
	  }

	  public static Complex mult(Complex a, Complex b){
	    return a.mult(b);
	  }

	   public static Complex conjunction(Complex a){
	     return new Complex(a.re(), -1 * a.im());
	   }

	  public static Complex sub(Complex a, Complex b){
	    return a.sub(b);
	  }
	  
	  public static double abs(Complex c) {
		    return (double)Math.sqrt(c.re*c.re + c.im*c.im);
		  }

	  public Complex complexSqrt(){
	    double newReal = 0.0;
	    double newImage = 0.0;

	    if(im > 0){
	      //println(Math.sqrt(re + Math.sqrt(re*re + im*im)) / Math.sqrt(2));
	      //println(Math.sqrt(-re + Math.sqrt(re*re + im*im)) / Math.sqrt(2));
	      return new Complex((double)(Math.sqrt(re + Math.sqrt(re*re + im*im)) / Math.sqrt(2)),
	                         (double)(Math.sqrt(-re + Math.sqrt(re*re + im*im)) / Math.sqrt(2)));

	    }else if(im < 0){
	      //println(Math.sqrt(re + Math.sqrt(re*re + im*im)) / Math.sqrt(2));
	      //println(-Math.sqrt(-re + Math.sqrt(re*re + im*im)) / Math.sqrt(2));
	      return new Complex((double)(Math.sqrt(re + Math.sqrt(re*re + im*im)) / Math.sqrt(2)),
	                         (double)(-Math.sqrt(-re + Math.sqrt(re*re + im*im)) / Math.sqrt(2)));
	    }

	    if(re < 0){
	      return new Complex(0.0, (double)Math.sqrt(Math.abs(re)));
	    }
	    //println("real");
	    //println(Math.sqrt(re));
	    return new Complex((double)Math.sqrt(re), 0.0);
	    //return new Complex(newReal, newImage);
	  }
	  public Complex round(int digit){
		if(Double.isInfinite(re) && Double.isNaN(re)) return new Complex(re, im);
		BigDecimal bigDecimal = new BigDecimal(re);
		double roundedRe = bigDecimal.setScale(digit, RoundingMode.HALF_UP).doubleValue();
		//System.out.println("aaaa"+roundedRe);
		bigDecimal = new BigDecimal(im);
		double roundedIm = bigDecimal.setScale(digit, RoundingMode.HALF_UP).doubleValue();
		return new Complex(roundedRe, roundedIm);
	  }
}
