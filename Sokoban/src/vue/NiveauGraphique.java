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

import controleur.Coup;

import java.awt.*;
import java.io.InputStream;

public class NiveauGraphique extends JComponent {
	public Jeu jeu;
	private Image imgSol;
	private Image imgPousseur;
	private Image imgMur;
	private Image imgCaisseSurBut;
	private Image imgCaisse;
	private Image imgBut;
	Image rouge;
	Image vert;
	private int tailleCase = 20;
	private boolean maximized;
	private Animation animationPousseur;
	private Animation animationCaisse;

	public NiveauGraphique(Jeu jeu) {
		// Chargement de l'image de la même manière que le fichier de niveaux
		try {
			// Chargement d'une image utilisable dans Swing
			this.imgSol = ImageIO.read(Configuration.charge("images/Sol.png"));
			this.imgPousseur = ImageIO.read(Configuration.charge("images/Pousseur.png"));
			this.imgMur = ImageIO.read(Configuration.charge("images/Mur.png"));
			this.imgCaisseSurBut = ImageIO.read(Configuration.charge("images/CaisseSurBut.png"));
			this.imgCaisse = ImageIO.read(Configuration.charge("images/Caisse.png"));
			this.imgBut = ImageIO.read(Configuration.charge("images/But.png"));
			this.rouge = ImageIO.read(Configuration.charge("images/Rouge.png"));
			this.vert = ImageIO.read(Configuration.charge("images/Vert.png"));
			
		} catch (Exception e) {
			Configuration.instance().logger().severe("Impossible de charger l'image");
			System.exit(1);
		}
		
		this.jeu = jeu;
		this.maximized = false;
		this.animationPousseur = null;
		this.animationCaisse = null;
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
		drawable.setPaint(Color.black);
		drawable.fillRect(0, 0, width, height);

		// affichage de l'arriere plan
		for (int i = 0; i < this.jeu.niveau().lignes(); i++) {
			for (int j = 0; j < this.jeu.niveau().colonnes(); j++) {
				drawable.drawImage(imgSol, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);

				if (this.jeu.niveau().aMur(i, j)) {
					drawable.drawImage(imgMur, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
				}
				
				if (this.jeu.niveau().aBut(i, j)) {
					drawable.drawImage(imgBut, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
				}
			}
		}
		
		// affichage du premier plan
		for (int i = 0; i < this.jeu.niveau().lignes(); i++) {
			for (int j = 0; j < this.jeu.niveau().colonnes(); j++) {
				if (this.jeu.niveau().aCaisse(i, j)) {
					if (animationCaisse != null && animationCaisse.estA(i, j)) {
						drawable.drawImage(imgCaisse, (int) ((j+animationCaisse.getX())*tailleCase), (int) ((i+animationCaisse.getY())*tailleCase), tailleCase, tailleCase, null);
					} else {
						drawable.drawImage(imgCaisse, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
					}
				}
				
				if (this.jeu.niveau().aPousseur(i, j)) {
					if (animationPousseur != null) {
						drawable.drawImage(imgPousseur, (int) ((j+animationPousseur.getX())*tailleCase), (int) ((i+animationPousseur.getY())*tailleCase), tailleCase, tailleCase, null);
					} else {
						drawable.drawImage(imgPousseur, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
					}
					
					showPossibilities(drawable, i, j);
				}
			}
		}
	}
	
	private void dessineCase(Graphics2D drawable, Image img, int j, int i) {
		drawable.drawImage(img, j*tailleCase, i*tailleCase, tailleCase, tailleCase, null);
	}
	
	public void setAnimationPousseur(Animation animation) {
		animationPousseur = animation;
	}
	
	public void setAnimationCaisse(Animation animation) {
		animationCaisse = animation;
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
	
	private void showPossibilities(Graphics2D drawable, int i, int j) {
		Coup coupActuel = jeu.niveau().getCoupActuel();
		
		//Testé : Fonctionne bien, par contre problème de plans entre l'image et la ligne
		
		if (coupActuel != null) {
			drawable.setColor(new Color(0xFF0000));
			drawable.setStroke(new BasicStroke(10));
			
			if (coupActuel.caseGauchePossible()) {
				drawable.drawImage(rouge, (j - 1) * tailleCase, i * tailleCase, tailleCase, tailleCase, null);
//				drawable.drawLine((j - 1) * tailleCase, i * tailleCase, j * tailleCase, (i + 1) * tailleCase);
//				drawable.drawLine((j - 1) * tailleCase, (i + 1) * tailleCase, j * tailleCase, i * tailleCase);
			}

			if (coupActuel.caseDroitePossible()) {
				drawable.drawImage(rouge, (j + 1) * tailleCase, i * tailleCase, tailleCase, tailleCase, null);
//				drawable.drawLine((j + 1) * tailleCase, i * tailleCase, (j + 2) * tailleCase, (i + 1) * tailleCase);
//				drawable.drawLine((j + 1) * tailleCase, (i + 1) * tailleCase, (j + 2) * tailleCase, i * tailleCase);
			}
			
			if (coupActuel.caseBasPossible()) {
				drawable.drawImage(rouge, j * tailleCase, (i + 1) * tailleCase, tailleCase, tailleCase, null);
//				drawable.drawLine(j * tailleCase, (i + 1) * tailleCase, (j + 1) * tailleCase, (i + 2) * tailleCase);
//				drawable.drawLine(j * tailleCase, (i + 2) * tailleCase, (j + 1) * tailleCase, (i + 1) * tailleCase);
			}
			
			if (coupActuel.caseHautPossible()) {
				drawable.drawImage(rouge, j * tailleCase, (i - 1) * tailleCase, tailleCase, tailleCase, null);
//				drawable.drawLine(j * tailleCase, (i - 1) * tailleCase, (j + 1) * tailleCase, i * tailleCase);
//				drawable.drawLine(j * tailleCase, i * tailleCase, (j + 1) * tailleCase, (i - 1) * tailleCase);
			}
		}
	}
}