import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class GrandmasGoodAtDishInputPanel extends RecipeInputPanel{
	private ImageIcon qIcon, rIcon, tcIcon;
	public JLabel qLabel, rLabel, tcLabel;
	private JTextField tabField;
	public GrandmasGoodAtDishInputPanel(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		loadIcon();
		setUI();
	}
	
	protected void loadIcon(){
		taIcon = new ImageIcon("./res/ta=.png");
		tbIcon = new ImageIcon("./res/tb=.png");
		tabIcon = new ImageIcon("./res/tab=.png");
		plusIcon = new ImageIcon("./res/plus.png");
		iIcon = new ImageIcon("./res/i.png");
		qIcon = new ImageIcon("./res/q.png");
		rIcon = new ImageIcon("./res/r.png");
		tcIcon = new ImageIcon("./res/tc2.png");
		z0Icon = new ImageIcon("./res/grandmas-good-at-z0.png");
		gen_aIcon = new ImageIcon("./res/grandmas-good-at-a.png");
		gen_bIcon = new ImageIcon("./res/grandmas-good-at-b.png");
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
		
		HorizontalPanel tabRow = new HorizontalPanel(sizeX, 30);
		tabField = new JTextField();
		tabRow.add(new JLabel(tabIcon));
		tabRow.add(tabField);
		tabRow.add(new JLabel(plusIcon));
		tabImageField = new JTextField();
		tabRow.add(tabImageField);
		tabRow.add(new JLabel(iIcon));
		
		HorizontalPanel tcRow = new HorizontalPanel(sizeX, 30);
		tcLabel = new JLabel(tcIcon);
		tcRow.add(tcLabel);
		HorizontalPanel qRow = new HorizontalPanel(sizeX, 30);
		qLabel = new JLabel(qIcon);
		qRow.add(qLabel);
		HorizontalPanel rRow = new HorizontalPanel(sizeX, 30);
		rLabel = new JLabel(rIcon);
		rRow.add(rLabel);
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
		tbImageField.addKeyListener(new InputFieldKeyListener(tabField));
		tabField.addKeyListener(new InputFieldKeyListener(tabImageField));
		tabImageField.addKeyListener(new InputFieldKeyListener(realAlphaField));
		
		add(taRow);
		add(tbRow);
		add(tabRow);
		add(conjugationCheckRow);
		add(transformOptionRow);
		add(conjugationPanel);
		add(generationRow);
		add(tcRow);
		add(qRow);
		add(rRow);
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
		   !tabField.getText().equals("") && !tabImageField.getText().equals("") &&
		   isOtherParamsFilled()){
			return true;
		}
		return false;
	}
	
	private void addButtonAction(){
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isAllFieldFilled()){
					Complex ta = new Complex(Float.valueOf(taField.getText()), Float.valueOf(taImageField.getText()));
					Complex tb = new Complex(Float.valueOf(tbField.getText()), Float.valueOf(tbImageField.getText()));
					Complex tab = new Complex(Float.valueOf(tabField.getText()), Float.valueOf(tabImageField.getText()));
					int maxLevel = Integer.valueOf(levelField.getText());
					float epsilon = Float.valueOf(thresholdField.getText());
					int expansion = Integer.valueOf(expansionField.getText());
					if(fractal != null)
						fractal.disposeLines();
					if(conjugationCheck.isSelected() && !conjugationList.isEmpty()){
						fractal = new GrandmasGoodAtDishGroup(ta, tb, tab, conjugationList, maxLevel, epsilon, expansion, GrandmasGoodAtDishInputPanel.this);
					}else{
						fractal = new GrandmasGoodAtDishGroup(ta, tb, tab, maxLevel, epsilon, expansion, GrandmasGoodAtDishInputPanel.this);
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
							}catch(Exception ex){
								
							}
							DrawPanel.drawPanel.repaint();
						}
					});
					calcThread.start();
				}
			}
		});*/
	}
}
