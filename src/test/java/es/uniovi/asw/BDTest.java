package es.uniovi.asw;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.uniovi.asw.DBManagement.modelo.ColegioElectoral;
import es.uniovi.asw.DBManagement.modelo.Elecciones;
import es.uniovi.asw.DBManagement.modelo.PartidoPolitico;
import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.modelo.Voto;
import es.uniovi.asw.DBManagement.repository.ColegioRepository;
import es.uniovi.asw.DBManagement.repository.EleccionesRepository;
import es.uniovi.asw.DBManagement.repository.VoterRepository;
import es.uniovi.asw.DBManagement.repository.VotoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BDTest {

	@Autowired
	VoterRepository vr;
	List<Voter> votantes = new ArrayList<Voter>();
	
	@Autowired
	ColegioRepository cr;
	List<ColegioElectoral> colegios = new ArrayList<ColegioElectoral>();
	
	@Autowired
	VotoRepository votor;
	List<Voto> votos = new ArrayList<Voto>();
	
	@Autowired
	EleccionesRepository er;
	List<Elecciones> elecciones = new ArrayList<Elecciones>();
	
	static Elecciones eleccionesElectorales=null;
	
	
	@Before
	public void setUp() throws Exception {
		votantes = vr.findAll(); //Almaceno la base de datos generada de Application.class
		vr.deleteAll();
		votor.deleteAll();
		cr.deleteAll();
		er.deleteAll();
		
		
		vr.save(new Voter("Monica Cueva", "monicac@gmail.com", "12151651s", 306, "monicac", "dnjwenk", false));
		
		Date fechaInicio=null;
		Date fechaFin = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		String fecha1 = "04/06/2016 09:00";
		String fecha2 = "04/06/2016 20:00";
		fechaInicio = formato.parse(fecha1);
		fechaFin = formato.parse(fecha2);
		
		eleccionesElectorales = new Elecciones("Referendum", fechaInicio, fechaFin);
		er.save(eleccionesElectorales);
		
		
		ColegioElectoral c1 = new ColegioElectoral(100, "Asturias", "Principado de Asturias");
		ColegioElectoral c2 = new ColegioElectoral(101,"León","Castilla y León");
		ColegioElectoral c3 = new ColegioElectoral(102,"Cantabria","Cantabria");
		cr.save(c1);
		cr.save(c2);
		cr.save(c3);
		
		votor.save(new Voto(c1, PartidoPolitico.PP.toString(), false, false, false));
		votor.save(new Voto(c1, PartidoPolitico.PSOE.toString(), false, false, false));
		votor.save(new Voto(c2, null, false, false, true)); //voto en blanco
		votor.save(new Voto(c3, null, false, true, false)); //voto nulo
	}
	
	
	@Test
	public void testSetElecciones() throws Exception {

		Date fechaInicio=null;
		Date fechaFin = null;
		Date fechaInicio2=null;
		Date fechaFin2 = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		String fecha1 = "15/06/2016 09:00";
		String fecha2 = "15/06/2016 20:00";
		String fecha11 = "20/06/2016 09:00";
		String fecha22 = "20/06/2016 20:00";
		fechaInicio = formato.parse(fecha1);
		fechaFin = formato.parse(fecha2);
		fechaInicio2 = formato.parse(fecha11);
		fechaFin2 = formato.parse(fecha22);
		
		Elecciones e = new Elecciones("Referendum", fechaInicio, fechaFin);
		
		e.setFechaInicio(fechaInicio2);
		e.setFechaFin(fechaFin2);
		e.setNombre("Otras elecciones");
		
		assertEquals("Otras elecciones", e.getNombre());
	}
	
	@Test
	public void testFindEleccionesByNombre(){
		assertEquals(eleccionesElectorales.getNombre(), er.findByNombre("Referendum").getNombre());
	}
	
	@Test
	public void testFindAllElecciones(){
		assertEquals(1, er.findAll().size());
	}
	
	
	@Test
	public void testSetColegio(){
		ColegioElectoral c = new ColegioElectoral(1000,"Cantabria","Cantabria");
		c.setCircunscripcion("Asturias");
		c.setCodigoColegio(200);
		c.setComunidadAutonoma("Principado de Asturias");
		assertEquals(200,c.getCodigoColegio());
	}
	
	@Test
	public void testFindAllColegios(){
		assertEquals(3, cr.findAll().size());
	}
	
	@Test
	public void testSetVoto(){
		ColegioElectoral c3 = new ColegioElectoral(1000,"Cantabria","Cantabria");
		Voto v = new Voto(c3, PartidoPolitico.Ciudadanos.toString(), false, false, false);
		v.setBlanco(true);
		v.setOpcion("");
		v.setContabilizado(false);
		v.setNulo(false);
		assertEquals("", v.getOpcion());
	}
	
	@Test
	public void testFindAllVotos(){
		assertEquals(4, votor.findAll().size());
	}
	
	@Test
	public void testSetVoter(){
		assertEquals("Monica Cueva", vr.findByNif("12151651s").getNombre());
		Voter v = vr.findByNif("12151651s");
		v.setNombre("Maria");
		v.setNif("2165165");
		v.setEjercioDerechoAlVoto(false);
		v.setCodigoColegio(222);
		v.setClave("1554");
		v.setUsuario("maria");
		assertNotEquals(vr.findByNif("12151651s"), v);
	}
	
	/**
	 * Test que comprueba el correcto funcionamiento 
	 * de la base de datos a la hora de encontrar un votante por su NIF.
	 */
	@Test
	public void testFindVoterByNif() {

		assertEquals("Monica Cueva", vr.findByNif("12151651s").getNombre());
		assertEquals("monicac@gmail.com", vr.findByNif("12151651s").getEmail());
		assertFalse(vr.findByNif("12151651s").isEjercioDerechoAlVoto());
		assertEquals(1, vr.count());
	
	
	}
	
	/**
	 * Método que prueba si elimina a los votantes correctos.
	 */
	@Test
	public void testDeleteVoter(){
		
		vr.delete(vr.findByNif("12151651s"));
		assertEquals(null, vr.findByNif("12151651s"));
		assertEquals(0, vr.count());
		
		vr.save(new Voter("Monica Cueva", "monicac@gmail.com", "12151651s", 306, "monicac", "dnjwenk", false));
	}
	
	/**
	 * Método que comprueba si añade bien, nuevos votantes a la base de datos
	 * y si los encuentra correctamente. 
	 */
	@Test
	public void testInsertarNuevosVotantes(){
		
		vr.save(new Voter("Paco Cueva", "pacoc@gmail.com", "188551651s", 307, "pacoc", "efewf", false));
		vr.save(new Voter("Maria Garcia", "mariag@gmail.com", "128921651s", 304,  "mariag", "ewff", false));
		vr.save(new Voter("Luis Bravo", "luisb@gmail.com", "19869861s", 205, "luisb", "ferw", false));
		vr.save(new Voter("Monica Fernandez", "monicaf@gmail.com", "21251651s", 304, "monicaf", "wfcsww", false));
		vr.save(new Voter("Adrian Suarez", "adrians@gmail.com", "122787651s", 307,  "adrians", "dwedx", false));
		vr.save(new Voter("Ana Martinez", "anam@gmail.com", "19056651s", 206, "anam", "fre", false));
		vr.save(new Voter("Manolo Cueva", "manoloc@gmail.com", "18521651s", 204, "manoloc", "xwex", false));
		vr.save(new Voter("Sara Garcia", "sarag@gmail.com", "162451651s", 210,  "sarag", "xw33x3", false));
		vr.save(new Voter("Pedro Bravo", "pedrob@gmail.com", "16871651s", 300, "pedrob", "xw3fgrt", false));
	
		assertEquals(10, vr.count());

		assertEquals("Paco Cueva", vr.findByNif("188551651s").getNombre());
		assertEquals("pacoc@gmail.com", vr.findByNif("188551651s").getEmail());
		assertEquals(307, vr.findByNif("188551651s").getCodigoColegio());
		assertFalse(vr.findByNif("188551651s").isEjercioDerechoAlVoto());
		
		assertEquals("Maria Garcia", vr.findByNif("128921651s").getNombre());
		assertEquals("mariag@gmail.com", vr.findByNif("128921651s").getEmail());
		assertEquals(304, vr.findByNif("128921651s").getCodigoColegio());
		assertFalse(vr.findByNif("128921651s").isEjercioDerechoAlVoto());
		
		assertEquals("Luis Bravo", vr.findByNif("19869861s").getNombre());
		assertEquals("luisb@gmail.com", vr.findByNif("19869861s").getEmail());
		assertEquals(205, vr.findByNif("19869861s").getCodigoColegio());
		assertFalse(vr.findByNif("19869861s").isEjercioDerechoAlVoto());
		
		assertEquals("Monica Fernandez", vr.findByNif("21251651s").getNombre());
		assertEquals("monicaf@gmail.com", vr.findByNif("21251651s").getEmail());
		assertEquals(304, vr.findByNif("21251651s").getCodigoColegio());
		assertFalse(vr.findByNif("21251651s").isEjercioDerechoAlVoto());
		
		assertEquals("Adrian Suarez", vr.findByNif("122787651s").getNombre());
		assertEquals("adrians@gmail.com", vr.findByNif("122787651s").getEmail());
		assertEquals(307, vr.findByNif("122787651s").getCodigoColegio());
		assertFalse(vr.findByNif("122787651s").isEjercioDerechoAlVoto());
		
		assertEquals("Ana Martinez", vr.findByNif("19056651s").getNombre());
		assertEquals("anam@gmail.com", vr.findByNif("19056651s").getEmail());
		assertEquals(206, vr.findByNif("19056651s").getCodigoColegio());
		assertFalse(vr.findByNif("19056651s").isEjercioDerechoAlVoto());
		
		assertEquals("Manolo Cueva", vr.findByNif("18521651s").getNombre());
		assertEquals("manoloc@gmail.com", vr.findByNif("18521651s").getEmail());
		assertEquals(204, vr.findByNif("18521651s").getCodigoColegio());
		assertFalse(vr.findByNif("18521651s").isEjercioDerechoAlVoto());
		
		assertEquals("Sara Garcia", vr.findByNif("162451651s").getNombre());
		assertEquals("sarag@gmail.com", vr.findByNif("162451651s").getEmail());
		assertEquals(210, vr.findByNif("162451651s").getCodigoColegio());
		assertFalse(vr.findByNif("162451651s").isEjercioDerechoAlVoto());
		
		assertEquals("Pedro Bravo", vr.findByNif("16871651s").getNombre());
		assertEquals("pedrob@gmail.com", vr.findByNif("16871651s").getEmail());
		assertEquals(300, vr.findByNif("16871651s").getCodigoColegio());
		assertFalse(vr.findByNif("16871651s").isEjercioDerechoAlVoto());
		
		
	}
	
	
	
	
	

}