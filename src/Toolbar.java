import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToolBar;


public class Toolbar extends JToolBar {
	private Hauptfenster eltern;
	private JButton newGame;
	private JButton settings;
	private JPanel punktestand;
	private JTextField punktestandtext;
	private JPanel munition;
	private drawAmmoImage munition_arr[] = new drawAmmoImage[3];;
	private JProgressBar fortschrittsbalken;
	private JPanel spielfortschritt;
	private int ammocount;
	private int ammoCounter = 0;
	private boolean toMuchAmmo = false;
	
	
	Toolbar(Hauptfenster parent){
		eltern = parent;
		//spielfortschritt = eltern.getGameProgress();
		
		this.setFloatable(false); //Toolbar kann vom Benutzer nicht verschoben werden
		
		//Button Neues Spiel
		newGame = new JButton("Neues Spiel");
		newGame.setActionCommand("new_game"); //damit listener weiss welcher button gedrückt wurde
		newGame.setEnabled(false);
		newGame.addActionListener(new ListenerToolbar());
		this.add(newGame);
		
		
		//Punktestand
		punktestand = new JPanel();
		punktestand.setBorder(BorderFactory.createTitledBorder("Punktestand"));
        punktestand.setToolTipText("Zeigt den aktuellen Punktestand an");
        
        punktestandtext = new JTextField();
        punktestandtext.setEditable(false);
        punktestandtext.setColumns(5);
        setScoreFieldText();
        
        punktestand.add(punktestandtext);
        this.add(punktestand);
        
        //Munition
        munition = new JPanel();
        munition.setToolTipText("Zeigt die verfuegbare Munition an");
        munition.setBorder(BorderFactory.createTitledBorder("Munition")); //Rahmen um die Munition
        munition.setLayout(new GridLayout(1, 3, 3, 3));
        for(int i=0;i<3;i++){
        	munition_arr[i] = new drawAmmoImage();
        	munition_arr[i].setPreferredSize(new Dimension(30,30));
        }
		
		
        paintMunitionsAnzeige();
        this.add(munition);
        
        
        //Spielfortschritt
        spielfortschritt = new JPanel();
        /* Rahmenbeschriftung, Tooltiptext */
        spielfortschritt.setToolTipText("Zeigt den aktuellen Spielfortschritt an");
        spielfortschritt.setBorder(BorderFactory.createTitledBorder("Spielfortschritt"));
        /* Fortschrittsbalken initialisieren mit min / max Werte */
        fortschrittsbalken = new JProgressBar(0,100);
        /* Wert setzen */
        fortschrittsbalken.setValue(eltern.getGameProgress());
        /* Prozentzahl anzeigen */
        fortschrittsbalken.setStringPainted(true);
        fortschrittsbalken.setPreferredSize(new Dimension(100,20));
        spielfortschritt.add(fortschrittsbalken);
        this.add(spielfortschritt);
        
        
        //Button Settings
        settings = new JButton("Settings");
		settings.setActionCommand("settings");
		/* Button ist nur aktiv wenn das Spiel gewonnen / verloren wurde */
		settings.setEnabled(true);
		settings.addActionListener(new ListenerToolbar());
		
		eltern.getSpielfeld().requestFocus();
		
		this.add(settings);
		
		
		eltern.getSpielfeld().requestFocus();
        
	}
	
	
	public void paintMunitionsAnzeige(){
		//String theme = eltern.getTheme();
		String pathIcon = "bilder/Dracula/ammo.png";
		ImageIcon icon_munition = new ImageIcon(pathIcon);
		
		
		ammocount = eltern.getModel().getShootAmount();
		
		//hat sich munitionsanzahl verändert?
		if(ammocount != this.ammoCounter){				
			
			this.ammoCounter = ammocount;
			
			for(int i = 0; i < 3; i++){
				munition.remove(munition_arr[i]);
			}
			
			//anzahl munition >3, zwei Icons zeichnen + text multiplikator
			if(ammocount > 3){
				setToMuchAmmo(true);
				munition_arr[0] = new drawAmmoImage();	
				munition.add(munition_arr[0]);
				setToMuchAmmo(false);
				for(int i = 1; i < 3; i++){
					munition_arr[i] = new drawAmmoImage();
					munition.add(munition_arr[i]);
				}
			}
			/* Anzahl an Munition <= 3 && >= 0 -> zeige zwischen 1-3 Icons an */
			else if(ammocount <= 3 && ammocount >= 0){
				setToMuchAmmo(false);
				for(int i=0; i < ammocount; i++){
					munition_arr[i] = new drawAmmoImage();
					munition.add(munition_arr[i]);
				}
			}	
			
			
		}
		
	}
	
	/**** 2D Graphics ****/
	
	public class drawAmmoImage extends JPanel
	{
		String theme = eltern.getTheme();
		String pathIcon = "icons" + File.separator +theme+File.separator+"ammo.png";
		boolean _toMuchAmmo = getToMuchAmmo();
		
		drawAmmoImage(){} //leerer Konstruktor
		
		public void paintComponent(Graphics g)
		{   Graphics2D g2d = (Graphics2D) g;
		   
		    
		    super.paintComponent(g);
		    

		    if(getAmmoCount() > 3 && _toMuchAmmo == true){
		    	g2d.drawString("x" + Integer.toString(getAmmoCount()), 25, 25);

		    }else{
		    	Image ammo_img = Toolkit.getDefaultToolkit().getImage(pathIcon);
		    	g2d.drawImage(ammo_img, 0 ,0 , 25, 25, this);
		    }
		    g2d.finalize();
		}
	}
	
	
	
	
	
	
	public int getAmmoCount(){
		return ammocount;
	}
	
	
	public boolean getToMuchAmmo(){
		return this.toMuchAmmo;
	}
	
	
	public void setToMuchAmmo(boolean status){
		this.toMuchAmmo = status;
	}
	
	
	
	
	/*public JPanel[] getAmmoJLabelArray(){
		return munition_arr;
	}*/
	
	public JPanel getAmmoJPanel(){
		return munition;
	}
	
	
	
	
	public void setScoreFieldText(){
		punktestandtext.setText(String.valueOf(eltern.getModel().getScore()));
	}
	
	
	public void setButtonSettingsEnabled() { //wenn Spiel gewonnen oder verloren wurde, wird Button aktiviert
		settings.setEnabled(true);
	}
	
	
	public void setButtonSettingsDisabled() { //Settings-Button wird deaktiviert
		settings.setEnabled(false);
	}
	
	
	public void setButtonNSEnabled() { //Button neues Spiel aktivieren
		newGame.setEnabled(true);
	}
	
	
	public void setButtonNSDisabled() { //Button neues Spiel deaktivieren
		newGame.setEnabled(false);
	}
	
	public void setProgressBarValue(){ //Fortschrittsbalken aktualisieren
		fortschrittsbalken.setValue(eltern.getGameProgress());
		System.out.println("Fortschritt: " + eltern.getGameProgress());
	}
	
	public void updateToolbar(){
		setScoreFieldText();
		setProgressBarValue();
		paintMunitionsAnzeige();
	}
	
	public void hideToolbar(){
		this.setVisible(false);
	}
	
	public void showToolbar(){
		this.setVisible(true);
	}
	
	
	
	
}
