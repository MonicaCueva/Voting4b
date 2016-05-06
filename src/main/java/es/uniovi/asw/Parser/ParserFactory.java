package es.uniovi.asw.Parser;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.repository.VoterRepository;
import es.uniovi.asw.Parser.Generador.GeneradorCartas;
import es.uniovi.asw.Parser.Generador.impl.CartasPDF;
import es.uniovi.asw.Parser.Generador.impl.CartasTXT;

public class ParserFactory {

	public ParserFactory(){
		
	}
	public static ReadCensus getReadCensusXlsx(VoterRepository repository){
		return new LeerFicheroXlsx(repository);
	}
	
	public static GeneradorCartas getGeneradorTxt(Voter votante) throws IOException, DocumentException{
		return new CartasTXT(votante);
	}
	
	public static GeneradorCartas getGeneradorPdf(Voter votante) throws IOException, DocumentException{
		return new CartasPDF(votante);
	}

}
