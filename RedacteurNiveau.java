import java.util.*;
import java.io.*;

class RedacteurNiveau {
    PrintStream m_print_stream;

    RedacteurNiveau(OutputStream stream) {
        m_print_stream = new PrintStream(stream);
    }

    void ecrisNiveau(Niveau niveau) {
        for (int i = 0; i < niveau.lignes(); i++) {
            for (int j = 0; j < niveau.colonnes(); j++) {
                if (niveau.aMur(i ,j)) {
                    m_print_stream.append('#');
                } else if (niveau.aBut(i, j)) {
                    if (niveau.aPousseur(i, j)) {
                        m_print_stream.append('+');
                    } else if (niveau.aCaisse(i, j)) {
                        m_print_stream.append('*');
                    } else {
                        m_print_stream.append('.');
                    }
                } else if (niveau.aPousseur(i, j)) {
                    m_print_stream.append('@');
                } else if (niveau.aCaisse(i, j)) {
                    m_print_stream.append('$');
                } else {
                    m_print_stream.append(' ');
                }
            }

            m_print_stream.println();
        }

        m_print_stream.println(";" + niveau.nom());
        m_print_stream.println();
    }
}
