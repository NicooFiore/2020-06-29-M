package it.polito.tdp.imdb.model;

public class DirettoreConPeso implements Comparable<DirettoreConPeso> {
	private Director d;
	private int peso;
	public DirettoreConPeso(Director d, int peso) {
		super();
		this.d = d;
		this.peso = peso;
	}
	public Director getD() {
		return d;
	}
	public void setD(Director d) {
		this.d = d;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		result = prime * result + peso;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirettoreConPeso other = (DirettoreConPeso) obj;
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return d.getFirstName() + " : " + peso;
	}
	@Override
	public int compareTo(DirettoreConPeso o) {
		return this.peso-o.getPeso();
	}
	

}
