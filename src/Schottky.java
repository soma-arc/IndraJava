import java.awt.Graphics;

import static java.lang.Math.*;

public class Schottky extends TwinGroup{
	Matrix gen_a, gen_b;
	Circle c_a, c_b, c_A, c_B;
	Schottky(){
		
	}
	
	private void setThetaSchottky(Graphics g, float theta, int expansion){
		float r = (float) (1/cos(theta));
		float rad = (float) tan(theta);
		gen_a = new Matrix(new Complex(1.0f, 0f)              , new Complex(0f, (float) cos(theta)),
								  new Complex(0f, (float)-cos(theta)), new Complex(1.0f, 0f)).mult((float) (1/sin(theta)));
		gen_b = new Matrix(new Complex(1.0f, 0f)            ,   new Complex((float)cos(theta), 0f),
								  new Complex((float)cos(theta),0f),   new Complex(1.0f, 0f)).mult((float)(1/sin(theta)));
		c_a = new Circle(rad, new Complex( 0,  r));
		c_A = new Circle(rad, new Complex( 0, -r));
		c_b = new Circle(rad, new Complex( r,  0));
		c_B = new Circle(rad, new Complex(-r,  0));
		calcSchottkyWithBFS(g, expansion);
	}
	
	//Indra's pearls p109 Box12
	private void calcSchottkyWithBFS(Graphics g, int expansion){
		Matrix[] gens = new Matrix[5];
		Matrix[] group = new Matrix[1000000];
		Circle[] circle = new Circle[5];
		//Circle  newCircle;
		//  int level;
		final int MAX_LEVEL = 5;
		int[] tag = new int[1000000];
		int[] num = new int[MAX_LEVEL + 1];
		int[] inverse = new int[5];

		gens[1] = gen_a;
		gens[2] = gen_b;
		gens[3] = gens[1].inverse();
		gens[4] = gens[2].inverse();
		inverse[1] = 3;
		inverse[2] = 4;
		inverse[3] = 1;
		inverse[4] = 2;
		circle[1] = c_a;
		circle[2] = c_b;
		circle[3] = c_A;
		circle[4] = c_B;

		for(int i = 1 ; i <= 4 ; i++){
			group[i] = gens[i];
			tag[i] = i;
			//fill(0,0,50);
			//fill(255);
			//println(circle[i].toString());
			circle[i].draw(g, expansion);
			//println(circle[i].expand(expansion).toString());
			//fill(255);
		}

		num[1] = 1;
		num[2] = 5;
		for(int level = 2 ; level <= MAX_LEVEL - 1 ; level++){
			int inew = num[level];
			for(int iold = num[level -1] ; iold <= num[level] -1 ; iold++){
				for(int j = 1 ; j <= 4 ; j++){
					if(j == inverse[tag[iold]]){
						continue;
					}
					group[inew] = Matrix.mult(group[iold], gens[j]);
					tag [inew] = j;
					inew++;
				}
			}
			num[level + 1] = inew;
		}
		int lev = 0;
		for(int i = 1; i <= num[MAX_LEVEL] -1 ; i++){
			for(int j = 0 ; j <= MAX_LEVEL ; j++){
				if(num[j] == i){
					lev++;
				}
			}
			for(int j = 1 ; j <= 4 ; j++){
				if(j == inverse[tag[i]]){
					continue;
				}
				Circle newCircle = mobius_on_circle(group[i], circle[j]);
				//fill(0, 0, lev * 60 + 60);
				//fill(255);
				newCircle.draw(g);
				//println(newCircle.expand(expansion).toString());
			}
		}
	}
}
