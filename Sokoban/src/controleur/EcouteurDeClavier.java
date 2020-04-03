package controleur;
import java.awt.GraphicsDevice;

import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import modele.Jeu;
import vue.NiveauGraphique;
import vue.Animation;

public class EcouteurDeClavier implements KeyListener {
	private Jeu jeu;
	private NiveauGraphique niveauGraphique;
	
	public EcouteurDeClavier(Jeu jeu, NiveauGraphique niveauGraphique) {
		this.jeu = jeu;
		this.niveauGraphique = niveauGraphique;
		//initTimer();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int codeTouche = e.getKeyCode();
		//System.out.println(codeTouche);
		
		if (codeTouche == EnumSymboles.QUITTER || codeTouche == EnumSymboles.QUITTER_BIS) {
			System.out.println("Vous avez quitt� le jeu");
			System.exit(0);
		} else if (codeTouche == EnumSymboles.GAUCHE || codeTouche == EnumSymboles.DROITE || codeTouche == EnumSymboles.HAUT || codeTouche == EnumSymboles.BAS) {
			movePousseur(codeTouche);
		} else if (codeTouche == EnumSymboles.PLEIN_ECRAN) {
			niveauGraphique.toggleFullScreen();
		} else if (codeTouche == EnumSymboles.NIVEAU_SUIVANT) {
			if (jeu.prochainNiveau()) {
				niveauGraphique.repaint();
			}
			else {
				System.out.println("Vous avez termin� tous les niveaux !");
				System.exit(0);
			}
		}
	}

	private void movePousseur(int touche) {
		try {
			int pousseurX = jeu.niveau().getPousseurX();
			int pousseurY = jeu.niveau().getPousseurY();
			
			if (touche == EnumSymboles.HAUT && jeu.niveau().aCaisse(pousseurY-1, pousseurX)) {
				niveauGraphique.setAnimationCaisse(new Animation(niveauGraphique, touche, pousseurY-2, pousseurX));
			} else if (touche == EnumSymboles.BAS && jeu.niveau().aCaisse(pousseurY+1, pousseurX)) {
				niveauGraphique.setAnimationCaisse(new Animation(niveauGraphique, touche, pousseurY+2, pousseurX));
			} else if (touche == EnumSymboles.GAUCHE && jeu.niveau().aCaisse(pousseurY, pousseurX-1)) {
				niveauGraphique.setAnimationCaisse(new Animation(niveauGraphique, touche, pousseurY, pousseurX-2));
			} else if (touche == EnumSymboles.DROITE && jeu.niveau().aCaisse(pousseurY, pousseurX+1)) {
				niveauGraphique.setAnimationCaisse(new Animation(niveauGraphique, touche, pousseurY, pousseurX+2));
			}
			
			niveauGraphique.setAnimationPousseur(new Animation(niveauGraphique, touche));
			
			if (!jeu.niveau().movePousseur(touche)) {
				niveauGraphique.setAnimationPousseur(null);
				niveauGraphique.setAnimationCaisse(null);
			}
		} catch (IllegalStateException ex) {

			niveauGraphique.repaint();
		} 
		
		if (jeu.niveau().lvlIsFinished()) {
			if (jeu.prochainNiveau()) {
				niveauGraphique.repaint();
			}
			else {
				System.out.println("Vous avez termin� tous les niveaux !");
				System.exit(0);
			}
		} else if (jeu.niveau().lvlIsBlocked()) {
			System.out.println("Le niveau est bloqué, vous pouvez recommencer  ! =)");
		}
	}
}
