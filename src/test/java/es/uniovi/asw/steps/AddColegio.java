package es.uniovi.asw.steps;

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
import es.uniovi.asw.DBManagement.modelo.ColegioElectoral;
import es.uniovi.asw.DBManagement.repository.ColegioRepository;

@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class AddColegio {

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	ColegioRepository cr = null;

	protected MockMvc mvc;
	protected MvcResult result;

	@Value("${local.server.port}")
	protected int port;

	@Cuando("^creamos un colegio electoral con codigo (\\d+) , circunscripcion \"([^\"]*)\" y comunidad autonoma \"([^\"]*)\"$")
	public void creamos_un_colegio_electoral_con_codigo_circunscripcion_y_comunidad_autonoma(
			int arg1, String arg2, String arg3) throws Throwable {
		ColegioElectoral colegio = new ColegioElectoral(arg1, arg2, arg3);
		cr.save(colegio);
	}

	@Entonces("^comprobamos que existe el colegio electoral con codigo (\\d+)$")
	public void comprobamos_que_existe_el_colegio_electoral_con_codigo(int arg1)
			throws Throwable {
		for (ColegioElectoral c : cr.findAll()) {
			if (c.getCodigoColegio() == arg1) {
				System.out
						.println("Existe un colegio electoral con dicho codigo");
			}
		}
	}

}
