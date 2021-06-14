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
import com.soleador.Administracion.Soleador.entidades.Envase;
import com.soleador.Administracion.Soleador.servicios.EnvaseService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/envase")
public class EnvaseController extends Controlador {

	@Autowired
	private EnvaseService envaseService;

	public EnvaseController() {
		super("envase-list", "envase-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.ENVASE_LABEL) Envase envase,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: envase.guardar -- PARAMETROS: " + envase);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				envaseService.guardar(envase);
				return "redirect:/envase/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el envase. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el envase.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.ENVASE_LABEL) Envase envase, ModelMap model) {
		log.info("METODO: envase.eliminar() -- PARAMETROS: " + envase);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			envaseService.eliminar(envase.getId());
			return "redirect:/envase/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el envase.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Envase envase = new Envase();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			envase = envaseService.buscar(id);
		}
		modelo.addObject(Textos.ENVASE_LABEL, envase);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: envase.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<Envase> page = envaseService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/envase/listado");
		modelo.addObject(Textos.ENVASE_LABEL, new Envase());

		return modelo;
	}

}