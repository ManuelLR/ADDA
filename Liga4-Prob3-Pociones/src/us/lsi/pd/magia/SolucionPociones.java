package us.lsi.pd.magia;

import java.util.Set;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class SolucionPociones {
	
	private Multiset<Pocion> m;

	public SolucionPociones(Multiset<Pocion> solucion) {
		// TODO Auto-generated constructor stub
		super();
		this.m=solucion;
	}
	public SolucionPociones() {
		// TODO Auto-generated constructor stub
		super();
		this.m=HashMultiset.create();
	}
	public static SolucionPociones create(){
		return new SolucionPociones();
	}
	public static SolucionPociones create(Multiset<Pocion> solucion) {
		// TODO Auto-generated method stub
		return new SolucionPociones(solucion);
	}
	
	public SolucionPociones add(Pocion ob, int nu){
		Multiset<Pocion> m = HashMultiset.create(this.m);
		m.add(ob, nu);
		return create(m);
	}
	public int count(Pocion po){
		return m.count(po);
	}
	
	public Set<Pocion> elements(){
		return m.elementSet();
	}
	public Integer getCoste(){
		Integer r=0;
		for(Pocion e:m){
			r = (r + e.getCoste().intValue());
		}
		return r;
	}
	public Integer getDano(){
		Integer r=0;
		for(Pocion e:m){
			r = (int) (r + e.getDano());
		}
		return r;
	}
	public int compareTo(Pocion s) {
		int r = getCoste().compareTo(s.getCoste().intValue());
		if(r == 0){
			r = this.toString().compareTo(s.toString());
		}
		return r;
	}
	
	
	public boolean equals(Object arg0) {
		return m.equals(arg0);
	}

	public int hashCode() {
		return m.hashCode();
	}
	
	public String toString() {
		String res=m.toString();
		res+="; costeTotal: "+getCoste();
		res +=",danoTotal: "+getDano();
		return res;
	}

}
