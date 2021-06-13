package com.soleador.Administracion.Soleador.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioController {

	@GetMapping("/")
	public String inicio() {
		return "inicio.html";
	}

	@GetMapping("/signin")
	public String log() {
		return "signin.html";
	}

	@GetMapping("dashboard")
	public String dashboard() {
		return "acopio-list.html";

	}

}
