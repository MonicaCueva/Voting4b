package es.uniovi.asw.WebObserver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import es.uniovi.asw.controller.Main;

/**
 * Created by Daplerio on 7/4/16.
 */
public class WebObserver implements Observer {

	public void actualizar(Map<String, Integer> map) {

		Main.resultados = generarDatosGrafica(map);
		Main.mensajeResultado = generarTextoGrafica(map);
		Main.fechaRefresco = generarHoraActualizacion();
	}

	@Override
	public int[] generarDatosGrafica(Map<String, Integer> resultados) {
		int[] aux = new int[4];
		aux[0] = resultados.get("SI");
		aux[1] = resultados.get("NO");
		aux[2] = resultados.get("NULO");
		aux[3] = resultados.get("BLANCO");
		return aux;
	}

	@Override
	public String generarTextoGrafica(Map<String, Integer> resultados) {
		return "Si: " + resultados.get("SI") + " - No: " + resultados.get("NO") + " - En blanco: " + resultados.get("BLANCO") + " - Nulo: " + resultados.get("NULO");
	}

	@Override
	public String generarHoraActualizacion() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	

}
