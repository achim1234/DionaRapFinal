import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Tastatur extends JPanel{
	
	public Tastatur(){
		this.setLayout(new GridLayout(3,3));
	/*	this.add(new JButton("7"));
		this.add(new JButton("8"));
		this.add(new JButton("9"));
		this.add(new JButton("4"));
		this.add(new JButton("5"));
		this.add(new JButton("6"));
		this.add(new JButton("1"));
		this.add(new JButton("2"));
		this.add(new JButton("3")); */
		
		this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		
		String pathTastaturIcons[] ={"bilder/taste1.gif","bilder/taste2.gif",
				"bilder/taste3.gif","bilder/taste4.gif",
				"bilder/taste5.gif","bilder/taste6.gif","bilder/taste7.gif",
				"bilder/taste8.gif","bilder/taste9.gif"};
		
		JButton button[] = new JButton[10];
		
		for(int i = 9; i >= 1; i--){
			//this.add(new JButton("" + i));
			button[i] = new JButton();
			button[i].setIcon(new ImageIcon(pathTastaturIcons[i-1]));
			button[i].setActionCommand(String.valueOf(i));
			button[i].setPreferredSize(new Dimension(50, 50));
			
			if(i == 5){
				button[i].addActionListener(new ListenerWaffe()); // ActionListener Waffe dem Button zuweisen
			}else{
				button[i].addActionListener(new ListenerBewegung()); // ActionListener Bewegen dem Button zuweisen
			}
			 
			add(button[i]);
		}
		
		this.setVisible(true);
	}
}
