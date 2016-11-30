

import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TripletGroup extends Fractal{
	protected static int id = 6;
	private Complex[] fixPoint = new Complex[6 + 1];
	protected List<Point> pointsList = Collections.synchronizedList(new ArrayList<Point>());
	public TripletGroup(Complex mu1, Complex mu2, int maxLevel, double epsilon, int expansion, TripletInputPanel panel) {
		super();
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		name = "triplet";
		Matrix gen_a = new Matrix(1, 2, 0, 1);
		Matrix gen_b = new Matrix(1, 0, 2, 1);
		Matrix gen_c = new Matrix(mu1.mult(mu2).add(new Complex(1, 0)),
								  mu1,
								  mu2,
								  new Complex(1, 0));
		panel.gen_cLabel.setIcon(LaTexHandler.getMatrixIcon("c",
															gen_c.a.toString(),
															gen_c.b.toString(),
															gen_c.c.toString(),
															gen_c.d.toString(),
															15));
		setGens(gen_a, gen_b, gen_c);
		for(int i = 1 ; i <= 6 ; i++){
    		fixPoint[i] = fix_plus(gens[i]);
      	}
	}

	protected void setGens(Matrix gen_a, Matrix gen_b, Matrix gen_c){
		gens[1] = gen_a;
	    gens[2] = gen_b;
	    gens[3] = gen_c;
	    gens[4] = gen_a.inverse();
	    gens[5] = gen_b.inverse();
	    gens[6] = gen_c.inverse();
	    word[1] = gens[1];
	}
	
	//Indra's pearls p140 Box16
	protected void calcLimitingSetWithDFS () throws InterruptedException{
		long pre = System.currentTimeMillis();
		UIPanel.millisLabel.setText(0 +" ms");
		UIPanel.stateLabel.setText("state : calculating...");
		do{
			System.out.println("tag[1]"+ tags[1]);

			while(branchTermination() == false){
				goForward();
				setPastTime(pre);
			}

			do{
				goBackward();
				setPastTime(pre);
			} while((level != 0 ) && !isAvailableTurn());

			turnAndGoForward();
			System.out.println("level "+ level +"  tags[1] "+ tags[1]);
			if(RecipeInputPanel.calcThread.interrupted()){
				UIPanel.stateLabel.setText("state : done");
				throw new InterruptedException();
			}
		}while(level != 1 || tags[1] != 1);
		setPastTime(pre);
		UIPanel.stateLabel.setText("state : done");
	}

	protected void goForward(){
		level++;
		tags[level] = Math.abs((tags[level - 1] + 1)%6);
		if(tags[level] == 0)
			tags[level] = 6;
		dumpWord();
		System.out.println("go forward current level "+ level +"tag["+ level +"]"+ tags[level]);
		word[level] = Matrix.mult(word[level -1], gens[tags[level]]);
	}

	protected boolean isAvailableTurn(){
		int t = Math.abs((tags[level] + 2)%6);
		if(t == 0)
			t = 6;
		int t2 = tags[level + 1] -1;
		if(t2 == 0)
			t2 = 6;
		if(t2  == t)
			return false;
		else
			return true;
	}

	protected void turnAndGoForward(){
		tags[level + 1] = Math.abs(tags[level + 1] - 1 % 6);
		if(tags[level +1] == 0)
			tags[level + 1] = 6;
		dumpWord();
		if(level == 0)
			word[1] = gens[tags[1]];
		else
			word[level + 1] = Matrix.mult(word[level], gens[tags[level + 1]]);
		level++;
		System.out.println("turn and go forward current level"+ level);
	}

	//Indra's pearls p207 “ÁŽêŒêƒAƒ‹ƒSƒŠƒYƒ€
	protected boolean branchTermination(){
		Complex newPoint;
		newPoint = mobius_on_point(word[level], fixPoint[tags[level]]);
		if(level == maxLevel){
			dumpWord();
			pointsList.add(new Point(newPoint.re(), newPoint.im()));
			repaint();
			System.out.println("termination");
			return true;
		}else{
			return false;
		}
	}

	protected void draw(Graphics2D g2){
		synchronized (pointsList) {	
			for(Point p : pointsList){
				p.draw(g2, expansion);
			}
		}
	}
}
