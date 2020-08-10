package com.sc.webim.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.services.DroneService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/drone")
public class DroneController {
	private DroneService droneService;
	private ImageDao 	imageDao;

	@RequestMapping()
	public String index(Model model, @RequestParam(value = "msg", required = false) String msg) {
		model.addAttribute("title", "Drone");
		model.addAttribute("alertMsg", msg);
		
		return "drone/index";
	}
	
	/*@RequestMapping(value = "/pget", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String pruebaJsonG(Model model) {
		return "Hola Get";
	}
	
	@RequestMapping(value = "/ppost", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String pruebaJsonP(Model model) {
		return "Hola post";
	}*/
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json")
	public String uploadImage(@RequestParam("imageFiles") MultipartFile[] imageFiles, RedirectAttributes redirectAttributes) {
		String msg = "";
		boolean rsp = false;
		int count = 0;
		try {
			List<String> fileNamesDuplicate = new ArrayList<>();
			List<MultipartFile> images = Arrays.asList(imageFiles);
			for(MultipartFile file:images) {
				droneService.saveImage(file, rsp);
				if(!rsp) {
					count++;
					fileNamesDuplicate.add(file.getOriginalFilename());
				}
		        rsp = false;
				}

			if(count==0) {
				msg = "\"Images save successfully\"";
			}
			else {
				msg = Integer.toString(count) + "\"Images not saved successfully : " + fileNamesDuplicate.toString() + "\"";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "\"An unexpected error occurred\"";
		}
		redirectAttributes.addAttribute("msg", msg);
		return "redirect:/drone";
	}
	
	/*@RequestMapping(value = "/uploadImages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json")
	public @ResponseBody String uploadImages(Locale locale, Model uModel,
			@RequestParam(value = "images[]") String[] images){
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if (images.length > 0) {
				for (int i = 0; i<images.length; i++) {
					String[] img = images[i].split("&%&");
					//droneService.saveImage(img);
				}
				msg = "mmmm";
				resp = true;				
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}*/
	
	@Autowired
	public void setServices(DroneService droneService) {
		this.droneService = droneService;
	}
}