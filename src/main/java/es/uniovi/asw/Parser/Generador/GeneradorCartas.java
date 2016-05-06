package es.uniovi.asw.Parser.Generador;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import es.uniovi.asw.DBManagement.modelo.Voter;

public interface GeneradorCartas {
	
	public void generador(Voter votante) throws IOException, DocumentException;
}
