

import java.awt.Graphics;
public class Circle {
	public double r;
	public Complex center;
	public int expansion;
	  Circle(double _r, Complex _center){
	    r = _r;
	    center = _center;
	  }
	  Circle(){}

	  Circle(double _r, Complex _center, int _expansion){
	    r = _r;
	    center = _center;
	    expansion = _expansion;
	  }

	  public void draw(Graphics g){
	    g.fillOval((int)center.re(), (int)center.im(), (int)r, (int)r);
	  }

	  public void draw(Graphics g, int expansion){
	    //double R = (center.re() * expansion - r * expansion);
	    g.fillOval((int)center.re() * expansion, (int)center.im() * expansion,
	               (int) r * 2 * expansion     , (int) r * 2 * expansion);
	  }

	  public double getR(){
	    return r;
	  }

	  public Complex getCenter(){
	    return center;
	  }

	  public Circle expand(double expansion){
	    return new Circle(r * expansion,
	                      center.mult(expansion)
	                      );
	  }
	  public String toString(){
	    return "("+center.re()+","+center.im()+")"+"r = "+ r;
	  }
}
