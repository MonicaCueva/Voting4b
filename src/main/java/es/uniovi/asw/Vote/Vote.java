package es.uniovi.asw.Vote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.modelo.Voto;
import es.uniovi.asw.DBManagement.repository.VoterRepository;
import es.uniovi.asw.DBManagement.repository.VotoRepository;

@Component("vote")
public class Vote implements InsertVote {

	@Autowired
	private VoterRepository voterRepository;

	@Autowired
	private VotoRepository voteRepository;

	public Vote() {

	}

	public Vote(VoterRepository repository) {
		this.voterRepository = repository;

	}

	@Override
	public void saveVote(Voto vote) {
		voteRepository.save(vote);

	}

	@Override
	public void updateEjercioDerechoAlVoto(boolean ejercioDerechoAlVoto, String usuario) {
		Voter voter = this.voterRepository.findByUsuario(usuario);
		voter.setEjercioDerechoAlVoto(ejercioDerechoAlVoto);
		this.voterRepository.save(voter);

	}

	@Override
	public Voter findByUsuario(String usuario) {
		return voterRepository.findByUsuario(usuario);

	}

	@Override
	public List<Voter> listAll() {
		return voterRepository.findAll();
	}

	@Override
	public Voter findByEmailAndClave(String email, String clave) {
		return voterRepository.findByEmailAndClave(email, clave);
	}

	@Override
	public boolean comprobarPosibilidadEjercerVoto(String usuario) {
		Voter voter = findByUsuario(usuario);
		if (voter != null && voter.isEjercioDerechoAlVoto())
			return true;
		return false;

	}

}
