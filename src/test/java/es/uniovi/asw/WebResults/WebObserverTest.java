package es.uniovi.asw.WebResults;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.WebObserver.WebObserver;
import es.uniovi.asw.controller.Main;

public class WebObserverTest {
	
	private WebObserver webObserver;
	private Map<String, Integer> resultados;
	
	@Before
	public void before(){
		webObserver = new WebObserver();
		resultados = new HashMap<>();
		resultados.put("SI", 810);
		resultados.put("NO", 40);
		resultados.put("NULO", 45);
		resultados.put("BLANCO", 10);
	}

	@Test
	public void testGenerarTextoGrafica() {
		String salidaEsperada = "Si: " + 810 + " - No: " + 40 + " - En blanco: " + 10 + " - Nulo: " + 45;
		assertEquals(salidaEsperada, webObserver.generarTextoGrafica(resultados));
	}
	
	@Test
	public void testGenerarDatosGrafica() {
		int[] aux = new int[4];
		aux[0] = 810;
		aux[1] = 40;
		aux[2] = 45;
		aux[3] = 10;
		assertEquals(aux[0], webObserver.generarDatosGrafica(resultados)[0]);
		assertEquals(aux[1], webObserver.generarDatosGrafica(resultados)[1]);
		assertEquals(aux[2], webObserver.generarDatosGrafica(resultados)[2]);
		assertEquals(aux[3], webObserver.generarDatosGrafica(resultados)[3]);
	}
	
	@Test
	public void testGenerarHoraActualizacion() {
		assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()), webObserver.generarHoraActualizacion());
	}
	
	@Test
	public void testActualizar() {		
		webObserver.actualizar(resultados);
		assertEquals(Main.resultados[0], webObserver.generarDatosGrafica(resultados)[0]);
		assertEquals(Main.resultados[1], webObserver.generarDatosGrafica(resultados)[1]);
		assertEquals(Main.resultados[2], webObserver.generarDatosGrafica(resultados)[2]);
		assertEquals(Main.resultados[3], webObserver.generarDatosGrafica(resultados)[3]);
		assertEquals(Main.mensajeResultado, webObserver.generarTextoGrafica(resultados));
		assertEquals(Main.fechaRefresco, webObserver.generarHoraActualizacion());
	}

}
