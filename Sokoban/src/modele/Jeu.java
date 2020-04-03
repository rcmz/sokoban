package modele;
import java.io.*;

import global.Paths;

public class Jeu {
	private Niveau niveau;
	private LecteurNiveau lecteurNiveau;
	
	public Jeu() {
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(Paths.MOTHERBRAIN_ANTOINE + "src/niveaux.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.lecteurNiveau = new LecteurNiveau(inputStream);
	}
	
	public Niveau niveau() {
		return this.niveau;
	}
	
	public boolean prochainNiveau() {
		this.niveau = lecteurNiveau.lisProchainNiveau();
		return this.niveau != null;
	}
	
	/*
	public boolean niveauPrecedent() {
		//TODO A impl�menter
	}
	 */
}
