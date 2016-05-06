package es.uniovi.asw.Election;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.uniovi.asw.DBManagement.modelo.ColegioElectoral;
import es.uniovi.asw.DBManagement.modelo.Elecciones;
import es.uniovi.asw.DBManagement.repository.ColegioRepository;
import es.uniovi.asw.DBManagement.repository.EleccionesRepository;

@Component("election")
public class Election implements InsertElection {

	@Autowired
	private EleccionesRepository eleccionesRepository;

	@Autowired
	private ColegioRepository colegioRepository;

	@Override
	public void saveElection(Elecciones e) {
		eleccionesRepository.save(e);

	}

	@Override
	public List<Elecciones> listAll() {
		return eleccionesRepository.findAll();
	}

	@Override
	public void saveColegio(ColegioElectoral c) {
		colegioRepository.save(c);

	}

	@Override
	public List<ColegioElectoral> listColegios() {
		return colegioRepository.findAll();
	}

	@Override
	public ColegioElectoral findByCodigoColegio(int codigo) {
		return colegioRepository.findByCodigoColegio(codigo);
	}

}
