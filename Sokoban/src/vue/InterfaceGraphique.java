package vue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import controleur.EcouteurDeClavier;
import controleur.EcouteurDeSouris;
import controleur.RandomIA;
import modele.Jeu;

public class InterfaceGraphique implements Runnable {	
	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("sokoban");

		// Ajout de notre composant de dessin dans la fenetre
		Jeu jeu = new Jeu();
		jeu.prochainNiveau();
		
		NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);
		frame.add(niveauGraphique);
		
		niveauGraphique.addMouseListener(new EcouteurDeSouris(jeu, niveauGraphique));
		frame.addKeyListener(new EcouteurDeClavier(jeu, niveauGraphique));
		
		new IAManager(new RandomIA(jeu.niveau().clone()), niveauGraphique);
		
		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setSize(1280, 720);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		// Swing s'exécute dans un thread séparé. En aucun cas il ne faut accéder directement
		// aux composants graphiques depuis le thread principal. Swing fournit la méthode
		// invokeLater pour demander au thread de Swing d'exécuter la méthode run d'un Runnable.
		SwingUtilities.invokeLater(new InterfaceGraphique());
	}
}
