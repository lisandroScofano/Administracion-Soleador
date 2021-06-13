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
import com.soleador.Administracion.Soleador.entidades.Cliente;
import com.soleador.Administracion.Soleador.servicios.ClienteService;

@Controller
//@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@RequestMapping("/cliente")
public class ClienteController extends Controlador {

	@Autowired
	private ClienteService clienteService;

	public ClienteController() {
		super("cliente-list", "cliente-form");
	}

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(Textos.CLIENTE_LABEL) Cliente cliente,
			BindingResult resultado, ModelMap modelo) {
		log.info("METODO: cliente.guardar -- PARAMETROS: " + cliente);
		try {
			if (resultado.hasErrors()) {
				error(modelo, resultado);
			} else {
				clienteService.guardar(cliente);
				return "redirect:/cliente/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error al intentar modificar el cliente. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar modificar el cliente.");
			log.error(Textos.ERROR_INESPERADO, e);
		}
		return vistaFormulario;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(Textos.CLIENTE_LABEL) Cliente cliente, ModelMap model) {
		log.info("METODO: cliente.eliminar() -- PARAMETROS: " + cliente);
		model.addAttribute(Textos.ACCION_LABEL, "eliminar");
		try {
			clienteService.eliminar(cliente.getId());
			return "redirect:/cliente/listado";
		} catch (Exception e) {
			model.addAttribute(Textos.ERROR, "Ocurrió un error inesperado al intentar eliminar el cliente.");
			return vistaFormulario;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id,
			@RequestParam(required = false) String accion) {

		ModelAndView modelo = new ModelAndView(vistaFormulario);
		Cliente cliente = new Cliente();

		if (accion == null || accion.isEmpty()) {
			accion = Textos.GUARDAR_LABEL;
		}

		if (id != null) {
			cliente = clienteService.buscar(id);
		}
		modelo.addObject(Textos.CLIENTE_LABEL, cliente);
		modelo.addObject(Textos.ACCION_LABEL, accion);
		return modelo;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {

		log.info("METODO: cliente.listar() -- PARAMETROS: " + paginable);

		ModelAndView modelo = new ModelAndView(vistaListado);
		ordenar(paginable, modelo);

		Page<Cliente> page = clienteService.listarActivos(paginable, q);
		if (q != null && !q.isEmpty()) {
			modelo.addObject(Textos.QUERY_LABEL, q);
		}
		modelo.addObject(Textos.PAGE_LABEL, page);
		modelo.addObject(Textos.URL_LABEL, "/cliente/listado");
		modelo.addObject(Textos.CLIENTE_LABEL, new Cliente());

		return modelo;
	}

}