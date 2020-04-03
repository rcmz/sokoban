package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import controleur.Coup;
import controleur.RandomIA;

public class IAManager {
	private RandomIA randomIA;
	
	private NiveauGraphique niveauGraphique;
	
	private ArrayList<Coup> suiteCoups;
	
	IAManager(RandomIA ia, NiveauGraphique niveauGraphique) {
		this.randomIA = ia;
		this.niveauGraphique = niveauGraphique;
		this.suiteCoups = randomIA.getListeCoups();
		moveRandomly();
	}
	
	private void moveRandomly() {
		ActionListener actionListener = new ActionListener() {
			int j = 0;
			
			public void actionPerformed(ActionEvent e) {
				if (j < suiteCoups.size()) {
					int[] position = suiteCoups.get(j).positionActuelle();
					niveauGraphique.niveau().setCoupActuel(suiteCoups.get(j));
					movePousseur(position);
					
					j++;
				}
			}
		};
		
		new Timer(1000, actionListener).start();
	}
	
	private void movePousseur(int[] posPousseur) {
		if (!niveauGraphique.niveau().lvlIsFinished()) {
			niveauGraphique.niveau().movePousseur(posPousseur);
			niveauGraphique.repaint();
		} else {
			niveauGraphique.jeu.prochainNiveau();
			niveauGraphique.repaint();
		}
	}
}
