package es.uniovi.asw;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import es.uniovi.asw.pageobjects.PO_colegio;
import es.uniovi.asw.pageobjects.PO_elecciones;
import es.uniovi.asw.pageobjects.PO_login;
import es.uniovi.asw.pageobjects.PO_voto;
import es.uniovi.asw.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTest {

	WebDriver driver;
	List<WebElement> elementos = null;
		

	@Before
	public void run() {
	
		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		// vamos a la página
		driver.get("http://localhost:8080/");

	}

	@After
	public void end() {
		// Cerramos el navegador
		driver.quit();
	}

	@Test
	public void testAddColegio() {

		loginModificar();

		// Pinchamos en el enlace Añadir colegio electoral
		By boton = By.className("boton_add_colegio");
		driver.findElement(boton).click();

		// Esperamos a que se cargue la pagina de añadir colegio
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Nuevo colegio electoral", 10);

		// Vamos a rellenar el formulario
		new PO_colegio().rellenaFormulario(driver, "10000", "Asturias",
				"Principado de Asturias");

		// Esperamos a que se cargue la pagina de añadir colegio
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Nuevo colegio electoral", 10);

		// Comprobamos que aparezca el mensaje en la página
		SeleniumUtils.textoPresentePagina(driver,
				"Se ha insertado el nuevo colegio electoral con código:");
	}

	private void loginModificar() {
		// Buscar el enlace con id modificar elecciones, y pinchar en él
		driver.get("http://localhost:8080/WelcomePage.html");

		// Esperamos a que se cargue la pagina de login
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Voting System Login",
				10);

		// Vamos a rellenar el formulario de login
		new PO_login().rellenaFormulario(driver, "junta@gmail.com", "junta");

		// Esperamos a que se cargue la pagina de tipo de elecciones
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Tipo de elecciones",
				10);

		// Pulsar el enlace de referendum.
		By enlace = By.className("boton_add_referendum");
		driver.findElement(enlace).click();

		// Esperamos a que se cargue la pagina de crear elecciones
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Crear elecciones",
				10);

	}

	@Test
	public void testAddElecciones() {

		loginModificar();

		// Vamos a rellenar el formulario
		new PO_elecciones().rellenaFormulario(driver, "Referendum",
				"2016/05/01 09:00", "2016/09/20 20:00");

		// Esperamos a que se cargue la pagina de crear elecciones
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Crear elecciones",
				10);

		// Comprobamos que aparezca el mensaje en la página
		SeleniumUtils.textoPresentePagina(driver,
				"Se han convocado las nuevas elecciones con nombre:");
	}

	@Test
	public void testVotarReferendum() {

		loginVotante("pamela@gmail.com", "patata");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Vamos a rellenar el formulario
		new PO_voto().rellenaFormulario(driver, "si");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Comprobamos que aparezca el mensaje en la página
		SeleniumUtils.textoPresentePagina(driver, "Ha votado correctamente");
	}

	private void loginVotante(String email, String clave) {
		// Buscar el enlace con id modificar elecciones, y pinchar en él
		driver.get("http://localhost:8080/WelcomePage.html");

		// Esperamos a que se cargue la pagina de login
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Voting System Login",
				10);

		// Vamos a rellenar el formulario de login
		new PO_login().rellenaFormulario(driver, email, clave);

		// Esperamos a que se cargue la pagina de menú del votante
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Voting System", 10);

		// Pulsar el enlace de votar.
		By enlace = By.id("votarPerfil");
		driver.findElement(enlace).click();

	}

	@Test
	public void testVotarNulo() {
		loginVotante("dario@gmail.com", "patata");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Vamos a rellenar el formulario
		new PO_voto().rellenaFormulario(driver, "Voto nulo");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Comprobamos que aparezca el mensaje en la página
		SeleniumUtils.textoPresentePagina(driver,
				"Ha votado correctamente: voto nulo");
	}

	@Test
	public void testVotarBlanco() {
		loginVotante("monica@gmail.com", "patata");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Vamos a rellenar el formulario
		new PO_voto().rellenaFormulario(driver, "");

		// Esperamos a que se cargue la pagina de votar
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Votar", 10);

		// Comprobamos que aparezca el mensaje en la página
		SeleniumUtils.textoPresentePagina(driver,
				"Ha votado correctamente: voto en blanco");
	}

}
