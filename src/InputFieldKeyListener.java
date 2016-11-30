import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

class InputFieldKeyListener extends KeyAdapter{
	JTextField next;
	public InputFieldKeyListener(JTextField next){
		this.next = next;
	}
	public InputFieldKeyListener(){
	}
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(c == KeyEvent.VK_ENTER){
			if(next != null)
				next.requestFocus();;
		}else if (!( (c >= '0') && (c <= '9') || c == '-' || c == '.' || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) )) {
			//JPanel.getToolkit().beep();
			//System.out.println("consume");
			e.consume();
		}
	}
}