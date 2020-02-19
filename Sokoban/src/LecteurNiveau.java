import java.util.*;
import java.io.*;

class LecteurNiveau {
    Scanner m_scanner;

    LecteurNiveau(InputStream stream) {
        m_scanner = new Scanner(stream);
    }

    Niveau lisProchainNiveau() {
        Niveau niveau = null;
        int i = 0;
        boolean niveauFini = false;

        while (m_scanner.hasNextLine() && niveauFini == false) {
            String ligne = m_scanner.nextLine();

            if (ligne.isEmpty()) {
                niveauFini = true;
            } else if (ligne.charAt(0) == ';') {
                niveau.fixeNom(ligne.substring(1));
            } else {
                if (niveau == null) {
                    niveau = new Niveau();
                }

                for (int j = 0; j < ligne.length(); j++) {
                    switch (ligne.charAt(j)) {
                        case EnumSymboles.SOL:
                            niveau.videCase(i, j);
                            break;
                        case EnumSymboles.MUR:
                            niveau.ajouteMur(i, j);
                            break;
                        case EnumSymboles.POUSSEUR:
                            niveau.ajoutePousseur(i, j);
                            break;
                        case EnumSymboles.POUSSEUR_SUR_BUT:
                            niveau.ajoutePousseur(i, j);
                            niveau.ajouteBut(i, j);
                            break;
                        case EnumSymboles.CAISSE:
                            niveau.ajouteCaisse(i, j);
                            break;
                        case EnumSymboles.CAISSE_SUR_BUT:
                            niveau.ajouteCaisse(i, j);
                            niveau.ajouteBut(i, j);
                            break;
                        case EnumSymboles.BUT:
                            niveau.ajouteBut(i, j);
                            break;
                    }
                }

                i++;
            }
        }

        return niveau;
    }
}
