package us.lsi.pd.magia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;
import us.lsi.pd.magia.ProblemaPociones.TipoPersonaje;

public class ProblemaPocionesPD implements
		ProblemaPD<Multiset<Pocion>, Integer, Object> {

	private static ProblemaPocionesPD problemaInicial;
	private int index;
	private Double objetivo;

	private Integer nivelActual;
	private Integer costeAcumulado;

	public List<Integer> multiplicidadesMaximas;

	// public List<Pocion> pociones; //quitar

	public static ProblemaPocionesPD create(String fichero,
			Integer nivelInicial, TipoPersonaje tipo) {

		ProblemaPociones.leeObjetosDisponibles(fichero);

		List<Integer> multiplicidades = ProblemaPociones.getPociones().stream()
				.mapToInt(x -> x.getCantidad()).boxed()
				.collect(Collectors.toList());
		Collections.reverse(multiplicidades);

		ProblemaPociones.setNivelInicialOponente(nivelInicial);

//		System.out.println("NivelInicial que se guarda: "
//				+ ProblemaPociones.getNivelInicialOponente());
		ProblemaPociones.setTipoOponente(tipo);
		ProblemaPocionesPD.problemaInicial = new ProblemaPocionesPD(0,
				ProblemaPociones.getNivelInicialOponente(), 0, multiplicidades);
		return ProblemaPocionesPD.problemaInicial;
	}

	/*
	 * public static ProblemaPocionesPD create(int index, int nivelActual,
	 * Integer costeAcumulado, List<Integer> multiplicidades, List<Pocion>
	 * pociones) { return new ProblemaPocionesPD(index, nivelActual,
	 * costeAcumulado, multiplicidades, pociones); }
	 */

	private ProblemaPocionesPD(int index, int nivelActual,
			Integer costeAcumulado, List<Integer> multiplicidades) {
		this.index = index;
		this.nivelActual = nivelActual;
		this.costeAcumulado = costeAcumulado;
		this.multiplicidadesMaximas = multiplicidades;
	}

	@Override
	public us.lsi.pd.ProblemaPD.Tipo getTipo() {
		// TODO Auto-generated method stub
		return Tipo.Min;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		//return 1;
		return ProblemaPociones.getPociones().size()-index+1;
	}

	@Override
	public boolean esCasoBase() {
		// TODO Auto-generated method stub
		return this.nivelActual <= 0
				|| index == ProblemaPociones.getPociones().size() - 1;
	}

	@Override
	public Sp<Integer, Object> getSolucionCasoBase() {
		// TODO Auto-generated method stub
		int dano = ProblemaPociones.getPociones().get(index).getDano();
		int num = this.nivelActual / dano;
		num = Math.min(num, ProblemaPociones.getPociones().get(index)
				.getCantidad());// es lo mismo que cogerlo de la lista
								// multiplicidad
		Double valor = (double) num
				* ProblemaPociones.getPociones().get(index).getCoste();
		return Sp.create(num, valor);
	}

	@Override
	public Sp<Integer, Object> seleccionaAlternativa(
			List<Sp<Integer, Object>> ls) {
		// TODO Auto-generated method stub
		Sp<Integer, Object> r = ls.stream().filter(x -> x.propiedad != null)
				.max(Comparator.naturalOrder()).orElse(null);
		objetivo = r != null ? r.propiedad : Double.MIN_VALUE;
		// objetivo = r != null ? r.propiedad : Double.MAX_VALUE;
		System.out.println(r.toString());
		return r;
	}

	@Override
	public ProblemaPD<Multiset<Pocion>, Integer, Object> getSubProblema(
			Integer a, int np) {
		// TODO Auto-generated method stub
		Preconditions.checkArgument(np == 0);
		int danoAcum = this.nivelActual
				- ProblemaPociones.getPociones().get(index).getDano() * a;

		Double costeAcum = ProblemaPociones.getPociones().get(index).getCoste()
				* a + this.costeAcumulado;
		return new ProblemaPocionesPD(index + 1, danoAcum,
				costeAcum.intValue(), multiplicidadesMaximas);
	}

	@Override
	public Sp<Integer, Object> combinaSolucionesParciales(Integer a,
			List<Sp<Integer, Object>> ls) {
		// TODO Auto-generated method stub
		Sp<Integer, Object> res = ls.get(0); // Solo pasa una solución
		Double valorNuevo = res.propiedad
				+ a*ProblemaPociones.getPociones().get(index).getCoste();//Antes no multiplicaba por "a"
		return Sp.create(a, valorNuevo);
	}

	@Override
	public List<Integer> getAlternativas() {
		// TODO Auto-generated method stub
		List<Integer> ls = new ArrayList<Integer>();
		if (!(ProblemaPociones.getTipoOponente().equals(
				TipoPersonaje.Nigromante) && ProblemaPociones.getPociones()
				.get(index).getNigromante().equals(TipoPersonaje.Otro))) {
			ls = IntStream
					.rangeClosed(0, ProblemaPociones.getPociones().get(this.index).getCantidad())
					.filter(x -> this.nivelActual
							- (x - 1)
							* ProblemaPociones.getPociones().get(this.index)
									.getDano() > 0)
					.boxed().collect(Collectors.toList());
			Collections.reverse(ls);
		} else {
			ls.add(0);
		}

		return ls;
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		// TODO Auto-generated method stub

		return 1;
	}

	@Override
	public Multiset<Pocion> getSolucionReconstruida(Sp<Integer, Object> sp) {
		// TODO Auto-generated method stub
		Multiset<Pocion> m = HashMultiset.create();
		m.add(ProblemaPociones.getPociones().get(index), sp.alternativa);
		return m;
	}

	@Override
	public Multiset<Pocion> getSolucionReconstruida(Sp<Integer, Object> sp,
			List<Multiset<Pocion>> ls) {
		// TODO Auto-generated method stub
		Multiset<Pocion> m = ls.get(0);
		m.add(ProblemaPociones.getPociones().get(index), sp.alternativa);
		return m;
	}

	public Integer getCotaSuperiorValorEstimado(Integer a) {
		Double r = 0.;
		Double nivelActual = (double) (this.nivelActual);
		Double nu = (double) a;
		int ind = this.index;
		while (true) {
			r = r + nu * ProblemaPociones.getPociones().get(ind).getCoste();
			nivelActual = nivelActual - nu
					* ProblemaPociones.getPociones().get(ind).getDano();
			ind++;
			if (ind >= ProblemaPociones.getPociones().size()
					|| nivelActual <= 0) {
				break;
			}
			nu = Math.min(ProblemaPociones.getPociones().get(ind).getCantidad(), nivelActual
					/ ProblemaPociones.getPociones().get(ind).getDano());
		}
		return (int) Math.ceil(r);
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		// TODO Auto-generated method stub
		return (double) (this.costeAcumulado + this
				.getCotaSuperiorValorEstimado(a));
	}

	@Override
	public Double getObjetivo() {
		// TODO Auto-generated method stub
		Double r;
		if (this.esCasoBase()) {
			r = this.costeAcumulado + this.getSolucionCasoBase().propiedad;
		} else if (problemaInicial.equals(this)) {
			r = objetivo;
		} else {
			r = Double.MAX_VALUE;
		}
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nivelActual == null) ? 0 : nivelActual.hashCode());
		return result * prime + index;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaPocionesPD other = (ProblemaPocionesPD) obj;
		if (nivelActual == null) {
			if (other.nivelActual != null)
				return false;
		} else if (!nivelActual.equals(other.nivelActual))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	public String toString() {
		return "(" + index + ", " + nivelActual + ")";
	}
}
