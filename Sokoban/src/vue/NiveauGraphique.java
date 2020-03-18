package vue;
/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 *
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 *
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */

import global.Configuration;
import global.Paths;
import modele.Jeu;
import modele.Niveau;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.io.InputStream;

public class NiveauGraphique extends JComponent {
	public Jeu jeu;
	Image imgSol;
	Image imgPousseur;
	Image imgMur;
	Image imgCaisseSurBut;
	Image imgCaisse;
	Image imgBut;
	private int tailleCase = 20;
	public int etape = 0;
	private boolean maximized;

	public NiveauGraphique(Jeu jeu) {
		// Chargement de l'image de la même manière que le fichier de niveaux
		try {
			// Chargement d'une image utilisable dans Swing
			this.imgSol = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/Sol.png"));
			this.imgPousseur = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/Pousseur.png"));
			this.imgMur = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/Mur.png"));
			this.imgCaisseSurBut = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/CaisseSurBut.png"));
			this.imgCaisse = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/Caisse.png"));
			this.imgBut = ImageIO.read(Configuration.charge(Paths.WINDOWS_QUENTIN + "images/But.png"));
		} catch (Exception e) {
			Configuration.instance().logger().severe("Impossible de charger l'image");
			System.exit(1);
		}
		
		this.jeu = jeu;
		this.maximized = false;
	}

	public void paintComponent(Graphics g) {
		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		Graphics2D drawable = (Graphics2D) g;

		// On recupere quelques infos provenant de la partie JComponent
		int width = getSize().width;
		int height = getSize().height;
		
		tailleCase = Math.min(width/this.jeu.niveau().colonnes(), height/this.jeu.niveau().lignes());

		// On efface tout
		drawable.setPaint(Color.white);
		drawable.fillRect(0, 0, width, height);

		// On affiche une petite image au milieu
		for (int i = 0; i < this.jeu.niveau().lignes(); i++) {
			for (int j = 0; j < this.jeu.niveau().colonnes(); j++) {
				drawable.drawImage(imgSol, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
				Image img = null;
				
				if (this.jeu.niveau().aMur(i, j)) {
					img = imgMur;
				} else if (this.jeu.niveau().aBut(i, j)) {
					if (this.jeu.niveau().aCaisse(i, j)) {
						img = imgCaisseSurBut;
					} else if (this.jeu.niveau().aPousseur(i, j)){
						drawable.drawImage(imgBut, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
						img = imgPousseur;
					} else {
						img = imgBut;
					}
				} else if (this.jeu.niveau().aPousseur(i, j)) {
					img = imgPousseur;
				} else if (this.jeu.niveau().aCaisse(i, j)) {
					img = imgCaisse;
				}
				
				drawable.drawImage(img, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
			}
		}
	}

	public int getTailleCase() {
		return this.tailleCase;
	}
	
	public Niveau niveau() {
		return this.jeu.niveau();
	}
	
	public void toggleFullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		if (maximized) {
			device.setFullScreenWindow(null);
			maximized = false;
		} else {
			device.setFullScreenWindow((JFrame) SwingUtilities.getWindowAncestor(this));
			maximized = true;
		}
	}
}