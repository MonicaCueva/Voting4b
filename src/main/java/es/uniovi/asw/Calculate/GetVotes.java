package es.uniovi.asw.Calculate;

import java.util.List;

import es.uniovi.asw.DBManagement.modelo.Voto;

public interface GetVotes {

	List<Voto> getVotes();

	void updateVote(Long id);

}
