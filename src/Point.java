
import java.awt.*;

public class Point {
	double x, y;
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Point(){}
	
	public void draw(Graphics2D g2, int expansion){
		g2.setColor(Color.BLACK);
		g2.fillRect((int) (x * expansion), (int) (y * expansion), 2, 2);
	}
	public void draw(Graphics2D g2, int size, int expansion){
		if(size < 2)
			size = 2;
		g2.setColor(Color.BLACK);
		g2.fillOval((int) (x * expansion) - size/2, (int) -(y * expansion)- size/2, size, size);
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double dist(Point p){
		return Math.sqrt( Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
	}
	public void setCoordinates(Point p){
		this.x = p.getX();
		this.y = p.getY();
	}
	public String toString(){
		return "("+x +", "+y+")";
	}
}
