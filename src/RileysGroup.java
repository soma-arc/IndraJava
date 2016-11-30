
import java.io.File;
import java.util.ArrayList;


public class RileysGroup extends TwinGroup{
	protected static int id = 3;
	RecipeInputPanel panel;
	public RileysGroup(Complex c, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.c = c;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "riley";
		directory = new File("./riley/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Matrix gen_a = new Matrix(new Complex(1.0, 0),
								  new Complex(0, 0),
								  c,
								  new Complex(1.0, 0));
		Matrix gen_b = new Matrix(new Complex(1.0, 0),
								  new Complex(2.0, 0),
								  new Complex(0, 0),
								  new Complex(1.0, 0));
		
		setLabels(gen_a);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	public RileysGroup(Complex c, ArrayList<Conjugation> conjugationList, int maxLevel, float epsilon, int expansion, RecipeInputPanel panel) {
		super();
		this.c = c;
		this.conjugationList = conjugationList;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "riley";
		directory = new File("./riley/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Matrix gen_a = new Matrix(new Complex(1.0, 0),
								  new Complex(0, 0),
								  c,
								  new Complex(1.0, 0));
		Matrix gen_b = new Matrix(new Complex(1.0, 0),
								  new Complex(2.0, 0),
								  new Complex(0, 0),
								  new Complex(1.0, 0));
		Matrix T = getCompositeConjugation(conjugationList);
		gen_a = gen_a.conjugation(T);
		gen_b = gen_b.conjugation(T);
		
		setLabels(gen_a);
	    setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	private void setLabels(Matrix gen_a){
		panel.gen_aLabel.setIcon(LaTexHandler.getMatrixIcon("a", gen_a, 15));
	}
	
	public Complex getC(){
		return c;
	}
	protected String getParamCSV(){
		return c.re() +","+ c.im();
	}
}
