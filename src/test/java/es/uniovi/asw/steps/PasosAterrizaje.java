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

@ContextConfiguration(classes=Application.class, loader=SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class PasosAterrizaje {
  
  @Autowired
  protected WebApplicationContext context;

  protected MockMvc mvc;
  protected MvcResult result;
  
  @Value("${local.server.port}")
  protected int port;

  
  @Cuando("^el cliente invoca /$")
  public void el_cliente_invoca() throws Throwable {
    System.out.println("El cliente invoca /");
  }

  @Entonces("^el cliente recibe el código de estado (\\d+)$")
  public void el_cliente_recibe_el_código_de_estado(int arg1) throws Throwable {
    System.out.println("Comprobar que recibe el código " + arg1);
  }

  @Entonces("^el cliente recibe una cadena que contiene \"([^\"]*)\"$")
  public void el_cliente_recibe_una_cadena_que_contiene(String arg1) throws Throwable {
    System.out.println("Comprobar que el cliente recibe que contiene " + arg1);
  }
}
