package es.uniovi.asw.VoterAccess;

import es.uniovi.asw.DBManagement.modelo.ServerResponse;

public interface GetVoterInfo {
	ServerResponse getVoter(String email, String password);

}
