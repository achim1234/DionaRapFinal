import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.fhwgt.dionarap.controller.DionaRapController;


public class ListenerWaffe implements ActionListener{

	//@Override
	public void actionPerformed(ActionEvent e){
		JButton source = (JButton) e.getSource();

		//benoetige DionaRapController um Spieler zu bewegen 
		Hauptfenster mainWindow = (Hauptfenster) source.getTopLevelAncestor().getParent(); //ancestor gibt container zurück in dem sich das Element befindet
		DionaRapController controller = (DionaRapController) mainWindow.getDionaRapController();
		
		
		//wenn keine munition vorhanden blinken starten, wenn schießen im navigator angeklickt
		if(mainWindow.getModel().getShootAmount() == 0){
			mainWindow.createThreadt_ammo();
			
			
			if(mainWindow.getThreadt_ammo() == null){
				mainWindow.createThreadt_ammo(); //wenn thread noch nicht besteht, wird neuer thread gestartet
			}
			else if(!(mainWindow.getThreadt_ammo().isAlive())){
				mainWindow.createThreadt_ammo(); //oder thread nicht mehr aktiv ist
			}
				
			
		}
		
		
		
		
		//wenn munition vorhanden, kann geschossen werden
		if((mainWindow.getModel().getShootAmount()) > 0){
			controller.shoot();
		}
		
		
		
		System.out.println("Shoot " + source.getActionCommand());
		//source.requestFocus();
		}
}


