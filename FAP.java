public class FAP<T extends Comparable<T>> {
	Sequence<T> sequence;
	
	private FAP(Sequence<T> sequence) {
		this.sequence = sequence;
	}

	public static FAP<T> FAPTableau() {
		return new FAP<T>(new Tableau<T>());
	}

	public static FAP<T> FAPListeChainee() {
		return new FAP<T>(new ListeChainee<T>());
	}

	public void inserer(T element) {
		sequence.insererTete(element);
	}

	public T extraire() {
		Iterateur<T> rechMax = sequence.iterateur();
		T max = rechMax.prochain;

		while (rechMax.aProchain()) {
			T elem = rechMax.prochain();

			if (max.compareTo(elem) < 0) {
				max = elem;
			}
		}

		Iterateur<T> extraitMax = sequence.iterateur();
		boolean maxTrouve = false;

		while (extraitMax.aProchain() && !maxTrouve) {
			if (extraitMax.prochain() == max) {
				extraitMax.supprime();
				maxTrouve = true;
			}
		}

		return max;
	}
}
