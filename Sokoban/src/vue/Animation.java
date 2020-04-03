package vue;

import controleur.EnumSymboles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.Timer;

public class Animation implements ActionListener {
	private int direction;
	private int etape = 0;
	private float taille = 10;
	private JComponent component;
	private Timer timer;
	private int i = -1;
	private int j = -1;
	
	public Animation(JComponent component, int direction) {
		this.component = component;
		this.direction = direction;
		this.component.repaint();
		this.timer = new Timer(16, this);
		this.timer.start();
	}
	
	public Animation(JComponent component, int direction, int i, int j) {
		this.component = component;
		this.direction = direction;
		this.i = i;
		this.j = j;
		this.component.repaint();
		this.timer = new Timer(16, this);
		this.timer.start();
	}
	
	public float getX() {
		if (etape > taille) {
			return 0;
		} else if (direction == EnumSymboles.GAUCHE) {
			return 1-etape/taille;
		} else if (direction == EnumSymboles.DROITE) {
			return etape/taille-1;
		} else {
			return 0;
		}
	}
	
	public float getY() {
		if (etape > taille) {
			return 0;
		} else if (direction == EnumSymboles.HAUT) {
			return 1-etape/taille;
		} else if (direction == EnumSymboles.BAS) {
			return etape/taille-1;
		} else {
			return 0;
		}
	}
	
	public boolean estA(int i, int j) {
		return this.i == i && this.j == j;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		etape++;
		component.repaint();
		
		if (etape >= taille) {
			timer.stop();
		}
	}

}
