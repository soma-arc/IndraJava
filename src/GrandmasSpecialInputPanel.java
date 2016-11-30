import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class GrandmasSpecialInputPanel extends RecipeInputPanel{
	public GrandmasSpecialInputPanel(int sizeX, int sizeY) {
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
	
	protected void setUI(){
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
		tabSelectionRow.add(new JLabel("‚Ì‚Æ‚è•û"));
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
		
		HorizontalPanel z0Row = new HorizontalPanel(sizeX, 60);
		z0Label = new JLabel(z0Icon);
		z0Row.add(z0Label);
		
		HorizontalPanel gen_aRow = new HorizontalPanel(sizeX, 80);
		gen_aLabel = new JLabel(gen_aIcon);
		gen_aRow.add(gen_aLabel);
		
		HorizontalPanel gen_bRow = new HorizontalPanel(sizeX, 100);
		gen_bLabel = new JLabel(gen_bIcon);
		gen_bRow.add(gen_bLabel);
		
		addButtonAction();
		
		taField.addKeyListener(new InputFieldKeyListener(taImageField));
		taImageField.addKeyListener(new InputFieldKeyListener(tbField));
		tbField.addKeyListener(new InputFieldKeyListener(tbImageField));
		tbImageField.addKeyListener(new InputFieldKeyListener(realAlphaField));
		
		add(taRow);
		add(tbRow);
		add(tabRow);
		add(tabSelectionRow);
		add(conjugationCheckRow);
		add(transformOptionRow);
		//addConjugationRow();
		add(conjugationPanel);
		add(generationRow);
		add(z0Row);
		add(gen_aRow);
		add(gen_bRow);
		add(otherParamsRow);
		add(showLimitOptionRow);
		add(drawRow);
	}
	
	private boolean isAllFieldFilled(){
		if(!taField.getText().equals("") && !taImageField.getText().equals("") &&
		   !tbField.getText().equals("") && !tbImageField.getText().equals("") &&
		   isOtherParamsFilled()){
				return true;
		}
		return false;
	}
	
	protected void addButtonAction(){
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isAllFieldFilled()){
					Complex t_a = getComplex(taField.getText(), taImageField.getText());
					Complex t_b = getComplex(tbField.getText(), tbImageField.getText());
					int maxLevel = Integer.valueOf(levelField.getText());
					float epsilon = Float.valueOf(thresholdField.getText());
					int expansion = Integer.valueOf(expansionField.getText());
					boolean isTabPlus = plusButton.isSelected();
					if(fractal != null)
						fractal.disposeLines();
					if(conjugationCheck.isSelected() && !conjugationList.isEmpty()){
						fractal = new GrandmasSpecialGroup(t_a, t_b, conjugationList, isTabPlus, maxLevel, epsilon, expansion, GrandmasSpecialInputPanel.this);
					}else{
						fractal = new GrandmasSpecialGroup(t_a, t_b, isTabPlus, maxLevel, epsilon, expansion, GrandmasSpecialInputPanel.this);
					}
				}else{
					showInputFieldError();
				}
			}
		});
		/*drawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fractal != null){
					calcThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try{
								fractal.calcLimitingSetWithDFS();
							}catch(InterruptedException ex){
								
							}
							//DrawPanel.drawPanel.repaint();
						}
					});
					calcThread.start();
				}
			}
		});*/
	}
}
