package com.sc.webim.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface DroneService {
	
	void saveImage(MultipartFile img) throws Exception;
}
