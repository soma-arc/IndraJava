

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MaskitGroup extends TwinGroup{
	protected static int id = 5;
	private MaskitInputPanel panel;
	public MaskitGroup(int denom, int expansion){
		getFareySequence(denom);
		System.out.println(newton(new Complex(0, 2), 1, 1000));
		//drawBoundary(denom, expansion);
	}
	
	public MaskitGroup(Complex mu, int maxLevel, float epsilon, int expansion, MaskitInputPanel panel){
		super();
		this.mu = mu;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "maskit";
		directory = new File("./maskit/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Matrix gen_a = new Matrix(mu.mult(new Complex(0, -1)),
								  new Complex(0, -1),
								  new Complex(0, -1),
								  new Complex(0, 0));
		Matrix gen_b = new Matrix(new Complex(1, 0),
								  new Complex(2, 0),
								  new Complex(0, 0),
								  new Complex(1, 0));
		
		setLabels(gen_a);
		setGens(gen_a, gen_b);
	    setFixPoint();
	}
	
	public MaskitGroup(Complex mu, ArrayList<Conjugation> conjugationList, int maxLevel, float epsilon, int expansion, MaskitInputPanel panel){
		super();
		this.mu = mu;
		this.conjugationList = conjugationList;
		this.maxLevel = maxLevel;
		this.epsilon = epsilon;
		this.expansion = expansion;
		this.panel = panel;
		name = "maskit";
		directory = new File("./maskit/");
		if(!directory.exists()){
			directory.mkdirs();
		}
		Matrix gen_a = new Matrix(mu.mult(new Complex(0, -1)),
								  new Complex(0, -1),
								  new Complex(0, -1),
								  new Complex(0, 0));
		Matrix gen_b = new Matrix(new Complex(1, 0),
								  new Complex(2, 0),
								  new Complex(0, 0),
								  new Complex(1, 0));
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
	
	//Indra's Pearls p270 Box24 トレース帰納法 p/qのトレースを求める
	private Complex tracePoly(Farey pq, Complex tr_a, Complex tr_B, Complex tr_aB){
		Farey f_01 = new Farey(0, 1);
		Farey f_10 = new Farey(1, 0);
		if(pq.isSame(f_01)){
			System.out.println("01");
			return tr_a;
		}else if(pq.isSame(f_10)){
			System.out.println("10");
			return tr_B;
		}
		
		Complex tr_u = tr_a;
		Complex tr_v = tr_B;
		Complex tr_uv = tr_aB;
		
		Farey pq1 = f_01;
		Farey pq2 = f_10;
		Farey pq3 = new Farey(1, 1);
		while(!pq3.isSame(pq)){
			if(pq.toDouble() < pq3.toDouble()){
				pq2 = pq3;
				pq3 = pq1.add(pq3);
				Complex temp = tr_uv;
				//System.out.println("tes"+temp);
				tr_uv = tr_u.mult(tr_uv).sub(tr_v);
				//System.out.println(temp);
				tr_v = temp;
			}else{
				pq1 = pq3;
				pq3 = pq3.add(pq2);
				Complex temp = tr_uv;
				//System.out.println("tes"+temp);
				tr_uv = tr_v.mult(tr_uv).sub(tr_u);
				//System.out.println("tes"+temp);
				tr_u = temp;
			}
		}
		return tr_uv;
	}
	 
	//Indra's Pearls p285 Box25 多項式Tp/q - 2の値を求める
	//方程式Tp/q(μ) - 2 = 0の解μを求めるために用いる
	private Complex trace_eqn(int p, int q, Complex mu){
		return tracePoly(new Farey(p, q), 
						 new Complex(0, -1).mult(mu), new Complex(2, 0), new Complex(0, -1).mult(mu).add(new Complex(0, 2))
						).sub(2);
	}
	
	private void drawBoundary(int denom, int expansion){
		System.out.println("calc start");
		Farey pq = new Farey(0, 1);
		Complex mu = new Complex(0, 2);
		int width = (int) DrawPanel.drawPanel.getSize().getWidth();
		int height = (int) DrawPanel.drawPanel.getSize().getHeight();
		
		DrawPanel.maskitSliceImage = DrawPanel.drawPanel.createImage(width, height);
		Graphics2D g2 = (Graphics2D) DrawPanel.maskitSliceImage.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		g2.setColor(Color.BLACK);
		g2.translate(width / 2, height / 2);
		g2.drawRect(0, 0, 1, 1);
		g2.drawRect((int) (mu.re() * expansion), (int) -(mu.im() * expansion), 1, 1);

		while(pq.toDouble() < 1){
			System.out.println("calc...");
			Complex oldMu = mu;
			//pq = nextPq(pq, denom);
			pq = nextPq(pq);
			System.out.println(pq);
			mu = newton(oldMu, pq.nume(), pq.denom());
			if(mu != null){
				System.out.println("plot"+  (mu.re() ) +","+  (mu.im() ));
				g2.drawRect((int) (mu.re() * expansion), (int) -(mu.im() * expansion), 1, 1);
			}else{
				mu = oldMu;
			}
			DrawPanel.drawPanel.repaint();

		}
		
	}
	
	private static final double PARAM_EPSILON = 0.0001;
	//private static final 
	//Indra's Pearls p286 Box26
	private Complex rate(Complex prm, int p, int q){
		Complex smidge = new Complex(0.1 * PARAM_EPSILON, 0);
		//System.out.println(prm.toString());
		Complex imageSmidge = new Complex(0, 0.1 * PARAM_EPSILON);
		Complex hrate = (trace_eqn(p, q, prm.add(smidge)).sub(trace_eqn(p, q, prm.sub(smidge)))).div(smidge.mult(2.0));
		//System.out.println("hrate"+ prm.add(smidge).toString());
		Complex vrate = trace_eqn(p, q, prm.add(imageSmidge)).sub(trace_eqn(p, q, prm.sub(imageSmidge))).div(imageSmidge.mult(2.0));
		return hrate.add(vrate).div(new Complex(2, 0));
	}
	
	private static final int MAX_ITERATION = 100;
	private static final double VALUE_EPSILON = 0.0000000000001;
	//Indra's Pearls p287 Box27 　
	private Complex newton(Complex prm0, int p, int q){
		//System.out.println(trace_eqn(p, q, prm0).abs());
		//System.out.println(prm0.sub(trace_eqn(p, q, prm0)).div(rate(prm0, p, q)).sub(prm0).abs());
		Complex prm;
		for(int i = 1 ; i < MAX_ITERATION ; i++){
			Complex rate = rate(prm0, p, q);
			
			prm = prm0.sub(trace_eqn(p, q, prm0).div(rate));
			System.out.println("loop");
			
			if(trace_eqn(p, q, prm0).abs() <= VALUE_EPSILON && prm.sub(prm0).abs() <= PARAM_EPSILON){
				System.out.println("rate"+ prm.sub(prm0).abs());
				System.out.println("value"+ trace_eqn(p, q, prm0).abs());
				return prm;
			}else{
				prm0 = prm;
			}
		}
		return null;
	}
	
	public static Farey nextPq(Farey pq, int denom){
		Farey pq1 = new Farey(0, 1);
		Farey pq2 = new Farey(1, 0);
		Farey rs = pq;
		//System.out.println("rs"+ rs);
		int sign = -1;
		while(rs.denom() != 0){
			int a = (int) Math.floor(rs.toDouble());
			//System.out.println(a);
			/*if(rs.toDouble()-0.00001 > 0){
				a = (int) Math.floor(rs.toDouble()-0.0000001);
			}else{
				a = (int) Math.ceil(rs.toDouble() - 0.0000001);
			}*/
			//System.out.println("re"+ Math.floor(rs.toDouble()));
			rs = new Farey(rs.denom(), rs.nume() - a * rs.denom());
			Farey temp = pq2;
			pq2 = new Farey(a * pq2.nume() + pq1.nume(), a * pq2.denom() + pq1.denom());
			pq1 = temp;
			//System.out.println("pq1="+ pq1 + "pq2="+ pq2 +"rs="+ rs);
			sign = -sign;
			//System.out.println(sign);
		}
		//double pre =  - 0.00000001;
		int k = (int) Math.floor(((denom - sign*pq1.denom()) / denom));
		return new Farey(k * pq.nume() + sign * pq1.nume(), k * pq.denom() + sign * pq1.denom());
	}
	
	private ArrayList<Farey> fareyList = new ArrayList<Farey>();
	public Farey nextPq(Farey pq){
		
		int i = 0;
		for(Farey f: fareyList){
			if(f.isSame(pq)){
				break;
			}
			i++;
		}
		if(i == fareyList.size()){
			return null;
		}
		return fareyList.get(i + 1);
	}
	
	
	
	public void getFareySequence(int n){
		 int h = 0, k = 1, x = 1, y = 0;
		 fareyList.clear();
		  do {
		    //System.out.println(h + "/"+ k);
		    fareyList.add(new Farey(h, k));
			
		    int r = (n-y)/k;
		    y += r*k;
		    x += r*h;
		    
		    int tmp = x;
		    x = h;
		    h = tmp;
		    
		    tmp = y;
		    y = k;
		    k = tmp;
		    
		    x = -x;
		    y = -y;
		  } while (k > 1);
		  fareyList.add(new Farey(1, 1));
		  //System.out.println("1/1");
	}
	
	protected String getParamCSV(){
		return mu.re() +","+ mu.im();
	}
}
