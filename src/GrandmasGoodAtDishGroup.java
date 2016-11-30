import java.util.*;
import java.io.File;


public class GrandmasGoodAtDishGroup extends TwinGroup{
	protected static int id = 4;
	public GrandmasGoodAtDishInputPanel panel;
	public GrandmasGoodAtDishGroup(Complex t_a, Complex t_b, Complex t_ab, int maxLevel, double epsilon, int expansion, GrandmasGoodAtDishInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.t_ab = t_ab;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "grandmas-good-at";
		directory = new File("./grandmas-good-at/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Complex t_C = t_a.mult(t_a).add(t_b.mult(t_b)).add(t_ab.mult(t_ab)).sub(t_a.mult(t_b).mult(t_ab)).sub(2.0);
	    Complex two = new Complex(2.0, 0);
	    Complex Q = two.sub(t_C).complexSqrt();
	    Complex R = t_C.add(2.0).complexSqrt();
	    Complex i = new Complex(0, 1.0);
	    double comp_plus = t_C.add(i.mult(Q).mult(t_C.add(2.0).complexSqrt())).abs();
	    double comp_minus = t_C.add(i.mult(Q).mult(t_C.add(2.0).complexSqrt())).abs();
	    if(comp_minus >=2 && comp_plus < 2){
	      R = t_C.add(2.0).complexSqrt().mult(-1.0);
	    }
	    Complex z0 = t_ab.sub(2.0).mult(t_b.add(R)).div(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(i.mult(Q).mult(t_ab)));
	    Matrix gen_a = new Matrix(t_a.mult(0.5),
	                              t_a.mult(t_ab).sub(t_b.mult(2.0)).add(i.mult(Q).mult(two)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_a.mult(t_ab).sub(t_b.mult(2.0)).sub(Q.mult(i).mult(2.0))).div(t_ab.mult(2.0).sub(4.0)),
	                              t_a.mult(0.5));
	    Matrix gen_b = new Matrix(t_b.sub(i.mult(Q)).div(two),
	                              t_b.mult(t_ab).sub(t_a.mult(2.0)).sub(i.mult(Q).mult(t_ab)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(i.mult(Q).mult(t_ab))).div(t_ab.mult(2.0).sub(4.0)),
	                              t_b.add(i.mult(Q)).div(two));
	    
	    
	    setLabels(t_C, Q, R, z0, gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}

	public GrandmasGoodAtDishGroup(Complex t_a, Complex t_b, Complex t_ab, ArrayList<Conjugation> conjugationList, int maxLevel, double epsilon, int expansion, GrandmasGoodAtDishInputPanel panel) {
		super();
		this.t_a = t_a;
		this.t_b = t_b;
		this.t_ab = t_ab;
		this.conjugationList = conjugationList;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "grandmas-good-at";
		directory = new File("./grandmas-good-at/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Complex t_C = t_a.mult(t_a).add(t_b.mult(t_b)).add(t_ab.mult(t_ab)).sub(t_a.mult(t_b).mult(t_ab)).sub(2.0);
	    Complex two = new Complex(2.0, 0);
	    Complex Q = two.sub(t_C).complexSqrt();
	    Complex R = t_C.add(2.0).complexSqrt();;
	    Complex i = new Complex(0, 1.0);
	    double comp_plus = t_C.add(i.mult(Q).mult(t_C.add(2.0).complexSqrt())).abs();
	    double comp_minus = t_C.add(i.mult(Q).mult(t_C.add(2.0).complexSqrt())).abs();
	    if(comp_minus >=2 && comp_plus < 2){
	      R = t_C.add(2.0).complexSqrt().mult(-1.0);
	    }
	    Complex z0 = t_ab.sub(2.0).mult(t_b.add(R)).div(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(i.mult(Q).mult(t_ab)));
	    Matrix gen_a = new Matrix(t_a.mult(0.5),
	                              t_a.mult(t_ab).sub(t_b.mult(2.0)).add(i.mult(Q).mult(two)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_a.mult(t_ab).sub(t_b.mult(2.0)).sub(Q.mult(i).mult(2.0))).div(t_ab.mult(2.0).sub(4.0)),
	                              t_a.mult(0.5));
	    Matrix gen_b = new Matrix(t_b.sub(i.mult(Q)).div(two),
	                              t_b.mult(t_ab).sub(t_a.mult(2.0)).sub(i.mult(Q).mult(t_ab)).div(z0.mult(t_ab.mult(2.0).add(4.0))),
	                              z0.mult(t_b.mult(t_ab).sub(t_a.mult(2.0)).add(i.mult(Q).mult(t_ab))).div(t_ab.mult(2.0).sub(4.0)),
	                              t_b.add(i.mult(Q)).div(two));
	    Matrix T = getCompositeConjugation(conjugationList);
	    gen_a = gen_a.conjugation(T);
	    gen_b = gen_b.conjugation(T);
	    setLabels(t_C, Q, R, z0, gen_a, gen_b);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	private void setLabels(Complex t_C, Complex Q, Complex R, Complex z0, Matrix gen_a, Matrix gen_b){
		panel.tcLabel.setIcon(LaTexHandler.getFormulaIcon("t_C = "+ t_C.toString(), 20));
	    panel.qLabel.setIcon(LaTexHandler.getFormulaIcon("Q ="+ Q.toString(), 20));
	    panel.rLabel.setIcon(LaTexHandler.getFormulaIcon("R ="+ R.toString(), 20));
	    panel.z0Label.setIcon(LaTexHandler.getFormulaIcon("z_0="+ z0.toString(), 20));
	    panel.gen_aLabel.setIcon(LaTexHandler.getMatrixIcon("a", gen_a, 15));
	    panel.gen_bLabel.setIcon(LaTexHandler.getMatrixIcon("b", gen_b, 15));
	}
	
	public Complex getT_a(){
		return t_a;
	}
	public Complex getT_b(){
		return t_b;
	}
	public Complex getT_ab(){
		return t_ab;
	}
	protected String getParamCSV(){
		return t_a.re() +","+ t_a.im() +","+ t_b.re() +","+ t_b.im() +","+ t_ab.re() +","+ t_ab.im();
	}
}
