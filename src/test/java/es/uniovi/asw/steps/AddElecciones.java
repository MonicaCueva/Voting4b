package es.uniovi.asw.steps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import es.uniovi.asw.Application;
import es.uniovi.asw.DBManagement.modelo.Elecciones;
import es.uniovi.asw.DBManagement.repository.EleccionesRepository;


@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class AddElecciones {
	
	@Autowired
	protected WebApplicationContext context;
	
	protected MockMvc mvc;
	protected MvcResult result;

	@Value("${local.server.port}")
	protected int port;
	
	@Autowired
	EleccionesRepository er = null;
	Elecciones elecciones = null;
	
	@Cuando("^agregamos unas elecciones con nombre \"([^\"]*)\", fecha inicio \"([^\"]*)\" y fecha final \"([^\"]*)\"$")
	public void agregamos_unas_elecciones_con_nombre_fecha_inicio_y_fecha_final(String arg1, String arg3, String arg4) throws Throwable {
		
		Date fechaInicio=null;
		Date fechaFin = null;
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		fechaInicio = formato.parse(arg3);
		fechaFin = formato.parse(arg4);

		elecciones = new Elecciones(arg1, fechaInicio, fechaFin);
		er.save(elecciones);
	}
	
	
	@Entonces("^comprobamos que existen las elecciones con nombre \"([^\"]*)\"$")
	public void comprobamos_que_existen_las_elecciones_con_nombre(String arg1)
			throws Throwable {
		List<Elecciones> eleccionesElectorales = new ArrayList<Elecciones>();
		eleccionesElectorales = er.findAll();
		
		for (Elecciones e : eleccionesElectorales) {
			if(e.getNombre().equals(arg1)){
				System.out.println("Existen dichas elecciones");
			}
		}
	}

}
