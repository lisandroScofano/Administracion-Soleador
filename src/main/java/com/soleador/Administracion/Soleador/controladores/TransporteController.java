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
import com.soleador.Administracion.Soleador.entidades.Transporte;
import com.soleador.Administracion.Soleador.servicios.TransporteService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/transporte")
public class TransporteController extends Controlador {

	@Autowired
	private TransporteService transporteService;

	public TransporteController() {
		super("transporte-list", "transporte-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.TRANSPORTE_LABEL) Transporte transporte,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: transporte.guardar -- PARAMETROS: " + transporte);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				transporteService.guardar(transporte);
				return "redirect:/transporte/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR,
					"Ocurrió un error al intentar modificar el transporte. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el transporte.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.TRANSPORTE_LABEL) Transporte transporte, ModelMap model) {
		log.info("METODO: transporte.eliminar() -- PARAMETROS: " + transporte);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			transporteService.eliminar(transporte.getId());
			return "redirect:/transporte/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el transporte.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Transporte transporte = new Transporte();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			transporte = transporteService.buscar(id);
		}
		modelo.addObject(Textos.TRANSPORTE_LABEL, transporte);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: transporte.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<Transporte> page = transporteService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/transporte/listado");
		modelo.addObject(Textos.TRANSPORTE_LABEL, new Transporte());

		return modelo;
	}

}