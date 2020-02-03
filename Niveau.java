import java.util.*;

class Niveau {
    private String m_nom;
    private char[][] m_cases;

    Niveau() {
        m_cases = new char[0][0];
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
        m_cases[i][j] = ' ';
    }

    void ajouteMur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = '#';
    }

    void ajoutePousseur(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? '+' : '@';
    }

    void ajouteCaisse(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aBut(i, j) ? '*' : '$';
    }

    void ajouteBut(int i, int j) {
        redimensionnerCasesSiNecessaire(i, j);
        m_cases[i][j] = aPousseur(i, j) ? '+' : aCaisse(i, j) ? '*' : '.';
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
        return m_cases[i][j] == ' ';
    }

    boolean aMur(int i, int j) {
        return m_cases[i][j] == '#';
    }

    boolean aBut(int i, int j) {
        return m_cases[i][j] == '.' || m_cases[i][j] == '*' || m_cases[i][j] == '+';
    }

    boolean aPousseur(int i, int j) {
        return m_cases[i][j] == '@' || m_cases[i][j] == '+';
    }

    boolean aCaisse(int i, int j) {
        return m_cases[i][j] == '$' || m_cases[i][j] == '*';
    }
}
