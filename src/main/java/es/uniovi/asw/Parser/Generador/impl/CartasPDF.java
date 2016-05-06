package es.uniovi.asw.Parser.Generador.impl;

import java.io.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.Parser.Generador.GeneradorCartas;
import es.uniovi.asw.Reportwriter.WriterFactory;
import es.uniovi.asw.Reportwriter.impl.WriterReportImpl;

public class CartasPDF implements GeneradorCartas {
	
	private String rutaCartasPdf = "cartasPDF/";

	@Override
	public void generador(Voter votante) throws IOException, DocumentException {
		
		generarCartaPdf(votante);
	}	

	/**
	 * Constructor de la clase
	 * @param votante
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public CartasPDF(Voter votante) throws IOException, DocumentException {
		
		generarCartaPdf(votante);
	}
	
	private void generarCartaPdf(Voter votante) throws FileNotFoundException, DocumentException {
				
		String rutaCarta = rutaCartasPdf + votante.getNif() + ".pdf";

		File fichero = new File(rutaCarta);
		
		/*if(votante.getClave().length() < 10)
		{
			GeneradorPasswordsImpl gp = new GeneradorPasswordsImpl();
			String pass = gp.generarPass(votante);
			votante.setClave(pass);
		}*/
		
		if (!fichero.exists())
		{
			File carpeta = new File(rutaCartasPdf);
			
			if (!carpeta.exists()) 
				carpeta.mkdir();
			
			Document documento = new Document();
			FileOutputStream ficheroPdf = new FileOutputStream(rutaCarta);
						 
			PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
			 
			documento.open();
			documento.add(new Paragraph("Usuario: " + votante.getUsuario(),
			                FontFactory.getFont("arial", 20, Font.BOLD)));       
			
			documento.add(new Paragraph(votante.getClave(),
	                FontFactory.getFont("arial", 20, Font.BOLD)));
			
			documento.close();	
		}
		else{
			WriterReportImpl w = (WriterReportImpl)WriterFactory.getWriterReportImpl();
			w.info("La carta en formato PDF para este usuario ya existe");
		}
			
	
	}
}
