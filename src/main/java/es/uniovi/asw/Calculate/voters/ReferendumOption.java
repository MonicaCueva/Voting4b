package es.uniovi.asw.Calculate.voters;

import java.util.HashMap;
import java.util.Map;

import es.uniovi.asw.DBManagement.modelo.Voto;

public class ReferendumOption implements OptionType {
	private Map<String, Integer> recuento;
	private int nVoto;

	public ReferendumOption() {
		this.recuento = new HashMap<String, Integer>();
		recuento.put("SI", 0);
		recuento.put("NO", 0);
		recuento.put("NULO", 0);
		recuento.put("BLANCO", 0);
	}

	@Override
	public void contarVoto(Voto vote) {
		if (vote.getOpcion() != null) {
			String dato = vote.getOpcion().toUpperCase();
			switch (dato) {
			case "SI":
				recuento.put("SI", recuento.get("SI").intValue() + 1);
				break;
			case "NO":
				recuento.put("NO", recuento.get("NO").intValue() + 1);
				break;
			default:
				recuento.put("NULO", recuento.get("NULO").intValue() + 1);
				break;
			}
		} else if (vote.isBlanco()){
			recuento.put("BLANCO", recuento.get("BLANCO").intValue() + 1);
		} else if (vote.isNulo()) {
			recuento.put("NULO", recuento.get("NULO").intValue() + 1);
		}
		nVoto++;
	}

	@Override
	public Map<String, Integer> getResult() {
		return recuento;
	}

	public Map<String, Double> getPercents() {
		Map<String, Double> porcentajes = new HashMap<String, Double>();
		for (String k : recuento.keySet()) {
			porcentajes.put(k, (recuento.get(k).doubleValue() / nVoto) * 100);
		}
		return porcentajes;
	}

	@Override
	public int nVoto() {
		return nVoto;
	}

}
