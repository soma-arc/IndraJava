import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

public class RecipeInputPanel extends JPanel{
	protected int sizeX, sizeY;
	protected static ImageIcon taIcon, tbIcon, tabIcon, tabEqualIcon, tabPlusIcon, tabMinusIcon, z0Icon, gen_aIcon,
							   gen_bIcon, plusIcon, iIcon, muIcon, alphaIcon, betaIcon, gammaIcon, deltaIcon, thetaIcon,
							   T_Icon, A_Icon, I_Icon, R_Icon, C_Icon;
	protected JLabel taLabel, tbLabel, tabLabel, z0Label, gen_aLabel, gen_bLabel, conjugationLabel;
	protected JTextField taField, taImageField, tbField, tbImageField, tabField, tabImageField, levelField, thresholdField, expansionField,
						 realAlphaField, imageAlphaField, realBetaField, imageBetaField, realGammaField, imageGammaField,
						 realDeltaField, imageDeltaField, thetaField, translateXField, translateYField, magnificationX, magnificationY;
	protected JCheckBox drawLimitPointCheck, drawLevel2LimitPointCheck, conjugationCheck, drawAxisCheck;
	protected JRadioButton plusButton, minusButton; 
	protected HorizontalPanel otherParamsRow, generationRow, drawRow, alphaRow, betaRow, gammaRow, deltaRow, conjugationCheckRow, showLimitOptionRow, transformOptionRow;
	protected JButton generateButton, drawButton, cancelButton;
	protected JRadioButton rotateButton, translateButton, involutionButton, handyButton, magnificationButton;
	protected ButtonGroup transformGroup;
	public Fractal fractal;
	public static Thread calcThread;
	protected JPanel conjugationInputPanel, conjugationPanel;
	protected ArrayList<Conjugation> conjugationList = new ArrayList<Conjugation>();
	//protected ArrayList<Matrix> conjugationList = new ArrayList<Matrix>();
	protected ArrayList<String> conjugationWordList = new ArrayList<String>();
	
	public RecipeInputPanel(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMaximumSize(new Dimension(sizeX, sizeY));
		this.setMinimumSize(new Dimension(sizeX, sizeY));
		
		setOtherParamsRow();
		setHandyRows();
		
		//ConjugationInputPanel handyPanel = getHandyPanel();
		//ConjugationInputPanel rotatePanel = getRotatePanel();
		
		conjugationInputPanel = new JPanel();
		conjugationInputPanel.setOpaque(false);
		conjugationInputPanel.setLayout(new CardLayout());
		conjugationInputPanel.add(getHandyPanel(), "éËì¸óÕ");
		conjugationInputPanel.add(getRotatePanel(), "âÒì]");
		conjugationInputPanel.add(getTranslatePanel(), "ïΩçsà⁄ìÆ");
		conjugationInputPanel.add(getInvolutionPanel(), "ëŒçá");
		conjugationInputPanel.add(getMagnificationPanel(), "ìôî{");
		
		conjugationPanel = new JPanel();
		conjugationPanel.setOpaque(false);
		conjugationPanel.setLayout(new BoxLayout(conjugationPanel, BoxLayout.Y_AXIS));
		conjugationPanel.add(conjugationInputPanel);
		conjugationLabel = new JLabel("");
		HorizontalPanel conjugationLabelRow = new HorizontalPanel(sizeX, 30); 
		conjugationLabelRow.add(conjugationLabel);
		conjugationPanel.add(conjugationLabelRow);
		
		setTransformOptionRow();
		
		conjugationCheckRow = new HorizontalPanel(sizeX, 30);
		conjugationCheck = new JCheckBox("ã§ñÇéÊÇÈ");
		conjugationCheck.setOpaque(false);
		conjugationCheckRow.add(conjugationCheck);
		
		showLimitOptionRow = new HorizontalPanel(sizeX, 30);
		drawLimitPointCheck = new JCheckBox("ã…å¿ì_Çï\é¶");
		drawLimitPointCheck.setOpaque(false);
		drawLimitPointCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				DrawPanel.drawPanel.isDrawLimitPoint = drawLimitPointCheck.isSelected();
				DrawPanel.drawPanel.repaint();
			}
		});
		showLimitOptionRow.add(drawLimitPointCheck);
		drawLevel2LimitPointCheck = new JCheckBox("ï°çáñ≥å¿åÍÇÃã…å¿ì_Çï\é¶");
		drawLevel2LimitPointCheck.setOpaque(false);
		drawLevel2LimitPointCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				DrawPanel.drawPanel.isDrawLevel2LimitPoint = drawLevel2LimitPointCheck.isSelected();
				DrawPanel.drawPanel.repaint();
			}
		});
		showLimitOptionRow.add(drawLevel2LimitPointCheck);
		
		generationRow = new HorizontalPanel(sizeX, 30);
		generateButton = new JButton("ê∂ê¨");
		generationRow.add(generateButton);
		
		drawRow = new HorizontalPanel(sizeX, 30);
		drawButton = new JButton("ï`âÊ");
		drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int maxLevel = Integer.valueOf(levelField.getText());
				float epsilon = Float.valueOf(thresholdField.getText());
				int expansion = Integer.valueOf(expansionField.getText());
				if(fractal != null){
					fractal.setOtherParams(maxLevel, epsilon, expansion);
					calcThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try{
								fractal.calcLimitingSetWithDFS();
							}catch(Exception ex){
							}
							DrawPanel.drawPanel.repaint();
						}
					});
					calcThread.start();
				}
			}
		});
		drawRow.add(drawButton);
		cancelButton = new JButton("ÉLÉÉÉìÉZÉã");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(RecipeInputPanel.calcThread != null && RecipeInputPanel.calcThread.isAlive())
					RecipeInputPanel.calcThread.interrupt();
			}
		});
		drawRow.add(cancelButton);
		drawAxisCheck = new JCheckBox("ç¿ïWé≤Çï\é¶Ç∑ÇÈ");
		drawAxisCheck.setOpaque(false);
		drawAxisCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				DrawPanel.drawPanel.isDrawAxis = drawAxisCheck.isSelected();
				DrawPanel.drawPanel.repaint();
			}
		});
		drawRow.add(drawAxisCheck);
		
		realAlphaField.addKeyListener(new InputFieldKeyListener(imageAlphaField));
		imageAlphaField.addKeyListener(new InputFieldKeyListener(realBetaField));
		realBetaField.addKeyListener(new InputFieldKeyListener(imageBetaField));
		imageBetaField.addKeyListener(new InputFieldKeyListener(realGammaField));
		realGammaField.addKeyListener(new InputFieldKeyListener(imageGammaField));
		imageGammaField.addKeyListener(new InputFieldKeyListener(realDeltaField));
		realDeltaField.addKeyListener(new InputFieldKeyListener(imageDeltaField));
		imageDeltaField.addKeyListener(new InputFieldKeyListener(levelField));
		levelField.addKeyListener(new InputFieldKeyListener(thresholdField));
		thresholdField.addKeyListener(new InputFieldKeyListener(expansionField));
		expansionField.addKeyListener(new InputFieldKeyListener(null));
	}
	
	private void setOtherParamsRow(){
		otherParamsRow = new HorizontalPanel(sizeX, 30);
		otherParamsRow.add(new JLabel("MaxLevel"));
		levelField = new JTextField("15");
		levelField.setMaximumSize(new Dimension(60, 30));
		otherParamsRow.add(levelField);
		
		otherParamsRow.add(new JLabel("Ëáíl"));
		thresholdField = new JTextField("0.006");
		thresholdField.setMaximumSize(new Dimension(60, 30));
		otherParamsRow.add(thresholdField);
		
		expansionField = new JTextField("100");
		expansionField.setMaximumSize(new Dimension(60, 30));
		otherParamsRow.add(new JLabel("ägëÂó¶"));
		otherParamsRow.add(expansionField);
	}
	
	private ConjugationInputPanel getHandyPanel(){
		T_Icon = new ImageIcon("./res/t.png");
		ConjugationInputPanel panel = new ConjugationInputPanel(sizeX, 300);
		panel.add(alphaRow);
		panel.add(betaRow);
		panel.add(gammaRow);
		panel.add(deltaRow);
		panel.add(getIconRow(sizeX, 60, T_Icon));
		panel.addConjugationButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isHandyConjugationFieldFilled()){
					conjugationList.add(new HandyConjugation(getHandyConjugation()));
					//conjugationList.add(getHandyConjugation());
					conjugationWordList.add("T");
					conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
				}else{
					showInputFieldError();
				}
			}
		}, new UndoConjugationListener());
		return panel;
	}
	
	private ConjugationInputPanel getRotatePanel(){
		ConjugationInputPanel panel = new ConjugationInputPanel(sizeX, 300);
		thetaIcon = new ImageIcon("./res/theta=.png");
		R_Icon = new ImageIcon("./res/rotate.png");
		thetaField = new JTextField();
		thetaField.addKeyListener(new InputFieldKeyListener(null));
		thetaField.setMaximumSize(new Dimension(100, 30));
		HorizontalPanel thetaInputRow = new HorizontalPanel(sizeX, 30);
		thetaInputRow.add(new JLabel(thetaIcon));
		thetaInputRow.add(thetaField);
		thetaInputRow.add(new JLabel("ìx"));
		panel.add(thetaInputRow);
		panel.add(getIconRow(sizeX, 50, R_Icon));
		panel.addConjugationButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!thetaField.getText().equals("")){
					conjugationList.add(new RotateConjugation(Double.valueOf(thetaField.getText())));
					//conjugationList.add(getRotateConjugation());
					conjugationWordList.add("R");
					conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
				}else{
					showInputFieldError();
				}
			}
		}, new UndoConjugationListener());
		return panel;
	}
	
	private ConjugationInputPanel getTranslatePanel(){
		A_Icon = new ImageIcon("./res/affine.png");
		ConjugationInputPanel panel = new ConjugationInputPanel(sizeX, 300);
		HorizontalPanel xRow = new HorizontalPanel(sizeX, 30);
		translateXField = new JTextField();
		translateXField.setMaximumSize(new Dimension(100, 30));
		translateXField.addKeyListener(new InputFieldKeyListener());
		xRow.add(new JLabel("xÇÃà⁄ìÆ"));
		xRow.add(translateXField);
		HorizontalPanel yRow = new HorizontalPanel(sizeX, 30);
		yRow.add(new JLabel("yÇÃà⁄ìÆ"));
		translateYField = new JTextField();
		translateYField.setMaximumSize(new Dimension(100, 30));
		translateYField.addKeyListener(new InputFieldKeyListener());
		yRow.add(translateYField);
		panel.add(xRow);
		panel.add(yRow);
		panel.add(getIconRow(sizeX, 50, A_Icon));
		panel.addConjugationButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isTranslateFieldFilled()){
					conjugationList.add(new TranslateConjugation(getComplex(translateXField, translateYField)));
					//conjugationList.add(getTranslateConjugation());
					conjugationWordList.add("A");
					conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
				}else{
					showInputFieldError();
				}
			}
		}, new UndoConjugationListener());
		return panel;
	}
	
	private ConjugationInputPanel getInvolutionPanel(){
		I_Icon = new ImageIcon("./res/involution.png");
		ConjugationInputPanel panel = new ConjugationInputPanel(sizeX, 300);
		panel.add(getIconRow(sizeX, 50, I_Icon));
		panel.addConjugationButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conjugationList.add(new InvolutionConjugation());
				//conjugationList.add(new Matrix(new Complex(0, 0), new Complex(1, 0),
					//						   new Complex(1, 0), new Complex(0, 0)));
				conjugationWordList.add("I");
				conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
			}
		}, new UndoConjugationListener());
		return panel;
	}
	
	private ConjugationInputPanel getMagnificationPanel(){
		C_Icon = new ImageIcon("./res/magnification.png");
		ConjugationInputPanel panel = new ConjugationInputPanel(sizeX, 300);
		HorizontalPanel xRow = new HorizontalPanel(sizeX, 30);
		magnificationX = new JTextField();
		magnificationX.setMaximumSize(new Dimension(50, 30));
		magnificationX.addKeyListener(new InputFieldKeyListener());
		xRow.add(new JLabel("î{ó¶"));
		xRow.add(magnificationX);
		xRow.add(new JLabel("î{"));
		HorizontalPanel yRow = new HorizontalPanel(sizeX, 30);
		magnificationY = new JTextField();
		magnificationY.setMaximumSize(new Dimension(50, 30));
		magnificationY.addKeyListener(new InputFieldKeyListener());
		yRow.add(new JLabel("yï˚å¸"));
		yRow.add(magnificationY);
		yRow.add(new JLabel("î{"));
		panel.add(xRow);
		//panel.add(yRow);
		panel.add(getIconRow(sizeX, 50, C_Icon));
		panel.addConjugationButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isMagnificationFieldFilled()){
					conjugationList.add(new MagnificationConjugation(Double.valueOf(magnificationX.getText())));
					//conjugationList.add(new Matrix(new Complex(Double.valueOf(magnificationX.getText()), 0), new Complex(0, 0),
						//						   new Complex(0, 0)                         , new Complex(1, 0)));
					conjugationWordList.add("C");
					conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
				}else{
					showInputFieldError();
				}
			}
		}, new UndoConjugationListener());
		return panel;
	}
	
	class AddConjugationListener implements ActionListener{
		String word;
		public AddConjugationListener(String word) {
			this.word = word;
		}
		
		public void actionPerformed(ActionEvent e){
			//conjugationlist.add(new Matrix())
			conjugationWordList.add(word);
			conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
		}
	}
	
	class UndoConjugationListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(conjugationWordList.size()!= 0){
				conjugationList.remove(conjugationList.size() - 1);
				conjugationWordList.remove(conjugationWordList.size() -1);
				conjugationLabel.setIcon(LaTexHandler.getConjugationIcon(conjugationWordList, "a", 20));
			}
		}
	}
	
	private void setHandyRows(){
		plusIcon = new ImageIcon("./res/plus.png");
		iIcon = new ImageIcon("./res/i.png");
		alphaIcon = new ImageIcon("./res/alpha=.png");
		betaIcon = new ImageIcon("./res/beta=.png");
		gammaIcon = new ImageIcon("./res/gamma=.png");
		deltaIcon = new ImageIcon("./res/delta=.png");
		
		realAlphaField = new JTextField();
		imageAlphaField = new JTextField();
		alphaRow = getComplexInputRow(alphaIcon, realAlphaField, imageAlphaField);
		
		realBetaField = new JTextField();
		imageBetaField = new JTextField();
		betaRow = getComplexInputRow(betaIcon, realBetaField, imageBetaField);
		
		realGammaField = new JTextField();
		imageGammaField = new JTextField();
		gammaRow = getComplexInputRow(gammaIcon, realGammaField, imageGammaField);
		
		realDeltaField = new JTextField();
		imageDeltaField = new JTextField();
		deltaRow = getComplexInputRow(deltaIcon, realDeltaField, imageDeltaField);
	}
	
	
	private void setTransformOptionRow(){
		transformGroup = new ButtonGroup();
		transformOptionRow = new HorizontalPanel(sizeX, 30);
		
		handyButton = getTransformRadioButton("éËì¸óÕ");
		transformOptionRow.add(handyButton);
		
		rotateButton = getTransformRadioButton("âÒì]");
		transformOptionRow.add(rotateButton);
		
		translateButton = getTransformRadioButton("ïΩçsà⁄ìÆ");
		transformOptionRow.add(translateButton);
		
		involutionButton = getTransformRadioButton("ëŒçá");
		transformOptionRow.add(involutionButton);
		
		magnificationButton = getTransformRadioButton("ìôî{");
		transformOptionRow.add(magnificationButton);
		
		handyButton.setSelected(true);
	}
	
	protected HorizontalPanel getIconRow(int sizeX, int sizeY, ImageIcon icon){
		HorizontalPanel panel = new HorizontalPanel(sizeX, sizeY);
		panel.add(new JLabel(icon));
		return panel;
	}
	
	private JRadioButton getTransformRadioButton(String name){
		JRadioButton button = new JRadioButton(name);
		button.setOpaque(false);
		transformGroup.add(button);
		button.addItemListener(new TransformChangeListener(name));
		return button;
	}
	
	protected boolean isHandyConjugationFieldFilled(){
		if(!realAlphaField.getText().equals("") && !imageAlphaField.getText().equals("") &&
		   !realBetaField.getText().equals("") && !imageBetaField.getText().equals("") &&
		   !realGammaField.getText().equals("") && !imageGammaField.getText().equals("") &&
		   !realDeltaField.getText().equals("") && !imageDeltaField.getText().equals("")){
				return true;
		}
		return false;
	}
	
	protected boolean isTranslateFieldFilled(){
		if(!translateXField.getText().equals("") && !translateYField.getText().equals("")){
			return true;
		}
		return false;
	}
	
	protected boolean isMagnificationFieldFilled(){
		if(!magnificationX.getText().equals("") /*&& !magnificationY.getText().equals("")*/){
			return true;
		}
		return false;
	}
	
	protected Matrix getTranslateConjugation(){
		return new Matrix(new Complex(1, 0), getComplex(translateXField, translateYField),
						  new Complex(0, 0), new Complex(1, 0));
	}
	
	protected Matrix getRotateConjugation(){
		double radian = Math.toRadians(Double.valueOf(thetaField.getText()));
		return new Matrix(new Complex(Math.cos(radian), Math.sin(radian)), new Complex(0, 0),
						  new Complex(0, 0), new Complex(1, 0));
	}
	
	protected Matrix getHandyConjugation(){
		Complex alpha = getComplex(realAlphaField, imageAlphaField);
		Complex beta = getComplex(realBetaField, imageBetaField);
		Complex gamma = getComplex(realGammaField, imageGammaField);
		Complex delta = getComplex(realDeltaField, imageDeltaField);
		return new Matrix(alpha, beta,
						  gamma, delta);
	}
	
	protected void addConjugationRow(){
		add(alphaRow);
		add(betaRow);
		add(gammaRow);
		add(deltaRow);
	}
	
	protected HorizontalPanel getComplexInputRow(ImageIcon paramIcon, JTextField real, JTextField image){
		HorizontalPanel p = new HorizontalPanel(sizeX, 30);
		real.addKeyListener(new InputFieldKeyListener());
		image.addKeyListener(new InputFieldKeyListener());
		p.add(new JLabel(paramIcon));
		p.add(real);
		p.add(new JLabel(plusIcon));
		p.add(image);
		p.add(new JLabel(iIcon));
		return p;
	}
	
	protected JLabel createLabel(ImageIcon icon, int x, int y){
		JLabel j = new JLabel(icon);
		j.setMaximumSize(new Dimension(x, y));
		j.setOpaque(false);
		return j;
	}
	
	protected JLabel createLabel(String caption, int x, int y){
		JLabel j = new JLabel(caption);
		j.setMaximumSize(new Dimension(x, y));
		j.setOpaque(false);
		return j;
	}
	
	protected JRadioButton createRadioButton(String caption){
		JRadioButton button = new JRadioButton(caption);
		button.setOpaque(false);
		return button;
	}
	
	protected boolean isOtherParamsFilled(){
		if(!levelField.getText().equals("") && !thresholdField.getText().equals("") && 
		   !expansionField.getText().equals("")){
				return true;
		}
		return false;
	}
	
	protected Complex getComplex(String strReal, String strImage){
		double re = Double.valueOf(strReal);
		double im = Double.valueOf(strImage);
		return new Complex(re, im);
	}
	
	protected Complex getComplex(JTextField real, JTextField image){
		double re = Double.valueOf(real.getText());
		double im = Double.valueOf(image.getText());
		return new Complex(re, im);
	}
	
	protected Matrix getCompositeConjugation(){
		Matrix t = new Matrix(1, 0, 0, 1);
		for(int i = 0 ; i < conjugationList.size() ; i++){
			t = t.mult(conjugationList.get(i).getMatrix());
		}
		return t;
	}
	
	public class TransformChangeListener implements ItemListener{
		int index;
		String name;
		public TransformChangeListener(String name) {
			this.name = name;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				((CardLayout) conjugationInputPanel.getLayout()).show(conjugationInputPanel, name);
			}
		}
	}
	
	protected void loadIcon(){}
	protected void setUI(){}
	protected void showInputFieldError(){
		JOptionPane.showMessageDialog(this, "ì¸óÕÇ≥ÇÍÇƒÇ¢Ç»Ç¢ÉtÉBÅ[ÉãÉhÇ™Ç†ÇËÇ‹Ç∑");
	}
	public String getName() {return "";
	}
}
