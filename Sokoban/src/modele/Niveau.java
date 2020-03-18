package modele;
import java.util.*;

import controleur.EnumSymboles;

public class Niveau implements Cloneable {
    private String m_nom;
    private char[][] m_cases;
    private int[] posPousseur;

    public Niveau() {
        m_cases = new char[0][0];
        posPousseur = new int[2];
    }

    private void redimensionnerCasesSiNecessaire(int l, int c) {
        if (l >= lignes()) {
            char[][] nouveau_cases = new char[l+1][colonnes()];

            for (int i = 0; i < lignes(); i++) {
                for (int j = 0; j < colonnes(); j++) {
                    nouveau_cases[i][j] = m_cases[i][j];
                }
            }

            m_cases = nouveau_cases;
        }

        if (c >= colonnes()) {
            char[][] nouveau_cases = new char[lignes()][c+1];

            for (int i = 0; i < lignes(); i++) {
                for (int j = 0; j < colonnes(); j++) {
                    nouveau_cases[i][j] = m_cases[i][j];
                }
            }

            m_cases = nouveau_cases;
        }
    }

    public void fixeNom(String nom) {
        m_nom = nom;
    }

    public void videCase(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = EnumSymboles.SOL;
    }

    public void ajouteMur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = EnumSymboles.MUR;
    }

    public void ajoutePousseur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? EnumSymboles.POUSSEUR_SUR_BUT : EnumSymboles.POUSSEUR;
        posPousseur = new int[] {i,j};
    }

    public void ajouteCaisse(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? EnumSymboles.CAISSE_SUR_BUT : EnumSymboles.CAISSE;
    }

    public void ajouteBut(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aPousseur(i, j) ? EnumSymboles.POUSSEUR_SUR_BUT : aCaisse(i, j) ? EnumSymboles.CAISSE_SUR_BUT : EnumSymboles.BUT;
    }

    public int lignes() {
        return m_cases.length;
    }

    public int colonnes() {
        return lignes() == 0 ? 0 : m_cases[0].length;
    }

    public String nom() {
        return m_nom;
    }

    public boolean estVide(int i, int j) {
        return m_cases[i][j] == EnumSymboles.SOL;
    }

    public boolean aMur(int i, int j) {
        return m_cases[i][j] == EnumSymboles.MUR;
    }

    public boolean aBut(int i, int j) {
        return m_cases[i][j] == EnumSymboles.BUT || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    public boolean aPousseur(int i, int j) {
        return m_cases[i][j] == EnumSymboles.POUSSEUR || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    public boolean aCaisse(int i, int j) {
        return m_cases[i][j] == EnumSymboles.CAISSE || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT;
    }
    
    public void movePousseurRandomly() {
    	int[] currentPosPousseur = posPousseur;    	
        
    	/*
    		Tant qu'on n'a "pas de chance" sur la valeur du random, i.e. que la valeur renvoyée ne permet
    		pas de déplacer le pousseur (mur a la case indiquée par exemple), on récupère une nouvelle
    		valeur et on retest
    	*/
    	
    	while (currentPosPousseur[0] == posPousseur[0] && currentPosPousseur[1] == posPousseur[1]) {
	    	Random rand = new Random();
	    	
	    	int val = rand.nextInt() % 4;
	    	val = (val < 0) ? val+4 : val;
	    	
	    	if (val == 0) {
	    		move(posPousseur[0]-1, posPousseur[1], -1, 0);
	    	} else if (val == 1) {
	    		move(posPousseur[0]+1, posPousseur[1], 1, 0);
	    	} else if (val == 2) {
	    		move(posPousseur[0], posPousseur[1]-1, 0, -1);
	    	} else if (val == 3) {
	    		move(posPousseur[0], posPousseur[1]+1, 0, 1);
	    	} else {
	    		throw new IllegalStateException();
	    	}
    	}
    }
    
    public void movePousseur(int caseX, int caseY) {
    	if (onPousseur(caseX+1, caseY)) {
    		move(caseX, caseY, -1, 0);
    	} else if (onPousseur(caseX-1, caseY)) {
    		move(caseX, caseY, 1, 0);
    	} else if (onPousseur(caseX, caseY+1)) {
    		move(caseX, caseY, 0, -1);
    	} else if (onPousseur(caseX, caseY-1)) {
    		move(caseX, caseY, 0, 1);
    	} else {
    		throw new IllegalStateException();
    	}
	}
    
    public void movePousseur(int toucheCliquee) {
    	switch(toucheCliquee) {
    		case EnumSymboles.BAS:
    			move(posPousseur[0]+1, posPousseur[1], 1, 0);
    			break;
    		case EnumSymboles.HAUT:
    			move(posPousseur[0]-1, posPousseur[1], -1, 0);
    			break;
    		case EnumSymboles.GAUCHE:
    			move(posPousseur[0], posPousseur[1]-1, 0, -1);
    			break;
    		case EnumSymboles.DROITE:
    			move(posPousseur[0], posPousseur[1]+1, 0, 1);
    			break;
    		default:
    			throw new IllegalStateException();
    	}
	}
    
    public boolean onPousseur(int x, int y) {
    	return (posPousseur[0] == x && posPousseur[1] == y);
    }
    
    public void move(int caseX, int caseY, int gd, int hb) {
    	if (m_cases[caseX][caseY] == EnumSymboles.CAISSE) {
			if (m_cases[caseX+gd][caseY+hb] == EnumSymboles.BUT || m_cases[caseX+gd][caseY+hb] == EnumSymboles.SOL) {
				boolean addGoal = false;
				
				if (aBut(posPousseur[0], posPousseur[1])) {
					addGoal = true;
				}
				
				videCase(posPousseur[0], posPousseur[1]);
				
				if (addGoal) {
					ajouteBut(posPousseur[0], posPousseur[1]);
				}
				
				ajoutePousseur(caseX, caseY);
				ajouteCaisse(caseX+gd, caseY+hb);
				
				posPousseur[0] = caseX;
				posPousseur[1] = caseY;
			}
		}
		else if (m_cases[caseX][caseY] == EnumSymboles.CAISSE_SUR_BUT) {
			if (m_cases[caseX+gd][caseY+hb] == EnumSymboles.BUT || m_cases[caseX+gd][caseY+hb] == EnumSymboles.SOL) {
				boolean addGoalP = false;
				boolean addGoalN = false;
				
				if (aBut(posPousseur[0], posPousseur[1])) {
					addGoalP = true;
				}
				
				videCase(posPousseur[0], posPousseur[1]);
				
				if (addGoalP) {
					ajouteBut(posPousseur[0], posPousseur[1]);
				}
				
				if (aBut(caseX, caseY)) {
					addGoalN = true;
				}
				
				videCase(caseX, caseY);
				
				ajoutePousseur(caseX, caseY);
				
				if (addGoalN) {
					ajouteBut(caseX, caseY);
				}
				
				ajouteCaisse(caseX+gd, caseY+hb);
				
				posPousseur[0] = caseX;
				posPousseur[1] = caseY;
			}
		}
		else if (m_cases[caseX][caseY] == EnumSymboles.BUT) {
			boolean addGoal = false;
			
			if (aBut(posPousseur[0], posPousseur[1])) {
				addGoal = true;
			}
			
			videCase(posPousseur[0], posPousseur[1]);
			
			if (addGoal) {
				ajouteBut(posPousseur[0], posPousseur[1]);
			}
			
			ajoutePousseur(caseX, caseY);
			ajouteBut(caseX, caseY);
			
			posPousseur[0] = caseX;
			posPousseur[1] = caseY;
		}
		else if (m_cases[caseX][caseY] == EnumSymboles.SOL) {
			boolean addGoal = false;
			
			if (aBut(posPousseur[0], posPousseur[1])) {
				addGoal = true;
			}
			
			videCase(posPousseur[0], posPousseur[1]);
			
			if (addGoal) {
				ajouteBut(posPousseur[0], posPousseur[1]);
			}
			
			ajoutePousseur(caseX, caseY);
			
			posPousseur[0] = caseX;
			posPousseur[1] = caseY;
		}
    }
    
    public boolean lvlIsFinished() {
    	boolean lvlIsFinished = true;
    	
    	for (int i=0; i < lignes(); i++) {
    		for (int j=0; j < colonnes(); j++) {
    			if (aCaisse(i,j)) {
    				if (!aBut(i,j)) {
    					lvlIsFinished = false;
    					break;
    				}
    			}
    		}
    		
    		if (!lvlIsFinished) {
    			break;
    		}
    	}
    	
    	return lvlIsFinished;
    }
    
    public Niveau clone() {
    	Niveau newNiveau;
    	
    	try {
    		newNiveau = (Niveau) super.clone();
    		newNiveau.m_cases = deepCopyChar(this.m_cases);
    		newNiveau.posPousseur = deepCopyInt(this.posPousseur);
    		
    		return newNiveau;
    	} catch (CloneNotSupportedException e) {
    		System.out.println("Impossible d'arriver ici mais bon");
    		return null;
    	}
    }
    
    private char[][] deepCopyChar(char[][] prevArray) {
    	char[][] newArray = new char[prevArray.length][prevArray[0].length];
    	
    	for (int i = 0; i < prevArray.length; i++) {
    		for (int j = 0; j < prevArray[0].length; j++) {
    			newArray[i][j] = prevArray[i][j];
    		}
    	}
    	
    	return newArray;
    }
    
    private int[] deepCopyInt(int[] prevArray) {
    	int[] newArray = new int[prevArray.length];
    	
    	for (int i = 0; i < prevArray.length; i++) {  		
   			newArray[i] = prevArray[i];
       	}
    	
    	return newArray;
    }
}
