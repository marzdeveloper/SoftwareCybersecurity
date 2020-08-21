package com.sc.webim.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DirettoreController {

	@RequestMapping("/direttore")
	public String index(Model model) {
		model.addAttribute("title", "Gestione Blockchain");
		return "direttore";
	}
	
	@RequestMapping("/direttore/inserisci")
	public String inserisci(Model model) {
		model.addAttribute("title", "Inserisci transazione nel giornale dei lavori");
		return "direttore/inserisci";
	}

	@RequestMapping("/direttore/consulta")
	public String consulta(Model model) {
		model.addAttribute("title", "Consulta il giornale dei lavori");
		return "direttore/consulta";
	}
	
}