package com.sc.webim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DirettoreController {

	@RequestMapping("/direttore")
	public String index(Model model) {
		model.addAttribute("title", "Direttore dei lavori");
		return "direttore";
	}	
}