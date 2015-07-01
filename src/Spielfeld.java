import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fhwgt.dionarap.model.data.DionaRapModel;
import de.fhwgt.dionarap.model.objects.AbstractPawn;
import de.fhwgt.dionarap.model.objects.Ammo;
import de.fhwgt.dionarap.model.objects.Destruction;
import de.fhwgt.dionarap.model.objects.Obstacle;
import de.fhwgt.dionarap.model.objects.Opponent;
import de.fhwgt.dionarap.model.objects.Player;
import de.fhwgt.dionarap.model.objects.Vortex;

public class Spielfeld extends JPanel {
	private JLabel[][] schachbrett; // Array mit Schachbrettfeldern Zeile x
									// Spalte
	private int zeilen; // Anzahl der Felder
	private int spalten;

	private String Spieler = "S"; // Spieler wird auf Feld durch ein S
									// dargestellt
	private String Gegner = "G";
	private String Hindernis = "H";
	private String Zerstört = "*";

	private Hauptfenster eltern;
	private Player spieler;
	
	
	//Icons
	private ImageIcon iconAmmo;
	private ImageIcon iconDestruction;
	private ImageIcon iconGameOver;
	private ImageIcon iconObstacle;
	private ImageIcon iconOpponent;
	private ImageIcon iconPlayer;
	private ImageIcon iconPlayer1;	//links unten
	private ImageIcon iconPlayer2;	//unten
	private ImageIcon iconPlayer3;	//unten rechts
	private ImageIcon iconPlayer4;	//links
	private ImageIcon iconPlayer6;	//rechts
	private ImageIcon iconPlayer7;	//oben links
	private ImageIcon iconPlayer8;	//oben
	private ImageIcon iconPlayer9;	//oben rechts
	private ImageIcon iconPlayerLost; 
	private ImageIcon iconVortex;
	
	
	public void changeTheme(){
		this.createIcons();
		this.clearSpielfeld();
		this.fillBattleground();
	}
	

	public Spielfeld(Hauptfenster parent) { // Konstruktor Spielfeld
		eltern = parent;
		DionaRapModel model = eltern.getModel();
		zeilen = model.getGrid().getGridSizeY();
		spalten = model.getGrid().getGridSizeX();
		
		
		

		// Spielfelder zum "Schachbrett" hinzufügen
		this.setLayout(new GridLayout(zeilen, spalten)); // GridLayout(10,10)
		schachbrett = new JLabel[zeilen][spalten];

		for (int y_achse = 0; y_achse < zeilen; y_achse++) {
			for (int x_achse = 0; x_achse < spalten; x_achse++) {

				// Label mit 50 x 50 pixel anlegen
				schachbrett[y_achse][x_achse] = new JLabel();
				schachbrett[y_achse][x_achse].setPreferredSize(new Dimension(
						50, 50));

				// Labels bzw. Spielfeld einfärben. Mit Modulo 2 prüfen ob
				// schwarz oder weis
				if ((y_achse % 2) == 0) {
					if ((x_achse % 2) == 0) {
						schachbrett[y_achse][x_achse]
								.setBackground(Color.black);
					} else {
						schachbrett[y_achse][x_achse]
								.setBackground(Color.white);
					}
				} else {
					if ((x_achse % 2) == 0) {
						schachbrett[y_achse][x_achse]
								.setBackground(Color.white);
					} else {
						schachbrett[y_achse][x_achse]
								.setBackground(Color.black);

					}
				}

				/* Label deckend darstellen */
				schachbrett[y_achse][x_achse].setOpaque(true);
				// Das Array mit den Labels zum Panel hinzufügen
				schachbrett[y_achse][x_achse].addMouseListener(new ListenerMaus(this.eltern));
				this.add(schachbrett[y_achse][x_achse]);
			}
		}
		createIcons(); //Icons erzeugen
		this.fillBattleground();
		
		
	}
	
	
	
	
	/**
	 * Funktion erzeugt das Verzeichnis dynamisch und Instanziert die Icons
	 * @author Achim Strohm
	 */
	private void createIcons(){
			
			String theme = this.eltern.getTheme();
			
			//verzeichnis zum Ordner in dem sich die Icons befinden
			String verzeichnis = "icons"+File.separator+theme+File.separator;
			
			iconAmmo = new ImageIcon(verzeichnis+"ammo.png");
			iconDestruction = new ImageIcon(verzeichnis+"destruction.gif");
			iconGameOver = new ImageIcon(verzeichnis+"gameover.gif");
			iconObstacle = new ImageIcon(verzeichnis + "obstacle.gif");
			iconOpponent = new ImageIcon(verzeichnis + "opponent.gif");
			iconPlayer = new ImageIcon(verzeichnis + "player.gif");
			iconPlayer1 = new ImageIcon(verzeichnis + "player1.gif");
			iconPlayer2 = new ImageIcon(verzeichnis + "player2.gif");
			iconPlayer3 = new ImageIcon(verzeichnis + "player3.gif");
			iconPlayer4 = new ImageIcon(verzeichnis + "player4.gif");
			iconPlayer6 = new ImageIcon(verzeichnis + "player6.gif");
			iconPlayer7 = new ImageIcon(verzeichnis + "player7.gif");
			iconPlayer8 = new ImageIcon(verzeichnis + "player8.gif");
			iconPlayer9 = new ImageIcon(verzeichnis + "player9.gif");
			iconPlayerLost = new ImageIcon(verzeichnis + "loss.gif");
			iconVortex = new ImageIcon(verzeichnis + "vortex.gif");
			
			
		}

	/**
	 * Holt vorhandene Spielfiguren aus dem Array und setzt diese aufs Spielfeld
	 * @author Achim Strohm
	 */
	public void fillBattleground() {
		AbstractPawn[] spielfiguren = eltern.getModel().getAllPawns(); //alle Spielfiguren auf dem Spielfeld werden herausgeholt
	
		int blickrichtung;
		
		for (AbstractPawn currentSpielfigur : spielfiguren) { // für jede
																// Spielfigur in
																// Spielfiguren
			int x = currentSpielfigur.getX();
			int y = currentSpielfigur.getY();

			// System.out.println("Objekt bei: " + x + ", " + y);
			if (eltern.getgamestatus() == Hauptfenster.GAMELOST){
				ImageIcon loss  =   new ImageIcon("bilder/loss.gif");
				schachbrett[y][x].setIcon(loss);
			}
			else if(eltern.getgamestatus() == Hauptfenster.GAMEWON){
				ImageIcon win  =   new ImageIcon("bilder/win.gif");
				schachbrett[y][x].setIcon(win);
			}
			else{
				
			
				if (currentSpielfigur instanceof Player) { // Player erbt von
															// AbstractPawn
					
					blickrichtung = ((Player) currentSpielfigur).getViewDirection();
					
					
					
					if(blickrichtung == 1){
						schachbrett[y][x].setIcon(iconPlayer1);
					}
					if(blickrichtung == 2){
						schachbrett[y][x].setIcon(iconPlayer2);
					}
					if(blickrichtung == 3){
						schachbrett[y][x].setIcon(iconPlayer3);
					}
					if(blickrichtung == 4){
						schachbrett[y][x].setIcon(iconPlayer4);
					}
					if(blickrichtung == 6){
						schachbrett[y][x].setIcon(iconPlayer6);
					}
					if(blickrichtung == 7){
						schachbrett[y][x].setIcon(iconPlayer7);
					}
					if(blickrichtung == 8){
						schachbrett[y][x].setIcon(iconPlayer8);
					}
					if(blickrichtung == 9){
						schachbrett[y][x].setIcon(iconPlayer9);
					}
				}
			}
			
			if (currentSpielfigur instanceof Ammo) {
				schachbrett[y][x].setIcon(iconAmmo);
			}
			
			if (currentSpielfigur instanceof Destruction) {
				schachbrett[y][x].setIcon(iconDestruction);
			}
			
			if (currentSpielfigur instanceof Obstacle) {
				schachbrett[y][x].setIcon(iconObstacle);
			}
			
			if (currentSpielfigur instanceof Opponent) {
				schachbrett[y][x].setIcon(iconOpponent);
			}
			
			if (currentSpielfigur instanceof Vortex) {
				schachbrett[y][x].setIcon(iconVortex);
			}
			
		}
	}
	
	// Farbe invertieren
	public Color farbeInvertieren(Color hintergrundfarbe) {

		if (hintergrundfarbe.equals(Color.black)) {
			return Color.white;
		} else {
			return Color.black;
		}
	}

	
	/**
	 * Funktion geht jedes Label durch und löscht dessen Inhalt
	 * @author Achim Strohm
	 */
	public void clearSpielfeld() {
		for (int k = 0; k < zeilen; k++) {
			for (int j = 0; j < spalten; j++) {
				schachbrett[k][j].setIcon(null);
			}
		}
	}
	
	public JLabel[][] getSpielfeldArray(){
		return this.schachbrett;
	}

}
