package com.soleador.Administracion.Soleador.controladores;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.soleador.Administracion.Soleador.Error.WebException;
import com.soleador.Administracion.Soleador.Util.Textos;

public abstract class Controlador {
	protected String vistaListado;
	protected String vistaFormulario;
	protected String vistaInvitacion;
	protected String vistaPerfil;

	protected Log log;
	
	


	public Controlador(String listado, String formulario) {
		this.vistaListado = listado;
		this.vistaFormulario = formulario;
		this.log = LogFactory.getLog(getClass());
	}
	
	public Controlador(String listado, String formulario, String invitacion, String perfilUsuario) {
		this.vistaListado = listado;
		this.vistaFormulario = formulario;
		this.vistaInvitacion = invitacion;
		this.vistaPerfil = perfilUsuario;
		this.log = LogFactory.getLog(getClass());
	}

//	public String getUsuario() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		return auth.getName();
//	}
	
//	public Usuario getUsuario(HttpSession session) {
//		return (Usuario) session.getAttribute(USUARIO_LABEL);
//	}

//	public Empresa getEmpresa(HttpSession session) {
//		Usuario usuario = getUsuario(session);
//		return usuario.getEmpresa();
//	}
//	
//	public EmpresaModel getEmpresaModel(HttpSession session) {
//		Empresa empresa = getEmpresa(session);
//		EmpresaModel empresaM = new EmpresaModel();
//		empresaM.setId(empresa.getId());
//		
//		return empresaM;
//	}
	
//	@SuppressWarnings("rawtypes")
//	public String getRol() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		Iterator it = auth.getAuthorities().iterator();
//		while (it.hasNext()) {
//			return it.next().toString();
//		}
//		return "";
//	}

//	public boolean isAdministrador() {
//		return getRol().equals("ROLE_ADMINISTRADOR");
//	}
//
//	public boolean isAdministrativo() {
//		return getRol().equals("ROLE_ADMINISTRATIVO");
//	}
//	
//	public boolean isCapataz() {
//		return getRol().equals("ROLE_CAPATAZ");
//	}
//	
//	public boolean isFletero() {
//		return getRol().equals("ROLE_FLETERO");
//	}

	public void error(ModelAndView model, Exception e) {
		model.addObject(Textos.ERROR, "Ocurrio un error inesperado mientras se ejecutaba la acción.");
		log.error("Error inesperado", e);
	}

	public void error(ModelAndView model, WebException e) {
		model.addObject(Textos.ERROR, e.getMessage());
	}

	public void error(ModelMap modelo, BindingResult resultado) {
		StringBuilder msg = new StringBuilder();
		for (ObjectError o : resultado.getAllErrors()) {
			msg.append(o.getDefaultMessage() + System.getProperty("line.separator"));
		}
		log.info("Error: " + msg.toString());
		modelo.addAttribute(Textos.ERROR, msg.toString());
	}

	public void error(ModelAndView model, String e) {
		model.addObject(Textos.ERROR, e);
		model.setViewName(vistaFormulario);
	}

	public void error(Model model, String e) {
		model.addAttribute(Textos.ERROR, e);
	}
	
	public void ordenar(Pageable paginable, ModelAndView mav) {

		
		if (!paginable.getSort().toString().equals("UNSORTED")) {

			String[] sort = paginable.getSort().toString().replace(": ", ",").split(",");
			StringBuilder campos = new StringBuilder();
			for (int n = 0; n < sort.length; n++) {
				if (n % 2 == 0) {
					campos.append(sort[n]).append(" ");
				}
			}

			mav.addObject("sort_field", campos.toString().trim().replace(" ", ","));
			mav.addObject("sort_dir", sort[1]);
		}
	}

}
