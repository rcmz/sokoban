import java.util.*;

class Tableau<T> implements Sequence<T> {
    
    public T[] tableau = (T[]) new Object[1];
    public int tete = 0;
    public int taille = 0;

    public void insereTete(T element) {
        if (taille == tableau.length) {
            T[] t = (T[]) new Object[tableau.length*2];

            for (int i = 0; i < taille; i++) {
                t[i+1] = tableau[(tete + i) % tableau.length];
            }

            tableau = t;
            tete = 0;
        } else {
            tete--;

            if (tete < 0) {
                tete += tableau.length;
            }
        }

        tableau[tete] = element;
        taille++;
    }

    public void insereQueue(T element) {
        if (taille == tableau.length) {
            T[] t = (T[]) new Object[tableau.length*2];

            for (int i = 0; i < taille; i++) {
                t[i] = tableau[(tete + i) % tableau.length];
            }

            tableau = t;
            tete = 0;
        }

        tableau[(tete + taille) % tableau.length] = element;
        taille++;
    }

    public T extraitTete() {
        if (estVide()) {
            throw new RuntimeException("Séquence vide");
        } 
        
        T e = tableau[tete];
        tete = (tete + 1) % tableau.length;
        taille--;

        return e;
    }

    public void supprime(int i) {
        if (i < 0 || i >= taille) {
            throw new RuntimeException("indice incorrect");
        }

        for (int j = i; j > 0; j--) {
            tableau[(tete + j) % tableau.length] = tableau[(tete + j - 1) % tableau.length]; 
        }

        taille--;
    }

    public T element(int i) {
        if (i < 0 || i >= taille) {
            throw new RuntimeException("indice incorrect");
        }

        return tableau[(tete + i) % tableau.taille];
    }

    public boolean estVide() {
        return taille == 0;
    }

    public String toString() {
        String s = "[";
        
        for (int i = 0; i < taille; i++) {
            s += tableau[(tete + i) % tableau.length];

            if (i < taille - 1) {
                s += ", ";
            }
        }
    
        s += "]";

        /*
        s += " tete : " + tete + ", taille : " + taille + ", memoire : [";
        
        for (int i = 0; i < tableau.length; i++) {
            s += tableau[i];

            if (i < tableau.length - 1) {
                s += ", ";
            }
        }
    
        s += "]";
        */

        return s;
    }
}
