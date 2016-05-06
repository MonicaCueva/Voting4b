package es.uniovi.asw.Calculate.voters;

import java.util.Map;

import es.uniovi.asw.DBManagement.modelo.Voto;

public interface OptionType {

	void contarVoto(Voto vote);

	Map<String, Integer> getResult();

	int nVoto();

}