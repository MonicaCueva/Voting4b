package es.uniovi.asw.DBManagement.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Clase Voter que identifica al votante
 * @author Mónica
 *
 */
@Entity
public class Voter {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; //identificador del votante
	private String nombre; //Nombre y apellidos del votante
	private String email; //Correo electrónico del votante
	private String nif; //Número de identificación fiscal del votante
	private int codigoColegio; //Código del colegio electoral al que tiene que acudir el votante
	private String usuario; //Nombre de usuario del votante para acceder al sistema
	private String clave; //Clave del votante para acceder al sistema
	private boolean ejercioDerechoAlVoto; //Atributo booleano que indica si el usuario ha votado o no
	
	
	 protected Voter() {}

	/**
	 * Constructor con todos los parámetros de la clase Votante.
	 * @param nombre y apellidos del votante
	 * @param email Correo electónico del votante
	 * @param nif Número de identificación fiscal del votante
	 * @param email Correo electrónico del votante
	 * @param nif Número de identificación fiscal del votante
	 * @param codigoColegio Código del colegio electoral al que tiene que acudir el votante
	 * @param usuario Nombre de usuario del votante para acceder al sistema
	 * @param clave Clave del votante para acceder al sistema
	 * @param ejercioDerechoAlVoto indica si el usuario ha votado o no	
	 * 
	 */
	public Voter(String nombre, String email, String nif, int codigoColegio, String usuario,
			String clave, boolean ejercioDerechoAlVoto) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.nif = nif;
		this.codigoColegio = codigoColegio;
		this.usuario = usuario;
		this.clave = clave;
		this.ejercioDerechoAlVoto = ejercioDerechoAlVoto;

	}
	
	
	
	/**
	 * Constructor con parámetros de la clase Votante sin generar usuario y clave.
	 * @param nombre y apellidos del votante
	 * @param email Correo electrónico del votante
	 * @param nif Número de identificación fiscal del votante
	 * @param codigoColegio Código del colegio electoral al que tiene que acudir el votante
	 * 
	 */
	public Voter(String nombre, String email, String nif, int codigoColegio) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.nif = nif;
		this.codigoColegio = codigoColegio;
		this.usuario = ""; //aún no se ha generado el usuario
		this.clave = ""; //aún no se ha generado el usuario
		this.ejercioDerechoAlVoto = false; //el votante aún no ha votado
	}
	
	

	/**
	 * Método que modifica el valor del atributo nombre.
	 * @param nombre del votante
	 */
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	/**
	 * Método que obtiene el valor del atributo nombre.
	 * @return nombre del votante
	 */
	public String getNombre(){
		return nombre;
	}
	
	
	/**
	 * Método que modifica el valor del atributo email.
	 * @param email del votante
	 */
	public void setEmail(String email){
		this.email=email;
	}
	
	/**
	 * Método que obtiene el valor del atributo email.
	 * @return email del votante
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Método que modifica el valor del atributo NIF.
	 * @param nif del votante
	 */
	public void setNif(String nif){
		this.nif=nif;
	}
	
	
	/**
	 * Método que obtiene el valor del atributo NIF.
	 * @return NIF del votante
	 */
	public String getNif(){
		return nif;
	}
	
	/**
	 * Método que modifica el valor del atributo codigoColegio.
	 * @param cod codigo del colegio al que tiene que ir el votante
	 */
	public void setCodigoColegio(int cod){
		this.codigoColegio=cod;
	}
	
	
	/**
	 * Método que obtiene el valor del atributo codigoColegio.
	 * @return codigo del colegio al que tiene que ir el votante
	 */
	public int getCodigoColegio(){
		return codigoColegio;
	}
	
	
	/**
	 * Método que modifica el valor del atributo usuario.
	 * @param usuario del votante para acceder al sistema
	 */
	public void setUsuario(String usuario){
		this.usuario=usuario;
	}
	
	/**
	 * Método que obtiene el valor del atributo usuario.
	 * @return usuario del votante para acceder al sistema
	 */
	public String getUsuario(){
		return usuario;
	}
	
	/**
	 * Método que modifica el valor del atributo clave.
	 * @param clave del votante para acceder al sistema
	 */
	public void setClave(String clave){
		this.clave=clave;
	}
	
	/**
	 * Método que obtiene el valor del atributo clave.
	 * @return clave del votante para acceder al sistema
	 */
	public String getClave(){
		return clave;
	}

	/**
	 * Método que obtiene el valor del atributo ejercioDerechoAlVoto.
	 * @return ejercioDerechoAlVoto devuelve true si el votante ha votado, false en caso contrario
	 */
	public boolean isEjercioDerechoAlVoto() {
		return ejercioDerechoAlVoto;
	}

	
	/**
	 * Método que modifica el valor del atributo ejercioDerechoAlVoto.
	 * @param ejercioDerechoAlVoto indica si el votante ha votado o no
	 */
	public void setEjercioDerechoAlVoto(boolean ejercioDerechoAlVoto) {
		this.ejercioDerechoAlVoto = ejercioDerechoAlVoto;
	}



	/**
	 * Método toString que muestra la información del votante.
	 */
	@Override
	public String toString() {
		return "Voter [nombre=" + nombre + ", email=" + email + ", nif=" + nif
				+ ", codigoColegio=" + codigoColegio + ", usuario=" + usuario
				+ ", clave=" + "****" + ", ejercioDerechoAlVoto="
				+ ejercioDerechoAlVoto + "]";
	}

	


	
	
	
	
	
	
	
	
}
