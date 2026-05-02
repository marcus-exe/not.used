import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		//JOptionPane.showMessageDialog(null, "This is some useless info", "title", JOptionPane.PLAIN_MESSAGE);
		//JOptionPane.showMessageDialog(null, "This is more useless info :D", "title", JOptionPane.INFORMATION_MESSAGE);
		//JOptionPane.showMessageDialog(null, "Is some useless info?", "title", JOptionPane.QUESTION_MESSAGE);
		//JOptionPane.showMessageDialog(null, "Your computer has a virus", "title", JOptionPane.WARNING_MESSAGE);
		//JOptionPane.showMessageDialog(null, "Call tech support now", "title", JOptionPane.ERROR_MESSAGE);
		//int answer = JOptionPane.showConfirmDialog(null, "do you even code?", "This is my title", JOptionPane.YES_NO_CANCEL_OPTION);
		//String name = JOptionPane.showInputDialog("What is your name?: ");
		//System.out.println("Hello " + name);
		String[] responses = {"No, you are awsome", "Thank you", "*blush*"};
		ImageIcon icon = new ImageIcon("smile-png-photo-19.png"); 
		JOptionPane.showOptionDialog(
				null, 
				"You are awsome", 
				"Secret Message", 
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, 
				icon, 
				responses, 
				0);
	
	}
}
