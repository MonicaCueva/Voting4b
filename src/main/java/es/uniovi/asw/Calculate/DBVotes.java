package es.uniovi.asw.Calculate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.uniovi.asw.DBManagement.modelo.Voto;
import es.uniovi.asw.DBManagement.repository.VotoRepository;


public class DBVotes implements GetVotes{
	
	@Autowired
	private VotoRepository repository;
	
	
	public DBVotes(VotoRepository repository){
		this.repository=repository;
	}

	@Override
	public List<Voto> getVotes() {
		return repository.findAll();
	}

	@Override
	public void updateVote(Long id) {
		Voto voto = null;
		try{
			voto = repository.findOne(id);
			voto.setContabilizado(true);
			repository.save(voto);
		}catch(Exception e){
			System.out.println("No se ha encontrado voto para actualizar");
		}
	}

}
