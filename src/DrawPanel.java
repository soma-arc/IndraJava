import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants; 
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.io.*;
public class DrawPanel extends JPanel{
	private static JFrame frame;
	private final int FRAME_SIZE_X = 1250;
	private final int FRAME_SIZE_Y = 800;
	private final int DRAW_PANEL_SIZE_X = 700;
	private final int UI_PANEL_SIZE_X = FRAME_SIZE_X - DRAW_PANEL_SIZE_X;
	private static UIPanel interfacePanel;
	public static DrawPanel drawPanel;
	public static Connection sqliteConnection;
	public static Statement sqliteStatement;
	public boolean isDrawLimitPoint = false;
	public boolean isDrawLevel2LimitPoint = false;
	public boolean isDrawAxis = false;
	
	public static void main(String[] args) {
		drawPanel = new DrawPanel();
		drawPanel.start();
	}
	
	private void start(){	
		//Complex t = new Complex(1.958591030, -0.011278560);
		//System.out.println(t.abs());
		//Matrix a = new Matrix(1, 2, 3, 4);
		//Matrix b = new Matrix(1, 0, 0, 1);
		//System.out.println(b.mult(a));
		/*try{
			boolean fileExists = false;
			File dbFile = new File("fractal.db");
			if(dbFile.exists()){
				fileExists = true;
			}
			Class.forName("org.sqlite.JDBC");
			sqliteConnection = DriverManager.getConnection("jdbc:sqlite:fractal.db");
			sqliteStatement = sqliteConnection.createStatement();
			if(!fileExists){
				createTables();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
		SQLiteHandler.init();
		String[] w = {"a", "b", "A", "B"};
		//new MaskitGroup(150, 10);
		
		String latex = "C=\\left("
     						+ "\\begin{array}{cc}"
     								+ "k & 0 \\\\"
     								+ "0 & 1"
     						+ "\\end{array}"
     					+ "\\right)";
		TeXFormula formula = new TeXFormula(latex);
		TeXIcon icon = formula.new TeXIconBuilder().setStyle(TeXConstants.STYLE_DISPLAY).setSize(20).build();
		icon.setInsets(new Insets(5, 5, 5, 5));

		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
	
		g2.setColor(Color.white);
		g2.fillRect(0,0,icon.getIconWidth(),icon.getIconHeight());
		JLabel jl = new JLabel(icon);
		jl.setForeground(new Color(0, 0, 0));
		icon.paintIcon(jl, g2, 0, 0);
		File file = new File("magnification.png");
		//try {ImageIO.write(image, "png", file.getAbsoluteFile());} catch (IOException ex) {}
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, FRAME_SIZE_X, FRAME_SIZE_Y);
		frame.setTitle("Indra's Pearls");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//this.setBorder(new BevelBorder(BevelBorder.RAISED));
		//this.setBorder(new TitledBorder(""));
		//setMaximumSize(new Dimension(DRAW_PANEL_SIZE_X, FRAME_SIZE_Y));
		
		frame.getContentPane().add(this);
		interfacePanel = new UIPanel(UI_PANEL_SIZE_X, FRAME_SIZE_Y);
		JScrollPane j = new JScrollPane(interfacePanel,
										JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		j.setMaximumSize(new Dimension(UI_PANEL_SIZE_X, 2000));
		frame.getContentPane().add(j);
		frame.setVisible(true);
		repaint();
		//MaskitGroup m = new MaskitGroup(57, 100);
		//System.out.println(MaskitGroup.nextPq(1, 2, 6 ));
		//MaskitGroup.farey(6);
		//String latex = "\\frac{t_at_b+\\sqrt[]{t_a^2t_b^2-4(t_a^2+t_b^2)}}{2}";
		/*String latex = "a=\\left("
				             + "\\begin{array}{cc}"
				                + "\\frac{t_a}{2} & \\frac{t_at_a_b - 2t_b + 4i}{(2t_a_b + 4)z_0} \\"
				                + "\\\\frac{(t_at_a_b - 2t_b - 4i)z_0}{2t_a_b - 4} & \\frac{t_a}{2}"
				             + "\\end{array}"
				         + "\\right)";*/
		/*String latex = "b=\\left("
							+ "\\begin{array}{cc}"
								+ "\\frac{t_b -2i}{2} & \\frac{t_b}{2} \\"
								+ "\\\\frac{t_b}{2} & \\frac{t_b + 2i}{2}"
							+ "\\end{array}"
					   + "\\right)";*/
		//String latex = "z_0=\\frac{(t_a_b - 2)t_b}{t_at_a_b - 2t_a + 2it_a_b}";
		/*String latex = "a=\\left("
	             			+ "\\begin{array}{cc}"
	             				+ "t_a - \\frac{t_b}{t_a_b} & \\frac{t_a}{t^2_a_b} \\\\"
	             				+ "t_a                      & \\frac{t_b}{t_a_b}"
	             			+ "\\end{array}"
	              	   + "\\right)";*/
		/*String latex = "b=\\left("
     						+ "\\begin{array}{cc}"
     							+ "t_b - \\frac{t_a}{t_a_b} & -\\frac{t_b}{t^2_a_b} \\\\"
     							+ "-t_b                     & \\frac{t_a}{t_a_b}"
     						+ "\\end{array}"
     				  + "\\right)";*/
		/*String latex = "b=\\left("
							+ "\\begin{array}{cc}"
								+ "1 & 2 \\\\"
								+ "0 & 1"
							+ "\\end{array}"
					+ "\\right)";*/
		//String latex = "t_C = t^2_a + t^2_b + t^2_a_b - t_at_bt_a_b -2";
		//String latex = "R = \\pm\\sqrt[]{t_C + 2}";
		//String latex = "z_0 = \\frac{(t_a_b - 2)(t_b + R)}{t_at_a_b - 2t_a + iQt_a_b}";
		/*String latex = "a=\\left("
							+ "\\begin{array}{cc}"
								+ "\\frac{t_a}{2}                                 & \\frac{t_at_a_b - 2t_b + 2iQ}{(2t_a_b + 4)z_0} \\\\"
								+ "\\frac{(t_at_a_b - 2t_b - 2iQ)z_0}{2t_a_b - 4} & \\frac{t_a}{2}"
							+ "\\end{array}"
					   + "\\right)";*/
		/*String latex = "b=\\left("
						  + "\\begin{array}{cc}"
						  		+ "\\frac{t_b -iQ}{2}      & \\frac{t_bt_a_b -2t_b -iQt_a_b}{(2t_a_b + 4)z_0} \\\\"
						  		+ "\\frac{(t_bt_a_b -2t_a + iQt_a_b)z_0}{2t_a_b - 4} & \\frac{t_b + iQ}{2}"
						  + "\\end{array}"
					   + "\\right)";*/
		//f = new TripletGroup(new Complex(-0.04, 0.97), new Complex(-0.16, 3.87), 15, 0.006, 100);
	}
	//Fractal f;
	private static final String FRACTAL_TABLE_DATA = "fractal_id integer primary key autoincrement, file_name text, org_date text, year integer, month integer, day integer,"
													+"hour integer, minute intger, second integer, recipe_id integer, param_csv text, is_exists_conjugation integer, max_level integer, epsilon real, expansion integer";
	private void createTables(){
		try{
			/*sqliteStatement.execute("create table grandmas_special_table("+ COMMON_TABLE_DATA +", t_a text, t_b text)");
			sqliteStatement.execute("create table jorgensens_table("+ COMMON_TABLE_DATA +", t_a text, t_b text)");
			sqliteStatement.execute("create table rileys_table("+ COMMON_TABLE_DATA +", c text)");
			sqliteStatement.execute("create table grandmas_good_at_table("+ COMMON_TABLE_DATA +", t_a text, t_b text, t_ab text)");
			sqliteStatement.execute("create table conjugation_table(id integer, )");*/
			sqliteStatement.execute("create table fractal_table("+ FRACTAL_TABLE_DATA +")");
			sqliteStatement.execute("create table conjugation_table(fractal_id integer, word_id integer, param text)");
			sqliteStatement.execute("create table recipe_table(recipe_id integer primary key autoincrement, recipe_name text)");
			sqliteStatement.execute("create table word_table(word_id integer primary key autoincrementl, word_name text)");
			insertRecipeData();
			insertConjugationWordData();	
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	private void insertRecipeData(){
		try{
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('granmas_special')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('jorgensen')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('riley')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('granmas_good_at')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('maskit')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('triplet')");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void insertConjugationWordData(){
		try{
			sqliteStatement.execute("insert into word_table (word_name) values ('T')");
			sqliteStatement.execute("insert into word_table (word_name) values ('R')");
			sqliteStatement.execute("insert into word_table (word_name) values ('A')");
			sqliteStatement.execute("insert into word_table (word_name) values ('I')");
			sqliteStatement.execute("insert into word_table (word_name) values ('C')");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static Image maskitSliceImage;
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, this.getWidth() , this.getHeight());
		g2.translate(this.getWidth() / 2, this.getHeight() / 2);
		g2.setColor(Color.GRAY);
		if(isDrawAxis){
			g2.drawLine(-this.getWidth(), 0, this.getWidth(), 0);
			g2.drawLine(0, -this.getHeight(), 0, this.getHeight());
		}
		for(Component c : interfacePanel.inputPanel.getComponents()){
			if(c.isVisible()){
				RecipeInputPanel p = (RecipeInputPanel) c;
				if(p.fractal != null){
					p.fractal.draw(g2);
					if(isDrawLimitPoint)
						p.fractal.drawLimitPoint(g2);
					if(isDrawLevel2LimitPoint){
						p.fractal.drawComplexLimitPoint(g2);
					}
				}
			}
		}
		if(maskitSliceImage != null){
			g2.drawImage(maskitSliceImage, 
					 	 0,
					 	 0,
					 	 this.getWidth(),
					 	 this.getHeight(),
					 	 this);
		}
		//for(interfacePanel.getComponents()){
		//}
		//interfacePanel.fractal.draw(g2);
		//g2.drawOval(10, 10, 200, 200);
	}
}
