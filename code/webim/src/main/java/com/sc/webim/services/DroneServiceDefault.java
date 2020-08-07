package com.sc.webim.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.entities.Image;


@Transactional
@Service("droneService")
public class DroneServiceDefault implements DroneService {
	private final Path root = Paths.get("uploads");
	private ImageDao imageRepository;
	
	@Override
	public void saveImage(MultipartFile image, boolean rsp) {
		try {
			byte[] bytes = image.getBytes();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));  
			List <Image> Imagedb = this.imageRepository.findAll();					//risolvere null se non ci sono immagini
			
			for(Image im:Imagedb) {
				if(hash.equals(im.getImage_hash())){
					 throw new Exception();
				}
			}
			Image img = imageRepository.create("drone", null, hash,null); //prova
			imageRepository.update(img);
			Path path = Paths.get(root + "/" + image.getOriginalFilename());
			Files.write(path, bytes);
			rsp = true;
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		
	};
	
	@Autowired
	public void setImageRepository(ImageDao imageRepository) {
		this.imageRepository = imageRepository;
	}

}
