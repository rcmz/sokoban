package structures;

public class IterateurListeChainee<T> {
    private boolean supprimeCallable;
    private CelluleListeChainee<T> prevCell;
	private CelluleListeChainee<T> currCell;
    private ListeChainee<T> liste;

    IterateurListeChainee(ListeChainee<T> liste) {
        this.liste = liste;
        this.supprimeCallable = false;
        this.prevCell = null;

		try {
			this.currCell = getCurrCell();
		} catch (RuntimeException e) {
			System.out.println("La liste chaînée sur laquelle vous souhaitez itérer est vide !");
		}
    }

	private CelluleListeChainee<T> getCurrCell() {
		if (liste.premier == null) {
			throw new RuntimeException("");
		} else {
			return liste.premier;
		}
	}

    boolean aProchain() {
        return currCell.suivant != null;
    }

    T prochain() {
        if (aProchain()) {
            supprimeCallable = true;
            prevCell = currCell;
			currCell = currCell.suivant;
			return currCell.valeur;
        }
        else {
            System.out.println("Vous vous situez déjà en bout de liste !");
        	return null;
		}
    }

    void supprime() {
        if (supprimeCallable) {
            supprimeCallable = false;
        	prevCell.suivant = currCell.suivant;
		} else {
            throw new IllegalStateException();
        }
    }
}

