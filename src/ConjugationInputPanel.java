import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class ConjugationInputPanel extends JPanel{
	private JButton applyConjugatiButton, undoConjugationButton;
	HorizontalPanel buttonRow;
	public ConjugationInputPanel(int sizeX, int sizeY) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		buttonRow = new HorizontalPanel(sizeX, 30);
		applyConjugatiButton = new JButton("ã§ñÇí«â¡Ç∑ÇÈ");
		applyConjugatiButton.setAlignmentX(LEFT_ALIGNMENT);
		undoConjugationButton = new JButton("éÊÇËè¡Çµ");
	}
	
	public void addConjugationButton(ActionListener addConjugationButtonListener, ActionListener undoButtonListener){
		buttonRow.add(applyConjugatiButton);
		applyConjugatiButton.addActionListener(addConjugationButtonListener);
		buttonRow.add(undoConjugationButton);
		undoConjugationButton.addActionListener(undoButtonListener);
		this.add(buttonRow);
	}
	/*public void addUndoConjugationButton(ActionListener undoButtonListener){
		buttonRow.add(undoConjugationButton);
		undoConjugationButton.addActionListener(undoButtonListener);
		this.add(buttonRow);
	}*/
	//buttonRow.add(undoConjugationButton);
	//undoConjugationButton.addActionListener(undoConjugationListener);
}
