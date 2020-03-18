package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.Timer;

import modele.Niveau;
import vue.NiveauGraphique;

public class RandomIA {
	private NiveauGraphique niveauGraphique;
	
	public RandomIA(NiveauGraphique niveauGraphique) {
		this.niveauGraphique = niveauGraphique;
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movePousseur();
			}
		};
		
		new Timer(200, actionListener).start();
	}
	
	private void movePousseur() {
		if (!niveauGraphique.niveau().lvlIsFinished()) {
			niveauGraphique.niveau().movePousseurRandomly();
			niveauGraphique.repaint();
		} else {
			niveauGraphique.jeu.prochainNiveau();
			//niveauGraphique.repaint();
		}
	}
}
