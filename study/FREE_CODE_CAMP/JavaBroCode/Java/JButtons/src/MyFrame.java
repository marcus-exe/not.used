import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MyFrame extends JFrame{
	JButton button;
	MyFrame(){
		
		ImageIcon icon = new ImageIcon("logo.png");
		
		
		button = new JButton();
		button.setBounds(200, 100, 850, 750);
		button.addActionListener(e -> System.out.println("poo"));
		button.setText("I'm a button!");
		button.setFocusable(false);
		button.setIcon(icon);
		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setFont(new Font("Comic Sans", Font.BOLD, 25)); 
		button.setIconTextGap(-15);
		button.setForeground(Color.cyan);
		button.setBackground(Color.DARK_GRAY);
		button.setBorder(BorderFactory.createEtchedBorder());
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1000,1000);
		this.setVisible(true);
		this.add(button);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			System.out.println("poo");
			button.setEnabled(false);
		}
	}


}
