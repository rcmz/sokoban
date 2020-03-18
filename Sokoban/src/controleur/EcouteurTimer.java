package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vue.NiveauGraphique;

public class EcouteurTimer implements ActionListener{
	private NiveauGraphique niveauGraphique;
	private int max = 3;
	
	public EcouteurTimer(NiveauGraphique niveauGraphique) {
		this.niveauGraphique = niveauGraphique;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		niveauGraphique.etape++;
		
		if (niveauGraphique.etape >= max) {
			//timer.stop();
		}
		
		niveauGraphique.repaint();
	}
}
