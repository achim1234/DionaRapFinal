import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ListenerToolbar implements ActionListener {
	
	
	
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		Hauptfenster hauptfenster = (Hauptfenster) button.getTopLevelAncestor(); // getTopLevelAncestor holt die Referenz des Hauptfensters
		
		
		//neues Spiel durch Toolbar-Button starten
		if(button.getActionCommand() == "new_game"){
			//Spiel neu instanziieren
			hauptfenster.startNewGame();
		}
		
		if(button.getActionCommand() == "settings"){
			hauptfenster.getToolbar().setButtonSettingsDisabled(); 
			new Settings(hauptfenster);
		}
	}
}
