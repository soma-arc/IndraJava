import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.TeXFormula.TeXIconBuilder;


public abstract class LaTexHandler {
	public static ImageIcon getFormulaIcon(String latex, int size){
		TeXFormula formula = new TeXFormula(latex);
		TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(size).build();
		icon.setInsets(new Insets(5, 5, 5, 5));

		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
		JLabel jl = new JLabel();
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		
		return new ImageIcon(image);
	}
	public static ImageIcon getMatrixIcon(String left, String a, String b, String c, String d, int size){
		String latex = left +"=\\left("
								+ "\\begin{array}{cc}"
									+ a +"&"+ c +"\\\\"
									+ c +"&"+ d +""
								+ "\\end{array}"
							+ "\\right)";
		return getFormulaIcon(latex, size);
	}
	public static ImageIcon getMatrixIcon(String left, Complex a, Complex b, Complex c, Complex d, int size){
		int digit = 6;
		String latex = left +"=\\left("
								+ "\\begin{array}{cc}"
									+ a.round(digit).toString() +"&"+ b.round(digit).toString() +"\\\\"
									+ c.round(digit).toString() +"&"+ d.round(digit).toString() +""
								+ "\\end{array}"
							+ "\\right)";
		return getFormulaIcon(latex, size);
	}
	
	public static ImageIcon getMatrixIcon(String left, Matrix m, int size){
		int digit = 6;
		String latex = left +"=\\left("
								+ "\\begin{array}{cc}"
									+ m.a.round(digit).toString() +"&"+ m.b.round(digit).toString() +"\\\\"
									+ m.c.round(digit).toString() +"&"+ m.d.round(digit).toString() +""
								+ "\\end{array}"
							+ "\\right)";
		return getFormulaIcon(latex, size);
	}
	public static ImageIcon getConjugationIcon(ArrayList<String> wordList, String origin, int size){
		String latex = "";
		for(int i = wordList.size() - 1 ; i >= 0 ; i--){
			latex += wordList.get(i);
		}
		latex += origin;
		for(String w : wordList){
			latex += w +"^{-1}";
		}
		return getFormulaIcon(latex, size);
	}
}
