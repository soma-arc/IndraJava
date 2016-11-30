

import java.io.File;
import java.util.ArrayList;



public class JorgensensGroup extends TwinGroup{
	protected static int id = 2;
	RecipeInputPanel panel;
	//Indra's Pearls p241 Box22　ヨルゲンセンのレシピ
	private boolean isT_abPlus;
	public JorgensensGroup(Complex t_a, Complex t_b, boolean isT_abPlus, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "jorgensen";
		directory = new File("./jorgensen/");
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
		
		Matrix gen_a = new Matrix(t_a.sub(t_b.div(t_ab)),
								  t_a.div(t_ab.mult(t_ab)),
								  t_a,
								  t_b.div(t_ab));
		Matrix gen_b = new Matrix(t_b.sub(t_a.div(t_ab)),
								  t_b.div(t_ab.mult(t_ab)).mult(-1.0),
								  t_b.mult(-1.0),
								  t_a.div(t_ab));
	    
	    setLabels(gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	public JorgensensGroup(Complex t_a, Complex t_b, ArrayList<Conjugation> conjugationList, boolean isT_abPlus, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.conjugationList = conjugationList;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "jorgensen";
		directory = new File("./jorgensen/");
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
		
		Matrix gen_a = new Matrix(t_a.sub(t_b.div(t_ab)),
								  t_a.div(t_ab.mult(t_ab)),
								  t_a,
								  t_b.div(t_ab));
		Matrix gen_b = new Matrix(t_b.sub(t_a.div(t_ab)),
								  t_b.div(t_ab.mult(t_ab)).mult(-1.0),
								  t_b.mult(-1.0),
								  t_a.div(t_ab));
		Matrix T =  getCompositeConjugation(conjugationList);
	    gen_a = gen_a.conjugation(T);
	    gen_b = gen_b.conjugation(T);
	    setLabels(gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	private void setLabels(Matrix gen_a, Matrix gen_b){
		panel.gen_aLabel.setIcon(LaTexHandler.getMatrixIcon("a", gen_a, 15));
	    panel.gen_bLabel.setIcon(LaTexHandler.getMatrixIcon("b", gen_b, 15));
	}
	public Complex getT_a(){
		return t_a;
	}
	public Complex getT_b(){
		return t_b;
	}
	protected String getParamCSV(){
		return t_a.re() +","+ t_a.im() +","+ t_b.re() +","+ t_b.im() +","+ isT_abPlus;
	}
}
