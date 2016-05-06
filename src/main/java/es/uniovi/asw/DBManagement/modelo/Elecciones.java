package es.uniovi.asw.DBManagement.modelo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Elecciones {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;

	public Elecciones(String nombre, Date fechaInicio, Date fechaFin) {
		super();
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;

	}

	protected Elecciones() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	@Override
	public String toString() {
		return "Elecciones [nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ "]";
	}

}
