import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Main {
	public static void main(String[] args) {
		
		ImageIcon image = new ImageIcon("gorilla.png"); 
		Border border = BorderFactory.createLineBorder(Color.green, 3);
		
		JLabel label = new JLabel();//create label
		label.setText("Bro, do you even code?"); //set text of label
		label.setIcon(image); 
		label.setHorizontalTextPosition(JLabel.CENTER);//CENTER LEFT RIGHT 
		label.setVerticalTextPosition(JLabel.TOP);// CENTER TOP BOTTON
		label.setForeground(new Color(0, 250, 0)); // SET font color of text
		label.setFont(new Font("MV Boli", Font.PLAIN, 50)); //Set font of Text
		label.setIconTextGap(10);//set gap of text to image
		label.setBackground(Color.black);
		label.setOpaque(true);
		label.setBorder(border);
		label.setVerticalAlignment(JLabel.CENTER);//Set Vertical Position of Icon + Text
		label.setHorizontalAlignment(JLabel.CENTER); //Set Horizontal Position of Icon + Text within frame
		//label.setBounds(100, 100 , 1600, 1080);
		
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(800, 1000);
		frame.setVisible(true); 
		//frame.setLayout(null);
		frame.add(label);
		frame.pack();//auto adjust for accommodate the label
		
		

	}
}
