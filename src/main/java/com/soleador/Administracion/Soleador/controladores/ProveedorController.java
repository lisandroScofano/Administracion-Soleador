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
import com.soleador.Administracion.Soleador.entidades.Proveedor;
import com.soleador.Administracion.Soleador.servicios.ProveedorService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/proveedor")
public class ProveedorController extends Controlador {

	@Autowired
	private ProveedorService proveedorService;

	public ProveedorController() {
		super("proveedor-list", "proveedor-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.PROVEEDOR_LABEL) Proveedor proveedor,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: proveedor.guardar -- PARAMETROS: " + proveedor);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				proveedorService.guardar(proveedor);
				return "redirect:/proveedor/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el proveedor. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el proveedor.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.PROVEEDOR_LABEL) Proveedor proveedor, ModelMap model) {
		log.info("METODO: proveedor.eliminar() -- PARAMETROS: " + proveedor);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			proveedorService.eliminar(proveedor.getId());
			return "redirect:/proveedor/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el proveedor.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Proveedor proveedor = new Proveedor();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			proveedor = proveedorService.buscar(id);
		}
		modelo.addObject(Textos.PROVEEDOR_LABEL, proveedor);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: proveedor.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<Proveedor> page = proveedorService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/proveedor/listado");
		modelo.addObject(Textos.PROVEEDOR_LABEL, new Proveedor());

		return modelo;
	}

}