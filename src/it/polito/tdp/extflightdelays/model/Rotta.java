package it.polito.tdp.extflightdelays.model;

public class Rotta implements Comparable<Rotta>{

	private Airport a1;
	private Airport a2;
	private double voli;
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public double getVoli() {
		return voli;
	}
	public void setVoli(double voli) {
		this.voli = voli;
	}
	public Rotta(Airport a1, Airport a2, double voli) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.voli = voli;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rotta other = (Rotta) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		if (voli != other.voli)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return a1+" "+a2+" "+voli+"\n";
	}
	@Override
	public int compareTo(Rotta o) {
		
		return (int) (this.voli-o.voli);
	}
	
	
}
