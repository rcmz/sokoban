import java.awt.GraphicsDevice;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

class EcouteurDeClavier implements KeyListener {
	private Niveau niveau;
	private NiveauGraphique niveauGraphique;
	private JFrame frame;
	private boolean maximized;
	
	EcouteurDeClavier(Niveau niveau, NiveauGraphique niveauGraphique, JFrame frame) {
		this.niveau = niveau;
		this.niveauGraphique = niveauGraphique;
		this.frame = frame;
		this.maximized = false;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int codeTouche = e.getKeyCode();
		
		if (codeTouche == EnumSymboles.Q || codeTouche == EnumSymboles.A) {
			System.out.println("Vous avez quitté le jeu");
			System.exit(0);
		} else if (codeTouche == EnumSymboles.FLECHE_GAUCHE || codeTouche == EnumSymboles.FLECHE_DROITE || codeTouche == EnumSymboles.FLECHE_HAUT || codeTouche == EnumSymboles.FLECHE_BAS) {
			movePousseur(codeTouche);
		} else if (codeTouche == EnumSymboles.ECHAP) {
			toggleFullScreen();
		}
	}

	private void movePousseur(int touche) {
		try {
			niveau.movePousseur(touche);
			niveauGraphique.repaint();
		} catch (IllegalStateException ex) {
			System.out.println("La touche cliquée n'a aucune fonction.");
		}
		
		if (niveau.lvlIsFinished()) {
			if (niveauGraphique.jeu.prochainNiveau()) {
				niveauGraphique.repaint();
			}
			else {
				System.out.println("Vous avez terminé tous les niveaux !");
				System.exit(0);
			}
		}
	}
	
	private void toggleFullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow(frame);
			maximized = true;
		}
	}

}
