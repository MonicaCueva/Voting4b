package es.uniovi.asw.Reportwriter.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import es.uniovi.asw.Application;
import es.uniovi.asw.Reportwriter.WriterReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriterReportImpl implements WriterReport{

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	

	/**
	 * Método que escribe una linea en el fichero log.
	 */
	@Override
	public void errorMismoEmail(String email) {
		String mensaje = "Se ha producido un error debido a que hay varios usuarios con un mismo email: " + email;
		log.error(mensaje);
		Date actual = new Date();
		crearFicheroLog(actual + " ERROR " + mensaje);
		
	}
	
	
	/**
	 * Método que crea un fichero log
	 * @param texto
	 */
	private void crearFicheroLog(String texto) {
		File fichero = new File("log.txt");
		
		if (!fichero.exists())
		{
						
	        BufferedWriter ficheroLog;
			try {
				ficheroLog = new BufferedWriter(new FileWriter(new 
						File("log.txt")));
				ficheroLog.write(texto);
				ficheroLog.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}else{
			 BufferedWriter ficheroLog;
				try {
					ficheroLog = new BufferedWriter(new FileWriter("log.txt", true));
					ficheroLog.newLine();
					ficheroLog.write(texto);
					ficheroLog.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}   
		}
			
		
	}

	@Override
	public void error(String mensaje) {
		log.error(mensaje);
		Date actual = new Date();
		crearFicheroLog(actual + " ERROR " + mensaje);		
	}

	@Override
	public void info(String mensaje) {
		log.info(mensaje);
		Date actual = new Date();
		crearFicheroLog(actual + " INFO " + mensaje);	
	}
}
