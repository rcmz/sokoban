package controleur;

public class Coup {
	boolean caseGauchePossible;
	boolean caseDroitePossible;
	boolean caseBasPossible;
	boolean caseHautPossible;
	int[] positionActuelle;
	
	public Coup() {
		this.caseGauchePossible = false;
		this.caseDroitePossible = false;
		this.caseBasPossible = false;
		this.caseHautPossible = false;
		this.positionActuelle = null;
	}
	
	public Coup(int[] positionActuelle, boolean gauchePossible, boolean droitePossible, boolean basPossible, boolean hautPossible) {
		this.positionActuelle = positionActuelle;
		this.caseGauchePossible = gauchePossible;
		this.caseDroitePossible = droitePossible;
		this.caseBasPossible = basPossible;
		this.caseHautPossible = hautPossible;
	}
	
	public void setIfCaseGauchePossible(boolean b) {
		this.caseGauchePossible = b;
	}
	
	public void setIfCaseDroitePossible(boolean b) {
		this.caseDroitePossible = b;
	}
	
	public void setIfCaseBasPossible(boolean b) {
		this.caseBasPossible = b;
	}
	
	public void setIfCaseHautPossible(boolean b) {
		this.caseHautPossible = b;
	}
	
	public void setPositionActuelle(int[] pos) {
		this.positionActuelle = pos;
	}
	
	public boolean caseGauchePossible() {
		return this.caseGauchePossible;
	}
	
	public boolean caseDroitePossible() {
		return this.caseDroitePossible;
	}
	
	public boolean caseBasPossible() {
		return this.caseBasPossible;
	}
	
	public boolean caseHautPossible() {
		return this.caseHautPossible;
	}
	
	public int[] positionActuelle() {
		return this.positionActuelle;
	}
}
