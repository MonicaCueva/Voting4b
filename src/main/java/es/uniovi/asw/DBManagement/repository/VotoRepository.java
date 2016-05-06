package es.uniovi.asw.DBManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.DBManagement.modelo.Voto;

public interface VotoRepository extends CrudRepository<Voto, Long> {
	List<Voto> findAll();
}
