import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MyFrame extends JFrame{
	MyFrame() {

		this.setTitle("JFrame Title goes here");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit out of application
		//HIDE_ON_CLOSE(default), DO_NOTHING_ON_CLOSE(annoying)
		this.setResizable(false); //prevent this from being resized
		this.setSize(420, 420); //sets the x dimension, and y-dimension of this
		this.setVisible(true); //make this visible
		ImageIcon image = new ImageIcon("gorilla.png");//create an image icon
		this.setIconImage(image.getImage());//change icon of this
		this.getContentPane().setBackground(new Color(100, 0, 200));
	}	
}
