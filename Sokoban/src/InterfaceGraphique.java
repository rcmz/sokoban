import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InterfaceGraphique implements Runnable {	
	private boolean maximized = false;
	
	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("sokoban");

		// Ajout de notre composant de dessin dans la fenetre
		Jeu jeu = new Jeu();
		jeu.prochainNiveau();
		
		NiveauGraphique niveauGraphique = new NiveauGraphique(jeu);
		frame.add(niveauGraphique);
		
		niveauGraphique.addMouseListener(new EcouteurDeSouris(jeu.niveau(), niveauGraphique));
		frame.addKeyListener(new EcouteurDeClavier(jeu.niveau(), niveauGraphique, frame));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setSize(500, 300);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		// Swing s'exécute dans un thread séparé. En aucun cas il ne faut accéder directement
		// aux composants graphiques depuis le thread principal. Swing fournit la méthode
		// invokeLater pour demander au thread de Swing d'exécuter la méthode run d'un Runnable.
		SwingUtilities.invokeLater(new InterfaceGraphique());
	}
}
