import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class EcouteurDeClavier implements KeyListener {
	private Niveau niveau;
	private NiveauGraphique niveauGraphique;
	
	EcouteurDeClavier(Niveau niveau, NiveauGraphique niveauGraphique) {
		this.niveau = niveau;
		this.niveauGraphique = niveauGraphique;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int codeTouche = e.getKeyCode();
		
		if (codeTouche == EnumSymboles.Q || codeTouche == EnumSymboles.A) {
			System.out.println("Vous avez quitté le jeu");
			System.exit(0);
		} else if (codeTouche == EnumSymboles.FLECHE_GAUCHE || codeTouche == EnumSymboles.FLECHE_DROITE || codeTouche == EnumSymboles.FLECHE_HAUT || codeTouche == EnumSymboles.FLECHE_BAS) {
			movePousseur(codeTouche);
		} else if (codeTouche == EnumSymboles.ECHAP) {
			//TODO A implémenter
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
