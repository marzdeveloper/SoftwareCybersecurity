package com.sc.webim.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		model.addAttribute("title", "Gestione Immagini");
		
		List<Image> list = imageService.findAll();
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
			imageService.delete(id);
			msg = "Image deleted successfully";
			resp = true;
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	@RequestMapping(value = "/measureImages", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String measureImages(Locale locale, Model uModel, @RequestParam("images[]") String[] images) {
		boolean resp = false;
		String msg = "Operation failed";
		try {
			if (images.length >= 6) {
				List<Image> list = imageService.findImages(images);
				if (list != null && list.size() > 0) {
					if (imageService.measureImages(list)) {
						msg = "All images were measured";
						resp = true;
					} else {
						msg = "Could not be measured";
					}
				} else {
					msg = "Some images were not found";
				}
			} else {
				msg = "Not enough images";
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	
	/*@RequestMapping(value = "/showNew", method = RequestMethod.GET)
	public String showNew(Locale locale, Model model) {
		model.addAttribute("device", new Device());
		model.addAttribute("sender", senderService.findAll());
		return "dispositivi/modal_new";
	}
	
	@RequestMapping(value = "/showEdit/{id}", method = RequestMethod.GET)
	public String showEdit(Locale locale, Model model, @PathVariable("id") int id) {
		Device dev = deviceService.findById(id);
		model.addAttribute("device", dev);
		
		List<Sender> lista_send_all = senderService.findAll();
		List<String[]> lista_senders = new ArrayList<String[]>();
		for (int i = 0; i < lista_send_all.size(); i++) {
			String[] s = new String[3];
			s[0] = lista_send_all.get(i).getId() + "";
			s[1] = lista_send_all.get(i).getName();
			s[2] = "0";
			if (dev.getSender() != null && lista_send_all.get(i).getId() == dev.getSender().getId()) {
				s[2] = "1";
			}
			lista_senders.add(s);
		}
		model.addAttribute("senders", lista_senders);
		
		return "dispositivi/modal_edit";
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
	public @ResponseBody String saveDevice(Locale locale, Model uModel,
			@RequestParam("serialNumber") String serialNumber, @RequestParam("brand") String brand, @RequestParam("model") String model,
			@RequestParam("reason") String reason, @RequestParam("document") String document, @RequestParam("checkIn") String checkIn, 
			@RequestParam(value = "checkOut", required = false) String checkOut, 
			@RequestParam("sender") String sender,
			@RequestParam(value = "id", required = false) String id) {
		boolean resp = false;
		String msg = "Operation failed: one or more empty fields";
		try {
			if(serialNumber != null && !serialNumber.trim().isEmpty() && brand != null && !brand.trim().isEmpty() && 
					model != null && !model.trim().isEmpty() && document != null && !document.trim().isEmpty()) {
				Device dev = null;
				if (id != null && !id.trim().isEmpty()) {
					dev = deviceService.findById(Integer.parseInt(id.trim()));
					dev.setSerialNumber(serialNumber);
					dev.setBrand(brand);
					dev.setModel(model);
					dev.setReason(reason);
					dev.setDocument(document);
					dev.setCheckIn(Utils.date(checkIn.trim()));
					
					List<Job> list_jobs = jobService.findAllByDeviceId(dev.getId());
					dev.setJobs(new HashSet<>(list_jobs));
					
					List<Positioning> list_pos = positioningService.findAllByDeviceId(dev.getId());
					dev.setPositionings(new HashSet<>(list_pos));
					msg = "Changes saved successfully";
				} else {
					dev = deviceService.create(serialNumber, brand, model, reason, document, Utils.date(checkIn.trim()));
					Set<Job> listS = new HashSet<Job>();
					dev.setJobs(listS);
					
					Set<Positioning> listP = new HashSet<Positioning>();
					dev.setPositionings(listP);
					msg = "Device created successfully";
				}
				if(checkIn != null && !checkIn.trim().isEmpty()) {
					dev.setCheckIn(Utils.date(checkIn.trim()));
				}
				if(checkOut != null && !checkOut.trim().isEmpty()) {
					dev.setCheckOut(Utils.date(checkOut.trim()));
				}
				if(sender != null && !sender.trim().isEmpty()) {
					Sender s = senderService.findById(Integer.parseInt(sender.trim()));
					dev.setSender(s);
				}
				this.deviceService.update(dev);
				resp = true;
			} 
		} catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			msg = "An unexpected error occurred";
		}
		String response = "{\"success\":" + resp + ", \"msg\":\"" + msg + "\"}";
		return response;
	}
	*/
	
	@Autowired
	public void setServices(ImageService imageService, MeasureService measureService) {
		this.imageService = imageService;
		this.measureService = measureService;
	}
}