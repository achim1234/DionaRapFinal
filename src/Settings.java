import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.fhwgt.dionarap.model.data.MTConfiguration;


public class Settings extends JFrame{
	private Hauptfenster hauptfenster;
	private MTConfiguration conf;
	private HashMap<String, String> map;
	private JTable table_settings;
	private int iterator;
	private Object data [][];
	
	
	public Settings(Hauptfenster hauptfenster){
		this.hauptfenster = hauptfenster;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //damit event geworfen wird
		this.addWindowListener(new WindowListener(this.hauptfenster));
		conf = hauptfenster.getMTConfiguration();
		this.setTitle("Settings");
		map = new HashMap<String, String>();
		this.initSettingsWindow();
		
		
		String ueberschrift [] = {"Name", "Wert"};
		table_settings = new JTable(data, ueberschrift);
		
		this.add(new JScrollPane(table_settings));
		this.setLocationRelativeTo(hauptfenster);
		this.setSize(300, 300);
		this.pack();
		this.setVisible(true);
	}
	
	
	
	
	public void initSettingsWindow(){
		
		map.put("Wartezeit der Gegner zu Beginn", String.valueOf(conf.getOpponentStartWaitTime()));
		map.put("Verzögerung eines Schusses", String.valueOf(conf.getShotWaitTime()));
		map.put("Wartezeit eines Gegners vor jedem Schuss", String.valueOf(conf.getOpponentWaitTime()));
		map.put("Anzahl Zeilen des Spielfeldes", String.valueOf(hauptfenster.getModel().getGrid().getGridSizeY()));
		map.put("Anzahl Spalten des Spielfeldes", String.valueOf(hauptfenster.getModel().getGrid().getGridSizeX()));
		map.put("Anzahl Hindernisse", String.valueOf(hauptfenster.getObstacle()));
		map.put("Anzahl Gegner", String.valueOf(hauptfenster.getModel().getOpponentCount()));

		if(conf.isRandomOpponentWaitTime()){
			map.put("Zufällige Wartezeit der Gegner", "Ein");
		}
		else{
			map.put("Zufällige Wartezeit der Gegner", "Aus");
		}
		
		if(conf.isAvoidCollisionWithObstacles()){
			map.put("Gegner meiden Hindernisse", "Ein");
		}
		else{
			map.put("Gegner meiden Hindernisse", "Aus");
		}
		
		if(conf.isAvoidCollisionWithOpponent()){
			map.put("Gegner meiden Kollision mit anderen Gegnern", "Ein");
		}
		else{
			map.put("Gegner meiden Kollision mit anderen Gegnern", "Aus");
		}
		
		data = new Object[map.size()][2];
		
		for(Map.Entry<String, String> entry : map.entrySet()){
			data[iterator][0] = entry.getKey();
			data[iterator][1] = entry.getValue();
			iterator++;
		}
	}
}
