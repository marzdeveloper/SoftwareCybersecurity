package com.sc.webim.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.services.DroneService;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController{

	@RequestMapping()
	public String index() {
		
		return "error/index";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
