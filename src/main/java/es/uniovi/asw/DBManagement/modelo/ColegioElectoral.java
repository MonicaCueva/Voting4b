package es.uniovi.asw.DBManagement.modelo;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity

public class ColegioElectoral {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private int codigoColegio;
    private String circunscripcion;
    private String comunidadAutonoma;
  
    protected ColegioElectoral() {}
	
	
    
    public ColegioElectoral( int codigoColegio, String circunscripcion,
			String comunidadAutonoma) {
		super();
		this.codigoColegio = codigoColegio;
		this.circunscripcion = circunscripcion;
		this.comunidadAutonoma = comunidadAutonoma;
	}


	public int getCodigoColegio() {
		return codigoColegio;
	}
	public void setCodigoColegio(int codigoColegio) {
		this.codigoColegio = codigoColegio;
	}
	public String getCircunscripcion() {
		return circunscripcion;
	}
	public void setCircunscripcion(String circunscripcion) {
		this.circunscripcion = circunscripcion;
	}
	public String getComunidadAutonoma() {
		return comunidadAutonoma;
	}
	public void setComunidadAutonoma(String comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}
	




	@Override
	public String toString() {
		return "ColegioElectoral [codigoColegio=" + codigoColegio
				+ ", circunscripcion=" + circunscripcion
				+ ", comunidadAutonoma=" + comunidadAutonoma + ", elecciones="
				+ "]";
	}




 
   
}
