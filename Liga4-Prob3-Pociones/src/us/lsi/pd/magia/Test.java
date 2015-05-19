package us.lsi.pd.magia;

import java.util.List;

import com.google.common.collect.Multiset;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.magia.ProblemaPociones.TipoPersonaje;

public class Test {

	public static void main(String[] args) {

		ProblemaPocionesPD p = ProblemaPocionesPD.create(
				"ficheros/despensa.txt", 100, TipoPersonaje.Nigromante);
		AlgoritmoPD.isRandomize = false;
		System.out.println("Datos introducidos: ");
		System.out.println(ProblemaPociones.getPociones() + "\n" + p.multiplicidadesMaximas);
		System.out.println("Problema Inicial =" + p);
		AlgoritmoPD<Multiset<Pocion>, Integer, Object> a = Algoritmos
				.createPD(p);
		System.out.println("Estas son las alternativas que se van a seleccionar: ");
		a.ejecuta();
		System.out.println("ProblemaPocionesPD: "+p.toString());
		a.showAllGraph("ficheros/pociones.gv", "Pociones", p);
		System.out.println("Solucion= "
				+ SolucionPociones.create(a.getSolucion(p)));
//		System.out.println("Máximos: ");
//		System.out.println("Nigromante: coste-> 326  || dano-> 102");
//		System.out.println("NoNigroman: coste-> ?¿   || dano-> ?¿ ");
		
		System.out.println("Conseguidos: ");
		System.out.println("Nigromante				: coste-> 426  || dano-> 108");
		System.out.println("Nigromante modificado			: coste-> 326  || dano-> 100");
		System.out.println("NoNigroman				: coste-> 163  || dano-> 102");
		
	}
	
	static void imprimeLista(List<Pocion> input){
		for (Pocion a :input){
			System.out.println(a.toString());
		}
	}

}
