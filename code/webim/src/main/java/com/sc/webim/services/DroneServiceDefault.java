package com.sc.webim.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service("droneService")
public class DroneServiceDefault implements DroneService {

	private final Path root = Paths.get("uploads");
	
	@Override
	public void saveImage(MultipartFile image) throws Exception {
		byte[] bytes = image.getBytes();
		Path path = Paths.get(root + "/" + image.getOriginalFilename());
		Files.write(path, bytes);
	};

}
