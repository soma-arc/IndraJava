

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;


public class GrandmasSpecialGroup extends TwinGroup{
	protected static int id = 1;
	//public static final String NAME 
	private boolean isT_abPlus;
	RecipeInputPanel panel;
	LimitPoint abABPoint;
	//protected File 
	//Indra's Pearls p228 Box21  ‚¨‚Î‚ ‚¿‚á‚ñ‚Ì•ú•¨Œ^ŒðŠ·ŽqŒQƒXƒyƒVƒƒƒ‹”Å
	public GrandmasSpecialGroup(Complex t_a, Complex t_b, boolean isT_abPlus, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.isT_abPlus = isT_abPlus;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "grandmas-special";
		directory = new File("./grandmas-special/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Complex t_ab;
		this.isT_abPlus = isT_abPlus; 
		if(isT_abPlus){
			t_ab = t_a.mult(t_b).add(t_a.mult(t_a).mult(t_b.mult(t_b)).sub(t_a.mult(t_a).add(t_b.mult(t_b)).mult(4.0)).complexSqrt()).mult(0.5);
		}else{
			t_ab = t_a.mult(t_b).sub(t_a.mult(t_a).mult(t_b.mult(t_b)).sub(t_a.mult(t_a).add(t_b.mult(t_b)).mult(4.0)).complexSqrt()).mult(0.5);
		}
		System.out.println("t_ab = "+ t_ab);
		//System.out.println(t_ab);
		Complex z0 = t_ab.sub(2.0).mult(t_b).div(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(t_ab.mult(new Complex(0, 2.0))));

	    Matrix gen_a = new Matrix(t_a.mult(0.5),
	                              t_a.mult(t_ab).sub(t_b.mult(2.0)).add(new Complex(0, 4.0)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_a.mult(t_ab).sub(t_b.mult(2.0)).sub(new Complex(0, 4.0)).div(t_ab.mult(2.0).sub(4.0))),
	                              t_a.mult(0.5));
	    Matrix gen_b = new Matrix(t_b.sub(new Complex(0, 2.0)).mult(0.5),
	                              t_b.mult(0.5),
	                              t_b.mult(0.5),
	                              t_b.add(new Complex(0, 2.0)).mult(0.5));
	    abABPoint = getabABPoint(gen_a, gen_b, gen_a.inverse(), gen_b.inverse());
	    
	    setLabels(z0, gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	public GrandmasSpecialGroup(Complex t_a, Complex t_b, ArrayList<Conjugation> conjugationList, boolean isT_abPlus, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.conjugationList = conjugationList;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "grandmas-special";
		directory = new File("./grandmas-special/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Complex t_ab;
		this.isT_abPlus = isT_abPlus; 
		if(isT_abPlus){
			t_ab = t_a.mult(t_b).add(t_a.mult(t_a).mult(t_b.mult(t_b)).sub(t_a.mult(t_a).add(t_b.mult(t_b)).mult(4.0)).complexSqrt()).mult(0.5);
			
		}else{
			t_ab = t_a.mult(t_b).sub(t_a.mult(t_a).mult(t_b.mult(t_b)).sub(t_a.mult(t_a).add(t_b.mult(t_b)).mult(4.0)).complexSqrt()).mult(0.5);
		}
		//System.out.println(t_ab);
		
		Complex z0 = t_ab.sub(2.0).mult(t_b).div(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(t_ab.mult(new Complex(0, 2.0))));

	    Matrix gen_a = new Matrix(t_a.mult(0.5),
	                              t_a.mult(t_ab).sub(t_b.mult(2.0)).add(new Complex(0, 4.0)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_a.mult(t_ab).sub(t_b.mult(2.0)).sub(new Complex(0, 4.0)).div(t_ab.mult(2.0).sub(4.0))),
	                              t_a.mult(0.5));
	    Matrix gen_b = new Matrix(t_b.sub(new Complex(0, 2.0)).mult(0.5),
	                              t_b.mult(0.5),
	                              t_b.mult(0.5),
	                              t_b.add(new Complex(0, 2.0)).mult(0.5));
	    
	    Matrix T = getCompositeConjugation(conjugationList);
	    gen_a = gen_a.conjugation(T);
	    gen_b = gen_b.conjugation(T);
	    
	    abABPoint = getabABPoint(gen_a, gen_b, gen_a.inverse(), gen_b.inverse());
	    
	    setLabels(z0, gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	private LimitPoint getabABPoint(Matrix gen_a, Matrix gen_b, Matrix gen_A, Matrix gen_B){
		Matrix abAB = calcMatrix(gen_a, gen_b, gen_A, gen_B);
	    Complex c = fix_plus(abAB);
	    return new LimitPoint(c.re(), c.im());
	}
	
	private void setLabels(Complex z0, Matrix gen_a, Matrix gen_b){
		if(panel.z0Label != null && panel.gen_aLabel != null && panel.gen_bLabel != null){
		panel.z0Label.setIcon(LaTexHandler.getFormulaIcon("z_0 = "+ z0.round(ROUND_DIGIT).toString(), 15));
	    panel.gen_aLabel.setIcon(LaTexHandler.getMatrixIcon("a", gen_a, 15));
	    panel.gen_bLabel.setIcon(LaTexHandler.getMatrixIcon("b", gen_b, 15));
		}
	}
	
	public Complex getT_a (){
		return t_a;
	}
	public Complex getT_b(){
		return t_b;
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

		g2.setColor(Color.BLACK);
		g2.drawLine(0, -DrawPanel.drawPanel.getHeight()/2 + 100, (int) (abABPoint.getX() * expansion), (int) (abABPoint.getY() * expansion));
		abABPoint.draw(g2, expansion);
		g2.drawImage(abAB_Image, -30, -DrawPanel.drawPanel.getHeight()/2 + 70, DrawPanel.drawPanel);
	}
	protected String getParamCSV(){
		return t_a.re() +","+ t_a.im() +","+ t_b.re() +","+ t_b.im() +","+ isT_abPlus;
	}
	protected void saveDB(Calendar c){
		
	}
}
