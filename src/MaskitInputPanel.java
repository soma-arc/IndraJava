import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class MaskitInputPanel extends RecipeInputPanel{
	private JTextField realMuField = new JTextField();
	private JTextField imageMuField = new JTextField();
	
	public MaskitInputPanel(int sizeX, int sizeY) {
		super(sizeX, sizeY);
		loadIcon();
		setUI();
	}
	
	protected void loadIcon(){
		muIcon = new ImageIcon("./res/mu=.png");
		iIcon = new ImageIcon("./res/i.png");
		gen_aIcon = new ImageIcon("./res/maskit-a2.png");
		gen_bIcon = new ImageIcon("./res/maskit-b2.png");
	}
	
	protected void setUI(){
		HorizontalPanel muInputPanel = new HorizontalPanel(sizeX, 30);
		muInputPanel.add(new JLabel(muIcon));
		realMuField.setMaximumSize(new Dimension(70, 30));
		muInputPanel.add(realMuField);
		muInputPanel.add(new JLabel(plusIcon));
		imageMuField.setMaximumSize(new Dimension(70, 30));
		muInputPanel.add(imageMuField);
		muInputPanel.add(new JLabel(iIcon));
		
		HorizontalPanel gen_aRow = new HorizontalPanel(sizeX, 60);
		gen_aLabel = new JLabel(gen_aIcon);
		gen_aRow.add(gen_aLabel);
		HorizontalPanel gen_bRow = new HorizontalPanel(sizeX, 60);
		gen_bRow.add(new JLabel(gen_bIcon));
		
		addButtonAction();
		
		add(muInputPanel);
		
		add(conjugationCheckRow);
		add(transformOptionRow);
		//addConjugationRow();
		add(conjugationPanel);
		add(generationRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(gen_aRow);
		add(gen_bRow);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(otherParamsRow);
		add(showLimitOptionRow);
		add(drawRow);
		
		realMuField.addKeyListener(new InputFieldKeyListener(imageMuField));
		imageMuField.addKeyListener(new InputFieldKeyListener(realAlphaField));
	}
	
	private boolean isAllFieldFilled(){
		if(!realMuField.getText().equals("") && !imageMuField.getText().equals("") && isOtherParamsFilled()){
			return true;
		}
		return false;
	}
	
	private void addButtonAction(){
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isAllFieldFilled()){
					Complex mu = getComplex(realMuField.getText(), imageMuField.getText());
					int maxLevel = Integer.valueOf(levelField.getText());
					float epsilon = Float.valueOf(thresholdField.getText());
					int expansion = Integer.valueOf(expansionField.getText());
					if(fractal != null)
						fractal.disposeLines();
					if(conjugationCheck.isSelected() && !conjugationList.isEmpty()){
						fractal = new MaskitGroup(mu, conjugationList, maxLevel, epsilon, expansion, MaskitInputPanel.this);
					}else{
						fractal = new MaskitGroup(mu, maxLevel, epsilon, expansion, MaskitInputPanel.this);
					}
				}else{
					showInputFieldError();
				}
			}
		});
	}
}
