package com.sc.webim.controller;

import com.sc.webim.services.ImageService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/drone")
public class DroneController {
	private ImageService imageService;

	@RequestMapping()
	public String index(Model model, @RequestParam(value = "msg", required = false) String msg, 
			@RequestParam(value = "resp", required = false) String resp) {
		model.addAttribute("title", "Drone");
		model.addAttribute("alertMsg", msg);
		model.addAttribute("typeMsg", resp);
		
		return "drone/index";
	}
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, headers="Accept=application/json")
	public String uploadImage(Principal principal, @RequestParam("imageFiles") MultipartFile[] imageFiles, RedirectAttributes redirectAttributes) {
		String msg = "";
		int resp = 0;
		String msg_temp = "";
		int code = 0;
		int count = 0;
		try {
			List<MultipartFile> images = Arrays.asList(imageFiles);
			for(MultipartFile file:images) {
				msg_temp = "";
				code = imageService.saveImage(principal.getName(), file);
				switch (code) {
					case -1:
						msg_temp += "The file is not an image: -> " + file.getOriginalFilename() + "\\t";
						break;
					case -2:
						msg_temp += "The image has no GPS metadata: -> " + file.getOriginalFilename() + "\\t";
						break;
					case -3:
						msg_temp += "The image has already been saved on the DB: -> " + file.getOriginalFilename() + "\\t";
						break;
					case -4:
						msg_temp += "An image on the DB has already the same name of the image, please change name: -> " + file.getOriginalFilename() + "\\t";
						break;
					case -9:
						msg_temp += "An unexpected error occurred: -> " + file.getOriginalFilename() + "\\t";
						break;
				}
				
				if(code < 0) {
					count++;
				}
			}

			if(count == 0) {
				msg = "\"Images save successfully\"";
				resp = 1;
			}
			else {
				msg = "\"" +  count + "/" + images.size() + " image/s cant be saved \"";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "\"An unexpected error occurred\"";
		}
		redirectAttributes.addAttribute("msg", msg);
		redirectAttributes.addAttribute("resp", resp);
		return "redirect:/drone";
	}
	
	@Autowired
	public void setServices(ImageService imageService) {
		this.imageService = imageService;
	}
}