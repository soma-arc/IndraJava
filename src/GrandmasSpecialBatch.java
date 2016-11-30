import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class GrandmasSpecialBatch extends RecipeInputPanel{
	
	public GrandmasSpecialBatch(int sizeX, int sizeY){
		super(sizeX, sizeY);
		loadIcon();
		setUI();
	}
	
	protected void loadIcon(){
		try{
			taIcon = new ImageIcon("./res/ta=.png"); 
			tbIcon = new ImageIcon("./res/tb=.png");
			tabIcon = new ImageIcon("./res/tab.png");
			tabEqualIcon = new ImageIcon("./res/tab=.png");
			tabPlusIcon= new ImageIcon("./res/tab-plus2.png");
			tabMinusIcon= new ImageIcon("./res/tab-minus2.png");
			gen_aIcon= new ImageIcon("./res/grandmas-special-matrix-a.png");
			gen_bIcon = new ImageIcon("./res/grandmas-special-matrix-b.png");
			z0Icon = new ImageIcon("./res/grandmas-special-z0.png");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private JTextField variationField, finalValueField;
	private JRadioButton taRealButton, taImageButton, tbRealButton, tbImageButton;
	private JButton batchButton;
	protected void setUI(){
		HorizontalPanel initialLabel = new HorizontalPanel(sizeX, 30);
		initialLabel.add(new JLabel("初期値"));
		
		HorizontalPanel taRow = new HorizontalPanel(sizeX, 30);
		taRow.add(new JLabel(taIcon));
		taField = new JTextField();
		taRow.add(taField);
		taRow.add(new JLabel(plusIcon));
		taImageField = new JTextField();
		taRow.add(taImageField);
		taRow.add(new JLabel(iIcon));
		
		HorizontalPanel tbRow = new HorizontalPanel(sizeX, 30);
		
		tbRow.add(new JLabel(tbIcon));
		tbField = new JTextField();
		tbRow.add(tbField);
		tbRow.add(new JLabel(plusIcon));
		tbImageField = new JTextField();
		tbRow.add(tbImageField);
		tbRow.add(new JLabel(iIcon));
		
		HorizontalPanel tabRow = new HorizontalPanel(sizeX, 45);
		tabRow.add(new JLabel(tabEqualIcon));
		tabLabel = new JLabel(tabPlusIcon);
		tabRow.add(tabLabel);
		
		HorizontalPanel tabSelectionRow = new HorizontalPanel(sizeX, 25);
		tabSelectionRow.add(new JLabel(tabIcon));
		tabSelectionRow.add(new JLabel("のとり方"));
		ButtonGroup tabGroup = new ButtonGroup();
		plusButton = new JRadioButton("plus");
		plusButton.setOpaque(false);
		plusButton.setSelected(true);
		plusButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					tabLabel.setIcon(tabPlusIcon);
				}else{
					tabLabel.setIcon(tabMinusIcon);
				}
			}
		});
		minusButton = new JRadioButton("minus");
		minusButton.setOpaque(false);
		tabGroup.add(plusButton);
		tabGroup.add(minusButton);
		tabSelectionRow.add(plusButton);
		tabSelectionRow.add(minusButton);
		
		HorizontalPanel batchParamRow = new HorizontalPanel(sizeX, 30);
		variationField = new JTextField();
		batchParamRow.add(new JLabel("増分"));
		batchParamRow.add(variationField);
		batchParamRow.add(new JLabel("最終値"));
		finalValueField = new JTextField();
		batchParamRow.add(finalValueField);
		
		HorizontalPanel moveLabelRow = new HorizontalPanel(sizeX, 30);
		moveLabelRow.add(new JLabel("変化量の設定"));
		
		ButtonGroup moveSelection = new ButtonGroup();
		HorizontalPanel moveParamButtons = new HorizontalPanel(sizeX, 30);
		taRealButton = createRadioButton("taの実部");
		taImageButton = createRadioButton("taの虚部");
		tbRealButton = createRadioButton("tbの実部");
		tbImageButton = createRadioButton("tbの虚部");
		moveSelection.add(taRealButton);
		moveSelection.add(taImageButton);
		moveSelection.add(tbRealButton);
		moveSelection.add(tbImageButton);
		moveParamButtons.add(taRealButton);
		moveParamButtons.add(taImageButton);
		moveParamButtons.add(tbRealButton);
		moveParamButtons.add(tbImageButton);
		
		HorizontalPanel batchButtonRow = new HorizontalPanel(sizeX, 30);
		batchButton = new JButton("処理の開始");
		batchButtonRow.add(batchButton);
		batchButtonRow.add(cancelButton);
		
		batchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcThread = new Thread(new Runnable() {
					@Override
					public void run() {
						startBatch();
					}
				});
				calcThread.run();
			}
		});
		
		add(initialLabel);
		add(taRow);
		add(tbRow);
		add(tabRow);
		add(tabSelectionRow);
		add(moveLabelRow);
		add(moveParamButtons);
		add(batchParamRow);
		add(otherParamsRow);
		add(showLimitOptionRow);
		add(drawAxisCheck);
		add(batchButtonRow);
	}
	
	public void startBatch(){
		System.out.println("start batch");
		boolean isTabPlus = plusButton.isSelected();
		int maxLevel = Integer.valueOf(levelField.getText());
		float epsilon = Float.valueOf(thresholdField.getText());
		int expansion = Integer.valueOf(expansionField.getText());
		Complex ta = getComplex(taField, taImageField);
		Complex tb = getComplex(tbField, tbImageField);
		double variation = Double.valueOf(variationField.getText());
		double firstValue = getFirstValue();
		double finalValue = Double.valueOf(finalValueField.getText());
		int index = 1;
		if(firstValue < finalValue){
			for(double value = firstValue ; value <= finalValue ; value += variation){
				setNextValue(ta, tb, value);
				Fractal f = new GrandmasSpecialGroup(ta, tb, isTabPlus, maxLevel, epsilon, expansion, this);
				try{
					f.calcLimitingSetWithDFS();
					f.batchSave(index,2560, 1440, drawAxisCheck.isSelected(), drawLimitPointCheck.isSelected(), drawLevel2LimitPointCheck.isSelected());
					index++;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}else{
			for(double value = firstValue ; value >= finalValue ; value += variation){
				setNextValue(ta, tb, value);
				Fractal f = new GrandmasSpecialGroup(ta, tb, isTabPlus, maxLevel, epsilon, expansion, this);
				try{
					f.calcLimitingSetWithDFS();
					f.batchSave(index,2560, 1440, drawAxisCheck.isSelected(), drawLimitPointCheck.isSelected(), drawLevel2LimitPointCheck.isSelected());
					index++;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	private void setNextValue(Complex ta, Complex tb, double value){
		if(taRealButton.isSelected()){
			ta.setRe(value);
		}else if(taImageButton.isSelected()){
			ta.setIm(value);
		}else if(tbRealButton.isSelected()){
			tb.setRe(value);
		}else{
			tb.setIm(value);
		}
	}
	
	private double getFirstValue(){
		if(taRealButton.isSelected()){
			return Double.valueOf(taField.getText());
		}else if(taImageButton.isSelected()){
			return Double.valueOf(taImageField.getText());
		}else if(tbRealButton.isSelected()){
			return Double.valueOf(tbField.getText());
		}else{
			return Double.valueOf(tbImageField.getText());
		}
	}
}
