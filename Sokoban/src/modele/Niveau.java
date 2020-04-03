package modele;
import java.util.*;

import controleur.Coup;
import controleur.EnumSymboles;

public class Niveau implements Cloneable {
    private String m_nom;
    private char[][] m_cases;
    private int[] posPousseur;
    private Coup coupActuel;
    
    public Niveau() {
        m_cases = new char[0][0];
        posPousseur = new int[2];
        coupActuel = null;
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
    	if (i >= m_cases.length || i < 0) {
    		return false;
    	}
    	
    	if (j >= m_cases[0].length || j < 0) {
    		return false;
    	}
    	
        return m_cases[i][j] == EnumSymboles.SOL;
    }

    public boolean aMur(int i, int j) {
    	if (i >= m_cases.length || i < 0) {
    		return false;
    	}
    	
    	if (j >= m_cases[0].length || j < 0) {
    		return false;
    	}
    	
        return m_cases[i][j] == EnumSymboles.MUR;
    }

    public boolean aBut(int i, int j) {
    	if (i >= m_cases.length || i < 0) {
    		return false;
    	}
    	
    	if (j >= m_cases[0].length || j < 0) {
    		return false;
    	}
    	
        return m_cases[i][j] == EnumSymboles.BUT || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    public boolean aPousseur(int i, int j) {
    	if (i >= m_cases.length || i < 0) {
    		return false;
    	}
    	
    	if (j >= m_cases[0].length || j < 0) {
    		return false;
    	}
    	
        return m_cases[i][j] == EnumSymboles.POUSSEUR || m_cases[i][j] == EnumSymboles.POUSSEUR_SUR_BUT;
    }

    public boolean aCaisse(int i, int j) {
    	if (i >= m_cases.length || i < 0) {
    		return false;
    	}
    	
    	if (j >= m_cases[0].length || j < 0) {
    		return false;
    	}
    	
        return m_cases[i][j] == EnumSymboles.CAISSE || m_cases[i][j] == EnumSymboles.CAISSE_SUR_BUT;
    }
    
    public void movePousseurRandomly() {
    	int[] currentPosPousseur = posPousseur;    	
        
    	/*
    		Tant qu'on n'a "pas de chance" sur la valeur du random, i.e. que la valeur renvoy�e ne permet
    		pas de d�placer le pousseur (mur a la case indiqu�e par exemple), on r�cup�re une nouvelle
    		valeur et on retest
    	*/
    	
    	while (currentPosPousseur[0] == posPousseur[0] && currentPosPousseur[1] == posPousseur[1]) {
	    	Random rand = new Random();
	    	
	    	//Pb ici : la fonction "move" modifie la grille même si le pousseur ne bouge pas : résolu en clonant le niveau 
	    	
	    	int val = rand.nextInt() % 4;
	    	val = (val < 0) ? val + 4 : val;
	    	
	    	if (val == 0) {
	    		move(posPousseur[0] - 1, posPousseur[1], -1, 0);
	    	} else if (val == 1) {
	    		move(posPousseur[0] + 1, posPousseur[1], 1, 0);
	    	} else if (val == 2) {
	    		move(posPousseur[0], posPousseur[1] - 1, 0, -1);
	    	} else if (val == 3) {
	    		move(posPousseur[0], posPousseur[1] + 1, 0, 1);
	    	} else {
	    		throw new IllegalStateException();
	    	}
    	}
    }
    
    public ArrayList<Coup> getSuiteCoupsRandom(int nbCoups) {
    	int[] posPousseurInit = posPousseur;    
    	ArrayList<Coup> suiteCoups = new ArrayList<>();
    	int i = 0;
        
    	/*
    	 * Tant que i est inférieur au nombre de coups souhaités,
    	 * on ajoute des coups a notre liste de coups avec notre
    	 * fonction de génération de coups aléatoires
    	 */
    	
    	while (i < nbCoups) {
	    	movePousseurRandomly();
	    	Coup coup = getCoupFromCurrentPos();
	    	suiteCoups.add(coup);
	    	i++;
    	}
    	
    	posPousseur = posPousseurInit;
    	
    	return suiteCoups;
    }
    
    private Coup getCoupFromCurrentPos() {
    	Coup coup = new Coup();
    	
    	int xPousseur = posPousseur[0];
    	int yPousseur = posPousseur[1];
    	
    	coup.setPositionActuelle(posPousseur);
    
    	//On vérifie si on peut aller à gauche
    	if (estVide(xPousseur, yPousseur - 1) || aBut(xPousseur, yPousseur - 1)) {
    		coup.setIfCaseGauchePossible(true);
    	} else if (aCaisse(xPousseur, yPousseur - 1)) {
    		if (estVide(xPousseur, yPousseur - 2) || aBut(xPousseur, yPousseur - 2)) {
    			coup.setIfCaseGauchePossible(true);
    		}
    	}
    	
    	//On vérifie si on peut aller à droite
    	if (estVide(xPousseur, yPousseur + 1) || aBut(xPousseur, yPousseur + 1)) {
    		coup.setIfCaseDroitePossible(true);
    	} else if (aCaisse(xPousseur, yPousseur + 1)) {
    		if (estVide(xPousseur, yPousseur + 2) || aBut(xPousseur, yPousseur + 2)) {
    			coup.setIfCaseDroitePossible(true);
    		}
    	}
    	
    	//On vérifie si on peut aller en bas
    	if (estVide(xPousseur + 1, yPousseur) || aBut(xPousseur + 1, yPousseur)) {
    		coup.setIfCaseBasPossible(true);
    	} else if (aCaisse(xPousseur + 1, yPousseur)) {
    		if (estVide(xPousseur + 1, yPousseur) || aBut(xPousseur + 1, yPousseur)) {
    			coup.setIfCaseBasPossible(true);
    		}
    	}
    	
    	//On vérifie si on peut aller en haut
    	if (estVide(xPousseur - 1, yPousseur) || aBut(xPousseur - 1, yPousseur)) {
    		coup.setIfCaseHautPossible(true);
    	} else if (aCaisse(xPousseur - 1, yPousseur)) {
    		if (estVide(xPousseur - 2, yPousseur) || aBut(xPousseur - 2, yPousseur)) {
    			coup.setIfCaseHautPossible(true);
    		}
    	}
    	
    	return coup;
    }
    
    public void movePousseur(int[] posPousseur) {
    	movePousseur(posPousseur[0], posPousseur[1]);
    }
    
    public void movePousseur(int caseX, int caseY) {
    	if (onPousseur(caseX + 1, caseY)) {
    		move(caseX, caseY, -1, 0);
    	} else if (onPousseur(caseX - 1, caseY)) {
    		move(caseX, caseY, 1, 0);
    	} else if (onPousseur(caseX, caseY + 1)) {
    		move(caseX, caseY, 0, -1);
    	} else if (onPousseur(caseX, caseY - 1)) {
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
    
    public boolean lvlIsBlocked() {
    	boolean isBlocked = false;
    	
    	//On ne vérifie pas la bordure puisqu'elle est nécessairement composée de murs
    	
    	for (int i = 1; i < m_cases.length - 1; i++) {
    		for (int j = 1; j < m_cases[i].length - 1; j++) {
    			if (aCaisse(i, j)) {
    				//S'il y a une caisse, on regarde si elle est bloquée ou pas
    				if (caisseEstBloqueeDefinitivement(i, j)) {
    					isBlocked = true;
    					break;
    				}
    			}
    		}
    		
    		if (isBlocked) {
    			break;
    		}
    	}
    	
    	return isBlocked;
    }
    
    private boolean caisseEstBloqueeDefinitivement(int i, int j) {
    	//On vérifie que la caisse n'est pas collée à deux murs diagonalement adjacents
    	boolean caisseBloquee = false;
    	
    	//On vérifie qu'on est bon a gauche
    	if (aMur(i, j - 1)) {
    		if ((aMur(i + 1, j) || aMur(i - 1, j)) && !aBut(i,j) ) {
    			caisseBloquee = true;
    		}
    	}
    	
    	//On vérifie qu'on est bon à droite
    	if (aMur(i, j + 1)) {
    		if ((aMur(i + 1, j) || aMur(i - 1, j)) && !aBut(i,j)) {
    			caisseBloquee = true;
    		}
    	}
    	
    	//On a fait tous les cas possibles de blocage donc on return
    	
    	return caisseBloquee;
    }
    
    public boolean caisseEstBloqueeTemporairementn(int i, int j) {
    	//Sur le même principe que le blocage définitif, mais on suppose que la caisse n'est pas bloquée définitivement (à utiliser en +)
    	boolean caisseBloquee = false;
    	
    	//On vérifie qu'on est bon a gauche
    	if (aMur(i, j - 1)) {
    		if (aCaisse(i + 1, j) || aCaisse(i - 1, j)) {
    			caisseBloquee = true;
    		}
    	} else if (aCaisse(i, j - 1)) {
    		if (aCaisse(i + 1, j) || aCaisse(i - 1, j) || aMur(i + 1, j) || aMur(i - 1, j)) {
    			caisseBloquee = true;
    		}
    	}
    	
    	//On vérifie qu'on est bon à droite
    	if (aMur(i, j + 1)) {
    		if (aCaisse(i + 1, j) || aCaisse(i - 1, j)) {
    			caisseBloquee = true;
    		}
    	} else if (aCaisse(i, j + 1)) {
    		if (aCaisse(i + 1, j) || aCaisse(i - 1, j) || aMur(i + 1, j) || aMur(i - 1, j)) {
    			caisseBloquee = true;
    		}
    	}
    	
    	//On a fait tous les cas possibles de blocage donc on return
    	
    	return caisseBloquee;
    }
    
    //Clone
    
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
    
    //Deep copy
    
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
    
    //Getters + setters
    
    public int[] getPosPousseur() {
    	return this.posPousseur;
    }
    
    public Coup getCoupActuel() {
    	return this.coupActuel;
    }
    
    public void setCoupActuel(Coup coup) {
    	this.coupActuel = coup;
    }
}
