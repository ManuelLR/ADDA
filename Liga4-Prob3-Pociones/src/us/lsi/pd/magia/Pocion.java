package us.lsi.pd.magia;

import java.rmi.UnexpectedException;

import us.lsi.pd.magia.ProblemaPociones.TipoPersonaje;


public class Pocion implements Comparable<Pocion>{

	public static Pocion create(Integer cost, Integer hurt, Integer maxObject,
			TipoPersonaje type) throws UnexpectedException {
		return new Pocion(cost, hurt, maxObject, type);
	}

	public static Pocion create(String input) {
		String[] vector = input.split("[,]");
		if (vector.length != 4) {
			throw new IllegalArgumentException("El texto de entrada \"" + input
					+ "\"no está correctamente formateado");
		}
		return new Pocion(new Integer(vector[0]), new Integer(vector[1]),
				new Integer(vector[2]), convierteATipo(new String(vector[3])));
	}

	private static Integer nextCode=0;
	
	private Integer codigo;
	private Double coste;
	private Integer dano;
	private Integer cantidad;// es la cantidad máxima de objetos también llamado
								// numMaxDeUnidades
	private TipoPersonaje nigromante;

	Pocion(Integer cost, Integer hurt, Integer maxObject, TipoPersonaje type) {
		codigo=nextCode;
		nextCode++;
		coste = cost.doubleValue();
		dano = hurt;
		cantidad = maxObject;
		nigromante = type;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Double getCoste() {
		return coste;
	}

	public Integer getDano() {
		return dano;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public TipoPersonaje getNigromante() {
		return nigromante;
	}
	
	public Double getRatioCosteDano() {
		return ((double) coste) / dano;
	}
	
	public int compareTo(Pocion a){
		int r = getRatioCosteDano().compareTo(a.getRatioCosteDano());
		if (r == 0) {
			r = getCodigo().compareTo(a.getCodigo());
		}
		return r;
	}
	
	@Override
	public String toString() {
		return "P"+ codigo + "(x"+cantidad+")<" + coste + "€,"
				+ dano + ","+ nigromante.toString() + ">";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidad == null) ? 0 : cantidad.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
//		result = prime * result + ((coste == null) ? 0 : coste.hashCode());
//		result = prime * result + ((dano == null) ? 0 : dano.hashCode());
//		result = prime * result
//				+ ((nigromante == null) ? 0 : nigromante.hashCode());
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
		Pocion other = (Pocion) obj;
		if (cantidad == null) {
			if (other.cantidad != null)
				return false;
		} else if (!cantidad.equals(other.cantidad))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (coste == null) {
			if (other.coste != null)
				return false;
		} else if (!coste.equals(other.coste))
			return false;
		if (dano == null) {
			if (other.dano != null)
				return false;
		} else if (!dano.equals(other.dano))
			return false;
		if (nigromante != other.nigromante)
			return false;
		return true;
	}

	private static TipoPersonaje convierteATipo(String text) {
		if (text.trim().toUpperCase().equals("NO")) {
			return TipoPersonaje.Otro;
		} else if (text.trim().toUpperCase().equals("SI")) {
			return TipoPersonaje.Nigromante;
		} else {
			throw new IllegalArgumentException(
					"No se puede convertir el texto \"" + text
							+ "\" a TipoPersonaje");
		}

	}
}
