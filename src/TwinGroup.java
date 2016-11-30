
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

public class TwinGroup extends Fractal{
	protected List<Line> linesList = Collections.synchronizedList(new ArrayList<Line>());
	protected boolean is_aaBParabolic = false;
	Matrix[][] repet;
	//Point[] limitPoints = new Point[4 + 1];
	Point[] level2LimitPoint = new Point[8 + 1];
	Point[][] limitShowPoint = new Point[4 + 1][4 + 1];
	protected static BufferedImage a_barImage, b_barImage, A_barImage, B_barImage, abAB_Image;
	protected static BufferedImage[] limitWordImage = new BufferedImage[4 + 1];
	protected static ArrayList<int[]> limitWordList = new ArrayList<int[]>();
	protected static Color[] colors = new Color[4 + 1];
	protected static LimitPoint[][] limitPoints = new LimitPoint[4 + 1][4 + 1];
	protected static BufferedImage[][] limitPointsImage = new BufferedImage[4 + 1][4 + 1];
	//protected File directory;
	
	public TwinGroup(){
		super();
		colors[1] = Color.RED;
		colors[2] = Color.BLUE;
		colors[3] = Color.GREEN;
		colors[4] = Color.BLACK;
		try{
			if(limitWordImage[1] == null)
				limitWordImage[1] = ImageIO.read(new File("./res/a-bar.png"));
			if(limitWordImage[2] == null)
				limitWordImage[2] = ImageIO.read(new File("./res/b-bar.png"));
			if(limitWordImage[3] == null)
				limitWordImage[3] = ImageIO.read(new File("./res/large-a-bar.png"));
			if(limitWordImage[4] == null)
				limitWordImage[4] = ImageIO.read(new File("./res/large-b-bar.png"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		int width = DrawPanel.drawPanel.getWidth();
		int height = DrawPanel.drawPanel.getHeight();
		
		try{
			limitPointsImage[1][1] = ImageIO.read(new File("./res/a-bar.png"));
			limitPointsImage[1][2] = ImageIO.read(new File("./res/ab.png"));
			limitPointsImage[1][4] = ImageIO.read(new File("./res/a-large-b.png"));
			
			limitPointsImage[2][1] = ImageIO.read(new File("./res/ba.png"));
			limitPointsImage[2][2] = ImageIO.read(new File("./res/b-bar.png"));
			limitPointsImage[2][3] = ImageIO.read(new File("./res/b-large-a.png"));
			
			limitPointsImage[3][2] = ImageIO.read(new File("./res/large-a-b.png"));
			limitPointsImage[3][3] = ImageIO.read(new File("./res/large-a-bar.png"));
			limitPointsImage[3][4] = ImageIO.read(new File("./res/large-a-large-b.png"));
			
			limitPointsImage[4][1] = ImageIO.read(new File("./res/large-b-a.png"));
			limitPointsImage[4][3] = ImageIO.read(new File("./res/large-b-large-a.png"));
			limitPointsImage[4][4] = ImageIO.read(new File("./res/large-b-bar.png"));
			
			abAB_Image = ImageIO.read(new File("./res/abAB.png"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		limitShowPoint[1][1] = new Point(50 -width/2, 50 -height/2);
		limitShowPoint[1][2] = new Point(100 -width/2, 50 -height/2);
		limitShowPoint[1][4] = new Point(50 -width/2, 100 -height/2);
		
		limitShowPoint[2][2] = new Point(width/2 - 50, 100 -height/2);
		limitShowPoint[2][1] = new Point(width/2 - 100, 50 -height/2);
		limitShowPoint[2][3] = new Point(width/2 - 50, 150 -height/2);
		
		limitShowPoint[3][4] = new Point(50 -width/2, height/2 - 100);
		limitShowPoint[3][3] = new Point(50 -width/2, height/2 - 50);
		limitShowPoint[3][2] = new Point(100 -width/2, height/2 - 50);
		
		limitShowPoint[4][1] = new Point(width/2 - 150, height/2 -50);
		limitShowPoint[4][4] = new Point(width/2 - 50, height/2 -50);
		limitShowPoint[4][3] = new Point(width/2 - 50, height/2 -100);
	}
	
	protected void setGens(Matrix gen_a, Matrix gen_b){
		gens[1] = gen_a;
	    gens[2] = gen_b;
	    gens[3] = gen_a.inverse();
	    gens[4] = gen_b.inverse();
	    word[1] = gens[1];
	}
	
	protected void setFixPoint(){
		
		double trace_aaB = calcMatrix(gens[1], gens[1], gens[4]).trace().abs();
		System.out.println(trace_aaB);
	    if((1.9 < trace_aaB && trace_aaB < 2.1) || (-2.1 < trace_aaB && trace_aaB < -1.9)){
	    	//Indra's Pearls 246p
	    	//aaB‚ª•ú•¨Œ^‚Å‚ ‚éŽž‚Ì“ÁŽêŒê
	    	numOfFixPoint[1] = numOfFixPoint[3] = 4;
	        numOfFixPoint[2] = numOfFixPoint[4] = 3;
	        Matrix[][] repet = new Matrix[4 + 1][4 + 1];
	        repet[1][1] = calcMatrix(gens[2], gens[3], gens[4], gens[1]);
	        repet[1][2] = calcMatrix(gens[1], gens[4], gens[1]);
	        repet[1][3] = calcMatrix(gens[4], gens[1], gens[1]);
	        repet[1][4] = calcMatrix(gens[4], gens[3], gens[2], gens[1]);

	        repet[2][1] = calcMatrix(gens[3], gens[4], gens[1], gens[2]);
	        repet[2][2] = calcMatrix(gens[3], gens[3], gens[2]);
	        repet[2][3] = calcMatrix(gens[1], gens[4], gens[3], gens[2]);

	        repet[3][1] = calcMatrix(gens[4], gens[1], gens[2], gens[3]);
	        repet[3][2] = calcMatrix(gens[3], gens[2], gens[3]);
	        repet[3][3] = calcMatrix(gens[2], gens[4], gens[4]);
	        repet[3][4] = calcMatrix(gens[2], gens[1], gens[4], gens[3]);

	        repet[4][1] = calcMatrix(gens[1], gens[2], gens[3], gens[4]);
	        repet[4][2] = calcMatrix(gens[1], gens[1], gens[3]);
	        repet[4][3] = calcMatrix(gens[3], gens[2], gens[1], gens[4]);

	        for(int i = 1 ; i <= 4 ; i ++){
	          for(int j = 1 ; j <= numOfFixPoint[i] ; j++){
	            fixPoint[i][j] = fix_plus(repet[i][j]);
	          }
	        }
	        is_aaBParabolic = true;
	    }else{
	    	System.out.println("aaB is not parabollic");
		    Matrix[][] repet = new Matrix[4 + 1][3 + 1];
		    repet[1][1] = calcMatrix(gens[2], gens[3], gens[4], gens[1]);
		    repet[1][2] = gens[1];
		    repet[1][3] = calcMatrix(gens[4], gens[3], gens[2], gens[1]);
	
		    repet[2][1] = calcMatrix(gens[3], gens[4], gens[1], gens[2]);
		    repet[2][2] = gens[2];
		    repet[2][3] = calcMatrix(gens[1], gens[4], gens[3], gens[2]);
	
		    repet[3][1] = calcMatrix(gens[4], gens[1], gens[2], gens[3]);
		    repet[3][2] = gens[3];
		    repet[3][3] = calcMatrix(gens[2], gens[1], gens[4], gens[3]);
	
		    repet[4][1] = calcMatrix(gens[1], gens[2], gens[3], gens[4]);
		    repet[4][2] = gens[4];
		    repet[4][3] = calcMatrix(gens[3], gens[2], gens[1], gens[4]);
	
		    for(int i = 1 ; i <= 4 ; i ++){
		    	for(int j = 1 ; j <= 3 ; j++){
		    		fixPoint[i][j] = fix_plus(repet[i][j]);
		    		System.out.println(fixPoint[i][j]);
		      	}
		    }
	    }
	    System.out.println(fix_plus(gens[1]));
		System.out.println(fix_plus(gens[2]));
		System.out.println(fix_plus(gens[3]));
		System.out.println(fix_plus(gens[4]));
	}
	
	protected void goForward(){
		level++;
		tags[level] = Math.abs((tags[level - 1] + 1)%4);
		if(tags[level] == 0)
			tags[level] = 4;
		//dumpWord();
		//System.out.println("go forward current level "+ level +"tag["+ level +"]"+ tags[level]);
		word[level] = Matrix.mult(word[level -1], gens[tags[level]]);
	}

	protected void goBackward(){
		level--;
		//System.out.println("go backward current level"+ level);
	}

	protected boolean isAvailableTurn(){
		int t = Math.abs((tags[level] + 2)%4);
		if(t == 0)
			t = 4;
		int t2 = tags[level + 1] -1;
		if(t2 == 0)
			t2 = 4;
		if(t2  == t)
			return false;
		else
			return true;
	}

	protected void turnAndGoForward(){
		tags[level + 1] = Math.abs((tags[level + 1]) - 1 % 4);
		if(tags[level +1] == 0)
			tags[level + 1] = 4;
		//dumpWord();
		if(level == 0)
			word[1] = gens[tags[1]];
		else
			word[level + 1] = Matrix.mult(word[level], gens[tags[level + 1]]);
		level++;
		//System.out.println("turn and go forward current level"+ level);
	}
	
	//Indra's pearls p207 “ÁŽêŒêƒAƒ‹ƒSƒŠƒYƒ€
	protected boolean branchTermination(){
		int level1Gen = 0;
		int infiniteGen = 0;
		int[] w = new int[level + 1];
		boolean isLimit = false;
		for(int i = 1 ; i <= level ; i++){
			w[i] = tags[i];
		}
		
		if(level > 2){
			level1Gen = w[1];
			infiniteGen = w[2];
			
			int count = 0;
			for(int i = 2 ; i <= w.length - 1 ; i++){
				if(infiniteGen == w[i]){
					count++;
				}
			}
			
			if(count == w.length - 2 ){
				//System.out.println("limit");
				isLimit = true;
			}
		}

		if(is_aaBParabolic){
			int num = numOfFixPoint[tags[level]];
		    Complex[] z = new Complex[num + 1];
		    for(int j = 1 ; j <= num ; j++){
		      z[j] = mobius_on_point(word[level], fixPoint[tags[level]][j]);
		    }
		    
		    if(num == 3){
		      if(level == maxLevel || (z[2].sub(z[1]).abs() <= epsilon && z[3].sub(z[2]).abs() <= epsilon)){
		    	if(isLimit){
		    		limitPoints[level1Gen][infiniteGen] = new LimitPoint(z[2].re(), z[2].im());
		    		//limitPoints[level1Gen][infiniteGen].setCoordinates(new Point(z[2].re(), z[2].im()));
					//limitPoints[limitGen] = new Point(z[2].re(), z[2].im());
				}
		    	//dumpWord();
		        //System.out.println("termination");
		    	
		    		
		    	
		        linesList.add(new Line(z[1].re(), z[1].im(),
		        					   z[2].re(), z[2].im(), w));
		        linesList.add(new Line(z[2].re(), z[2].im(),
		        					   z[3].re(), z[3].im(), w));
		        //repaint();
		        return true;
		      }else{
		        return false;
		      }
		    }
		    if(level == maxLevel || (z[2].sub(z[1]).abs() <= epsilon && z[3].sub(z[2]).abs() <= epsilon && z[4].sub(z[3]).abs() <= epsilon)){
		    	if(isLimit){
		    		limitPoints[level1Gen][infiniteGen] = new LimitPoint(z[2].re(), z[2].im());
		    		//limitPoints[level1Gen][infiniteGen].setCoordinates(new Point(z[2].re(), z[2].im()));
					//limitPoints[limitGen] = new Point(z[2].re(), z[2].im());
				}
		    	//dumpWord();
		        //System.out.println("termination");
		        linesList.add(new Line(z[1].re(), z[1].im(),
		        				  	   z[2].re(), z[2].im(), w));
		        linesList.add(new Line(z[2].re(), z[2].im(),
		        					   z[3].re(), z[3].im(), w));
		        linesList.add(new Line(z[3].re(), z[3].im(),
		        					   z[4].re(), z[4].im(), w));
		        //repaint();
		        return true;
		      }else{
		        return false;
		      }
		}else{
			Complex[] z = new Complex[3 + 1];
			for(int j = 1 ; j <= 3 ; j++){
				z[j] = mobius_on_point(word[level], fixPoint[tags[level]][j]);
			}
			if(level == maxLevel || (z[2].sub(z[1]).abs() <= epsilon && z[3].sub(z[2]).abs() <= epsilon)){
//				for(int i = 1 ; i <= maxLevel ; i++){
//					System.out.print(tags[i]);
//				}
//				System.out.println();
				//dumpWord();
				if(isLimit){
					limitPoints[level1Gen][infiniteGen] = new LimitPoint(z[2].re(), z[2].im());
					//limitPoints[limitGen] = new Point(z[2].re(), z[2].im());
				}
//				if(level == maxLevel){
//		    		System.out.println(z[2]);
//		    	}
				//System.out.println("termination");
				linesList.add(new Line(z[1].re(), z[1].im(),
						               z[2].re(), z[2].im(), w));
				linesList.add(new Line(z[2].re(), z[2].im(),
						               z[3].re(), z[3].im(), w));
				//repaint();
				return true;
			}else{
				return false;
			}
		}
	}
		
	protected void draw(Graphics2D g2){
		synchronized (linesList) {	
			for(Line line : linesList){
				//System.out.println(linesList.size());
				line.draw(g2, expansion);
			}
		}
	}
	
	protected void drawLimitPoint(Graphics2D g2){
		for(int i = 1 ; i <= 4 ; i++){
			//for(int j = 1 ; j <= 4 ; j++){
				if(limitPoints[i][i] != null && limitPoints[i][i].isExistsCoordinates()){
					
					limitPoints[i][i].draw(g2, 5, expansion);
					//System.out.println(limitPoints[i][j]);
					int x1 = (int) limitShowPoint[i][i].getX();
					int y1 = (int) limitShowPoint[i][i].getY();
					int x2 = (int) (limitPoints[i][i].getX() * expansion);
					int y2 = (int) -(limitPoints[i][i].getY() * expansion);
					//System.out.println(i +"  "+ x2+","+y2);
					//System.out.println(x1 +":"+y1 +"to"+ x2 +":"+y2);
					g2.setColor(colors[i]);
					g2.drawLine(x1, y1, x2, y2);
					if(i == 1)
						g2.drawImage(limitPointsImage[i][i], x1 - 10, y1 - 10, DrawPanel.drawPanel);
					else if(i == 2)
						g2.drawImage(limitPointsImage[i][i], x1 + 10, y1 - 10, DrawPanel.drawPanel);
					else if(i == 3)
						g2.drawImage(limitPointsImage[i][i], x1 - 20, y1 - 20, DrawPanel.drawPanel);
					else if(i == 4)
						g2.drawImage(limitPointsImage[i][i], x1 + 10, y1 - 20, DrawPanel.drawPanel);
				//}
			}
		}
	}
	
	protected void drawComplexLimitPoint(Graphics2D g2){
		for(int i = 1 ; i <= 4 ; i++){
			for(int j = 1 ; j <= 4 ; j++){
				if(i != j && limitPoints[i][j] != null && limitPoints[i][j].isExistsCoordinates()){
					limitPoints[i][j].draw(g2, 5, expansion);
					//System.out.println(limitPoints[i][j]);
					int x1 = (int) limitShowPoint[i][j].getX();
					int y1 = (int) limitShowPoint[i][j].getY();
					int x2 = (int) (limitPoints[i][j].getX() * expansion);
					int y2 = (int) -(limitPoints[i][j].getY() * expansion);
					//System.out.println(x1 +":"+y1 +"to"+ x2 +":"+y2);
					g2.setColor(colors[i]);
					g2.drawLine(x1, y1, x2, y2);
					if(i == 1)
						g2.drawImage(limitPointsImage[i][j], x1 - 10, y1 - 10, DrawPanel.drawPanel);
					else if(i == 2)
						g2.drawImage(limitPointsImage[i][j], x1 + 10, y1 - 10, DrawPanel.drawPanel);
					else if(i == 3)
						g2.drawImage(limitPointsImage[i][j], x1 - 20, y1 - 20, DrawPanel.drawPanel);
					else if(i == 4)
						g2.drawImage(limitPointsImage[i][j], x1 + 10, y1 - 20, DrawPanel.drawPanel);
				}
			}
		}
	}
	
	protected Matrix calcMatrix(Matrix m1, Matrix m2, Matrix m3, Matrix m4){
        Matrix m1m2 = m1.mult(m2);
        Matrix m1m2m3  = m1m2.mult(m3);
        Matrix m1m2m3m4 = m1m2m3.mult(m4);
        return m1m2m3m4;
	}

    protected Matrix calcMatrix(Matrix m1, Matrix m2, Matrix m3){
    	Matrix m1m2 = m1.mult(m2);
        Matrix m1m2m3  = m1m2.mult(m3);
        return m1m2m3;
    }
    
    public void disposeLines(){
    	linesList.clear();
    	System.gc();
    }

    protected void saveScreenshot(File file, int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException{
		BufferedImage image = ( BufferedImage ) DrawPanel.drawPanel.createImage(width,  height);
		Graphics2D g2 = ( Graphics2D ) image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, width , height);
		g2.translate(width / 2, height / 2);
		if(isDrawAxis){
			g2.drawLine(-width, 0, width, 0);
			g2.drawLine(0, height, 0, height);
		}
		if(isDrawLimitPoint)
			drawLimitPoint(g2);
		if(isDrawLevel2LimitPoint)
			drawComplexLimitPoint(g2);
		draw(g2);
		ImageIO.write(image, "jpg", file);
    }
    
    protected void saveScreenshotWithParam(File file, int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException{
		BufferedImage image = ( BufferedImage ) DrawPanel.drawPanel.createImage(width,  height);
		Graphics2D g2 = ( Graphics2D ) image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
							RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, width , height);
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
		g2.drawString("t_a = "+ t_a.toString(), 10, 30);
		g2.drawString("t_b = "+ t_b.toString(), 10, 55);
		g2.translate(width / 2, height / 2);
		if(isDrawAxis){
			g2.drawLine(-width, 0, width, 0);
			g2.drawLine(0, height, 0, height);
		}
		if(isDrawLimitPoint)
			drawLimitPoint(g2);
		if(isDrawLevel2LimitPoint)
			drawComplexLimitPoint(g2);
		draw(g2);
		ImageIO.write(image, "jpg", file);
    }
    
    protected void saveScad(){
    	synchronized (linesList) {	
    	try{
    		FileWriter writer = new FileWriter(new File("klein7.scad"), false);
    		writer.write("union(){\n");
    		ArrayList<Point> pointsList = new ArrayList<Point>();
    		ArrayList<Line> longLinesList = new ArrayList<Line>();
    		for(Line line : linesList){
    			Point p1 = new Point(line.x1, line.y1);
    			Point p2 = new Point(line.x2, line.y2);
    			if(p1.dist(p2) > 0.03){
    				longLinesList.add(line);
    			}
    			boolean near = false;
    			for(Point p : pointsList){
    				if(p1.dist(p) < 0.01){
    					near = true;
    					break;
    				}
    			}
    			if(!near)
    				pointsList.add(p1);
    			
    			near = false;
    			for(Point p : pointsList){
    				if(p2.dist(p) < 0.01){
    					near = true;
    					break;
    				}
    			}
    			if(!near)
    				pointsList.add(p2);
    		}
    		for(int i = 0 ; i < pointsList.size() ; i += 2){
    			if(i == pointsList.size()-1){
    				Point p = pointsList.get(i);
    				writer.write("translate(["+ p.getX() +","+ p.getY() +", 0]) cylinder(h = 0.1, r = 0.01);\n");
    				break;
    			}
    			Point p = pointsList.get(i);
    			Point np = pointsList.get(i + 1);
    			writer.write("hull(){\n");
				writer.write("translate(["+ p.getX() +","+ p.getY() +", 0]) cylinder(h = 0.1, r = 0.01);\n");
				writer.write("translate(["+ np.getX() +","+ np.getY() +", 0]) cylinder(h = 0.1, r = 0.01);\n");
				writer.write("}\n");
    		}
    		
    		for(Line l : longLinesList){
    			writer.write("hull(){\n");
				writer.write("translate(["+ l.x1 +","+ l.y1 +", 0]) cylinder(h = 0.1, r = 0.01);\n");
				writer.write("translate(["+ l.x2 +","+ l.y2 +", 0]) cylinder(h = 0.1, r = 0.01);\n");
				writer.write("}\n");
    		}
//    		Point prePoint = pointsList.get(0);
//    		for(Point point : pointsList){
//    			if(point.dist(prePoint) < 0.01){
//    				continue;
//    			}else{
//    				writer.write("hull(){\n");
//    				writer.write("translate(["+ prePoint.getX() +","+ prePoint.getY() +", 0]) cylinder(h = 0.1, r = 0.01);\n");
//    				writer.write("translate(["+ point.getX() +","+ point.getY() +", 0]) cylinder(h = 0.1, r = 0.01);\n");
//    				writer.write("}\n");
//    				prePoint = point;
//    			}
//    		}
    		writer.write("}");
    		writer.flush();
    		writer.close();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	}
    }
    
    protected void save(int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException, SQLException{
    	Calendar c = Calendar.getInstance();
    	String orgDate = SQLiteHandler.getFormattedData(c.getTime());
    	String fileName = orgDate +".jpg";
    	File file = new File(directory.getAbsolutePath()+"/"+ fileName);
    	System.out.println(file.getAbsolutePath());
    	//SQLiteHandler.insertFractalData(c, directory.getName(), paramCSV, words, conjugationParams, maxLevel, epsilon, expansion);
    	//saveScreenshot(file, width, height, isDrawAxis, isDrawLimitPoint, isDrawLevel2LimitPoint);
    	//SQLiteHandler.insertFractalData(c, name, getParamCSV(), conjugationList, maxLevel, epsilon, expansion);
    	saveScad();
    }
    protected void batchSave(int index, int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException, SQLException{
    	Calendar c = Calendar.getInstance();
    	String orgDate = SQLiteHandler.getFormattedData(c.getTime());
    	String fileName = index +".jpg";
    	File file = new File(directory.getAbsolutePath()+"/"+ fileName);
    	System.out.println(file.getAbsolutePath());
    	//SQLiteHandler.insertFractalData(c, directory.getName(), paramCSV, words, conjugationParams, maxLevel, epsilon, expansion);
    	saveScreenshotWithParam(file, width, height, isDrawAxis, isDrawLimitPoint, isDrawLevel2LimitPoint);
    	SQLiteHandler.insertFractalData(c, name, getParamCSV(), conjugationList, maxLevel, epsilon, expansion);
    }
}
