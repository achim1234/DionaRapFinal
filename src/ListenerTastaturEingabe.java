import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.fhwgt.dionarap.controller.DionaRapController;


public class ListenerTastaturEingabe implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void keyTyped(KeyEvent e) {
		Hauptfenster hauptfenster = (Hauptfenster) e.getSource();
		DionaRapController dr_controller = hauptfenster.getDionaRapController();

		/* Taste 5 - Schuss */
		if(e.getKeyChar() == '5'){ //gibt character von dem das Event ausgelöst wurde zurück
			
			//wenn keine munition vorhanden blinken starten, wenn schießen im navigator angeklickt
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
				dr_controller.shoot();
			}
			
			System.out.println("Shot " + e.getKeyChar());
		}
		/* Taste 1-4 + 6-9 */
		else if(e.getKeyChar() != '5' &&  ('1' <= e.getKeyChar() && e.getKeyChar() <= '9')){
		
			/* bewege Spieler in entsprechende Richtun */
			dr_controller.movePlayer(Character.getNumericValue(e.getKeyChar()));
			System.out.println("Move " + e.getKeyChar());
		}
		/* beliebige andere Taste */
		else {
			System.out.println(e.getKeyChar());
		}
		
	}

}
