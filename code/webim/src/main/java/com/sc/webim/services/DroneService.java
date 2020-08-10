package com.sc.webim.services;

import java.io.IOException;
import java.util.List;

import org.apache.sanselan.ImageReadException;
import org.springframework.web.multipart.MultipartFile;

public interface DroneService {
	
	int saveImage(MultipartFile img);
	
	void metadataExample(String fileName) throws ImageReadException, IOException;
}
