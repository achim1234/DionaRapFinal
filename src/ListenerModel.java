import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import de.fhwgt.dionarap.model.events.DionaRapChangedEvent;
import de.fhwgt.dionarap.model.events.GameStatusEvent;
import de.fhwgt.dionarap.model.listener.DionaRapListener;


public class ListenerModel implements DionaRapListener{

	private Hauptfenster hauptfenster;
	private boolean gameLost = false;
	
	
	public ListenerModel(Hauptfenster _hauptfenster){
		hauptfenster = _hauptfenster;
	}
	
	
	public void modelChanged(DionaRapChangedEvent arg0) {
		hauptfenster.repaintGame(); //Figuren neu einzeichnen, repaintGame in Hauptfenster definiert
		hauptfenster.getToolbar().updateToolbar();
	}

	@Override
	public void statusChanged(GameStatusEvent arg0) {
		String[] options = new String[2];
		options[0] = "Neues Spiel";
		options[1] = "Abbrechen";

		ImageIcon image = null;
		String title = null;
		String message = null;

		/*
		 * isGameWon() externe Methode
		 */
		if (arg0.isGameWon()) {
			hauptfenster.setgamestatus(Hauptfenster.GAMEWON);
			hauptfenster.repaintGame();
			
			title = "Gewonnen!";
			message = "Sie haben das Spiel gewonnen";
			image = new ImageIcon("bilder/Dracula/win.gif");
		} else if (arg0.isGameOver()) {
			
			hauptfenster.setgamestatus(Hauptfenster.GAMELOST); //setzt Status auf verloren
			hauptfenster.repaintGame(); //zeichnet Spielfeld neu, damit loser-Icon ausgegeben wird
			

			title = "Verloren!";
			message = "Sie haben das Spiel leider verloren";
			
			image = new ImageIcon("bilder/Dracula/gameover.gif");
			
			
		} else {
			return;
		}
		
		
		hauptfenster.getToolbar().setButtonSettingsEnabled();
		hauptfenster.getToolbar().setButtonNSEnabled();
		hauptfenster.getToolbar().updateToolbar();
		
		
		
		/*neu*/
		hauptfenster.getMenubar().setGameSettingsEnabled();
		hauptfenster.getMenubar().setLevelReaderEnabled(); //LevelReader kann angeklickt werden
		
		
		
		
		
		
		int result = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, image,
				options, options[0]);
		if (result == 0) {
			hauptfenster.startNewGame(); //Spieleinstellungen lassen sich nur nach einem Spiel ändern
		}
	}
}
