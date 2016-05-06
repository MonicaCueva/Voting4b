package es.uniovi.asw.steps;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import es.uniovi.asw.Application;
import es.uniovi.asw.DBManagement.modelo.Elecciones;
import es.uniovi.asw.DBManagement.repository.EleccionesRepository;


@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class Counting {
	
	WebDriver driver;
	List<WebElement> elementos = null;
	
	@Autowired
	protected WebApplicationContext context;

	@Autowired
	EleccionesRepository er = null;
	Elecciones elecciones = null;

	protected MockMvc mvc;
	protected MvcResult result;

	@Value("${local.server.port}")
	protected int port;

	@Cuando("^creamos unas elecciones y vota la gente$")
	public void creamos_unas_elecciones_y_vota_la_gente() throws Throwable {
		System.out.println("Existen dichas elecciones y votos");
	}
	

	@Y("^vamos a la pagina de resultados /showResults$")
	public void vamos_a_la_pagina_de_resultados_showResults()throws Throwable{
		 Assert.notNull(context);
		 this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
		 result = mvc.perform(get("/showResults")).andReturn();
		 
	}
	
	@Entonces("^comprobamos que existe el string \"([^\"]*)\"$")
	public void comprobamos_que_existe_el_string(String arg1)
			throws Throwable {

		assertThat(result.getResponse().getContentAsString(), containsString(arg1));
		result = mvc.perform(get("/")).andReturn();
		System.out.println("Se pueden ver los resultados de las elecciones");
		System.out.println("No se actualizan más votos");
	}


}
