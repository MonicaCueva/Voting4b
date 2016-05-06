package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.Calculate.GetVotes;
import es.uniovi.asw.DBManagement.modelo.Voto;

public class VotersTypeImpl implements VotersType {

	private OptionType tipo;

	public VotersTypeImpl() {
		tipo = new ReferendumOption();
	}

	@Override
	public Map<String, Integer> getResult() {
		return tipo.getResult();
	}

	@Override
	public void actualize(GetVotes votes) {
		for (Voto v : votes.getVotes()) {
			if (!v.isContabilizado()) {
				tipo.contarVoto(v);
				votes.updateVote(v.getId());// actualizamos el estado del voto
			}
		}
	}

	public OptionType getTipo() {
		return tipo;
	}

	public void setTipo(OptionType tipo) {
		this.tipo = tipo;
	}
}
