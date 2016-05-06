package es.uniovi.asw.Election;

import java.util.List;

import es.uniovi.asw.DBManagement.modelo.ColegioElectoral;
import es.uniovi.asw.DBManagement.modelo.Elecciones;

public interface InsertElection {

	void saveElection(Elecciones e);

	List<Elecciones> listAll();

	void saveColegio(ColegioElectoral c);

	List<ColegioElectoral> listColegios();

	ColegioElectoral findByCodigoColegio(int codigo);

}
