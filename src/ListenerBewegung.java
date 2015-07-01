import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import de.fhwgt.dionarap.controller.DionaRapController;


public class ListenerBewegung implements ActionListener{

	
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();

		//benoetige DionaRapController um Spieler zu bewegen 
		Hauptfenster mainWindow = (Hauptfenster) source.getTopLevelAncestor().getParent(); //gibt top-level container (hauptfenster zurück)
		DionaRapController controller = (DionaRapController) mainWindow.getDionaRapController();
		
		//bewege Spieler in entsprechende Richtung
		controller.movePlayer(Integer.parseInt(e.getActionCommand()));
       
        System.out.println("Move " + source.getActionCommand());
	}

}
