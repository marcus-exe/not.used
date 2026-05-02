import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

public class Game {
	
	JFrame frame;
	JLabel label;
	
	Action upAction;
	Action downAction;
	Action leftAction;
	Action rightAction;
	
	Game(){
		frame = new JFrame("KeyBinding Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420,420);
		frame.setLayout(null);
		
		label = new JLabel();
		label.setBackground(Color.red);
		label.setBounds(100, 100, 100, 100);
		label.setOpaque(true);
		
		upAction = new upAction();
		downAction = new downAction();
		leftAction= new leftAction();
		rightAction = new rightAction();
		
		label.getInputMap().put(KeyStroke.getKeyStroke("UP"),"upAction");
		label.getActionMap().put("upAction", upAction);
		label.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"downAction");
		label.getActionMap().put("downAction", downAction);
		label.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"leftAction");
		label.getActionMap().put("leftAction", leftAction);
		label.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"rightAction");
		label.getActionMap().put("rightAction", rightAction);
		
		
		frame.add(label);		
		frame.setVisible(true);
	}
	
	
	public class upAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			label.setLocation(label.getX(),label.getY()-10);
		}	
	}
	
	
	
	public class downAction extends AbstractAction {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			label.setLocation(label.getX(),label.getY()+10);		
		}
	}
	
	
	
	public class leftAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			label.setLocation(label.getX()-10,label.getY());
		}
	}
	
	
	
	public class rightAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			label.setLocation(label.getX()+10,label.getY());
		}
	}
	
	
}
