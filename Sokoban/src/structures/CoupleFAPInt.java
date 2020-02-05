package structures;

class  CoupleFAPInt <E> implements Comparable <CoupleFAPInt <E>> {
	E element; 
	int priorite;
	
	CoupleFAPInt(E e, int p) {
		element = e; 
		priorite = p;
	}
	
	@Override
	public int compareTo(CoupleFAPInt <E> o) {
		return  priorite  - o.priorite;
	}
}
