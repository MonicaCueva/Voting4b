package es.uniovi.asw.Reportwriter;

import es.uniovi.asw.Reportwriter.impl.WriterReportImpl;

public class WriterFactory {

public WriterFactory(){
		
	}
	public static WriterReport getWriterReportImpl(){
		return new WriterReportImpl();
	}
}
