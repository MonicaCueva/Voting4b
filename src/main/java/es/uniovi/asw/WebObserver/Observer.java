package es.uniovi.asw.WebObserver;

import java.util.Map;

/**
 * Created by Daplerio on 7/4/16.
 */
public interface Observer {

	public void actualizar(Map<String, Integer> resultados);
	int[] generarDatosGrafica(Map<String, Integer> resultados);
	String generarTextoGrafica(Map<String, Integer> resultados);
	String generarHoraActualizacion();

}
