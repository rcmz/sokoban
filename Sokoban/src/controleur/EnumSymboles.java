package controleur;

import java.awt.event.KeyEvent;

public class EnumSymboles {
	public static final char SOL = ' ';
	public static final char MUR = '#';
	public static final char CAISSE = '$';
	public static final char BUT = '.';
	public static final char CAISSE_SUR_BUT = '*';
	public static final char POUSSEUR = '@';
	public static final char POUSSEUR_SUR_BUT = '+';
	
	public static final int BAS = KeyEvent.VK_DOWN;
	public static final int GAUCHE = KeyEvent.VK_LEFT;
	public static final int DROITE = KeyEvent.VK_RIGHT;
	public static final int HAUT = KeyEvent.VK_UP;
	public static final int PLEIN_ECRAN = KeyEvent.VK_ESCAPE;
	public static final int QUITTER_BIS = KeyEvent.VK_A;
	public static final int QUITTER = KeyEvent.VK_Q;
	public static final int NIVEAU_SUIVANT = KeyEvent.VK_N;
	// TODO public static final int NIVEAU_PRECEDENT = KeyEvent.VK_P;
}
