//package com.soleador.Administracion.Soleador.controladores;
//
//import static com.iris.web.util.Textos.ACCION_LABEL;
//import static com.iris.web.util.Textos.EMPRESAS_LABEL;
//import static com.iris.web.util.Textos.ERROR;
//import static com.iris.web.util.Textos.ERROR_INESPERADO;
//import static com.iris.web.util.Textos.EXITO_LABEL;
//import static com.iris.web.util.Textos.GUARDAR_LABEL;
//import static com.iris.web.util.Textos.PAGE_LABEL;
//import static com.iris.web.util.Textos.QUERY_LABEL;
//import static com.iris.web.util.Textos.URL_LABEL;
//import static com.iris.web.util.Textos.USUARIO_LABEL;
//
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//
//import com.google.gson.JsonObject;
//import com.iris.web.entidades.Empresa;
//import com.iris.web.entidades.Obra;
//import com.iris.web.entidades.Remito;
//import com.iris.web.entidades.Token;
//import com.iris.web.entidades.Usuario;
//import com.iris.web.enumeraciones.Rol;
//import com.iris.web.modelos.UsuarioModel;
//import com.iris.web.repositorios.RemitoRepository;
//import com.iris.web.repositorios.TokenRepository;
//import com.iris.web.repositorios.UsuarioRepository;
//import com.iris.web.servicios.EmpresaService;
//import com.iris.web.servicios.FirebaseService;
//import com.iris.web.servicios.ObraService;
//import com.iris.web.servicios.UsuarioService;
//import com.soleador.Administracion.Soleador.Error.WebException;
//
//@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
//@RequestMapping("/usuario")
//public class UsuarioController extends Controlador {
//
//	@Autowired
//	private UsuarioService usuarioService;
//
//	@Autowired
//	private ObraService obraService;
//
//	@Autowired
//	private UsuarioRepository usuarioRepository;
//
//	@Autowired
//	private RemitoRepository remitoRepository;
//
//	@Autowired
//	private TokenRepository tokenRepository;
//
//	@Autowired
//	private EmpresaService empresaService;
//
//	@Autowired
//	private FirebaseService firebaseService;
//
//	public UsuarioController() {
//		super("usuario-list", "usuario-form");
//	}
//
//	private void llenarCombo(ModelMap modelo) {
//		List<Empresa> empresas = empresaService.listarActivos();
//		modelo.addAttribute(EMPRESAS_LABEL, empresas);
//	}
//
//	@PostMapping("/guardar")
//	public String guardar(HttpSession session, @Valid @ModelAttribute(USUARIO_LABEL) UsuarioModel usuario, BindingResult resultado, ModelMap modelo, String imagen) {
//		log.info("METODO: usuario.guardar -- PARAMETROS: " + usuario);
//		try {
//			if (resultado.hasErrors()) {
//				error(modelo, resultado);
//			} else {
//				Empresa empresa = getEmpresa(session);
//				if(usuario.getIdEmpresa() != null && !usuario.getIdEmpresa().isEmpty() && !usuario.getIdEmpresa().equals(empresa.getId())){
//					return "redirect:/usuario/listado";
//				}
//				
//				usuario.setIdEmpresa(empresa.getId());				
//				usuarioService.guardar(usuario, imagen);
//				return "redirect:/usuario/listado";
//			}
//		} catch (WebException e) {
//			llenarCombo(modelo);
//			modelo.addAttribute(USUARIO_LABEL, usuario);
//			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el usuario. " + e.getMessage());
//		} catch (Exception e) {
//			llenarCombo(modelo);
//			modelo.addAttribute(USUARIO_LABEL, usuario);
//			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el usuario.");
//			log.error(ERROR_INESPERADO, e);
//		}
//		return vistaFormulario;
//	}
//
//	@PostMapping("/eliminar")
//	public String eliminar(HttpSession session, @ModelAttribute(USUARIO_LABEL) UsuarioModel usuario, ModelMap model) {
//		log.info("METODO: usuario.eliminar() -- PARAMETROS: " + usuario);
//		model.addAttribute(ACCION_LABEL, "eliminar");
//		usuario = usuarioService.buscarModel(usuario.getId());
//		try {
//			
//			Empresa empresa = getEmpresa(session);
//			if(usuario.getIdEmpresa() != null && !usuario.getIdEmpresa().isEmpty() && usuario.getIdEmpresa().equals(empresa.getId())){
//				return "redirect:/usuario/listado";
//			}
//			
//			usuarioService.eliminar(usuario.getId());
//			return "redirect:/usuario/listado";
//		} catch (WebException e) {
//			model.addAttribute("usuario", usuario);
//			model.addAttribute(ERROR, "Ocurrió un error al intentar eliminar el usuario. " + e.getMessage());
//			return vistaFormulario;
//		} catch (Exception e) {
//			model.addAttribute("usuario", usuario);
//			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el usuario.");
//			return vistaFormulario;
//		}
//	}
//
//	@GetMapping("/formulario")
//	public ModelAndView formulario(HttpSession session, @RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
//		ModelAndView modelo = new ModelAndView(vistaFormulario);
//		UsuarioModel usuario = new UsuarioModel();
//		if (accion == null || accion.isEmpty()) {
//			accion = GUARDAR_LABEL;
//		}
//
//		if (id != null) {
//			usuario = usuarioService.buscarModel(id);
//		
//			Empresa empresa = getEmpresa(session);
//
//			if(usuario.getIdEmpresa() != null && !usuario.getIdEmpresa().isEmpty() && !usuario.getIdEmpresa().equals(empresa.getId())){
//				return new ModelAndView(new RedirectView("/usuario/listado"));
//			}
//		
//		}
//		
//		modelo.addObject(USUARIO_LABEL, usuario);
//		modelo.addObject(ACCION_LABEL, accion);
//		llenarCombo(modelo.getModelMap());
//		return modelo;
//	}
//
//	@GetMapping("/listado")
//	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
//		ModelAndView modelo = new ModelAndView(vistaListado);
//
//		ordenar(paginable, modelo);
//		
//		Empresa empresa = getEmpresa(session);
//
//		Page<Usuario> page = usuarioService.buscarTodos(paginable, q, empresa.getId());
//		modelo.addObject(QUERY_LABEL, q);
//
//		log.info("METODO: usuario.listar() -- PARAMETROS: " + paginable);
//
//		modelo.addObject(URL_LABEL, "/usuario/listado");
//		modelo.addObject(PAGE_LABEL, page);
//		modelo.addObject(USUARIO_LABEL, new UsuarioModel());
//		return modelo;
//	}
//
//	@GetMapping("/perfil")
//	public ModelAndView perfil(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
//		ModelAndView modelo = new ModelAndView("usuario-perfil");
//
//		ordenar(paginable, modelo);
//		Page<Obra> page = null;
//		if (isAdministrador()) {
//			page = obraService.listarActivos(paginable, getEmpresa(session).getId(), q);
//		}
//
//		modelo.addObject(QUERY_LABEL, q);
//
//		log.info("METODO: usuario.perfil() -- PARAMETROS: " + paginable);
//
//		modelo.addObject(URL_LABEL, "/usuario/perfil");
//		modelo.addObject(PAGE_LABEL, page);
//		return modelo;
//	}
//
//	@PreAuthorize("hasAnyRole('ROLE_CAPATAZ', 'ROLE_FLETERO')")
//	@GetMapping("/perfil/mobile")
//	public ModelAndView perfilMobile(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
//		ModelAndView modelo = new ModelAndView("usuario-perfil-mobile");
//
//		ordenar(paginable, modelo);
//		Page<Obra> page = null;
//		if (isCapataz()) {
//			page = obraService.listarActivosReponsable(paginable, getUsuario(session).getId());
//		} else if (isFletero()) {
//			page = obraService.listarActivos(paginable, getEmpresa(session).getId(), q);
//		}
//
//		modelo.addObject(QUERY_LABEL, q);
//
//		log.info("METODO: usuario.perfil() -- PARAMETROS: " + paginable);
//
//		modelo.addObject(URL_LABEL, "/usuario/perfil");
//		modelo.addObject(PAGE_LABEL, page);
//		return modelo;
//	}
//
//	@GetMapping("/papelera")
//	public ModelAndView papelera(HttpSession session, Pageable paginable) {
//		ModelAndView modelo = new ModelAndView(vistaListado);
//		
//		Empresa empresa = getEmpresa(session);
//
//		Page<Usuario> page = usuarioService.buscarTodos(paginable, null, empresa.getId());
//		log.info("METODO: usuario.papelera() -- PARAMETROS: " + paginable);
//
//		modelo.addObject(URL_LABEL, "/usuario/papelera");
//		modelo.addObject(PAGE_LABEL, page);
//		modelo.addObject(USUARIO_LABEL, new UsuarioModel());
//		return modelo;
//	}
//
//	@GetMapping("/auditoria")
//	public ModelAndView auditoria(HttpSession session, Pageable paginable) {
//		ModelAndView modelo = new ModelAndView(vistaListado);
//
//		Empresa empresa = getEmpresa(session);
//
//		Page<Usuario> page = usuarioService.buscarTodos(paginable, null, empresa.getId());
//		log.info("METODO: usuario.auditoria() -- PARAMETROS: " + paginable);
//
//		modelo.addObject(URL_LABEL, "/usuario/auditoria");
//		modelo.addObject(PAGE_LABEL, page);
//		modelo.addObject(USUARIO_LABEL, new UsuarioModel());
//		return modelo;
//	}
//
//	@PostMapping("/blanquear")
//	public String cambiarClave(@ModelAttribute(USUARIO_LABEL) UsuarioModel usuario, ModelMap model) {
//		log.info("METODO: usuario.eliminar() -- PARAMETROS: " + usuario);
//		model.addAttribute(ACCION_LABEL, "actualizar");
//		try {
//			usuarioService.cambiarClave(usuario);
//			usuario = usuarioService.buscarModel(usuario.getId());
//			model.addAttribute(USUARIO_LABEL, usuario);
//			model.addAttribute(EXITO_LABEL, "La contraseña se actualizó correctamente. ");
//			return vistaFormulario;
//		} catch (WebException e) {
//			usuario = usuarioService.buscarModel(usuario.getId());
//			model.addAttribute(USUARIO_LABEL, usuario);
//			model.addAttribute(ERROR, "Ocurrió un error al intentar actualizar la contraseña del usuario. " + e.getMessage());
//			return vistaFormulario;
//		} catch (Exception e) {
//			usuario = usuarioService.buscarModel(usuario.getId());
//			model.addAttribute(USUARIO_LABEL, usuario);
//			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar actualizar la contraseña del usuario.");
//			return vistaFormulario;
//		}
//	}
//
//	@PreAuthorize("permitAll()")
//	@PostMapping("/firebase/token")
//	@ResponseBody
//	public ResponseEntity<String> finalizar(HttpSession session, @Valid @RequestBody String token) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		try {
//			Usuario usuario = getUsuario(session);
//			usuarioService.guardarTokenFirebase(usuario, token);
//			return new ResponseEntity<>("{}", headers, HttpStatus.OK);
//		} catch (Exception e) {
//			log.error("Error: ", e);
//			JsonObject error = new JsonObject();
//			error.addProperty("mensaje", e.getMessage());
//			return new ResponseEntity<>(error.toString(), headers, HttpStatus.OK);
//		}
//	}
//
//	@GetMapping("/notificar")
//	public ResponseEntity<String> notificar(@RequestParam String id, @RequestParam String notificacion) {
//		List<Token> tokens = tokenRepository.buscarPorUsuarioId(id);
//		for (Token token : tokens) {
//			firebaseService.enviar(token.getTexto(), notificacion, new HashMap<>());
//		}
//		return new ResponseEntity<>("", HttpStatus.OK);
//	}
//
//	@Scheduled(cron = "0 0 5 * * *")
//	@GetMapping("/validar/tokens")
//	public ResponseEntity<String> validar() {
//		firebaseService.validarTokens();
//		return new ResponseEntity<>("", HttpStatus.OK);
//	}
//
//	@Scheduled(cron = "0 0 9 * * *")
//	@GetMapping("/notificacion/retiros/pendientes")
//	public ResponseEntity<String> notificarRetirosPendientes() {
//		log.info("Notificar retiros pendientes fleteros");
//
//		HashMap<String, String> datos = new HashMap<>();
//		datos.put("tipo", "retiros-pendientes");
//
//		DateTime inicio = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
//		DateTime fin = new DateTime().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
//
//		List<Usuario> fleteros = usuarioRepository.buscarPorRolTodasEmpresas(Rol.FLETERO);
//		for (Usuario fletero : fleteros) {
//			List<Remito> remitos = remitoRepository.buscarActivosPorFletero(fletero.getId(), inicio.toDate(), fin.toDate());
//			if (!remitos.isEmpty()) {
//				firebaseService.notificar(fletero.getId(), "Tienes " + remitos.size() + " pedidos para ser retirados hoy.");
//			}
//		}
//
//		return new ResponseEntity<>("", HttpStatus.OK);
//
//	}
//
//}