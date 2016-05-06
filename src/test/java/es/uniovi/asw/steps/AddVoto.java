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
import es.uniovi.asw.DBManagement.modelo.PartidoPolitico;
import es.uniovi.asw.DBManagement.modelo.Voto;
import es.uniovi.asw.DBManagement.repository.ColegioRepository;
import es.uniovi.asw.DBManagement.repository.VotoRepository;


@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class AddVoto {
	
	@Autowired
	protected WebApplicationContext context;

	@Autowired
	ColegioRepository cr = null;
	
	@Autowired
	VotoRepository vr = null;
	
	static Voto voto1=null;

	protected MockMvc mvc;
	protected MvcResult result;

	@Value("${local.server.port}")
	protected int port;

	@Cuando("^votamos al partido \"([^\"]*)\"$")
	public void votamos_al_partido(String arg1) throws Throwable {
		ColegioElectoral c1 = new ColegioElectoral(1000, "Asturias", "Principado de Asturias");
		cr.save(c1);
		
		boolean encontrado = false;
		for (PartidoPolitico p : PartidoPolitico.values()) {
			if(arg1.toString().equals(p.toString())){
				voto1=new Voto(c1, p.toString(), false, false, false);
				vr.save(voto1);
				encontrado=true;
			}
		}
		if(arg1.equals("")){
			voto1=new Voto(c1, null, false, false, true);
			vr.save(voto1);
		}else{
			if(encontrado==false){
				voto1=new Voto(c1, null, false, true, false);
				vr.save(voto1);
			}
		}
		
	}
	
	@Entonces("^comprobamos que no existe el partido y sera voto \"([^\"]*)\"$")
	public void comprobamos_que_no_existe_el_partido_y_sera_voto(String arg1)
			throws Throwable {
		if(voto1.isNulo() && arg1.equals("nulo")){
			System.out.println("No existe el partido y es voto nulo");
		}
	}
	
}
