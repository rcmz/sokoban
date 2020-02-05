package structures;

public class ListeChainee<T> implements Sequence<T> {
    CelluleListeChainee<T> premier = null;

	public Iterateur<T> iterateur() {
		return (Iterateur<T>) new IterateurListeChainee<T>(this);
	}

    public void insereTete(T element) {
		CelluleListeChainee<T> tete = new CelluleListeChainee<>();
        tete.valeur = element;
        tete.suivant = premier;
        premier = tete;
    }

    public void insereQueue(T element) {
        CelluleListeChainee<T> queue = new CelluleListeChainee<>();
        queue.valeur = element;
        queue.suivant = null;

        if (premier == null) {
            premier = queue;
        } else {
            CelluleListeChainee<T> dernier = premier;

            while (dernier.suivant != null) {
                dernier = dernier.suivant;
            }

            dernier.suivant = queue;
        }
    }

	public void inserer(T element, int position) {
		CelluleListeChainee<T> prevCell = premier;
		
		for (int i=0; i < position-1; i++) {
			if (prevCell == null) {
				throw new IllegalStateException();
			}

			prevCell = prevCell.suivant;
		}

		CelluleListeChainee<T> currCell = new CelluleListeChainee<>();

		currCell.valeur = element;
		currCell.suivant = prevCell.suivant;
		prevCell.suivant = currCell;
	}

    public T extraitTete() {
        if (premier != null) {
            T valeur = premier.valeur;
            premier = premier.suivant;
            return valeur;
        } else {
            throw new RuntimeException("Séquence vide");
        }
    }

    public boolean estVide() {
        return premier == null;
    }

    public String toString() {
        String string = "[";
        CelluleListeChainee<T> cellule = premier;

        while (cellule != null) {
            string += cellule.valeur;

            if (cellule.suivant != null) {
                string += ", ";
            }

            cellule = cellule.suivant;
        }
        
        string += "]";
        return string;
    }
}
