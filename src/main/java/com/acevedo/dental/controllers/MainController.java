package com.acevedo.dental.controllers;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String mobileIndex(Model model, @RequestParam(name = "fecha", required = false) String fecha) {
		if(StringUtils.isEmpty(fecha)) {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			model.addAttribute("fecha", format.format(new java.util.Date()));
		} else {
			model.addAttribute("fecha", fecha);
		}
		return "mobile/citas";
	}
		
	@RequestMapping( value = "/mobile/agendar", method = RequestMethod.GET)
	public String mobileAgendar(Model model) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		model.addAttribute("fecha", format.format(new java.util.Date()));
		return "mobile/agendar";
	}	
}
