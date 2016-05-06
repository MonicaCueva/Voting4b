package es.uniovi.asw.DBManagement.modelo;

public class ServerResponse {

	private final String name;
	private final String nif;
	private final String email;
	private final String pollingStationCode;

	public ServerResponse(String name, String nif, String email, String pollingStationCode) {
		this.name = name;
		this.nif = nif;
		this.email = email;
		this.pollingStationCode = pollingStationCode;
	}

	public String getName() {
		return name;
	}

	public String getNif() {
		return nif;
	}

	public String getEmail() {
		return email;
	}

	public String getPollingStationCode() {
		return pollingStationCode;
	}

}