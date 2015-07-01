import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;


public class ThreadAmmo extends Thread{

	private Hauptfenster hauptfenster;
	private static final int blinkTime = 500;
	
	ThreadAmmo(Hauptfenster hauptfenster){
		this.hauptfenster = hauptfenster;
	}
	
	
	public void run(){
		for(int i=0; (i<6) && (hauptfenster.getModel().getShootAmount() == 0); i++){
			//JLabel[] ammo = hauptfenster.getToolbar().getMuniJLabelArr();
			for(int k=0; k < 3; k++){
				/* abwechselnd Rand hinzufuegeb / entfernen */
				if((i % 2) == 0){
				
					
					hauptfenster.getToolbar().getAmmoJPanel().setBorder(BorderFactory.createTitledBorder(null, "Munition", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null, Color.RED));
				
				}else{
					
					hauptfenster.getToolbar().getAmmoJPanel().setBorder(BorderFactory.createTitledBorder(null, "Munition", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, null, Color.BLACK));
					
				}
				hauptfenster.getToolbar().getAmmoJPanel().updateUI();
			}
			try{
				/*Thread schlafen legen*/
				Thread.sleep(blinkTime);
			}catch (InterruptedException ex){
				System.out.println("Catched exception in Thread Ammo.");
				System.out.println(ex.getStackTrace());
			}
		}
	}
}
