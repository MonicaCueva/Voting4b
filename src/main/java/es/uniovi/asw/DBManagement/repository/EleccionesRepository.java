package es.uniovi.asw.DBManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.uniovi.asw.DBManagement.modelo.Elecciones;

public interface EleccionesRepository extends CrudRepository<Elecciones, Long>  {

	Elecciones findByNombre(String nombre);
	List<Elecciones> findAll();
}
