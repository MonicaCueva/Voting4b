package es.uniovi.asw.Vote;

import java.util.List;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.modelo.Voto;

public interface InsertVote {

	void saveVote(Voto vote);

	void updateEjercioDerechoAlVoto(boolean ejercioDerechoAlVoto, String usuario);

	Voter findByUsuario(String usuario);

	List<Voter> listAll();

	Voter findByEmailAndClave(String email, String clave);

	boolean comprobarPosibilidadEjercerVoto(String usuario);
}
