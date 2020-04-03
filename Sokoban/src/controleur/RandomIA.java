package controleur;

import java.util.ArrayList;

import modele.Niveau;

public class RandomIA {
	public static final int NB_COUPS_SOUHAITES = 10;
	
	private ArrayList<Coup> listeCoups;
	
	public RandomIA(Niveau niveau) {
		this.listeCoups = niveau.getSuiteCoupsRandom(NB_COUPS_SOUHAITES);
	}
	
	public ArrayList<Coup> getListeCoups() {
		return this.listeCoups;
	}
}
