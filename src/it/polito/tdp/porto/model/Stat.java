package it.polito.tdp.porto.model;

public class Stat implements Comparable<Stat> {
	
	private int nrPubb;
	private int nrRiv;
	
	public Stat(int nrPubb, int nrRiv) {
		super();
		this.nrPubb = nrPubb;
		this.nrRiv = nrRiv;
	}

	public int getNrPubb() {
		return nrPubb;
	}

	public void setNrPubb(int nrPubb) {
		this.nrPubb = nrPubb;
	}

	public int getNrRiv() {
		return nrRiv;
	}

	public void setNrRiv(int nrRiv) {
		this.nrRiv = nrRiv;
	}

	@Override
	public int compareTo(Stat other) {
		
		return this.nrPubb-other.getNrPubb();
	}
	
	

}
