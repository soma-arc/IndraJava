
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.sound.sampled.LineListener;


public class Fractal {
	protected static int id = 0;
	protected Complex t_a, t_b, t_ab, c, mu;
	protected Matrix[] gens = new Matrix[6 + 1];
	protected double epsilon;
	protected int maxLevel, expansion;
	protected int[] tags = new int[10000];
	protected Matrix[] word = new Matrix[10000];
	protected int level = 1;
	protected Complex[][] fixPoint = new Complex[6 + 1][6 + 1];
	protected int[] numOfFixPoint = new int[4 + 1];
	protected final int ROUND_DIGIT = 6;
	protected static File directory;
	protected ArrayList<Conjugation> conjugationList = new ArrayList<Conjugation>();
	public static String name = ""; 
	
	public Fractal(){
		tags[1] = 1;
		word[0] = new Matrix(new Complex(1, 0), new Complex(0, 0),
							 new Complex(0, 0), new Complex(1, 0));
	}
	
	//Indra's pearls p140 Box16
	protected void calcLimitingSetWithDFS () throws InterruptedException{
		long pre = System.currentTimeMillis();
		UIPanel.millisLabel.setText(0 +" ms");
		UIPanel.stateLabel.setText("state : calculating...");
		do{
			//System.out.println("tag[1]"+ tags[1]);

			while(branchTermination() == false){
				goForward();
				setPastTime(pre);
			}
			do{
				goBackward();
				setPastTime(pre);
			} while((level != 0 ) && !isAvailableTurn());

			turnAndGoForward();
			//System.out.println("level "+ level +"  tags[1] "+ tags[1]);
			if(RecipeInputPanel.calcThread.interrupted()){
				UIPanel.stateLabel.setText("state : done");
				throw new InterruptedException();
			}
		}while(level != 1 || tags[1] != 1);
		
		repaint();
		setPastTime(pre);
		UIPanel.stateLabel.setText("state : done");
	}

	protected void goForward(){}

	protected void goBackward(){
		level--;
		//System.out.println("go backward current level"+ level);
	}

	protected boolean isAvailableTurn(){
		return false;
	}

	protected void turnAndGoForward(){

	}

	//Indra's pearls p207 “ÁŽêŒêƒAƒ‹ƒSƒŠƒYƒ€
	protected boolean branchTermination(){
		return false;
	}

	//Indra's pearls p71 Box8
	protected Complex mobius_on_point(Matrix t, Complex z){
		if(z.isInfinity()){
			if(!t.c.isZero()){
				return Complex.div(t.a, t.c);
			}else{
				return Complex.INFINITY;
			}
		}else{
			Complex numerix = Complex.add( Complex.mult(t.a, z), t.b);
			Complex denominator = Complex.add( Complex.mult(t.c, z), t.d);

			if(denominator.isZero()){
				return Complex.INFINITY;
			}else{
				return Complex.div( numerix, denominator);
			}
		}
	}

	protected void draw(Graphics2D g2){}

	//Indra's pearls p84 Box10
	protected Circle mobius_on_circle(Matrix t, Circle c){
		Circle d = new Circle();
		Complex z;
		double r = (double)c.getR();
		Complex centerR = new Complex(r, 0);
		z = c.center.sub( centerR.mult(centerR).div( Complex.conjunction(t.d.div(t.c).add( c.center))));
		d.center = mobius_on_point(t, z);
		d.r = Complex.abs(d.center.sub( mobius_on_point(t, c.center.add(c.r))));
		return d;
	}

	//Indra's pearls p73 Note3.3
	protected Complex fix_plus(Matrix t){
		Complex num =  t.a.sub(t.d).add(t.trace().mult(t.trace()).sub(4.0f).complexSqrt());
		return num.div(t.c.mult(2.0f));
	}

	protected Complex fix_minus(Matrix t){
		Complex num =  t.a.sub(t.d).sub(t.trace().mult(t.trace()).sub(4.0f).complexSqrt());
		return num.div(t.c.mult(2.0f));
	}

	protected void setOtherParams(int maxLevel, double epsilon, int expansion){
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
	}

	protected void repaint(){
		DrawPanel.drawPanel.repaint();
	}

	protected void dumpWord(){
		for(int i = 1 ; i <= level ; i++){
			System.out.print(tags[i]);
		}
		System.out.println("\n");
	}

	public int getMaxLevel(){
		return maxLevel;
	}

	public double getEpsilon(){
		return epsilon;
	}

	public int getExpansion(){
		return expansion;
	}
	
	protected int getId(){
		return id;
	}
	
	protected void save(int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException, SQLException{
		
	}
	protected void batchSave(int index, int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException, SQLException{
		
	}
	protected void saveScreenshot(File file, int width, int height, boolean isDrawAxis, boolean isDrawLimitPoint, boolean isDrawLevel2LimitPoint)throws IOException{
		
	}
	protected void saveDB(Calendar c)throws SQLException{
		
	}
	protected void setPastTime(long pre){
		UIPanel.millisLabel.setText(System.currentTimeMillis() - pre +" ms");
	}
	public void disposeLines(){
	}
	protected void drawLimitPoint(Graphics2D g2){}
	protected void drawComplexLimitPoint(Graphics2D g2){}
	protected String getParamCSV(){
		return "";
	}
	
	protected Matrix getCompositeConjugation(ArrayList<Conjugation> conjugationList){
		Matrix t = new Matrix(1, 0, 0, 1);
		for(int i = 0 ; i < conjugationList.size() ; i++){
			t = t.mult(conjugationList.get(i).getMatrix());
		}
		return t;
	}
}
