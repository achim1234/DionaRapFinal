

import java.awt.event.WindowEvent;

import javax.swing.JToolBar;


public class WindowListener implements java.awt.event.WindowListener{

	private Hauptfenster hauptfenster;
	
	WindowListener(Hauptfenster hauptfenster){
		this.hauptfenster = hauptfenster;
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Wenn Settingsfenster geschlossen wird, wird der Button wieder aktiviert
	 * @author Achim Strohm
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		this.hauptfenster.getToolbar().setButtonSettingsEnabled();
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
