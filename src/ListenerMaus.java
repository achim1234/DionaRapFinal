import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.fhwgt.dionarap.controller.DionaRapController;


public class ListenerMaus extends MouseAdapter implements ActionListener{
	
	private Hauptfenster hauptfenster;
	private DionaRapController dionaRapController;
	
	private JLabel[][] labelArray;
	private int playerPositionX;
	private int playerPositionY;
	
	private JPopupMenu popUpMenu;
	private JMenuItem dracula;
	private JMenuItem spaceWars;
	private JMenuItem squareHead;
	
	private String gamedirectory = Hauptfenster.getGameDirectory();
	private String separator = System.getProperty("file.separator");
	
	
	
	
	public ListenerMaus(Hauptfenster hauptfenster){
		this.hauptfenster = hauptfenster;
		
		popUpMenu = new JPopupMenu("Thema");
		
		
		popUpMenu.add(dracula = new JMenuItem("Dracula", (Icon) new ImageIcon(gamedirectory + "icons" + separator + "Dracula" + separator + "player.gif")));
		dracula.addActionListener(this);
		popUpMenu.add(spaceWars = new JMenuItem("SpaceWars", (Icon) new ImageIcon(gamedirectory + "icons" + separator + "SpaceWars" + separator + "player1.gif")));
		spaceWars.addActionListener(this);
		popUpMenu.add(squareHead = new JMenuItem("SquareHead", (Icon) new ImageIcon(gamedirectory + "icons" + separator + "SquareHead" + separator + "player.gif")));
		squareHead.addActionListener(this);
		
		
		if(this.hauptfenster.getTheme().equals("Dracula")){
			dracula.setEnabled(false);
		}
		else if(this.hauptfenster.getTheme().equals("SpaceWars")){
			spaceWars.setEnabled(false);
		}
		else if(this.hauptfenster.getTheme().equals("SquareHead")){
			squareHead.setEnabled(false);
		}
		
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		/* setze Theme Dracula */
		if(e.getSource() == dracula){
			hauptfenster.setTheme("Dracula");
			dracula.setEnabled(false);
			spaceWars.setEnabled(true);
			squareHead.setEnabled(true);
		}
		/* setze Theme SpaceWars */
		if(e.getSource() == spaceWars){
			hauptfenster.setTheme("SpaceWars");
			dracula.setEnabled(true);
			spaceWars.setEnabled(false);
			squareHead.setEnabled(true);
		}
		/* setze Theme Squaredhead */
		if(e.getSource() == squareHead){
			hauptfenster.setTheme("Squarehead");
			dracula.setEnabled(true);
			spaceWars.setEnabled(true);
			squareHead.setEnabled(false);
		}
		
	}
	
	
	
	public void mousePressed(MouseEvent e){
		
		/*Rechtsklick*/
		if(e.getButton() == 3){
			/*zeige Popupmenu an*/
			popUpMenu.show(e.getComponent(), e.getX(), e.getY());
		}
		//Linksklick
		else if(e.getButton() == 1) {	
			System.out.println("linksklick");
			System.err.println(e.getSource());
			
			/* groesse des Spielfelds */
			int size_x = hauptfenster.getModel().getGrid().getGridSizeX();
			int size_y = hauptfenster.getModel().getGrid().getGridSizeY();
			/* lege Spielfeld an */
			labelArray = new JLabel[size_x][size_y];
			labelArray = hauptfenster.getSpielfeld().getSpielfeldArray();
			/* aktuelle Spielerposition*/
			playerPositionX = hauptfenster.getPlayer().getX();
			playerPositionY = hauptfenster.getPlayer().getY();
			dionaRapController = (DionaRapController) hauptfenster.getDionaRapController();
			/* pruefen auf welches Label der Linksklick gemacht wurde */
			for(int i = 0; i < size_y; i++){
				for(int j = 0; j < size_x; j++){
				
					if(e.getSource() == labelArray[i][j]){///
						
						/*es wurde auf den Spieler geklickt - schiessen */
						if( i == playerPositionY && j == playerPositionX){
							
							//wenn keine munition vorhanden blinken starten, wenn schießen auf spieler angeklickt
							if(hauptfenster.getModel().getShootAmount() == 0){
								hauptfenster.createThreadt_ammo();
								
								
								if(hauptfenster.getThreadt_ammo() == null){
									hauptfenster.createThreadt_ammo(); //wenn thread noch nicht besteht, wird neuer thread gestartet
								}
								else if(!(hauptfenster.getThreadt_ammo().isAlive())){
									hauptfenster.createThreadt_ammo(); //oder thread nicht mehr aktiv ist
								}
									
								
							}
							
							//wenn munition vorhanden, kann geschossen werden
							if((hauptfenster.getModel().getShootAmount()) > 0){
								dionaRapController.shoot();
							}
							
							
						
						//break;
						}
						/* es wurde "links unten geklickt */
						else if((playerPositionY - i == -1) && (playerPositionX - j == 1)){
						
							dionaRapController.movePlayer(1);
							System.out.println("links unten (1)");
						}
						/*es wurde "unten"geklickt*/
						else if((playerPositionY - i == -1) && (playerPositionX - j == 0)){
							dionaRapController.movePlayer(2);
							System.out.println("unten (2)");
						}
						/*es wurde rechts unten geklickt*/
						else if((playerPositionY - i == -1) && (playerPositionX - j == -1)){
							dionaRapController.movePlayer(3);
							System.out.println("rechts unten (3)");
						}
						/*es wurde links geklickt*/
						else if((playerPositionY - i == 0) && (playerPositionX - j == 1)){
							dionaRapController.movePlayer(4);
							System.out.println("links (4)");
						}
						/*es wurde rechts geklickt*/
						else if((playerPositionY - i == 0) && (playerPositionX - j == -1)){
							dionaRapController.movePlayer(6);
							System.out.println("rechts (6)");
						}
						/*es wurde oben links geklickt*/
						else if((playerPositionY - i == 1) && (playerPositionX - j == 1)){
							dionaRapController.movePlayer(7);
							System.out.println("links oben (7)");
						}
						/*es wurde oben geklickt*/
						else if((playerPositionY - i == 1) && (playerPositionX - j == 0)){
							dionaRapController.movePlayer(8);
							System.out.println("oben (8)");
						}
						/*es wurde oben rechts geklickt*/
						else if((playerPositionY - i == 1) && (playerPositionX - j == -1)){
							dionaRapController.movePlayer(9);
							System.out.println("rechts oben (9)");
						}else{
							
							System.out.println("es wurde an eine Falsche stelle geklickt");
						}
					}
				}
			}	
		}
	}
}
