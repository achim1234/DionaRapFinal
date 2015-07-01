import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import de.fhwgt.dionarap.controller.DionaRapController;
import de.fhwgt.dionarap.model.data.DionaRapModel;
import de.fhwgt.dionarap.model.data.MTConfiguration;
import de.fhwgt.dionarap.model.objects.AbstractPawn;
import de.fhwgt.dionarap.model.objects.Ammo;
import de.fhwgt.dionarap.model.objects.Player;



public class Hauptfenster extends JFrame{
	static int GAMEINPROGRESS = 0;
	static int GAMEWON = 1;
	static int GAMELOST = 2;
	
	private Spielfeld spielfeld;
	private DionaRapModel spielModel;
	private DionaRapController spielController; 
	private Navigator navigator;
	private Toolbar toolbar;
	
	private String theme = "Dracula";
	private int gamestatus = GAMEINPROGRESS;
	private MenuBar menubar;
	
	private static MTConfiguration mtConf = new MTConfiguration(); //Konfiguration Multithreading
	
	private boolean gameSettingsChanged = false; //Flag ob Spieleeinstellungen angepasst wurden
	
	private Thread t_ammo; //Thread für blinken der Munitionsanzeige
	private int ammoCount = 3;
	private int ammoStart = 3;
	private int obstacle = 4;
	public boolean a = false;
	private int enemy = 2;
	private int x = 15; //ändern der spaltenanzahl
	private int y = 15; //ändern der zeilenanzahl
	
	public Hauptfenster(){
		this.setTitle("DionaRap");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(500, 500); 
		
		this.setLayout(new BorderLayout());
		
		
		
		this.initGame();
		
		
		this.setLocationRelativeTo(null); //zentriert Fenster in der Mitte des Bildschirms, erst aufrufen nachdem die größe festgelegt ist
		this.setVisible(true);
		this.pack(); //BorderLayout auf nötige größe skalieren
		this.navigator = new Navigator(this); //frame wird an Konstruktor von Navigator übergeben, somit kann die Position ermittelt werden
						 
		
		
		this.addComponentListener(new ListenerFenster(navigator)); //Navigator wird beim ComponentListener registriert
		
		
		
		this.setJMenuBar(menubar = new MenuBar(this)); //Menüleiste hinzufügen
		
		this.addKeyListener(new ListenerTastaturEingabe());
		
		this.toolbar = new Toolbar(this);
		this.add(this.toolbar, BorderLayout.NORTH);
		
		
			
		this.requestFocus();
		
		
	}
	
	public Hauptfenster(MTConfiguration conf, DionaRapModel model,boolean b){
		this.a = b;
		this.spielModel= model;
		this.mtConf= conf;
		System.out.println("a:" + this.a);
		this.setTitle("DionaRap");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setSize(500, 500); 
		
		this.setLayout(new BorderLayout());
		
		
		
		this.setgamestatus(GAMEINPROGRESS);
		//spielModel = new DionaRapModel(y, x, enemy, obstacle);
		spielModel.getGrid().getGridSizeY();
		spielModel.getGrid().getGridSizeX();
		
		spielController = new DionaRapController(spielModel);
		
		if(spielfeld != null){
			this.remove(spielfeld);
		}
		
		spielModel.addModelChangedEventListener(new ListenerModel(this));

		this.spielfeld = new Spielfeld(this);
	
		this.add(BorderLayout.CENTER, spielfeld);	//Schachbrettmuster dem Fenster hinzufügen
		
		spielController.setMultiThreaded(mtConf); //Spiel wird auf Multithread-Variante umgeschaltet
		
		
		
		
		
		
		
		if(toolbar != null){ //Toolbar aktualisieren
			toolbar.updateToolbar();			
		}
		
		
		this.setLocationRelativeTo(null); //zentriert Fenster in der Mitte des Bildschirms, erst aufrufen nachdem die größe festgelegt ist
		this.setVisible(true);
		this.pack(); //BorderLayout auf nötige größe skalieren
		this.navigator = new Navigator(this); //frame wird an Konstruktor von Navigator übergeben, somit kann die Position ermittelt werden
						 
		
		
		this.addComponentListener(new ListenerFenster(navigator)); //Navigator wird beim ComponentListener registriert
		
		
		
		this.setJMenuBar(menubar = new MenuBar(this)); //Menüleiste hinzufügen
		
		this.addKeyListener(new ListenerTastaturEingabe());
		
		this.toolbar = new Toolbar(this);
		this.add(this.toolbar, BorderLayout.NORTH);
		
		
			
		this.requestFocus();
		
		
	}
	
	
	
	
	
	public void initGame(){
		
		this.setgamestatus(GAMEINPROGRESS);
		spielModel = new DionaRapModel(y, x, enemy, obstacle);
		
		spielController = new DionaRapController(spielModel);
		
		if(spielfeld != null){
			this.remove(spielfeld);
		}
		
		
		spielModel.setShootAmount(ammoStart);//Legt fest, wieviele Schüsse der Spieler zu Beginn hat (in toolbar)
		for(int i = 1; i <= ammoCount; i++){
			spielModel.addAmmo(new Ammo()); //setzt Ammo Objet auf Spielfeld, zufällige Platzierung
		}
		
		
		spielModel.addModelChangedEventListener(new ListenerModel(this)); //event bei jeder bewegung des spielers usw. geworfen -> spielfeld neu zeichnen

		this.spielfeld = new Spielfeld(this);
	
		this.add(BorderLayout.CENTER, spielfeld);	//Schachbrettmuster dem Fenster hinzufügen
		
		
		/*Multi-Threading Konfiguration initialisieren*/
		if(gameSettingsChanged == false){
			this.initMtConfiguration();
		}
		spielController.setMultiThreaded(mtConf); //Spiel wird auf Multithread-Variante umgeschaltet
		
		
		
		
		
		
		
		if(toolbar != null){ //Toolbar aktualisieren
			toolbar.updateToolbar();			
		}
		
	}
	
	
	
	public void initMtConfiguration(){ //Regelwerk Multithreading der Gegener
		mtConf.setAlgorithmAStarActive(true);
		mtConf.setAvoidCollisionWithObstacles(true);
		mtConf.setAvoidCollisionWithOpponent(true);
		mtConf.setMinimumTime(800);
		mtConf.setShotGetsOwnThread(true);
		mtConf.setOpponentStartWaitTime(6000);
		mtConf.setOpponentWaitTime(4000);
		mtConf.setShotWaitTime(1000);
		mtConf.setRandomOpponentWaitTime(false);
		mtConf.setDynamicOpponentWaitTime(false);
	}
	
	
	
	public void setFlagGameSettingsChanged(){
		this.gameSettingsChanged = true;
	}
	
	
	/**
	 * Munitionsblinkthread wird zurückgegeben
	 * @author Achim Strohm
	 * @return Thread
	 */
	public Thread getThreadt_ammo(){
		return t_ammo;
	}

	
	
	public DionaRapModel getModel(){
		return this.spielModel;
	}
	
	
	
	
	public DionaRapController getDionaRapController(){
		return this.spielController;
	}
	
	
	
	public void createThreadt_ammo(){
		t_ammo = new ThreadAmmo(this);
		t_ammo.start();
	}
	
	
	
	
	
	public Player getPlayer(){
		return this.spielModel.getPlayer();
	}
	
		
	/**
	 * Spielfeld wird zuerst gelöscht, anschließend wieder neu gezeichnet
	 * @author Achim Strohm
	 */
	public void repaintGame(){
		spielfeld.clearSpielfeld();
		spielfeld.fillBattleground();
	}
	
	
	/**
	 * Berechnet den Spielfortschritt
	 * @author Achim Strohm
	 */
	public int getGameProgress(){ //gibt den Spielfortschritt in Prozent, anhand der verbleibenden Gegner an
		float progress = ((enemy - (float)spielModel.getOpponentCount()) / enemy) * 100;
		System.out.println("Progress_hauptfenster"+ ((int)progress));
		return (int)progress;
	}
	
	
	
	public Toolbar getToolbar(){
		return this.toolbar;
	}
	
	
	
	public String getTheme(){
		return theme;
	}
	
	
	
	/** Init-Methode wird aufgerufen, Buttons werden auf Ausgangsposition gesetzt
	 * Munitionsblinkthread wird zurückgegeben
	 * @author Achim Strohm
	 */
	public void startNewGame(){
		initGame();
		
		this.getToolbar().setButtonNSDisabled();
		this.getMenubar().setGameSettingsDisabled();
		this.getMenubar().setLevelReaderDisabled();
		this.pack();
		this.navigator.setNavLocation();
		this.requestFocus();
		
	}
	
	
	
	public Spielfeld getSpielfeld(){
		return spielfeld;
	}
	
	
	public int getgamestatus(){
		return gamestatus;
	}
	
	
	public void setgamestatus(int gamestatus){
		this.gamestatus = gamestatus;
	}
	
	
	
	
	public void setToolbarPosition(boolean top){
		
		//Toolbar oben anzeigen
		if(top){
			this.remove(toolbar);
			this.add(toolbar, BorderLayout.NORTH);
			this.pack();
		}
		else{  //Toolbar unten anzeigen
			this.remove(toolbar);
			this.add(toolbar, BorderLayout.SOUTH);
			this.pack();
		}
	}
	
	
	
	
	public Navigator getNavigator(){
		return this.navigator;
	}
	
	
	
	public static String getGameDirectory(){
		String gamedirectory = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		return (gamedirectory + separator);
	}
	
	
	
	public void setTheme(String theme){
		this.theme = theme;
		this.spielfeld.changeTheme();
	}
	
	
	public void updateGameSettings(int y, int x, int opponents, int obstacles){
		this.y = y;
		this.x = x;
		
		this.enemy = opponents;
		this.obstacle = obstacles;
	}
	
	
	
	public int getZeilen(){
		return y;
	}
	
	public int getSpalten(){
		return x;
	}
	
	
	
	public MTConfiguration getMTConfiguration() {
		return mtConf;
	}
	
	

	public int getObstacle() {
		return obstacle;
	}

	public MenuBar getMenubar() {
		return menubar;
	}
	
	
	
	public static void main(String[] args) {
		new Hauptfenster();
	}
	
}
