import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RileyInputPanel extends RecipeInputPanel{
	private static final String NAME = "ライリーのレシピ";
	public RileyInputPanel(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		loadIcon();
		setUI();
	}
	
	private ImageIcon cIcon;
	private JLabel cLabel, iLabel;
	private JTextField cRealField, cImageField;
	
	protected void loadIcon(){
		cIcon = new ImageIcon("./res/c.png");
		iIcon = new ImageIcon("./res/i.png");
		plusIcon = new ImageIcon("./res/plus.png");
		gen_aIcon = new ImageIcon("./res/riley-a.png");
		gen_bIcon = new ImageIcon("./res/riley-b.png");
	}
	
	protected void setUI(){
		HorizontalPanel cInputRow = new HorizontalPanel(sizeX, 30);
		cInputRow.add(new JLabel(cIcon));
		cRealField = new JTextField();
		cRealField.setMaximumSize(new Dimension(70, 30));
		cInputRow.add(cRealField);
		cInputRow.add(new JLabel(plusIcon));
		cImageField = new JTextField();
		cImageField.setMaximumSize(new Dimension(70, 30));
		cInputRow.add(cImageField);
		cInputRow.add(new JLabel(iIcon));
		
		HorizontalPanel gen_aRow = new HorizontalPanel(sizeX, 35);
		gen_aLabel = new JLabel(gen_aIcon);
		gen_aRow.add(gen_aLabel);
		
		HorizontalPanel gen_bRow = new HorizontalPanel(sizeX, 35);
		gen_bLabel = new JLabel(gen_bIcon);
		gen_bRow.add(gen_bLabel);
		
		addButtonAction();
		
		cRealField.addKeyListener(new InputFieldKeyListener(cImageField));
		cImageField.addKeyListener(new InputFieldKeyListener(realAlphaField));
		add(cInputRow);
		add(conjugationCheckRow);
		add(transformOptionRow);
		//addConjugationRow();
		add(conjugationPanel);
		add(generationRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_aRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_bRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(otherParamsRow);
		add(showLimitOptionRow);
		add(drawRow);
	}
	
	private boolean isAllFieldFilled(){
		if(!cRealField.getText().equals("") && !cImageField.getText().equals("") && isOtherParamsFilled()){
			return true;
		}
		return false;
	}
	
	private void addButtonAction(){
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isAllFieldFilled()){
					Complex c = getComplex(cRealField.getText(), cImageField.getText());
					int maxLevel = Integer.valueOf(levelField.getText());
					float epsilon = Float.valueOf(thresholdField.getText());
					int expansion = Integer.valueOf(expansionField.getText());
					if(fractal != null)
						fractal.disposeLines();
					if(conjugationCheck.isSelected() && !conjugationList.isEmpty()){
						fractal = new RileysGroup(c, conjugationList, maxLevel, epsilon, expansion, RileyInputPanel.this);
					}else{
						fractal = new RileysGroup(c, maxLevel, epsilon, expansion, RileyInputPanel.this);
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
							DrawPanel.drawPanel.repaint();
						}
					});
					calcThread.start();
				}
			}
		});*/
	}
}
