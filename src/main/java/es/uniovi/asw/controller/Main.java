package es.uniovi.asw.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import es.uniovi.asw.UserInfo;
import es.uniovi.asw.Calculate.Calculate;
import es.uniovi.asw.Calculate.DBVotes;
import es.uniovi.asw.Calculate.voters.VotersTypeImpl;
import es.uniovi.asw.DBManagement.modelo.ColegioElectoral;
import es.uniovi.asw.DBManagement.modelo.Elecciones;
import es.uniovi.asw.DBManagement.modelo.Gizmo;
import es.uniovi.asw.DBManagement.modelo.Referendum;
import es.uniovi.asw.DBManagement.modelo.ServerResponse;
import es.uniovi.asw.DBManagement.modelo.Voter;
import es.uniovi.asw.DBManagement.modelo.Voto;
import es.uniovi.asw.DBManagement.repository.VotoRepository;
import es.uniovi.asw.Election.Election;
import es.uniovi.asw.Vote.Vote;
import es.uniovi.asw.VoterAccess.VoterAcces;
import groovy.lang.Grab;

@Grab("thymeleaf-spring4")
@Controller
public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	@Autowired
	VotoRepository votoRepository = null;

	private UserInfo userInfo;
	private ServerResponse serverResponse;

	// Guarda un array con los resultados de las votaciones para mostrar
	public static boolean finVotos = false;
	public static int[] resultados;
	public static String mensajeResultado;
	public static String fechaRefresco;
	public static int numeroVotosContabilizados;

	private boolean primerAccesoWeb = true;

	private Calculate calculate;

	@Autowired
	private VoterAcces voterAccess;

	@Autowired
	private Election election;

	@Autowired
	private Vote vote;

	private static final long TIEMPO_MS = 12000;
	public static final int MAX_VOTOS_PERMITIDOS = 50000;

	@RequestMapping("/")
	public ModelAndView landing(Model model) {
		LOG.info("Index page access");

		return new ModelAndView("index");
	}

	@RequestMapping(value = "/votar")
	public String votar(Voto voto, @ModelAttribute("opcion") String opcionVoto,
			Model model, HttpSession session) {

		if (session.getAttribute("usuario") != null) {
			List<Elecciones> elecciones = election.listAll();
			if (!elecciones.isEmpty()) {
				Elecciones e = elecciones.get(0);
				Date hoy = new Date();
				if (e.getFechaInicio().after(hoy)) {
					model.addAttribute("mensaje",
							"Por favor, espere a que se inicien las elecciones");
					return "esperarElecciones";
				} else if (e.getFechaFin().before(hoy)) {
					model.addAttribute("mensaje",
							"Las elecciones ya han finalizado, espere a unas nuevas elecciones");
					return "esperarElecciones";
				}
			} else {
				model.addAttribute("mensaje",
						"Aún no se han añadido elecciones."
								+ "Por favor, espere a unas nuevas elecciones.");
				return "esperarElecciones";
			}

			List<Referendum> opciones = new ArrayList<Referendum>();
			for (Referendum opcion : Referendum.values()) {
				opciones.add(opcion);
			}
			model.addAttribute("opciones", opciones);
			return "/votar";
		} else {
			return "error";
		}

	}

	@RequestMapping(value = "/votar", method = RequestMethod.POST)
	public String saveVote(Voto voto,
			@ModelAttribute("opcion") String opcionVoto, Model model,
			HttpSession session) {

		List<Referendum> opciones = new ArrayList<Referendum>();
		for (Referendum opcion : Referendum.values()) {
			opciones.add(opcion);
		}
		model.addAttribute("opciones", opciones);

		if (session.getAttribute("usuario") != null) {

			if (vote.comprobarPosibilidadEjercerVoto((String) session
					.getAttribute("usuario"))) {
				LOG.info("El usuario "
						+ vote.findByUsuario(
								(String) session.getAttribute("usuario"))
								.getUsuario() + " ya ha votado");
				model.addAttribute("mensaje",
						"No puede votar, porque ya ha votado anteriormente");
				return "/votar";
			}

		}

		if (opcionVoto == null) {
			if (session.getAttribute("usuario") != null) {
				Voter voter = vote.findByUsuario((String) session
						.getAttribute("usuario"));
				if (voter != null) {
					vote.updateEjercioDerechoAlVoto(true, voter.getNif());
					ColegioElectoral colegio = election
							.findByCodigoColegio(voter.getCodigoColegio());
					Voto v = new Voto(colegio, null, false, false, true);
					votoRepository.save(v);
					LOG.info("Queda registrado de que el votante ha votado");
					model.addAttribute("mensaje",
							"Ha votado correctamente: voto en blanco");
					LOG.info("Se ha añadido un nuevo voto en blanco");

				}
			}

			return "/votar";
		}
		boolean encontrado = false;

		if (opcionVoto.toLowerCase().equals(
				Referendum.SI.toString().toLowerCase())
				|| opcionVoto.toLowerCase().equals(
						Referendum.NO.toString().toLowerCase())) {

			if (session.getAttribute("usuario") != null) {
				Voter voter = vote.findByUsuario((String) session
						.getAttribute("usuario"));
				if (voter != null) {
					ColegioElectoral colegio = election
							.findByCodigoColegio(voter.getCodigoColegio());
					Voto v = new Voto(colegio, opcionVoto, false, false, false);
					votoRepository.save(v);
					vote.updateEjercioDerechoAlVoto(true, voter.getUsuario());
					model.addAttribute("mensaje", "Ha votado correctamente");
					LOG.info("Queda registrado de que el votante ha votado");
					LOG.info("Se ha añadido un nuevo voto");
				}
			}
			encontrado = true;

			return "/votar";
		}
		if (opcionVoto.equals("")) {
			if (session.getAttribute("usuario") != null) {
				Voter voter = vote.findByUsuario((String) session
						.getAttribute("usuario"));
				if (voter != null) {
					ColegioElectoral colegio = election
							.findByCodigoColegio(voter.getCodigoColegio());
					Voto v = new Voto(colegio, null, false, false, true);
					votoRepository.save(v);
					vote.updateEjercioDerechoAlVoto(true, voter.getUsuario());
					model.addAttribute("mensaje",
							"Ha votado correctamente: voto en blanco");
					LOG.info("Se ha añadido un nuevo voto en blanco");
					LOG.info("Queda registrado de que el votante ha votado");
				}
			}
			return "/votar";

		} else {
			if (encontrado == false) {
				if (session.getAttribute("usuario") != null) {
					Voter voter = vote.findByUsuario((String) session
							.getAttribute("usuario"));
					if (voter != null) {

						ColegioElectoral colegio = election
								.findByCodigoColegio(voter.getCodigoColegio());
						Voto v = new Voto(colegio, null, false, true, false);
						votoRepository.save(v);
						vote.updateEjercioDerechoAlVoto(true,
								voter.getUsuario());
						LOG.info("Se ha añadido un nuevo voto nulo");
						model.addAttribute("mensaje",
								"Ha votado correctamente: voto nulo");
						LOG.info("Queda registrado de que el votante ha votado");
					}
				}
				return "/votar";
			}
			return "/votar";
		}

	}

	@RequestMapping(value = "/modificar_elecciones")
	public String modificar(Elecciones elecciones, Model model,
			HttpSession session) {
		LOG.info("Modificar elecciones page access");
		if (session.getAttribute("usuario") != null
				&& session.getAttribute("usuario").equals("junta")) {
			return "/modificar_elecciones";
		}
		return "error";
	}

	@RequestMapping(value = "/modificar_elecciones", method = RequestMethod.POST)
	public String addElecciones(Elecciones elecciones, Model model,
			HttpSession session) {
		LOG.info("Modificar elecciones page access");
		if (session.getAttribute("usuario") != null
				&& session.getAttribute("usuario").equals("junta")) {
			try {
				if (elecciones.getNombre() != null
						&& elecciones.getFechaInicio() instanceof Date
						&& elecciones.getFechaFin() instanceof Date
						&& elecciones.getFechaFin().after(
								elecciones.getFechaInicio())) {

					List<Elecciones> listaElecciones = election.listAll();
					if (listaElecciones == null || listaElecciones.isEmpty()) {

						Elecciones eleccion = new Elecciones(
								elecciones.getNombre(),
								elecciones.getFechaInicio(),
								elecciones.getFechaFin());
						election.saveElection(eleccion);
						insertColegios();
						model.addAttribute("mensaje",
								"Se han convocado las nuevas elecciones con nombre: "
										+ elecciones.getNombre());
						LOG.info("Se han convocado las nuevas elecciones con nombre: "
								+ elecciones.getNombre());
						return "/modificar_elecciones";

					} else {
						model.addAttribute("mensaje",
								"No se puede convocar más de una elección");
						LOG.info("No se han convocado nuevas elecciones, porque ya existe una");
						return "/modificar_elecciones";
					}
				} else {

					model.addAttribute("mensaje",
							"Por favor, rellene todos los campos");
					LOG.info("No se han podido convocar nuevas elecciones");
					return "/modificar_elecciones";

				}
			} catch (Exception e) {
				model.addAttribute("mensaje",
						"Por favor, rellene todos los campos");
				LOG.info("No se han podido convocar nuevas elecciones");
				return "/modificar_elecciones";
			}
		} else {
			return "error";
		}
	}

	/**
	 * Método que añade los colegios electorales iniciales
	 */
	private void insertColegios() {

		for (Voter voter : vote.listAll()) {
			if (existeColegio(voter.getCodigoColegio()) == false) {
				ColegioElectoral c = new ColegioElectoral(
						voter.getCodigoColegio(), "", "");
				election.saveColegio(c);
			}
		}

	}

	/**
	 * Método que comprueba si ya existe un colegio con el mismo codigo
	 * 
	 * @return
	 */
	private boolean existeColegio(int cod) {
		List<ColegioElectoral> colegios = election.listColegios();
		if (colegios != null && !colegios.isEmpty()) {
			for (ColegioElectoral colegio : colegios) {
				if (colegio.getCodigoColegio() == cod) {
					return true;
				}
			}
		}
		return false;
	}

	@RequestMapping(value = "/add_colegio")
	public String colegio(ColegioElectoral colegioElectoral, Model model,
			HttpSession session) {
		LOG.info("Add school page access");
		if (session.getAttribute("usuario") != null
				&& session.getAttribute("usuario").equals("junta")) {
			return "/add_colegio";
		}
		return "error";
	}

	@RequestMapping(value = "/add_colegio", method = RequestMethod.POST)
	public String addColegio(ColegioElectoral colegioElectoral, Model model,
			HttpSession session) {
		if (session.getAttribute("usuario") != null
				&& session.getAttribute("usuario").equals("junta")) {
			if (colegioElectoral.getCircunscripcion() == null
					|| colegioElectoral.getComunidadAutonoma() == null) {
				model.addAttribute("mensaje",
						"Por favor, rellene todos los campos");
				LOG.info("No se ha añadido ningún colegio electoral");
				return "/add_colegio";
			} else {

				List<ColegioElectoral> colegios = election.listColegios();
				if (colegios.isEmpty()) {
					ColegioElectoral c = new ColegioElectoral(
							colegioElectoral.getCodigoColegio(),
							colegioElectoral.getCircunscripcion(),
							colegioElectoral.getComunidadAutonoma());
					election.saveColegio(c);
					model.addAttribute("mensaje",
							"Se ha insertado el nuevo colegio electoral con código: "
									+ c.getCodigoColegio());
					LOG.info("Se ha insertado un nuevo colegio electoral");
				} else {
					for (ColegioElectoral colegioElectoral2 : colegios) {
						if (colegioElectoral.getCodigoColegio() != colegioElectoral2
								.getCodigoColegio()) {
							ColegioElectoral c = new ColegioElectoral(
									colegioElectoral.getCodigoColegio(),
									colegioElectoral.getCircunscripcion(),
									colegioElectoral.getComunidadAutonoma());
							election.saveColegio(c);
							model.addAttribute("mensaje",
									"Se ha insertado el nuevo colegio electoral con código: "
											+ c.getCodigoColegio());
							LOG.info("Se ha insertado un nuevo colegio electoral");
							return "/add_colegio";

						} else {
							model.addAttribute("mensaje",
									"No se ha podido insertar el colegio electoral");
							LOG.info("Error, no se ha podido insertar el colegio electoral");
							return "/add_colegio";

						}
					}
				}
				return "/add_colegio";
			}
		} else {
			return "error";
		}
	}

	@RequestMapping("/WelcomePage")
	public String voterInicioSesion(Model model) {
		LOG.info("Voter log in page access");
		model.addAttribute("gizmo", new Gizmo());
		return "WelcomePage";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public Object getVI(@RequestBody UserInfo userInfo) {

		this.userInfo = userInfo;

		this.serverResponse = this.voterAccess.getVoter(userInfo.getEmail(),
				userInfo.getClave());

		return serverResponse;
	}

	@RequestMapping(value = "/showUserInfo", method = RequestMethod.POST)
	public String getVR(Gizmo gizmo, Model model, Elecciones elecciones,
			HttpSession session) {
		LOG.info("Show User info in page access");

		try {
			ServerResponse response = this.voterAccess.getVoter(
					gizmo.getField1(), gizmo.getField2());
			this.serverResponse = response;
			userInfo = new UserInfo(gizmo.getField1(), gizmo.getField2());
		} catch (ResourceNotFoundException e) {
			model.addAttribute("mensaje",
					"Por favor, introduzca un email y password correctos");
			LOG.info("No se ha podido iniciar sesión porque no es correcto el usuario");
			return "WelcomePage";
		}

		Voter usuarioConectado = vote.findByEmailAndClave(userInfo.getEmail(),
				userInfo.getClave());

		if (usuarioConectado != null) {
			session.setAttribute("usuario", usuarioConectado.getUsuario());
			if (usuarioConectado.getUsuario().equals("junta")) {
				LOG.info("Se ha iniciado sesión como Junta Electoral");
				return "elecciones_tipo";
			} else {
				ArrayList<Object> atributos = new ArrayList<>();
				atributos.add(this.serverResponse);

				model.addAttribute("atributes", atributos);
				session.setAttribute("atributes", atributos);
				LOG.info("Se ha iniciado sesión como votante");
				return "voterpage";
			}
		}

		return "WelcomePage";

	}

	@RequestMapping("/voterpage")
	public String voterPage(Gizmo gizmo, Model model, Elecciones elecciones,
			HttpSession session) {
		LOG.info("Voter page in page access");
		return "voterpage";
	}

	@RequestMapping("/InfoPage")
	public String voterInfo(Gizmo gizmo, Model model, Elecciones elecciones,
			HttpSession session) {
		LOG.info("Voter info in page access");

		return "InfoPage";
	}

	@RequestMapping(value = "/cerrarSesion", method = RequestMethod.POST)
	public String cerrarSesion(Model model, HttpSession session) {
		LOG.info("Close session in page access");
		session.invalidate();
		return "index";

	}

	@RequestMapping(value = "/finVotos", method = RequestMethod.POST)
	public String finRecuento(Model model, HttpSession session) {
		LOG.info("Acceso a fin de votos");
		List<Elecciones> elecciones = election.listAll();
		if (!elecciones.isEmpty()) {
			Elecciones e = elecciones.get(0);
			Date hoy = new Date();
			if (e.getFechaFin().before(hoy)) {
				if (finVotos == false) {
					finVotos = true;
					model.addAttribute("mensaje",
							"Se ha finalizado la llegada de votos");
					try {
						calculate.recalcularYActualizarObjetosWeb();
						if (calculate.getType().getTipo().nVoto() != votoRepository
								.count()) {
							LOG.error("¡Numero de votos no coincidente!");
						}
					} catch (NullPointerException ex) {
						LOG.error("No hay votos suficientes");
					}

					return "elecciones_tipo";
				} else {
					model.addAttribute("mensaje",
							"Ya se ha finalizado la llegada de votos anteriormente");
					return "elecciones_tipo";
				}
			} else {
				model.addAttribute("mensaje",
						"Aún no han finalizado las elecciones");
				return "elecciones_tipo";
			}
		} else {
			model.addAttribute("mensaje", "Aún no hay elecciones");
			return "elecciones_tipo";
		}

	}

	@RequestMapping("/esperar_tipos")
	public String esperarTipos(Model model) {
		LOG.info("Acceso a página esperar que se creen nuevos tipos de elecciones");
		return "esperar_tipos";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePass(Model model) {
		model.addAttribute(new Gizmo());
		return "ChangePassword";
	}

	@RequestMapping(value = "/changingPassword", method = RequestMethod.POST)
	public String changingPassword(Gizmo gizmo, Model model) {

		this.voterAccess.updatePassword(userInfo.getEmail(),
				userInfo.getClave(), gizmo.getField1());

		ArrayList<Object> atributos = new ArrayList<>();
		atributos.add(serverResponse);

		model.addAttribute("atributes", atributos);

		return "InfoPage";
	}

	@RequestMapping("/showResults")
	public String showResults(Model model) {

		List<Elecciones> elecciones = election.listAll();
		if (!elecciones.isEmpty()) {
			Elecciones e = elecciones.get(0);
			Date hoy = new Date();
			if (!e.getFechaFin().before(hoy)) {
				model.addAttribute(
						"mensaje",
						"Por favor, espere para ver los resultados, las elecciones aún no han finalizado");
				return "esperarElecciones";
			}
		} else {
			model.addAttribute("mensaje", "Aún no se han añadido elecciones."
					+ "Por favor, espere a unas nuevas elecciones.");
			return "esperarElecciones";
		}

		if (primerAccesoWeb) {
			primerAccesoWeb = false;
			cargarPrimerosDatosVotos();
			calculate = new Calculate(new DBVotes(votoRepository),
					new VotersTypeImpl());
			numeroVotosContabilizados = calculate.getType().getTipo().nVoto();
		}

		preparaModelo(model);

		Timer timer = new Timer();
		timer.schedule(new TimerWebData(model), 0, TIEMPO_MS);
		return "/showResults";
	}

	private void preparaModelo(Model model) {
		model.addAttribute("nombreElecciones", election.listAll().get(0)
				.getNombre());
		model.addAttribute("resultadosString", mensajeResultado);
		model.addAttribute("resultados", resultados);
		model.addAttribute("ultimaActualizacion", "Ultima actualizacion: "
				+ fechaRefresco);
		model.addAttribute("votosContabilizados",
				"Votos contabilizados hasta el momento: "
						+ numeroVotosContabilizados);

		boolean refrescar = true;

		// Se da por hecho que se llegan a introducir 5000 votos como máximo, es
		// un limite
		// artificl porque sino se sabriamos cuando para de meter datos
		// simulados de los
		// puntos de voto fisicos
		if (finVotos) {
			refrescar = false;
			double si = ((double) resultados[0] / (double) numeroVotosContabilizados) * 100;
			double no = ((double) resultados[1] / (double) numeroVotosContabilizados) * 100;
			double blanco = ((double) resultados[2] / (double) numeroVotosContabilizados) * 100;
			double nulo = ((double) resultados[3] / (double) numeroVotosContabilizados) * 100;
			model.addAttribute("porcentajes",
					"Si: " + Math.round(si) + "% - No: " + Math.round(no)
							+ "% - En blanco: " + Math.round(blanco)
							+ "% - Nulo: " + Math.round(nulo) + "%");
			model.addAttribute("nombreElecciones", election.listAll().get(0)
					.getNombre()
					+ " - Elecciones Finalizadas");
			model.addAttribute("votosContabilizados",
					"Total de votos contabilizados: "
							+ numeroVotosContabilizados);
		}

		model.addAttribute("refrescar", refrescar);
	}

	// Clase timer que actualiza los datos de la web si han llegado nuevos datos
	class TimerWebData extends TimerTask {

		private Model model;

		public TimerWebData(Model model) {
			super();
			this.model = model;
		}

		public void run() {
			// Se da por hecho que se llegan a introducir 5000 votos como
			// máximo, es un limite
			// artificl porque sino se sabriamos cuando para de meter datos
			// simulados de los
			// puntos de voto fisicos
			if (finVotos) {
				cancel();
				return;
			}
			agregarVotosAleatorios();
			actualizar(model);
		}

		private void actualizar(Model model) {
			// Se actualiza si en la base de datos hay mas votos de los que se
			// han contado
			// hasta el momento
			if (calculate.getType().getTipo().nVoto() < votoRepository.count()) {
				calculate.recalcularYActualizarObjetosWeb();
				numeroVotosContabilizados = calculate.getType().getTipo()
						.nVoto();
				preparaModelo(model);
			}
		}

		private void agregarVotosAleatorios() {
			int randomIndex = (int) (Math.random() * 40);
			for (int i = randomIndex; i < 200; i++) {
				int random = (int) (Math.random() * 4);
				switch (random) {
				case 0:
					votoRepository.save(new Voto(null, "Si", false, false,
							false));
					votoRepository.save(new Voto(null, "Si", false, false,
							false));
					votoRepository.save(new Voto(null, "si", false, false,
							false));
					break;
				case 1:
					votoRepository
							.save(new Voto(null, "No", false, true, false));
					break;
				case 2:
					votoRepository
							.save(new Voto(null, null, false, false, true));
					break;
				case 3:
					votoRepository
							.save(new Voto(null, null, false, true, false));
					break;

				}
			}
		}
	}

	private void cargarPrimerosDatosVotos() {
		// VOTOS DE PRUEBA QUE TIENEN QUE ESTAR DEL CONTEO DIGITAL
		Voto votoSi = new Voto(null, Referendum.SI.toString(), false, false,
				false);
		Voto votoSi2 = new Voto(null, Referendum.SI.toString(), false, false,
				false);
		Voto votoNo = new Voto(null, Referendum.NO.toString(), false, false,
				false);
		Voto votoNulo = new Voto(null, null, false, true, false);

		Voto votoBlanco1 = new Voto(null, null, false, false, true);
		Voto votoBlanco2 = new Voto(null, null, false, true, true);

		votoRepository.save(votoSi);
		votoRepository.save(votoSi2);
		votoRepository.save(votoNo);
		votoRepository.save(votoNulo);
		votoRepository.save(votoBlanco1);
		votoRepository.save(votoBlanco2);
	}

}