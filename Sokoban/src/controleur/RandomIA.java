package controleur;

import java.util.ArrayList;

import modele.Niveau;

public class RandomIA {
	public static final int NB_COUPS_SOUHAITES = 10;
	
	private ArrayList<Coup> listeCoups;
	private ArrayList<char[][]> maps;
	
	public RandomIA(Niveau niveau) {
		//this.listeCoups = niveau.getSuiteCoupsRandom(NB_COUPS_SOUHAITES);
		this.listeCoups = new ArrayList<Coup>();
		this.maps = new ArrayList<char[][]>();
		maps.add(niveau.getMap());
		niveau.getSuiteCoupsFinal(maps, listeCoups, niveau.getPosPousseur()[0], niveau.getPosPousseur()[1]);
		
		System.out.println("on passe ici");
		
		for (int i = 0; i < listeCoups.size(); i++) {
			System.out.println("Pos pousseur : " + listeCoups.get(i).positionActuelle[0] + "/" + listeCoups.get(i).positionActuelle[1]);
		}
	}
	
	public ArrayList<Coup> getListeCoups() {
		return this.listeCoups;
	}
}
