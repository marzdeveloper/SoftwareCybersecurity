package com.sc.webim.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController{

	@RequestMapping()
	public String index(Model model) {
		model.addAttribute("title", "Error");
		return "error/index";
	}

	@Override
	public String getErrorPath() {
		return null;
	}
	
}
