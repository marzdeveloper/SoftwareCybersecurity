package com.sc.webim.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("tittle", "WebIm");
		model.addAttribute("app_css", "login.css");
		return "login";
	}
	
	@RequestMapping("/login1")
	public String login(Model model) {
		model.addAttribute("tittle", "WebIm");
		model.addAttribute("app_css", "login.css");
		return "login";
	}

}