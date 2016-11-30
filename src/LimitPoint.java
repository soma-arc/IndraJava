import java.awt.*;
import java.awt.image.BufferedImage;
public class LimitPoint extends Point{
	BufferedImage limitImage;
	private boolean isExistsCoordinates = false;
	public LimitPoint(double x, double y, BufferedImage image){
		super(x, y);
		limitImage = image;
		isExistsCoordinates = true;
	}
	public LimitPoint(double x, double y){
		super(x, y);
		isExistsCoordinates = true;
	}
	public LimitPoint(BufferedImage image){
		limitImage = image;
		isExistsCoordinates = false;
	}
	public BufferedImage getLimitImage(){
		return limitImage;
	}
	public void setCoordinates(Point p){
		this.x = p.getX();
		this.y = p.getY();
		isExistsCoordinates = true;
	}
	public boolean isExistsCoordinates(){
		return isExistsCoordinates;
	}
}
