import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyFrame extends JFrame implements KeyListener {

	JLabel label;
	ImageIcon icon; 

	MyFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setLayout(null);
		this.addKeyListener(this);
		
		icon = new ImageIcon("rocket.png"); 

		label = new JLabel();
		label.setBounds(0, 0, 100, 100);
		label.setIcon(icon);
		//label.setBackground(Color.red);
		//label.setOpaque(true);
		this.getContentPane().setBackground(Color.black);

		this.add(label);
		this.setVisible(true);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyChar()) {
			case 'a' : label.setLocation(label.getX()-5, label.getY()); 
				break;
			case 'w' : label.setLocation(label.getX(), label.getY()-5);
				break;
			case 'd' : label.setLocation(label.getX()+5, label.getY());	
				break;
			case 's' : label.setLocation(label.getX(), label.getY()+5);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 37 : label.setLocation(label.getX()-5, label.getY()); 
			break;
		case 38 : label.setLocation(label.getX(), label.getY()-5);
			break;
		case 39 : label.setLocation(label.getX()+5, label.getY());	
			break;
		case 40 : label.setLocation(label.getX(), label.getY()+5);
	}
	
}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// System.out.println("you released key char: " + e.getKeyChar());
		System.out.println("you released key code: " + e.getKeyCode());

	}
}
