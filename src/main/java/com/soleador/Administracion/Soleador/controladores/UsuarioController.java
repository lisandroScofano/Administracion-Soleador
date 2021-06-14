package com.soleador.Administracion.Soleador.controladores;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soleador.Administracion.Soleador.Error.WebException;
import com.soleador.Administracion.Soleador.Util.Textos;
import com.soleador.Administracion.Soleador.entidades.Usuario;
import com.soleador.Administracion.Soleador.servicios.UsuarioService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/usuario")
public class UsuarioController extends Controlador {

	@Autowired
	private UsuarioService usuarioService;

	public UsuarioController() {
		super("usuario-list", "usuario-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.USUARIO_LABEL) Usuario usuario,
			BindingResult resultado, ModelMap modelo, String imagen) {
		log.info("METODO: usuario.guardar -- PARAMETROS: " + usuario);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				usuarioService.guardar(usuario);
				return "redirect:/usuario/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.USUARIO_LABEL, usuario);
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el usuario. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.USUARIO_LABEL, usuario);
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el usuario.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(HttpSession session, @ModelAttribute(Textos.USUARIO_LABEL) Usuario usuario, ModelMap model) {
		log.info("METODO: usuario.eliminar() -- PARAMETROS: " + usuario);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			usuarioService.eliminar(usuario.getId());
			return "redirect:/usuario/listado";
		} catch (WebException e) {
			model.addAttribute("usuario", usuario);
			model.addAttribute(Textos.ERROR, "Ocurrió un error al intentar eliminar el usuario. " + e.getMessage());
			return vistaFormulario;
		} catch (Exception e) {
			model.addAttribute("usuario", usuario);
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el usuario.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(HttpSession session, @RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {
		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Usuario usuario = new Usuario();
		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			usuario = usuarioService.buscar(id);
		}

		modelo.addObject(Textos.USUARIO_LABEL, usuario);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(vistaListado);

		ordenar(paginable, modelo);

		Page<Usuario> page = usuarioService.buscarTodos(paginable, q);
		modelo.addObject(Textos.QUERY_LABEL, q);

		log.info("METODO: usuario.listar() -- PARAMETROS: " + paginable);

		modelo.addObject(Textos.URL_LABEL, "/usuario/listado");
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.USUARIO_LABEL, new Usuario());
		return modelo;
	}

	@GetMapping("/perfil")
	public ModelAndView perfil(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView("usuario-perfil");

		ordenar(paginable, modelo);

		modelo.addObject(Textos.QUERY_LABEL, q);

		log.info("METODO: usuario.perfil() -- PARAMETROS: " + paginable);

		modelo.addObject(Textos.URL_LABEL, "/usuario/perfil");
		return modelo;
	}

}