import java.util.*;

class Niveau {
    private String m_nom;
    private char[][] m_cases;
    private int[] posPousseur;

    Niveau() {
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

    void fixeNom(String nom) {
        m_nom = nom;
    }

    void videCase(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = EnumSymboles.SOL;
    }

    void ajouteMur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = EnumSymboles.MUR;
    }

    void ajoutePousseur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? EnumSymboles.POUSSEUR_SUR_BUT : EnumSymboles.POUSSEUR;
        posPousseur = new int[] {i,j};
    }

    void ajouteCaisse(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? EnumSymboles.CAISSE_SUR_BUT : EnumSymboles.CAISSE;
    }

    void ajouteBut(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aPousseur(i, j) ? EnumSymboles.POUSSEUR_SUR_BUT : aCaisse(i, j) ? EnumSymboles.CAISSE_SUR_BUT : EnumSymboles.BUT;
    }

    int lignes() {
        return m_cases.length;
    }

    int colonnes() {
        return lignes() == 0 ? 0 : m_cases[0].length;
    }

    String nom() {
        return m_nom;
    }

    boolean estVide(int i, int j) {
        return m_cases[i][j] == EnumSymboles.SOL;
    }

    boolean aMur(int i, int j) {
        return m_cases[i][j] == EnumSymboles.MUR;
    }

    boolean aBut(int i, int j) {
        return m_cases[i][j] == EnumSymboles.BUT || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    boolean aPousseur(int i, int j) {
        return m_cases[i][j] == EnumSymboles.POUSSEUR || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    boolean aCaisse(int i, int j) {
        return m_cases[i][j] == EnumSymboles.CAISSE || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT;
    }
    
    void movePousseur(int caseX, int caseY) {
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
    
    boolean onPousseur(int x, int y) {
    	return (posPousseur[0] == x && posPousseur[1] == y);
    }
    
    void move(int caseX, int caseY, int gd, int hb) {
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
}
