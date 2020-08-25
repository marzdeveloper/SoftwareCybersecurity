package com.sc.webim.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;
import com.sc.webim.services.ImageService;
import com.sc.webim.services.MeasureService;

@Controller
@RequestMapping("/image")
public class ImageController {
	private ImageService imageService;
	private MeasureService measureService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String images(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg, 
			@RequestParam(value = "resp", required = false) String resp) {
		List<Image> list = imageService.findAllTransactionless();
		
		model.addAttribute("title", "Gestione immagini");
		model.addAttribute("images", list);
		model.addAttribute("alertMsg", msg);
		model.addAttribute("typeMsg", resp);
		
		return "image/list";
	}
	
	@RequestMapping(value = "/getDetails/{id}", method = RequestMethod.GET)
	public String getDetails(Locale locale, Model model, @PathVariable("id") int id) {
		Image img = imageService.findById(id);
		model.addAttribute("name", img.getName());
		return "image/modal";
	}
	
	@RequestMapping(value = "/getMeasure/{id}", method = RequestMethod.GET)
	public String getMeasure(Locale locale, Model model, @PathVariable("id") int id) {
		Measure measure = measureService.findById(id);
		model.addAttribute("name", measure.getName());
		
		return "image/modal_measure";
	}
	
	
	@RequestMapping(value = "/getMap/{id}", method = RequestMethod.GET)
	public String getMap(Locale locale, Model model, @PathVariable("id") int id) {
		Image image = imageService.findById(id);
		String[] gps = image.getGPS().split(",", 2);
		String latitude = gps[0];
		String longitude = gps[1];
		model.addAttribute("name", image.getName());
		model.addAttribute("longitude", longitude);
		model.addAttribute("latitude", latitude);
		return "image/modal_map";
	}
	
	@RequestMapping(value = "/deleteData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String deleteData(Locale locale, Model model, @RequestParam("id") int id) {
		boolean resp = false;
		String msg = "";
		try {
			resp = imageService.delete(id);
			if(resp) {
				msg = "Image deleted successfully";
			}
			else msg = "The image is in the journal, is impossible to delete";
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@RequestMapping(value = "/measureImages", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String measureImages(Principal principal, Model uModel, @RequestParam("images") ArrayList<String> images, 
			@RequestParam(value = "measure", required = false) MultipartFile measure) {
		boolean resp = false;
		String msg = "Operation failed";
		try {
			if (measure != null) {
				//Controllo siano più di 5 immagini
				if (images.size() > 5) {
					//Controllo esistano le immagini
					List<Image> list = imageService.findImages(images);
					if (list != null && list.size() > 0) {
						//Controllo non esista la misura gia nel DB
						int id = measureService.saveMeasure(principal.getName(), measure);
						if (id > 0) {
							//Associo la misura alle immagini
							if (imageService.setMeasureImages(list, id)) {
								msg = "Images measured successfully";
								resp = true;
							} else {
								msg = "Could not be measured";
							}
						} else {
							msg = "The measure already exists";
						}
					} else {
						msg = "Some images were not found";
					}
				} else {
					msg = "Not enough images";
				}
			} else {
				msg = "No measure selected";
			}
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@Autowired
	public void setServices(ImageService imageService, MeasureService measureService) {
		this.imageService = imageService;
		this.measureService = measureService;
	}
}