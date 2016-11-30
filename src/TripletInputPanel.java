import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TripletInputPanel extends RecipeInputPanel{
	private JTextField realMu1Field, imageMu1Field, realMu2Field, imageMu2Field;
	private ImageIcon gen_cIcon, mu1Icon, mu2Icon;
	public JLabel gen_cLabel;
	public TripletInputPanel(int sizeX, int sizeY){
		super(sizeX, sizeY);
		loadIcon();
		setUI();
	}
	
	protected void loadIcon(){
		gen_aIcon = new ImageIcon("./res/triplet-a.png");
		gen_bIcon = new ImageIcon("./res/triplet-b.png");
		gen_cIcon = new ImageIcon("./res/triplet-c.png");
		mu1Icon = new ImageIcon("./res/mu1=.png");
		mu2Icon = new ImageIcon("./res/mu2=.png");
	}
	
	protected void setUI(){
		realMu1Field = new JTextField();
		imageMu1Field = new JTextField();
		realMu2Field = new JTextField();
		imageMu2Field = new JTextField();
		
		HorizontalPanel mu1_Row = getComplexInputRow(mu1Icon, realMu1Field, imageMu1Field);
		HorizontalPanel mu2_Row = getComplexInputRow(mu2Icon, realMu2Field, imageMu2Field);
		HorizontalPanel gen_aRow = new HorizontalPanel(sizeX, 30);
		gen_aRow.add(new JLabel(gen_aIcon));
		HorizontalPanel gen_bRow = new HorizontalPanel(sizeX, 30);
		gen_bRow.add(new JLabel(gen_bIcon));
		HorizontalPanel gen_cRow = new HorizontalPanel(sizeX, 50);
		gen_cLabel = new JLabel(gen_cIcon);
		gen_cRow.add(gen_cLabel);
		
		realMu1Field.addKeyListener(new InputFieldKeyListener(imageMu1Field));
		imageMu1Field.addKeyListener(new InputFieldKeyListener(realMu2Field));
		realMu2Field.addKeyListener(new InputFieldKeyListener(imageMu2Field));
		imageMu2Field.addKeyListener(new InputFieldKeyListener(null));
		
		add(mu1_Row);
		add(mu2_Row);
		add(generationRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_aRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_bRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_cRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(otherParamsRow);
		add(drawRow);
		
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isAllFieldFilled()){
					Complex mu1 = getComplex(realMu1Field.getText(), imageMu1Field.getText());
					Complex mu2 = getComplex(realMu2Field.getText(), imageMu2Field.getText());
					int maxLevel = Integer.valueOf(levelField.getText());
					float epsilon = Float.valueOf(thresholdField.getText());
					int expansion = Integer.valueOf(expansionField.getText());
					fractal = new TripletGroup(mu1, mu2, maxLevel, epsilon, expansion, TripletInputPanel.this);
				}else{
					showInputFieldError();
				}
			}
		});
	}
	
	private boolean isAllFieldFilled(){
		if(!realMu1Field.getText().equals("") && !imageMu1Field.getText().equals("") &&
		   !realMu2Field.getText().equals("") && !imageMu2Field.getText().equals("") &&
		   isOtherParamsFilled()){
			return true;
		}
		return false;
	}
}
