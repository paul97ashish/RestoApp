package ca.mcgill.ecse223.resto.application;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.RestoAppPage;

public class RestoAppApplication {
private static RestoApp r;

private static String filename = "menu v2.resto";

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RestoAppPage().displayGui();
            }
        });
	}

	public static RestoApp getr() {
		if (r == null) {
			// load model
			r = load();
		}
 		return r;
	}
	
	public static void save() {
		PersistenceObjectStream.serialize(r);
	}
	
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(filename);
		r = (RestoApp) PersistenceObjectStream.deserialize();
		// model cannot be loaded - create empty BTMS
		if (r == null) {
			r = new RestoApp();
		}
		else {
			r.reinitialize();
		}
		return r;
	}
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}

	
}

