import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class MyFrame extends JFrame implements ActionListener{
	JRadioButton pizzaButton;
	JRadioButton hamburguerButton;
	JRadioButton hotdogButton;
	ImageIcon pizzaIcon;
	ImageIcon hamburguerIcon;
	ImageIcon hotdogIcon;
	
	
	
	MyFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		pizzaIcon = new ImageIcon("pizza.png");
		hamburguerIcon = new ImageIcon("hamburguer.png");
		hotdogIcon = new ImageIcon("hotdog.png");
				
		pizzaButton = new JRadioButton("pizza");
		hamburguerButton = new JRadioButton("hamburguer");
		hotdogButton = new JRadioButton("hotdog");
		
		ButtonGroup group = new ButtonGroup();
		group.add(pizzaButton);
		group.add(hamburguerButton);
		group.add(hotdogButton);
		
		pizzaButton.addActionListener(this);
		hamburguerButton.addActionListener(this);
		hotdogButton.addActionListener(this);
		
		pizzaButton.setIcon(pizzaIcon);
		hamburguerButton.setIcon(hamburguerIcon);
		hotdogButton.setIcon(hotdogIcon);		
		
		this.add(pizzaButton);
		this.add(hamburguerButton);
		this.add(hotdogButton);
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==pizzaButton) {
			System.out.println("You ordered pizza");
		}
		else if(e.getSource()==hamburguerButton) {
			System.out.println("You ordered hamburguer");
		}
		else if(e.getSource()==hotdogButton) {
			System.out.println("You ordered hotdog");
		}
		
	}
	
}
