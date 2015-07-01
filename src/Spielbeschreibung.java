import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;


public class Spielbeschreibung extends JDialog{
	
	private Hauptfenster hauptfenster;
//	private java.net.URL url = "file:///" + "user.dir"+ "file.separator" + "html" + "file.separator" + "Spielbeschreibung.html";
	
	private java.net.URL url;
	
	Spielbeschreibung(Hauptfenster hauptfenster){
		this.hauptfenster = hauptfenster;
		
		this.setDefaultCloseOperation(2);
		this.setTitle("Beschreibung");
		this.setLayout(new BorderLayout());
		
	
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
	
		try{
			
			File f = new File("html/Spielbeschreibung.html");
			editorPane.setPage(f.toURI().toURL());
			
		}catch(Exception e){
			System.out.println("Fehler bei der url");
		}
		
		
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(editorScrollPane, "Center");
		
		
		
		JButton closeButton = new JButton("Schlieﬂen!");
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Spielbeschreibung.this.dispose(); //wenn Button geklickt, Fenster schlieﬂen
				
			}
		});
		
		this.add(closeButton, "South");
		this.setSize(600,400);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	
	}

}
