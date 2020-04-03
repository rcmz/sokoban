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

public class EcouteurDeClavier implements KeyListener {
	private Jeu jeu;
	private NiveauGraphique niveauGraphique;
	private EcouteurTimer ecouteurTimer;
	
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
			jeu.niveau().movePousseur(touche);
			niveauGraphique.repaint();
		} catch (IllegalStateException e) {
			System.out.println("La touche cliqu�e n'a aucune fonction.");
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
	
	private void initTimer() {
		niveauGraphique.etape = 0;
		ecouteurTimer = new EcouteurTimer(niveauGraphique);
		Timer timer = new Timer(1000, ecouteurTimer);
		timer.start();
		niveauGraphique.repaint();
	}
}
