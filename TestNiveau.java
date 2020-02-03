import java.util.*;
import java.io.*;

class TestNiveau {
    public static void main(String[] args) {
        try {
            LecteurNiveau lecteur_niveau = new LecteurNiveau(new FileInputStream(args[0]));
            RedacteurNiveau redacteur_niveau = new RedacteurNiveau(System.out);
            Niveau niveau = null;

            do {
                niveau = lecteur_niveau.lisProchainNiveau();

                if (niveau != null) {
                    redacteur_niveau.ecrisNiveau(niveau);
                }
            } while (niveau != null);
        } catch (FileNotFoundException e) {
            System.out.println("Impossible d'ouvrir le fichier " + args[0]);
        }
    }
}
