package es.uniovi.asw.Parser.Generador.impl;

import java.io.*;

import com.itextpdf.text.*;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.Parser.Generador.GeneradorCartas;
import es.uniovi.asw.Reportwriter.WriterFactory;
import es.uniovi.asw.Reportwriter.impl.WriterReportImpl;

/**
 * Clase que genera las cartas de los votantes con su usuario y su clave
 * 
 * @author Adrian
 */
public class CartasTXT implements GeneradorCartas {
		
	private String rutaCartasTxt = "cartasTXT/";

	@Override
	public void generador(Voter votante) throws IOException, DocumentException {

		generarCarta(votante);
	}	
	
	/**
	 * Constructor de la clase
	 * @param votante
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public CartasTXT(Voter votante) throws IOException, DocumentException {
		
		generarCarta(votante);
	}

	/**
	 * Metodo principal que genera la carta
	 * @param votante
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	private void generarCarta(Voter votante) throws IOException, DocumentException {
		
		String rutaCarta = rutaCartasTxt + votante.getNif() + ".txt";

		File fichero = new File(rutaCarta);
		
		/*if(votante.getClave().length() < 10)
		{
			GeneradorPasswordsImpl gp = new GeneradorPasswordsImpl();
			String pass = gp.generarPass(votante);
			votante.setClave(pass);
		}*/
		
		if (!fichero.exists())
		{
			File carpeta = new File(rutaCartasTxt);
			
			if (!carpeta.exists()) 
				carpeta.mkdir();
						
	        BufferedWriter carta = new BufferedWriter(new FileWriter(new 
	        		File(rutaCartasTxt + votante.getNif() + ".txt")));
	            
	        carta.write("Usuario: " + votante.getUsuario());
			String pass = "\nClave: " + votante.getClave();
	        carta.write(pass);
	        
			carta.close();
		}
		else{
			WriterReportImpl w = (WriterReportImpl)WriterFactory.getWriterReportImpl();
			w.info("La carta en formato TXT para este usuario ya existe");
		}
	}
}