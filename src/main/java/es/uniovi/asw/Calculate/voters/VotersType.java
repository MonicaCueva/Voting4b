package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.Calculate.GetVotes;

public interface VotersType {

	public Map<String, Integer> getResult();
	public void actualize(GetVotes votes);
	public OptionType getTipo();
	

}
