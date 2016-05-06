package es.uniovi.asw.ReportWriter;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.Reportwriter.WriterFactory;
import es.uniovi.asw.Reportwriter.WriterReport;
import es.uniovi.asw.Reportwriter.impl.WriterReportImpl;

public class WriterFactoryTest {
	
	private WriterFactory factory;
	private WriterReport report;
	
	@Before
	public void setUp() {
		factory = new  WriterFactory();
		report = new WriterReportImpl();
	}

	@Test
	public void testGetWriterReporter() {
		WriterReportImpl impl = (WriterReportImpl) factory.getWriterReportImpl();
		WriterReportImpl impl2 = impl;
		assertEquals(impl, impl2);
	}
	
	@Test
	public void testMismoEmail() {
		Scanner in = new Scanner(System.in);
		report.errorMismoEmail("dario@gmail.com");
	}
	
	@Test
	public void testError() {
		report.error("Esto es un mensaje de error");
	}

}
