

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Line {
	double x1, y1, x2, y2;
	private int[] words;
	private boolean isInfinite;
	Line(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	Line(double x1, double y1, double x2, double y2, int[] words){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.words = words;
		
	}
	
	Line(double x1, double y1, double x2, double y2, Graphics2D g2, int expansion){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		int drawX1 = (int) (x1 * expansion);
		int drawY1 = (int) -(y1 * expansion);
		int drawX2 = (int) (x2 * expansion);
		int drawY2 = (int) -(y2 * expansion);
		g2.setColor(Color.BLACK);
		g2.drawLine(drawX1, drawY1, drawX2, drawY2);
	}
	
	/*public void draw(Graphics2D g2){
		if((int) x1 == 0 && (int) x2 == 0 && (int) y1 == 0 && (int) y2 == 0){
			return;
		}
		g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}*/
	
	public void draw(Graphics2D g2){
		if((int) x1 == 0 && (int) x2 == 0 && (int) y1 == 0 && (int) y2 == 0){
			return;
		}
		if(words[1] == 1){
			g2.setColor(Color.RED);
		}else if(words[1] == 2){
			g2.setColor(Color.BLUE);
		}else if(words[1] == 3){
			g2.setColor(Color.GREEN);
		}else if(words[1] == 4){
			g2.setColor(Color.BLACK);
		}
		g2.drawLine((int) x1, (int) -y1, (int) x2, (int) -y2);
	}
	
	public void draw(Graphics2D g2, int expansion){
		int drawX1 = (int) (x1 * expansion);
		int drawY1 = (int) -(y1 * expansion);
		int drawX2 = (int) (x2 * expansion);
		int drawY2 = (int) -(y2 * expansion);
		//System.out.println(drawX1);
		/*if(drawX1 == 0 && drawY1 == 0 && drawX2 == 0 && drawY2 == 0){
			return;
		}*/
		if(words[1] == 1){
			g2.setColor(Color.RED);
		}else if(words[1] == 2){
			g2.setColor(Color.BLUE);
		}else if(words[1] == 3){
			g2.setColor(Color.GREEN);
		}else if(words[1] == 4){
			g2.setColor(Color.BLACK);
		}
		//if(isInfinite){
		//	g2.setColor(Color.black);
		//	g2.fillOval((drawX1 + drawX2)/2, (drawY1 + drawY2)/ 2, 5, 5);
		//}
		g2.drawLine(drawX1, drawY1, drawX2, drawY2);
	}
}
