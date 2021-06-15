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
import com.soleador.Administracion.Soleador.entidades.ProductoVenta;
import com.soleador.Administracion.Soleador.servicios.ProductoVentaService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/productoVenta")
public class ProductoVentaController extends Controlador {

	@Autowired
	private ProductoVentaService productoVentaService;

	public ProductoVentaController() {
		super("productoVenta-list", "productoVenta-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.PRODUCTO_VENTA_LABEL) ProductoVenta productoVenta,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: productoVenta.guardar -- PARAMETROS: " + productoVenta);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				productoVentaService.guardar(productoVenta);
				return "redirect:/productoVenta/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el productoVenta. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el productoVenta.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.PRODUCTO_VENTA_LABEL) ProductoVenta productoVenta, ModelMap model) {
		log.info("METODO: productoVenta.eliminar() -- PARAMETROS: " + productoVenta);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			productoVentaService.eliminar(productoVenta.getId());
			return "redirect:/productoVenta/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el productoVenta.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		ProductoVenta productoVenta = new ProductoVenta();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			productoVenta = productoVentaService.buscar(id);
		}
		modelo.addObject(Textos.PRODUCTO_VENTA_LABEL, productoVenta);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: productoVenta.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<ProductoVenta> page = productoVentaService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/productoVenta/listado");
		modelo.addObject(Textos.PRODUCTO_VENTA_LABEL, new ProductoVenta());

		return modelo;
	}

}