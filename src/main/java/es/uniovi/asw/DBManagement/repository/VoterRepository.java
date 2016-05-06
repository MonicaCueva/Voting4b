package es.uniovi.asw.DBManagement.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.DBManagement.modelo.Voter;

public interface VoterRepository extends CrudRepository<Voter, Long> {

	Voter findByNif(String nif);
	Voter findByUsuario(String usuario);
	Voter findByEmailAndClave(String email, String clave);
	List<Voter> findAll();
}
