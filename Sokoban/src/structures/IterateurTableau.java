package structures;

public class IterateurTableau<T> {
	private boolean supprimeCallable;
	private int position;
	private Tableau<T> tableau;

	IterateurTableau(Tableau<T> tableau) {
		this.tableau = tableau;
		this.supprimeCallable = false;
		this.position = 0;
	}
	
	boolean aProchain() {
		return position < this.tableau.taille;
	}

	T prochain() {
		if (aProchain()) {
			supprimeCallable = true;
			return tableau.element(++position);
		}
		else {
			System.out.println("Vous vous situez déjà en bout de liste !");
			return null;
		}
	}

	void supprime() {
		if (supprimeCallable) {
			supprimeCallable = false;
            tableau.supprime(position);
		} else {
			throw new IllegalStateException();
		}
	}
}
