import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.fhwgt.dionarap.levelreader.LevelReader;
import de.fhwgt.dionarap.model.data.DionaRapModel;
import de.fhwgt.dionarap.model.data.MTConfiguration;


public class MenuBar extends JMenuBar implements ActionListener {
	
	private Hauptfenster hauptfenster;
	
	private boolean toolBarSichtbar = true;
	private boolean showToolbarOnTop = true;
	private boolean NavigatorSichtbar = true;
	
	private UIManager.LookAndFeelInfo[] lookAndFeelUiManagerInfoArray;
	private JRadioButtonMenuItem lookAndFeelRadioButtonArray[];
	private int activeRadiobutton = 0;
	private int lookAndFeelCounter;
	
	//Menuleiste Elemente
	private JMenu ansicht;
	private JMenu hilfe;
	private JMenu konfiguration;
		
	//Ansicht Elemente
	private JMenu toolbarposition;
	private JMenu lookandfeelmenu;
	private JMenuItem toolbartop;
	private JMenuItem toolbarbottom;
	private JCheckBoxMenuItem toolbaranzeigen;
	private JCheckBoxMenuItem navigatoranzeigen;
		
	// Konfiguration Elemente
	private JMenuItem leveleinlesen;
	private JMenuItem spieleinstellungen;
		
		
	//Hilfe Elemeten
	private JMenuItem spielbeschreibung;
	
	
	
	public MenuBar(Hauptfenster hauptfenster) {
		this.hauptfenster = hauptfenster;
		
		
		//Elemente der Menüleiste
		
		ansicht = new JMenu("Ansicht");
		konfiguration = new JMenu("Konfiguration");
		hilfe = new JMenu("Hilfe");
		
		
		//Unterpunkte von Ansicht
		
		toolbaranzeigen = new JCheckBoxMenuItem("Toolbar anzeigen"); //Menüunterpunkt
		toolbaranzeigen.setState(true);
		toolbaranzeigen.addActionListener(this);
		ansicht.add(toolbaranzeigen);
		
		//Position der Toolbar
		toolbarposition = new JMenu("Toolbar Position"); //Menüunterpunkt
		toolbartop = new JMenuItem("oben");
		toolbarbottom = new JMenuItem("unten");
		toolbarposition.add(toolbartop);
		toolbarposition.add(toolbarbottom);
		toolbartop.addActionListener(this);
		toolbarbottom.addActionListener(this);
		toolbartop.setEnabled(false);
		ansicht.add(toolbarposition);
		ansicht.add(new JSeparator());
		
		
		//Navigator anzeigen
		navigatoranzeigen = new JCheckBoxMenuItem("Navigator anzeigen");
		navigatoranzeigen.setState(true);
		navigatoranzeigen.addActionListener(this);
		ansicht.add(navigatoranzeigen);
		ansicht.add(new JSeparator());
		
		
		//Look and Feel
		lookandfeelmenu = new JMenu("Look and Feel"); //Menüunterpunkt
		lookAndFeelUiManagerInfoArray = UIManager.getInstalledLookAndFeels();
		lookAndFeelCounter = lookAndFeelUiManagerInfoArray.length;
		lookAndFeelRadioButtonArray = new JRadioButtonMenuItem[lookAndFeelCounter];
		
		
		for(int i = 0; i < lookAndFeelCounter; i++){
			//fuelle RadioButtonMenu mit installierten Look and Feels
			lookAndFeelRadioButtonArray[i] = new JRadioButtonMenuItem(lookAndFeelUiManagerInfoArray[i].getName());
			lookandfeelmenu.add(lookAndFeelRadioButtonArray[i]);
			//fuege Menupunkt hinzu
			lookandfeelmenu.add(lookAndFeelRadioButtonArray[i]);
			lookAndFeelRadioButtonArray[i].addActionListener(this);
			//lege aktuelles look and feel fest
			if(UIManager.getLookAndFeel().getName().equals(lookAndFeelUiManagerInfoArray[i].getName())){
				lookAndFeelRadioButtonArray[i].setSelected(true);
				activeRadiobutton = i;
			}
		}
		ansicht.add(lookandfeelmenu);
		
		
		//Konfiguration
		leveleinlesen = new JMenuItem("Level Einlesen"); //Menüunterpunkt
		leveleinlesen.addActionListener(this);
		leveleinlesen.setEnabled(false); //neu
		konfiguration.add(leveleinlesen);
		
		//Spieleinstellungen
		spieleinstellungen = new JMenuItem("Spieleinstellungen");
		spieleinstellungen.addActionListener(this);
		spieleinstellungen.setEnabled(false);
		konfiguration.add(spieleinstellungen);
		
		
		
		
		//Hilfe
		spielbeschreibung = new JMenuItem("Spielbeschreibung"); //Menüunterpunkt
		spielbeschreibung.addActionListener(this);
		hilfe.add(spielbeschreibung);
		
		
		//JMenus zur Menüleiste hinzufügen
		this.add(ansicht);
		this.add(konfiguration);
		this.add(hilfe);
		
	}
	
	private void showHelpPage(){
		new Spielbeschreibung(hauptfenster);
	}
	
	
	public void setGameSettingsEnabled(){ //spieleinstellungen können angeklickt werden
		spieleinstellungen.setEnabled(true);
	}
	
	public void setGameSettingsDisabled(){ //spieleinstellungen ausgegraut
		spieleinstellungen.setEnabled(false);
	}
	
	public void setLevelReaderEnabled(){
		leveleinlesen.setEnabled(true);
	}
	
	public void setLevelReaderDisabled(){
		leveleinlesen.setEnabled(false);
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		
		
		//Standardmäßig wird Toolbar angezeigt
		if(e.getSource() == toolbaranzeigen){
			if(toolBarSichtbar){
				hauptfenster.getToolbar().hideToolbar();
				toolBarSichtbar = false;
				toolbarposition.setEnabled(false);
			}
			else{
				hauptfenster.getToolbar().showToolbar();
				toolBarSichtbar = true;
				toolbarposition.setEnabled(true);
			}
			hauptfenster.pack();
		}
		
		/* Toolbar oben positionieren */
		if(e.getSource() == toolbartop){ //oben wurde angeklickt
			showToolbarOnTop = true;
			hauptfenster.setToolbarPosition(showToolbarOnTop);
			toolbartop.setEnabled(false);//Ansicht->Toolbarposition->oben (deaktiveren) 
			toolbarbottom.setEnabled(true);//Ansicht->Toolbarposition->unten (aktiveren) 
			hauptfenster.pack();
		}
		
		/* Toolbar unten positionnieren */
		if(e.getSource() == toolbarbottom){ //unten wurde angeklickt
			showToolbarOnTop = false;
			hauptfenster.setToolbarPosition(showToolbarOnTop);
			toolbartop.setEnabled(true);//Ansicht->Toolbarposition->oben (aktiveren) 
			toolbarbottom.setEnabled(false);//Ansicht->Toolbarposition->unte (deaktiveren) 
			hauptfenster.pack();
		}
	
	
	
		/*Navigator anzeigen oder ausblenden */
		if(e.getSource() == navigatoranzeigen){
			/*Navigator ausblenden*/
			if(NavigatorSichtbar){
				hauptfenster.getNavigator().hideNavigator();
				NavigatorSichtbar = false;
			}
			/*Navigator einblenden*/
			else{
				hauptfenster.getNavigator().showNavigator();
				NavigatorSichtbar = true;
			}
			hauptfenster.pack();
		}
		
		
		//Look and feel, radiobuttons durchgehen und prüfen
		
		for(int i = 0; i < lookAndFeelCounter; i++){
			
			if(e.getSource() == lookAndFeelRadioButtonArray[i]){
				lookAndFeelRadioButtonArray[activeRadiobutton].setSelected(false);
				activeRadiobutton = i;
				
				try{
					UIManager.setLookAndFeel(lookAndFeelUiManagerInfoArray[i].getClassName());
					SwingUtilities.updateComponentTreeUI(hauptfenster);	//look and feel hauptfenster ändern
					SwingUtilities.updateComponentTreeUI(hauptfenster.getNavigator()); //look and feel navigator ändern
				}catch(ClassNotFoundException e1){
					e1.printStackTrace();
				}catch(InstantiationException e1){
					//TODO Auto-generated catch block
					e1.printStackTrace();
				}catch(IllegalAccessException e1){
					//TODO Auto-generated catch block
					e1.printStackTrace();
				}catch(UnsupportedLookAndFeelException e1){
					//TODO Auto-generated catch block
					e1.printStackTrace();
				}
				hauptfenster.pack();
				
			}
		}
		
		
		/*Level einlesen*/
		
		if(e.getSource() == leveleinlesen){ //wenn LevelEinlesen angeklickt wurde, ActionCommand auf leveleinlesen gesetzt
			
			//filechooser mit xml filter
			JFileChooser filechooser = new JFileChooser(Hauptfenster.getGameDirectory() + "levels");
			filechooser.setFileFilter(new XMLExtensionFileFilter("XML", new String[]{"xml"}));
			
			System.out.println(Hauptfenster.getGameDirectory() + "levels");
			
			int returnvalue = filechooser.showOpenDialog(hauptfenster);
			if(returnvalue == JFileChooser.APPROVE_OPTION){
				
				
				DionaRapModel levelModel = new DionaRapModel(); //neues Model erzeugen
				MTConfiguration conf = new MTConfiguration(); //neue Konfiguration erzeugen
				
						
//				System.out.println(filechooser.getSelectedFile().toString());
				
				LevelReader lr = new LevelReader(conf,levelModel);
				lr.readLevel(filechooser.getSelectedFile().toString()); //holt Pfad von ausgewähltem Level
				hauptfenster.getNavigator().dispose(); //alter Navigator zerstören
				hauptfenster.dispose(); //altes hauptfenster zerstören
				new Hauptfenster(conf, levelModel,true); //neues Hauptfenster erzeugen, neue Konfiguration und Level übergeben
				
			}
		}
			
		
		
		
		
		/*Spieleinstellungsdialog anzeigen*/
		
		if(e.getSource() == spieleinstellungen){ //verschoben nach toolbar
			System.out.println("settings aufgerufen");
			new Spieleinstellungen(hauptfenster);
		}
	
		
		
		/*Spielbeschreibung anzeigen*/
		if(e.getSource() == spielbeschreibung){
			System.out.println("spielebeschreibung aufgerufen");
			showHelpPage();
		}
	}
}
