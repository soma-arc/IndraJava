import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;

public class UIPanel extends JPanel{
	private static int sizeX, sizeY;
	private static ImageIcon taImage, tbImage, tabImage, tabPlusImage, tabMinusImage, tab, grandmasSpecialGen_aImage, grandmasSpecialGen_bImage,
							 grandmasSpecialZ0;
	public UIPanel(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		setSize(new Dimension(sizeX, sizeY));
		//setMaximumSize(new Dimension(sizeX, 2000));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		loadFormulaImages();
		setUI();
		repaint();
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void loadFormulaImages(){
		try{
			taImage = new ImageIcon("ta=.png"); 
			tbImage = new ImageIcon("tb=.png");
			tabImage = new ImageIcon("tab=.png");
			tab = new ImageIcon("tab.png");
			tabPlusImage = new ImageIcon("tab-plus2.png");
			tabMinusImage = new ImageIcon("tab-minus2.png");
			grandmasSpecialGen_aImage = new ImageIcon("grandmas-special-matrix-a.png");
			grandmasSpecialGen_bImage = new ImageIcon("grandmas-special-matrix-b.png");
			grandmasSpecialZ0 = new ImageIcon("grandmas-special-z0.png");
		}catch(Exception e){}
	}
	
	private void setUI(){
		setRecipe();
		setParameterInterface();
		setInfoPanel();
		defaultButton.setSelected(true);
	}
	
	ButtonGroup recipeGroup;
	JRadioButton defaultButton;
	private JRadioButton linear, theta, grandmaParabolic, jorgensen, riley, maskette, grandmaSpecialty;
	private void setRecipe(){
		recipeGroup = new ButtonGroup();
		JPanel recipePanel = new JPanel();
		recipePanel.setLayout(new GridLayout(6, 1));
		recipePanel.setBorder(new TitledBorder("レシピ"));
		recipePanel.setMaximumSize(new Dimension(sizeX, 100));
		//recipePanel.setMinimumSize(new Dimension(sizeX, 100));
		recipePanel.setOpaque(false);
		
		defaultButton = getRecipeButton("おばあちゃんの放物型交換子群");
		recipePanel.add(defaultButton);
		recipePanel.add(getRecipeButton("ヨルゲンセンのレシピ"));
		recipePanel.add(getRecipeButton("ライリーのレシピ"));
		recipePanel.add(getRecipeButton("おばあちゃんの二元生成群"));
		recipePanel.add(getRecipeButton("マスキットのレシピ"));
		recipePanel.add(getRecipeButton("三元生成"));
		this.add(recipePanel);
	}
	
	JPanel inputPanel, batchPanel;
	private void setParameterInterface(){
		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		tabPanel.setBackground(Color.WHITE);
		tabPanel.setOpaque(true);
		inputPanel = new JPanel();
		inputPanel.setMaximumSize(new Dimension(sizeX, this.getHeight()));
		inputPanel.setBackground(Color.WHITE);
		inputPanel.setBorder(new TitledBorder("元の生成"));
		inputPanel.setLayout(new CardLayout());
		inputPanel.add(new GrandmasSpecialInputPanel(sizeX, 200), "おばあちゃんの放物型交換子群");
		inputPanel.add(new JorgensensInputPanel(sizeX, 200), "ヨルゲンセンのレシピ");
		inputPanel.add(new RileyInputPanel(sizeX, 200), "ライリーのレシピ");
		inputPanel.add(new GrandmasGoodAtDishInputPanel(sizeX, 200), "おばあちゃんの二元生成群");
		inputPanel.add(new MaskitInputPanel(sizeX, 200), "マスキットのレシピ");
		inputPanel.add(new TripletInputPanel(sizeX, 200), "三元生成");
		tabPanel.add("Normal", inputPanel);
		
		((CardLayout) inputPanel.getLayout()).show(inputPanel, "おばあちゃんの放物型交換子群");
		inputPanel.setVisible(true);
		
		batchPanel = new JPanel();
		batchPanel.setMaximumSize(new Dimension(sizeX, this.getHeight()));
		batchPanel.setBackground(Color.WHITE);
		batchPanel.setBorder(new TitledBorder("処理"));
		batchPanel.setLayout(new CardLayout());
		batchPanel.add(new GrandmasSpecialBatch(sizeX, 200), "おばあちゃんの放物型交換子群");
		tabPanel.add("Batch", batchPanel);
		((CardLayout) batchPanel.getLayout()).show(batchPanel, "おばあちゃんの放物型交換子群");
		
		this.add(tabPanel);
	}
	
	
	private JPanel infoPanel;
	private JLabel numOfLimitingSetLabel, timeLabel;
	public static JLabel millisLabel, stateLabel;
	private void setInfoPanel(){
		infoPanel = new JPanel();
		infoPanel.setOpaque(false);
		infoPanel.setBorder(new TitledBorder(""));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		//infoPanel.setMaximumSize(new Dimension(sizeX, 30));
		//infoPanel.setMinimumSize(new Dimension(sizeX, 30));
		/*JButton cancelButton = new JButton("キャンセル");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(RecipeInputPanel.calcThread != null && RecipeInputPanel.calcThread.isAlive())
					RecipeInputPanel.calcThread.interrupt();
			}
		});
		infoPanel.add(cancelButton);*/
		HorizontalPanel stateRow = new HorizontalPanel(sizeX, 20);
		stateLabel = new JLabel("state : blank");
		stateLabel.setFont(new Font(Font.DIALOG,Font.PLAIN,14));
		
		stateRow.add(stateLabel);
		HorizontalPanel numRow = new HorizontalPanel(sizeX, 20);
		numRow.add(new JLabel("計算時間"));
		millisLabel = new JLabel("0");
		numRow.add(millisLabel);
		HorizontalPanel SSRow = new HorizontalPanel(sizeX, 30);
		JButton SSButton = new JButton("スクリーンショット");
		
		SSButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					RecipeInputPanel recipeInputPanel = null;
					for(Component c : inputPanel.getComponents()){
						if(c.isVisible()){
							recipeInputPanel = (RecipeInputPanel) c;
							if (recipeInputPanel.fractal != null) {
								Calendar ca = Calendar.getInstance();
								/*recipeInputPanel.fractal.saveScreenshot(new File(SQLiteHandler.getFormattedData(ca.getTime()) +".jpg"),
																		DrawPanel.drawPanel.getWidth(),
																		DrawPanel.drawPanel.getHeight(),
																		DrawPanel.drawPanel.isDrawAxis,
																		DrawPanel.drawPanel.isDrawLimitPoint,
																		DrawPanel.drawPanel.isDrawLevel2LimitPoint);*/
								recipeInputPanel.fractal.save(DrawPanel.drawPanel.getWidth(),
															  DrawPanel.drawPanel.getHeight(),
															  DrawPanel.drawPanel.isDrawAxis,
															  DrawPanel.drawPanel.isDrawLimitPoint,
															  DrawPanel.drawPanel.isDrawLevel2LimitPoint);
							}else{	
								return;
							}
						}
					}
					JOptionPane.showMessageDialog(UIPanel.this, "図形を画像として保存しました");
				}catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(UIPanel.this, "保存に失敗しました", "書き込みエラー", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		infoPanel.add(stateRow);
		SSRow.add(SSButton);
		infoPanel.add(numRow);
		infoPanel.add(SSRow);
		this.add(infoPanel);
	}
	
	private JTextField taField, tbField, levelField, thresholdField, expansionField;
	private JLabel tabLabel, gen_aLabel, gen_bLabel, z0Label;
	private JRadioButton plusButton, minusButton;
	private JPanel getGrandmaParabolicInput(){
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setMaximumSize(new Dimension(sizeX, 200));
		HorizontalPanel ta_tbRow = new HorizontalPanel(sizeX, 30);
		
		ta_tbRow.add(new JLabel(taImage));
		taField = new JTextField();
		ta_tbRow.add(taField);
		ta_tbRow.add(new JLabel(tbImage));
		tbField = new JTextField();
		ta_tbRow.add(tbField);
		
		HorizontalPanel tabRow = new HorizontalPanel(sizeX, 45);
		tabRow.add(new JLabel(tabImage));
		tabLabel = new JLabel(tabPlusImage);
		tabRow.add(tabLabel);
		
		HorizontalPanel tabSelectionRow = new HorizontalPanel(sizeX, 25);
		tabSelectionRow.add(new JLabel(tab));
		tabSelectionRow.add(new JLabel("のとり方"));
		ButtonGroup tabGroup = new ButtonGroup();
		plusButton = new JRadioButton("plus");
		plusButton.setOpaque(false);
		plusButton.setSelected(true);
		plusButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					tabLabel.setIcon(tabPlusImage);
				}else{
					tabLabel.setIcon(tabMinusImage);
				}
			}
		});
		minusButton = new JRadioButton("minus");
		minusButton.setOpaque(false);
		tabGroup.add(plusButton);
		tabGroup.add(minusButton);
		tabSelectionRow.add(plusButton);
		tabSelectionRow.add(minusButton);
		
		HorizontalPanel z0Row = new HorizontalPanel(sizeX, 60);
		z0Label = new JLabel(grandmasSpecialZ0);
		z0Row.add(z0Label);
		
		HorizontalPanel gen_aRow = new HorizontalPanel(sizeX, 80);
		gen_aLabel = new JLabel(grandmasSpecialGen_aImage);
		gen_aRow.add(gen_aLabel);
		
		HorizontalPanel gen_bRow = new HorizontalPanel(sizeX, 100);
		gen_bLabel = new JLabel(grandmasSpecialGen_bImage);
		gen_bRow.add(gen_bLabel);
		
		HorizontalPanel otherParamRow = new HorizontalPanel(sizeX, 30);
		otherParamRow.add(new JLabel("MaxLevel"));
		levelField = new JTextField("15");
		levelField.setMaximumSize(new Dimension(60, 30));
		otherParamRow.add(levelField);
		
		otherParamRow.add(new JLabel("閾値"));
		thresholdField = new JTextField("0.006");
		thresholdField.setMaximumSize(new Dimension(60, 30));
		otherParamRow.add(thresholdField);
		
		expansionField = new JTextField("100");
		expansionField.setMaximumSize(new Dimension(60, 30));
		otherParamRow.add(new JLabel("拡大率"));
		otherParamRow.add(expansionField);
		
		HorizontalPanel generationRow = new HorizontalPanel(sizeX, 30);
		JButton generateButton = new JButton("生成");
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		generationRow.add(generateButton);
		
		HorizontalPanel drawRow = new HorizontalPanel(sizeX, 30);
		JButton drawButton = new JButton("描画");
		drawRow.add(drawButton);
		
		taField.addKeyListener(new InputFieldKeyListener(tbField));
		tbField.addKeyListener(new InputFieldKeyListener(levelField));
		levelField.addKeyListener(new InputFieldKeyListener(thresholdField));
		thresholdField.addKeyListener(new InputFieldKeyListener(expansionField));
		expansionField.addKeyListener(new InputFieldKeyListener(null));
		panel.add(ta_tbRow);
		panel.add(tabRow);
		panel.add(tabSelectionRow);
		panel.add(generationRow);
		panel.add(z0Row);
		panel.add(gen_aRow);
		panel.add(gen_bRow);
		panel.add(otherParamRow);
		panel.add(drawRow);
		
		return panel;
	}
	
	
	
	private JRadioButton getRecipeButton(String caption){
		JRadioButton button = new JRadioButton(caption);
		button.setOpaque(false);
		button.addItemListener(new RecipeChangeListener(caption));
		recipeGroup.add(button);
		return button;
	}
	
	public class RecipeChangeListener implements ItemListener{
		int index;
		String name;
		public RecipeChangeListener(String name) {
			this.name = name;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				((CardLayout) inputPanel.getLayout()).show(inputPanel, name);
			}
		}
	}
}
