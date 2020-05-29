package com.acevedo.dental.controllers;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	@RequestMapping( value = "/main", method = RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute("date", new java.util.Date());
		return "main";
	}	

	@RequestMapping( value = "/agregarCita", method = RequestMethod.GET)
	public String agregarCita(Model model) {
		model.addAttribute("date", new java.util.Date());
		return "agregarCita";
	}		
	
	@RequestMapping( value = "/cambiarCita", method = RequestMethod.GET)
	public String cambiarCita(Model model) {
		model.addAttribute("date", new java.util.Date());
		return "cambiarCita";
	}	

	
	@RequestMapping( value = "/listaDeEspera", method = RequestMethod.GET)
	public String listaEspera(Model model) {
		model.addAttribute("date", new java.util.Date());
		return "listaDeEspera";
	}
	
	@RequestMapping( value = "/mobile", method = RequestMethod.GET)
	public String mobileIndex(Model model) {
		String pattern = "dd-MM-yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		model.addAttribute("date", format.format(new java.util.Date()));
		return "mobile/citas";
	}
		
	@RequestMapping( value = "/mobile/agendar", method = RequestMethod.GET)
	public String mobileAgendar(Model model) {
		model.addAttribute("date", new java.util.Date());
		return "mobile/agendar";
	}	
}
