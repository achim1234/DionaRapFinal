import java.io.File;

import javax.swing.filechooser.FileFilter;


public class XMLExtensionFileFilter extends FileFilter {

	private String beschreibung;
	private String dateiextension[];
	
	
	public XMLExtensionFileFilter(String beschreibung, String dateiendung[]){
		this.beschreibung = beschreibung;
		this.dateiextension = dateiendung;
	}
	
	
	
	public boolean accept(File file) {
		
		
		if(file.isDirectory()){
			return true;
		}
		else{
			String path = file.getAbsolutePath();
			for(int i = 0; i < dateiextension.length; i++){
				/* pruefe ob Dateiendung XML */
				String extension = dateiextension[i];
				if((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')){
					return true;
				}
			}
		}
		
		
		return false;
	}

	
	public String getDescription() {
		
		return beschreibung;
	}

}
