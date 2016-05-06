package es.uniovi.asw.DBManagement.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class Voto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean contabilizado;

	private boolean nulo;
	private boolean blanco;
	private String opcion;

	@ManyToOne
	private ColegioElectoral colegio;

	protected Voto() {
	}

	public Voto(ColegioElectoral colegio, String opcion, boolean contabilizado, boolean nulo, boolean blanco) {
		super();
		this.colegio = colegio;
		this.opcion = opcion;
		this.contabilizado = contabilizado;
		this.nulo = nulo;
		this.blanco = blanco;
	}

	public Long getId() {
		return id;
	}

	public boolean isNulo() {
		return nulo;
	}

	public void setNulo(boolean nulo) {
		this.nulo = nulo;
	}

	public boolean isBlanco() {
		return blanco;
	}

	public void setBlanco(boolean blanco) {
		this.blanco = blanco;
	}

	public ColegioElectoral getColegio() {
		return colegio;
	}

	public boolean isContabilizado() {
		return contabilizado;
	}

	public void setContabilizado(boolean contabilizado) {
		this.contabilizado = contabilizado;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String partidoPolitico) {
		this.opcion = partidoPolitico;
	}

	public void setColegio(ColegioElectoral colegio) {
		this.colegio = colegio;
	}

	@Override
	public String toString() {
		return "Voto [contabilizado=" + contabilizado + ", nulo=" + nulo + ", blanco=" + blanco + ", partidoPolitico="
				+ opcion + ", colegio=" + colegio + "]";
	}

}
