package structures;

public interface Sequence<T> {
    void insereTete(T element);
    void insereQueue(T element);
	void inserer(T element, int indice);
	T extraitTete();
    boolean estVide();
	Iterateur<T> iterateur();
}
