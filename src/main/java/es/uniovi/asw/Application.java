package es.uniovi.asw;

import java.util.ArrayList;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.repository.VoterRepository;
import es.uniovi.asw.Parser.LeerFicheroXlsx;
import es.uniovi.asw.Parser.ParserFactory;
import es.uniovi.asw.Parser.Generador.impl.CartasPDF;
import es.uniovi.asw.Parser.Generador.impl.CartasTXT;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Bean
	public CommandLineRunner demo(final VoterRepository repository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				try {
					LeerFicheroXlsx leerxlsx = (LeerFicheroXlsx) ParserFactory
							.getReadCensusXlsx(repository);
					leerxlsx.readCensus("test.xlsx"); // lee el fichero en
														// formato .xlsx
					leerxlsx.insert();
					ArrayList<Voter> votantes = (ArrayList<Voter>) leerxlsx
							.getVotantes();
					for (Voter voter : votantes) {
						new CartasPDF(voter);
						new CartasTXT(voter);
					}

				
					LOG.info("--------------------------------------------");
					LOG.info("Información de los votantes: ");
					for (Voter voter : repository.findAll()) {
						LOG.info(voter.getEmail() + " " + voter.getClave());
						LOG.info(voter.toString());
					}
					LOG.info("--------------------------------------------");
				} catch (Exception e) {
					System.out.println("Excepcion..." + e);
				}
				
				Voter pamela = new Voter("Pamela", "pamela@gmail.com",
						"1111111A", 10000, "pamela", "patata", false);
				Voter juntaElectoral = new Voter("Junta Electoral",
						"junta@gmail.com", "22222A", 10000, "junta", "junta",
						false);
				
				Voter dario = new Voter("Dario", "dario@gmail.com",
						"1111111A", 10000, "dario", "patata", false);
				
				Voter monica = new Voter("Monica", "monica@gmail.com",
						"1111111A", 10000, "monica", "patata", false);
				
				//Elecciones elecciones = new Elecciones("Eleccioes", new Date(2015, 01, 01), new Date(2018, 01, 01), "Si, No");
				
				repository.save(pamela);
				repository.save(juntaElectoral);
				repository.save(dario);
				repository.save(monica);
				
			}
		};

	}

}