package us.lsi.pd.magia;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.stream.Stream2;

public class ProblemaPociones {

	public enum TipoPersonaje {
		Nigromante, Otro
	}

	private static List<Pocion> pocionesDisponibles;
	private static List<Integer> multiplicidadesMaximasIniciales;
	private static Comparator<Pocion> ordenPociones;
	private static Integer nivelInicialOponente;
	private static TipoPersonaje tipoOponente;


	public static List<Pocion> getPociones() {
		return pocionesDisponibles;
	}

	public static Comparator<Pocion> getOrdenPociones() {
		return ordenPociones;
	}

	public static Integer getNivelInicialOponente() {
		return nivelInicialOponente;
	}
	
	public static void setNivelInicialOponente(Integer nivelOponente) {
		ProblemaPociones.nivelInicialOponente = nivelOponente;
	}

	public static TipoPersonaje getTipoOponente() {
		return tipoOponente;
	}

	public static void setTipoOponente(TipoPersonaje tipoOponente) {
		ProblemaPociones.tipoOponente = tipoOponente;
	}

	public static List<Integer> getMultiplicidadesMaximasIniciales() {
		return multiplicidadesMaximasIniciales;
	}

	public static void setMultiplicidadesMaximasIniciales(
			List<Integer> multiplicidadesMaximas) {
		ProblemaPociones.multiplicidadesMaximasIniciales = multiplicidadesMaximas;
	}

	public static void leeObjetosDisponibles(String fichero) {
		// TODO Auto-generated method stub
		ordenPociones = Comparator.naturalOrder();
		pocionesDisponibles = Stream2.fromFile(fichero)
				.<Pocion> map((String s) -> Pocion.create(s))
				.sorted(ordenPociones).collect(Collectors.<Pocion> toList());
	}


	
	
}
