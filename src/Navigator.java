import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Polygon;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class Navigator extends JWindow{
	private JPanel rahmen_jwindow;
	//private Hauptfenster eltern;
	private int x = 200;
	private int y = 200;
	
	
	Navigator(Hauptfenster parent){
		//super von JWindow wird automatisch aufgerufen
	//	eltern = parent;
		super(parent);
		
		
		
		this.setSize(x, y);
		
		Polygon achteck = newPolygon();
		this.setShape(achteck);
		
		rahmen_jwindow = new JPanel();
		rahmen_jwindow.setLayout(new BorderLayout());
		rahmen_jwindow.add(new Tastatur()); //rahmen wird eine Tastatur zugeordnet
	//	rahmen_jwindow.setBorder(BorderFactory.createLineBorder(Color.red,1)); /**/
		
		this.getContentPane().add(rahmen_jwindow);
		
		setNavLocation();
		//this.setVisible(true);
		showNavigator();
	
	}
	
	
	public void showNavigator(){
		this.setVisible(true);
	}
	
	public void hideNavigator(){
		this.setVisible(false);
	}
	
	
	
	
	
	
	public void setNavLocation(){
		this.setLocation( (this.getParent().getLocation().x + getParent().getWidth() + 50), getParent().getLocation().y);
	}
	
	private Polygon newPolygon(){
		Polygon achteck = new Polygon();
		
		achteck.addPoint(0, (y/3)*2);
		achteck.addPoint(0, y/3);
		achteck.addPoint(x/3, 0);
		
		achteck.addPoint((x/3)*2, 0);
		achteck.addPoint(x, y/3);
		achteck.addPoint(x, (y/3)*2);
		achteck.addPoint((x/3)*2, y);
		achteck.addPoint(x/3, y);
	
		return achteck;
	}
}
