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
import com.soleador.Administracion.Soleador.entidades.Vendedor;
import com.soleador.Administracion.Soleador.servicios.VendedorService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/vendedor")
public class VendedorController extends Controlador {

	@Autowired
	private VendedorService vendedorService;

	public VendedorController() {
		super("vendedor-list", "vendedor-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.VENDEDOR_LABEL) Vendedor vendedor,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: vendedor.guardar -- PARAMETROS: " + vendedor);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				vendedorService.guardar(vendedor);
				return "redirect:/vendedor/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el vendedor. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el vendedor.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.VENDEDOR_LABEL) Vendedor vendedor, ModelMap model) {
		log.info("METODO: vendedor.eliminar() -- PARAMETROS: " + vendedor);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			vendedorService.eliminar(vendedor.getId());
			return "redirect:/vendedor/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el vendedor.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Vendedor vendedor = new Vendedor();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			vendedor = vendedorService.buscar(id);
		}
		modelo.addObject(Textos.VENDEDOR_LABEL, vendedor);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: vendedor.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<Vendedor> page = vendedorService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/vendedor/listado");
		modelo.addObject(Textos.VENDEDOR_LABEL, new Vendedor());

		return modelo;
	}

}